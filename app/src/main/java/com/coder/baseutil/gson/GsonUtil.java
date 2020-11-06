package com.coder.baseutil.gson;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：gson转换工具
 **/
public class GsonUtil {

    public static String object2Json(Object obj, Type typetoken) {
        Gson gson = new Gson();
        return gson.toJson(obj, typetoken);
    }

    public static <T> T json2Object(String json, Type typetoken) {
        Gson gson = new Gson();
        return gson.fromJson(json, typetoken);
    }
}
