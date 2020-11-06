package com.coder.baseutil.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import androidx.fragment.app.Fragment;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：图片加载工具
 **/
public class ImageLoaderUtil {
    public static void loadImage(Activity activity, ImageView imageView, String imgUrl) {
        if(activity ==null || activity.isDestroyed()) return;
        Glide.with(activity).load(imgUrl).into(imageView);
    }

    public static void loadImage(Activity activity, ImageView imageView, int drawable) {
        if(activity ==null || activity.isDestroyed()) return;

        Glide.with(activity).load(drawable).into(imageView);
    }
    public static void loadImage(Context context, ImageView imageView, String imgUrl) {
        if(context == null) return;

        Glide.with(context).load(imgUrl).into(imageView);
    }

    public static void loadImage(Fragment fragment, ImageView imageView, String imgUrl) {
        if(fragment == null || fragment.isDetached()) return;

        Glide.with(fragment).load(imgUrl).into(imageView);
    }
    public static void loadImage(Fragment fragment, ImageView imageView, String imgUrl, int errDrawable) {
        if(fragment == null || fragment.isDetached()) return;
        Glide.with(fragment).load(imgUrl).error(errDrawable).into(imageView);
    }
    public static void loadImage(Context context, ImageView imageView, String imgUrl,int errDrawable) {
        if(context == null) return;
        Glide.with(context).load(imgUrl).error(errDrawable).into(imageView);
    }
    public static void loadImage(Fragment fragment, ImageView imageView, int drawable) {
        if(fragment == null || fragment.isDetached()) return;
        Glide.with(fragment).load(drawable).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, int drawable) {
        if(context == null) return;
        Glide.with(context).load(drawable).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, Drawable drawable) {
        if(context == null) return;
        Glide.with(context).load(drawable).into(imageView);
    }
    public static void loadImage(Context context, ImageView imageView, Bitmap drawable) {
        if(context == null) return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        drawable.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).into(imageView);
    }
}
