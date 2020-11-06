package com.coder.baseutil.download;


import com.liulishuo.filedownloader.BaseDownloadTask;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：
 **/
public interface IDownLoadCallBack {
    /**
     * 下载进度
     * @param task
     * @param soFarBytes
     * @param totalBytes
     */
    void progress(BaseDownloadTask task, int soFarBytes, int totalBytes);

    /**
     * 下载完成
     * @param task
     */
    void completed(BaseDownloadTask task);

    /**
     * 下载失败
     * @param task
     * @param e
     */
    void error(BaseDownloadTask task, Throwable e);
}
