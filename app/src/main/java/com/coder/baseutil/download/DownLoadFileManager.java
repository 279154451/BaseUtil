package com.coder.baseutil.download;

import android.app.Application;
import android.util.Log;

import com.coder.baseutil.base.StringUtils;
import com.coder.baseutil.log.LogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.NonNull;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：文件下载工具
 * 1、支持暂停
 * 2、支持断点续传
 * 3、支持多任务下载，可查看官方使用方法https://github.com/lingochamp/FileDownloader/blob/master/README-zh.md
 *
 **/
public class DownLoadFileManager implements IDownLoadCallBack {
    private String TAG = DownLoadFileManager.class.getSimpleName();
    private static volatile DownLoadFileManager manager;
    private Map<String , DownLoadTaskStruct> taskMap = new ConcurrentHashMap<>();
    private DownLoadFileManager(){

    }
    public static DownLoadFileManager getManager(){
        if(manager == null){
            synchronized (DownLoadFileManager.class){
                if(manager == null){
                    manager = new DownLoadFileManager();
                }
            }
        }
        return manager;
    }

    /**
     * 初始化FileDownloader
     * @param application
     */
    public void init(Application application){
        FileDownloader.setupOnApplicationOnCreate(application)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                )).commit();
    }


    /**
     *下载文件
     * @param loadPath  文件存放地址
     * @param webUrl  网络下载地址
     */
    public void downFile(String loadPath, String webUrl, IDownLoadCallBack callBack) {
        LogUtil.getUtil().e(TAG, "downFile: loadPath="+loadPath+" webUrl="+webUrl);
        if(StringUtils.isNullStr(webUrl)) return;
        if(taskMap.containsKey(loadPath)){
            LogUtil.getUtil().d(TAG, "downFile: task isExist "+loadPath);
            DownLoadTaskStruct task = taskMap.get(loadPath);
            if(task.isRunning(loadPath,webUrl)){
                return;
            }else {
                DownLoadTaskStruct taskStruct = createTask(loadPath,webUrl,callBack);
                taskStruct.start();
                taskMap.put(loadPath,taskStruct);
            }
        }else {
           DownLoadTaskStruct taskStruct = createTask(loadPath,webUrl,callBack);
            taskStruct.start();
            taskMap.put(loadPath,taskStruct);
        }
    }

    private DownLoadTaskStruct createTask(final String loadPath, String webUrl, IDownLoadCallBack callBack){
        BaseDownloadTask task = FileDownloader.getImpl().create(webUrl)
                .setPath(loadPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.getUtil().e(TAG, "pending: soFarBytes="+soFarBytes+" totalBytes="+totalBytes );
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        LogUtil.getUtil().e(TAG, "connected: soFarBytes="+soFarBytes+" totalBytes="+totalBytes );
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.getUtil().e(TAG, "progress: soFarBytes="+soFarBytes+" totalBytes="+totalBytes );
                        manager.progress(task,soFarBytes,totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        LogUtil.getUtil().e(TAG, "blockComplete: " );
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        LogUtil.getUtil().e(TAG, "retry: retryingTimes="+retryingTimes +" soFarBytes="+soFarBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.getUtil().e(TAG, "completed: " );
                        manager.completed(task);
                        taskMap.remove(task.getTargetFilePath());//任务完成移除task
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.getUtil().e(TAG, "paused: soFarBytes"+soFarBytes+" totalBytes="+totalBytes );
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.getUtil().e(TAG, "error: "+e );
                        manager.error(task,e);
                        taskMap.remove(task.getTargetFilePath());//任务完成移除task
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
//                        manager.error(task,new IOException("already same url & path in pending/running list"));
                    }
                });
        DownLoadTaskStruct taskStruct = new DownLoadTaskStruct(task,loadPath,webUrl,callBack);
        return taskStruct;
    }

    public DownLoadTaskStruct findDownLoadTask(@NonNull String loadPath){
        if(taskMap.containsKey(loadPath)){
            return taskMap.get(loadPath);
        }
        return null;
    }


    /**
     * 删除下载任务
     * @param loadPath
     */
    public void cancelTask(String loadPath){
        if(taskMap.containsKey(loadPath)){
            DownLoadTaskStruct task = taskMap.get(loadPath);
            task.cancel();
        }
    }

    /**
     * 暂停下载任务
     * @param loadPath
     */
    public void pauseTask(String loadPath){
        if(taskMap.containsKey(loadPath)){
            DownLoadTaskStruct task = taskMap.get(loadPath);
            task.pause();
        }
    }

    /**
     * 恢复下载
     * @param loadPath
     */
    public void restartTask(String loadPath){
        if(taskMap.containsKey(loadPath)){
            DownLoadTaskStruct task = taskMap.get(loadPath);
            task.start();
        }
    }

    @Override
    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.d(TAG, "progress: "+task.getTargetFilePath());
        DownLoadTaskStruct struct = findDownLoadTask(task.getTargetFilePath());
        if(struct!=null && struct.getCallBack()!=null){
            struct.getCallBack().progress(task,soFarBytes,totalBytes);
        }
    }

    @Override
    public void completed(BaseDownloadTask task) {
        LogUtil.getUtil().d(TAG, "completed: "+task.getTargetFilePath());
        DownLoadTaskStruct struct = findDownLoadTask(task.getTargetFilePath());
        if(struct!=null && struct.getCallBack()!=null){
            struct.getCallBack().completed(task);
        }
    }

    @Override
    public void error(BaseDownloadTask task, Throwable e) {
        Log.d(TAG, "error: "+task.getTargetFilePath());
        DownLoadTaskStruct struct = findDownLoadTask(task.getTargetFilePath());
        if(struct!=null && struct.getCallBack()!=null){
            struct.getCallBack().error(task,e);
        }
    }
}
