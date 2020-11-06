package com.coder.baseutil.network.api;




import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 * 用于管理Rxjava 注册订阅和取消订阅
 */

public class RxManager<T> {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者者

    public void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    public void unSubscribe() {
        mCompositeDisposable.dispose();// 取消订阅
    }

    public void clearSubscribe() {
        mCompositeDisposable.clear();// 取消订阅
    }




    /**
     * 统一线程处理
     * <p>
     * 发布事件io线程，接收事件主线程
     */
    public static <K> ObservableTransformer<K, K> rxSchedulerHelper() {//compose处理线程
        return new ObservableTransformer<K, K>() {

            @Override
            public ObservableSource<K> apply(Observable<K> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 生成Flowable
     *
     * @param t
     * @return Flowable
     */
//    public static <T> Flowable<T> createFlowable(final T t) {
//        return Flowable.create(new FlowableOnSubscribe<T>() {
//            @Override
//            public void subscribe(FlowableEmitter<T> e) throws Exception {
//
//            }
//        },BackpressureStrategy.BUFFER);
//    }

    /**
     * 生成Observable
     *
     * @param t
     * @return Flowable
     */
    public static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public static <T> Observable<T> createObservable(final ObservableRunnable<T> runnable){
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    T object = runnable.onRun();
                    emitter.onNext(object);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

    }

    public interface ObservableRunnable<T> {
        T onRun();
    }
}