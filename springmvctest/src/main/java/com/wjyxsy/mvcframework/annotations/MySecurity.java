package com.wjyxsy.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author 继洋
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MySecurity {
    String [] value();
}
