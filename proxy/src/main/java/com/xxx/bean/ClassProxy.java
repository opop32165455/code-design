package com.xxx.bean;

import lombok.Data;

/**
 * @author R4441-zxc
 * @date 2023/6/14 18:54
 */
@Data
public class ClassProxy implements Interface0 {
    private Interface0 interface0;


    @Override
    public String method0(String name, Integer age) {
        System.out.println("start simple proxy method0");
        return interface0.method0(name, age);
    }

    @Override
    public void method1() {
        System.out.println("start simple proxy method1");
        interface0.method1();
    }
}
