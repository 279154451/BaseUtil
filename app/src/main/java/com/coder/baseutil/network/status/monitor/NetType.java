package com.coder.baseutil.network.status.monitor;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：
 **/
public enum NetType {
    /**
     * 所有网络状态
     */
    AUTO,
    /**
     * 网络可访问外部服务
     */
    ENABLE,
    /**
     * 网络不可访问外部服务
     */
    ERROR,
    /**
     * 网络信号差
     */
    LOW,
    /**
     * WIFI网络
     */
    WIFI,
    /**
     * 手机上网
     */
    CMWAP,
    /**
     * 没有网络
     */
    NONE
}
