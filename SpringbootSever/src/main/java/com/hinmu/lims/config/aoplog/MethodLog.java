package com.hinmu.lims.config.aoplog;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    /**
     * 该注解作用于方法上时需要备注信息
     */
    String remarks() default "";
}
