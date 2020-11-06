package com.coder.baseutil.network.api;



import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 *     Retrofit创建管理结合RxManager：实现RxJava+Retrofit+OkHttp网络请求
 */

public class RetrofitManager {
    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;
    public static OkHttpClient createOkHttp(){
        return createOkHttp(null,null);
    }
    public static OkHttpClient createOkHttp(Interceptor interceptor){
        return createOkHttp(interceptor,null);
    }
    public  static OkHttpClient createOkHttp(Interceptor interceptor, HttpLoggingInterceptor.Level level){
        OkHttpClient okHttpClient = null;
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //SSL证书
//            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
//            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                //打印日志
                if(level != null){
                    builder.addInterceptor(new HttpLoggingInterceptor().setLevel(level));
                }else {
                    builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                }
                if(interceptor != null){
                    builder.addInterceptor(interceptor);//添加头
                }
                builder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true);
                okHttpClient = builder.build();
                return okHttpClient;
    }
    public static <T> T createApi(Class<T> clazz, OkHttpClient okHttpClient,String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}

