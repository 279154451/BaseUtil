package com.coder.baseutil.network.status.monitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间：2020/10/26
 * 创建人：singleCode
 * 功能描述：
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NetworkStatus {
    NetType netType() default NetType.AUTO;
}
