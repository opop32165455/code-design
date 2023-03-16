package com.xxx.springstrategy;


/**
 * @Desciption: 策略模式实现方式   InitializingBean（初始化bean的时候都会执行该接口的afterPropertiesSet方法）
 *              比 @Bean (initMethod = "方法")初始化方法执行更早
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 15:11
 */
public interface StrategyHandler extends Handler {
   //可以写上你特有的方法哟 但是工厂那边会无法调用
}
