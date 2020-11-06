package com.coder.baseutil.event;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * 创建时间：2020/10/28
 * 创建人：singleCode
 * 功能描述：自定义类如果想监听liveDataBus发送的消息可以继承该基类，或者实现LifecycleOwner接口并设置生命周期状态
 **/
public abstract class LiveOwner implements LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    public LiveOwner(){
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    protected void finalize() throws Throwable {
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.finalize();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
