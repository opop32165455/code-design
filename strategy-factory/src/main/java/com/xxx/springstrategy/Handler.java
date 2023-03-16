package com.xxx.springstrategy;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Desciption:
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 16:32
 */
public interface Handler extends InitializingBean {
    /**
     * method1
     */
    void method1();

    /**
     * method2
     */
    void method2();

    /**
     * method3
     */
    void method3();

    /**
     * method4
     */
    void method4();
}
