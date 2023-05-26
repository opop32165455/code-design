package com.xxx.bean;

import org.springframework.stereotype.Component;

/**
 * @author zhangxuecheng4441
 * @date 2023/3/17/017 15:48
 */
@Component(value = "class02")
public class Class02 implements Interface0{
    @Override
    public String method0(String name, Integer age) {
        System.out.println(">>>>class02 method 0 name:" + name + " age:" + age);
        return "Class02";
    }

    @Override
    public void method1() {
        System.out.println(">>>>class02 method 1");
    }
}
