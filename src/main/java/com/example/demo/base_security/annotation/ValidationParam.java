package com.example.demo.base_security.annotation;

import java.lang.annotation.*;

/**
 * @author liugh
 * @since on 2018/5/9.
 */
@Target(ElementType.PARAMETER)          // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)     // 运行时有效
@Documented
public @interface ValidationParam {
    /**
     * 必填参数
     */
    String value() default "";
}
