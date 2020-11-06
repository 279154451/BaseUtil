package com.coder.baseutil.network.api.function;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 创建时间：2020/10/15
 * 创建人：singleCode
 * 功能描述：RxJava出错重试
 **/

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {

    private int maxRetries = 5;//最大出错重试次数
    private int retryDelayMillis = 1000;//重试间隔时间
    private int retryCount = 0;//当前出错重试次数
    private PollWithDelay pollWithDelay;
    public RetryWithDelay() {
    }

    public RetryWithDelay(PollWithDelay pollWithDelay) {
        this.pollWithDelay = pollWithDelay;
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if(pollWithDelay!=null){
                            if(pollWithDelay.isStopTry()){
                                return Observable.error(throwable);
                            }else {
                                return Observable.timer(pollWithDelay.getDelayTime(), TimeUnit.MILLISECONDS);
                            }
                        }else {
                            if (++retryCount <= maxRetries) {
                                Log.d("RetryWithDelay","get error, it will try after " + retryDelayMillis * retryCount
                                        + " millisecond, retry count " + retryCount);
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                return Observable.timer(retryDelayMillis * retryCount, TimeUnit.MILLISECONDS);
                            }
                            return Observable.error(throwable);
                        }
                    }
                });
    }
}