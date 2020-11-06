# BaseUtil
工作中常用的工具类封装

1、数据缓存工具封装
（1）、SpUtil
    基于SharedPreferences缓存数据
​   支持：基础数据类型存取用put、get
         Object类型存取用putObject、getObject
    如果有如下缓存需求：可以使用ACache缓存框架
      * 1、指定缓存数据大小
      * 2、限制缓存时间，缓存超时时间，缓存超时自动失效
      * 3、缓存bitmap
      * 4、缓存byte数组，二进制数据
      **/
（2）、AChace
    1：轻，轻到只有一个JAVA文件。
    2：可配置，可以配置缓存路径，缓存大小，缓存数量等。
    3：可以设置缓存超时时间，缓存超时自动失效，并被删除。
    4：多进程的支持。
    用法例子：
     * // ACache mCache = ACache.get(this); // 初始化，一般放在基类里
     * // mCache.put("test_key1","test value");
     * // mCache.put("test_key2", "test value", 10);// 保存10秒，如果超过10秒去获取这个key，将为null
     * // mCache.put("test_key3", "test value", 2 ACache.TIME_DAY);// 保存两天，如果超过两天去获取这个key，将为null
     * // String value = mCache.getAsString("test_key1");// 获取数据
（3）、MMKVUtil
    腾讯MMKV缓存框架
     * 1、轻量、存储速度快
     * 2、支持多进程访问：进程A修改值后，进程B读取的是进程A修改后的值
2、文件下载工具封装
    * 1、支持暂停
    * 2、支持断点续传
    * 3、支持多任务下载，
    可查看官方使用方法https://github.com/lingochamp/FileDownloader/blob/master/README-zh.md

3、组件间通信框架封装
（1）、EventBusHelper
    基于EventBus，做到全局EventBus统一调用。
    推荐使用LiveDataBus
 (2)、LiveDataBusHelper
    基于LiveDataBus消息总线封装

4、媒体播放工具封装
    MediaPlayerHelper媒体播放工具
     * 1、支持多任务播放
     * 2、支持暂停、继续
     * 3、运行在独立线程，有效避免ANR
5、网络请求框架封装
    RxManager+RetrofitManager = RxJava + Retrofit 封装
    让网络请求代码更简洁

6、网络状态监听框架封装
    NetWorkMonitorManager全局监听网络状态框架
     使用方式：与EventBus类似
     在需要监听网络状态的界面执行如下操作
      * 1、调用register
      * 2、通过NetworkStatus注解定义监听函数
      * @NetworkStatus(netType = NetType.AUTO)
      * public void netWorkStatus(NetType type){
      * Log.d(TAG, "netWorkStatus: "+type.name());
      *     }
      * 3、界面结束时unregister

混淆代码：
#liveDataBus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class android.arch.lifecycle.** { *; }
-keep class android.arch.core.** { *; }
# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

#Okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

#okio
-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#google
-dontwarn com.google.**
-keep class com.google.** { *; }
-keep class io.reactivex.** { *; }
-keep class org.reactivestreams.** { *; }

#glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#如果你的 target API 低于 Android API 27，请添加：
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

