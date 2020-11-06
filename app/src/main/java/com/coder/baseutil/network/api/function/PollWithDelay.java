package com.coder.baseutil.network.api.function;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 创建时间：2020/10/15
 * 创建人：singleCode
 * 功能描述：RxJava轮询执行
 **/
public class PollWithDelay implements Function<Observable<Object>, Observable<?>> {
    private long delayTime  = 2000;//轮询间隔时间
    private boolean stopTry = false;

    public void setStopTry(boolean stopTry) {
        this.stopTry = stopTry;
    }

    public PollWithDelay(long delayTime) {
        this.delayTime = delayTime;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public boolean isStopTry() {
        return stopTry;
    }

    public PollWithDelay() {
    }

    @Override
    public Observable<?> apply(Observable<Object> objectObservable) throws Exception {
        // 发送1Next事件以继续轮询
        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
        if(stopTry){//停止轮询
            return Observable.error(new Throwable("轮询结束"));
        }
        return Observable.just(1).delay(delayTime, TimeUnit.MILLISECONDS);
    }
}
