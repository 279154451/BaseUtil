package com.coder.baseutil.network.api.throwable;



import com.coder.baseutil.log.LogUtil;

import io.reactivex.functions.Consumer;

/**
 * ========================================
 * <p>
 * 版 权：CZF
 * <p>
 * 作 者：CZF
 * <p>
 * 创建日期：2019/3/19  下午2:21
 * <p>
 * 描 述：
 * <p>
 * ========================================
 */
public abstract class BaseThrowable implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        onError(throwable);
    }

    public void onError(Throwable throwable) {
        ExceptionHandle.ResponeThrowable mResponeThrowable;
        if(throwable instanceof Exception) {
            mResponeThrowable = ExceptionHandle.handleException(throwable);
            int statusCode = mResponeThrowable.code;
            switch (statusCode){
                case ExceptionHandle.ERROR.SSL_ERROR:
                    break;
                case ExceptionHandle.ERROR.UNKNOWN:
                    break;
                case ExceptionHandle.ERROR.PARSE_ERROR:
                    break;
                case ExceptionHandle.ERROR.NETWORD_ERROR:
                    break;
                case ExceptionHandle.ERROR.HTTP_ERROR:
                    break;
                case ExceptionHandle.ERROR.SOCKET_TIMEOUT_ERROR:
                    break;
                case ExceptionHandle.ERROR.UNKNOW_HOSST_ERROR:
                    break;
            }
        } else {
            mResponeThrowable = new ExceptionHandle.ResponeThrowable(throwable,ExceptionHandle.ERROR.UNKNOWN);
        }
        LogUtil.getUtil().d("BaseThrowable",mResponeThrowable.message);
    }

}
