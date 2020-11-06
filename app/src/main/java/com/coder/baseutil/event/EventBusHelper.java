package com.coder.baseutil.event;

import de.greenrobot.event.EventBus;

/**
 * 创建时间：2020/10/27
 * 创建人：singleCode
 * 功能描述：基于EventBus，推荐使用LiveDataBus
 **/
public class EventBusHelper {

    public static void register(Object object){
        if(!isRegistered(object)){
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object){
        if(!isRegistered(object)){
            EventBus.getDefault().unregister(object);
        }
    }
    public void post(Object object){
        EventBus.getDefault().post(object);
    }
    public static void postSticky(Object object){
        EventBus.getDefault().postSticky(object);
    }
    public static boolean isRegistered(Object object){
        return EventBus.getDefault().isRegistered(object);
    }

}
