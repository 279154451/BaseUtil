package com.coder.baseutil.download;

import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;

import androidx.annotation.NonNull;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：
 **/
public class DownLoadTaskStruct {
    private BaseDownloadTask task;
    private String loadPath;
    private String webUrl;
    private IDownLoadCallBack callBack;

    public DownLoadTaskStruct(BaseDownloadTask task, @NonNull String loadPath, @NonNull String webUrl, IDownLoadCallBack callBack) {
        this.task = task;
        this.loadPath = loadPath;
        this.webUrl = webUrl;
        this.callBack = callBack;
    }
    public boolean cancel(){
        return task.cancel();
    }
    public boolean pause(){
       return task.pause();
    }
    public int start(){
        return task.start();
    }
    public boolean reuse(){
        return task.reuse();
    }

    /**
     * 是否为同一个任务
     * @param loadPath
     * @param webUrl
     * @return
     */
    public boolean isSameTask(String loadPath,String webUrl){
        if(TextUtils.equals(loadPath,this.loadPath) && TextUtils.equals(webUrl,this.webUrl)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断任务是否在执行
     * @param loadPath
     * @param webUrl
     * @return
     */
    public boolean isRunning(String loadPath,String webUrl){
        if(isSameTask(loadPath,webUrl)){
            return task.isRunning();
        }else {
            return false;
        }
    }

    public IDownLoadCallBack getCallBack() {
        return callBack;
    }
}
