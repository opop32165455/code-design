package com.xxx.springstrategy.strategy.impl;

import com.xxx.springstrategy.Factory;
import com.xxx.springstrategy.StrategyHandler;
import org.springframework.stereotype.Component;

/**
 * @Desciption: 类似于servic的写法
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */
@Component
public class Strategy1 implements StrategyHandler {

    @Override
    public void method1() {
       System.out.println("策略方法2：AAAAAAAAAA");
    }

    @Override
    public void method2() {
       System.out.println("策略方法2：BBBBBBBBBB");
    }

    @Override
    public void method3() {
       System.out.println("策略方法2：CCCCCCCCCCC");
    }

    @Override
    public void method4() {
       System.out.println("策略方法2：DDDDDDDDDDD");
    }
    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("strategy2",this);
    }
}
