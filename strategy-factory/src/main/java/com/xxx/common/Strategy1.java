package com.xxx.common;


/**
 * @author ZhangXueCheng4441
 * @date 2020/12/12/012 15:16
 */
@StrategyAnno("s1")
public class Strategy1 implements MethodHandler {

    @Override
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

}
