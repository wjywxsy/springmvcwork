package com.wjyxsy.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author 继洋
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyController {
    String value() default "";
}
