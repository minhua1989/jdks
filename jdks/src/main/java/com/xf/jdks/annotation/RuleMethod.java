package com.xf.jdks.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Rio-lee on 2016/6/10.
 * 规则方法注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleMethod {
    int ruletype()default 0;
    String ruledesc();

}
