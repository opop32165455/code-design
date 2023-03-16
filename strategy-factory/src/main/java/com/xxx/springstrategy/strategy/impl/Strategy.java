package com.xxx.springstrategy.strategy.impl;


import com.xxx.springstrategy.Factory;
import com.xxx.springstrategy.StrategyAnno;
import com.xxx.springstrategy.StrategyHandler;
import org.springframework.stereotype.Component;

/**
 * @Desciption: 类似于servic的写法
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */
@Component
public class Strategy implements StrategyHandler {

    @Override
    @StrategyAnno("abc")
    public void method1() {
       System.out.println("策略方法1：AAAAAAAAAA");
    }

    @Override
    public void method2() {
       System.out.println("策略方法1：BBBBBBBBBB");
    }

    @Override
    public void method3() {
       System.out.println("策略方法1：CCCCCCCCCC");
    }

    @Override
    public void method4() {
       System.out.println("策略方法1：DDDDDDDDDD");
    }

    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("strategy1",this);
    }
}
