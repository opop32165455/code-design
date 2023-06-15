package com.xxx.v1;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author R4441-zxc
 * @date 2023/6/15 14:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SinkField {
    /**
     * @return 在kafka中的字段名
     */
    String kafka() default "";

    /**
     * @return 在es 中的字段名
     */
    String elastic() default "";

    /**
     * @return 在clickhouse中的字段名
     */
    String clickhouse() default "";


}
