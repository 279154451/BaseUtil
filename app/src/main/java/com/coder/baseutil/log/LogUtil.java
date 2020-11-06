package com.coder.baseutil.log;

import android.util.Log;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：日志打印工具
 **/
public class LogUtil implements IProxyLog {
    private volatile static LogUtil util;
    private IProxyLog logProxy;
    private LogUtil(){

    }
    public static LogUtil getUtil(){
        if(util == null){
            synchronized (LogUtil.class){
                if(util == null){
                    util = new LogUtil();
                }
            }
        }
        return util;
    }
    public void initProxy(IProxyLog proxyLog){
        this.logProxy = proxyLog;
    }

    @Override
    public void d(String TAG, String message) {
        if(logProxy != null){
            logProxy.d(getTAG(TAG),message);
        }else {
            Log.d(getTAG(TAG),message);
        }
    }

    @Override
    public void i(String TAG, String message) {
        if(logProxy != null){
            logProxy.i(getTAG(TAG),message);
        }else {
            Log.i(getTAG(TAG),message);
        }
    }

    @Override
    public void w(String TAG, String message) {
        if(logProxy != null){
            logProxy.w(getTAG(TAG),message);
        }else {
            Log.w(getTAG(TAG),message);
        }
    }

    @Override
    public void e(String TAG, String message) {
        if(logProxy != null){
            logProxy.e(getTAG(TAG),message);
        }else {
            Log.e(getTAG(TAG),message);
        }
    }

    private String getTAG(String TAG){
        return TAG;
    }

    private static int originStackIndex = 2;

    /**
     * 获取当前方法所在的文件名
     *
     * @return 当前方法所在的文件名
     */
    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();
    }

    /**
     * 获取当前方法所在的Class名
     *
     * @return 当前方法所在的Class名
     */
    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
    }

    /**
     * 获取当前方法名
     *
     * @return 当前方法名
     */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
    }

    /**
     * 获取当前代码执行处行数
     *
     * @return 当前代码执行处行数
     */
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
    }
}
