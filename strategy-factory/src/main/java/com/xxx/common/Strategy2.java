package com.xxx.common;


/**
 * @author ZhangXueCheng4441
 * @date 2020/12/12/012 15:16
 */
@StrategyAnno("s2")
public class Strategy2 implements MethodHandler {

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
        System.out.println("策略方法2：CCCCCCCCCC");
    }

    @Override
    public void method4() {
        System.out.println("策略方法2：DDDDDDDDDD");
    }

}
