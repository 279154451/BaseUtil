package com.coder.baseutil.network.status.monitor;

import java.lang.reflect.Method;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：保存符合要求的网络监听注解方法，封装类
 **/
public class NetSubMethod {
    /**
     * 参数类型 NetType
     */
    private Class<?> type;
    /**
     * 方法名
     */
    private Method method;
    /**
     * 网络类型
     */
    private NetType netType;

    public NetSubMethod(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.method = method;
        this.netType = netType;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }
}
