package com.wjyxsy.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author 继洋
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowired {
    String value() default "";
}
