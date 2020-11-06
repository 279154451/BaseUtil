package com.coder.baseutil.network.status.monitor;

import android.app.Application;

import com.coder.baseutil.log.LogUtil;
import com.coder.baseutil.network.status.utils.NetWorkUtil;
import com.coder.baseutil.network.status.utils.PhoneUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：全局监听网络状态框架
 * 在需要监听网络状态的界面执行如下操作
 * 1、调用register
 * 2、 定义监听函数
 * @NetworkStatus(netType = NetType.AUTO)
 *     public void netWorkStatus(NetType type){
 *         Log.d(TAG, "netWorkStatus: "+type.name());
 *     }
 *3、界面结束时unregister
 **/
public class NetWorkMonitorManager implements OnNetworkStateListener {
    private NetType netType;
    private NetworkMonitor monitor;
    private Application application;
    private Map<Object, List<NetSubMethod>> networkSubMap;
    private OnNetworkStateListener listener;
    private static volatile NetWorkMonitorManager manager;
    private NetWorkMonitorManager(){
        this.netType = NetType.NONE;
        networkSubMap = new HashMap<>();
    }
    public static NetWorkMonitorManager getInstance(){
        if(manager == null){
            synchronized (NetWorkMonitorManager.class){
                if(manager == null){
                    manager = new NetWorkMonitorManager();
                }
            }
        }
        return manager;
    }

    public void init(Application application){
        init(application,null);
    }
    public void init(@NonNull Application application, OnNetworkStateListener listener){
        this.application =application;
        if(listener != null){
            this.listener = listener;
        }
        if (monitor == null) {
            monitor = new NetworkMonitor(application);
        }
        monitor.openServerCheck("www.baidu.com");
        monitor.registerOnNetworkStateListener(this);
    }

    private Application getApplication() {
        return application;
    }

