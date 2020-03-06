package com.my.aspect.annotion;

import java.lang.annotation.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/3/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String value() default "";
}
