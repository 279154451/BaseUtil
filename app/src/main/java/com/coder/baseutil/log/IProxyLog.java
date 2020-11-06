package com.coder.baseutil.log;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：日志代理接口
 **/
public interface IProxyLog {
    void d(String TAG, String message);
    void i(String TAG, String message);
    void w(String TAG, String message);
    void e(String TAG, String message);
}