    /**
     * 同时分发出去
     *
     * @param netType
     */
    private void post(NetType netType) {
        Set<Object> set = networkSubMap.keySet();
        for (final Object getter : set) {
            //所有注解方法
            List<NetSubMethod> methodList = networkSubMap.get(getter);
            if (methodList != null) {
                //循环每个方法
                for (NetSubMethod netSubMethod : methodList) {
                    if (netSubMethod.getType().isAssignableFrom(netType.getClass())) {
                        switch (netSubMethod.getNetType()) {
                            case AUTO:
                                invoke(netSubMethod,getter,netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE){
                                    invoke(netSubMethod,getter,netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE){
                                    invoke(netSubMethod,getter,netType);
                                }
                                break;
                            case LOW:
                                if(netType == NetType.LOW || netType == NetType.NONE){
                                    invoke(netSubMethod,getter,netType);
                                }
                                break;
                            case ENABLE:
                                if(netType == NetType.ENABLE || netType == NetType.NONE){
                                    invoke(netSubMethod,getter,netType);
                                }
                                break;
                            case ERROR:
                                if(netType == NetType.ERROR || netType == NetType.NONE){
                                    invoke(netSubMethod,getter,netType);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(NetSubMethod netSubMethod, Object getter, NetType netType) {
        Method execute = netSubMethod.getMethod();
        try {
            execute.invoke(getter,netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void register(Object object) {
        //获取注册者的所有的网络监听注解方法
        List<NetSubMethod> managerList = networkSubMap.get(object);
        if (managerList == null) {
            //开始添加方法
            managerList = findAnnotationMethod(object);
            networkSubMap.put(object, managerList);
        }
    }

    private List<NetSubMethod> findAnnotationMethod(Object object) {
        List<NetSubMethod> subMethods = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz!=null){
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("android.") || name.startsWith("javax.")){
                break;
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                NetworkStatus networkStatus = method.getAnnotation(NetworkStatus.class);
                if (networkStatus == null) {
                    continue;
                }

                //方法返回校验
                Type returnType = method.getGenericReturnType();
                if (!"void".equals(returnType.toString())) {
                    throw new RuntimeException(method.getName() + "方法返回必须是void类型");
                }

                //参数校验
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException(method.getName() + "方法只能有一个参数");
                }

                //过滤上面，得到符合要求的方法，才开始添加到集合
                NetSubMethod subMethod = new NetSubMethod(parameterTypes[0], networkStatus.netType(), method);
                subMethods.add(subMethod);

            }
            clazz = clazz.getSuperclass();
        }
        return subMethods;
    }

    /**
     * 解注册
     * @param object
     */
    public void unRegister(Object object) {
        if (!networkSubMap.isEmpty()) {
            networkSubMap.remove(object);
        }
        LogUtil.getUtil().e(Constants.LOG_TAG, object.getClass().getName() + "解注册成功");
    }

    /**
     * 注销monitor
     * @return
     */
    private boolean unRegisterMonitor(){
        if(networkSubMap.isEmpty()){
            LogUtil.getUtil().d(Constants.LOG_TAG, "unRegisterMonitor");
            monitor.unRegisterOnNetworkStateListener();
            return true;
        }
        return false;
    }

    /**
     * 全部解注册
     */
    public void unRegisterAll() {
        if (!networkSubMap.isEmpty()) {
            networkSubMap.clear();
        }
        unRegisterMonitor();
        networkSubMap = null;
        LogUtil.getUtil().e(Constants.LOG_TAG, "全部解注册成功");
    }

    @Override
    public void onServerAvailable(int networkType, boolean available) {
        if(listener != null){
            listener.onServerAvailable(networkType,available);
        }
        if (!available) {
            LogUtil.getUtil().d(Constants.LOG_TAG, "onServerAvailable: 当前服务器无法正常访问");
            post(NetType.ERROR);
        } else {
            LogUtil.getUtil().d(Constants.LOG_TAG,"onServerAvailable: 当前服务器正常访问");
            post(NetType.ENABLE);
        }
    }

    @Override
    public void onMobileSignalStrengthsChanged(PhoneSignalHandler.SimCard simCard, int level) {
       if(listener != null){
           listener.onMobileSignalStrengthsChanged(simCard,level);
       }
        if (NetWorkUtil.isLowLevel(level)) {
            LogUtil.getUtil().d(Constants.LOG_TAG, "onMobileSignalStrengthsChanged: 当前移动网络情况不佳");
            post(NetType.LOW);
        }
    }

    @Override
    public void onWifiSignalStrengthsChanged(int level) {
        if(listener != null){
            listener.onWifiSignalStrengthsChanged(level);
        }
        //信号弱
        if (NetWorkUtil.isLowLevel(level)) {
            if (PhoneUtil.hasSimCard(getApplication())) {
                LogUtil.getUtil().d(Constants.LOG_TAG,  "onWifiSignalStrengthsChanged: 当前wifi网络情况不佳，请切换到移动数据网络");
            } else {
                LogUtil.getUtil().d(Constants.LOG_TAG, "onWifiSignalStrengthsChanged: 当前wifi网络情况不佳");
            }
            post(NetType.LOW);
        }
    }

    @Override
    public void onMobileDataConnectivity(boolean isSim1Exist, boolean isSim2Exist) {
        LogUtil.getUtil().d(Constants.LOG_TAG, "onMobileDataConnectivity: 手机数据网络连接");
        if(listener != null){
            listener.onMobileDataConnectivity(isSim1Exist,isSim2Exist);
        }
        post(NetType.CMWAP);
    }

    @Override
    public void onWifiConnectivity() {
        LogUtil.getUtil().d(Constants.LOG_TAG, "onWifiConnectivity: wifi连接成功");
        if(listener != null){
            listener.onWifiConnectivity();
        }
        post(NetType.WIFI);
    }

    @Override
    public void onDisconnection() {
        LogUtil.getUtil().d(Constants.LOG_TAG, "onDisconnection: 无网络");
        if(listener != null){
            listener.onDisconnection();
        }
        post(NetType.NONE);
    }
}
