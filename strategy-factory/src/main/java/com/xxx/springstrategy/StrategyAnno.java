package com.xxx.springstrategy;

import java.lang.annotation.*;

/**
 * @Desciption:
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 17:44
 */
@Retention(RetentionPolicy.RUNTIME)
//作用范围
@Target({ElementType.TYPE,ElementType.METHOD})
//生效到文档
@Documented
public @interface StrategyAnno {
    //todo 注解+aop也可以实现注册
    String value();
}
