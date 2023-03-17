package com.xxx.enumstrategy;


/**
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */

public class EnumStrategy2 implements EnumFactory.EnumHandler {

    @Override
    public void method1() {
       System.out.println("Enum策略方法2：AAAAAAAAAA");
    }

    @Override
    public void method2() {
       System.out.println("Enum策略方法2：BBBBBBBBBB");
    }

    @Override
    public void method3() {
       System.out.println("Enum策略方法2：CCCCCCCCCC");
    }

    @Override
    public void method4() {
       System.out.println("Enum策略方法2：DDDDDDDDDD");
    }

}
