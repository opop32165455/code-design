package com.xxx.bean;

import com.xxx.anno.AopAnno;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuecheng4441
 * @date 2023/3/17/017 15:48
 */
@Component(value = "class01")
public class Class01 implements Interface0 {
    @Override
    @AopAnno
    public String method0(String name, Integer age) {
        System.out.println(">>>>class01 method 0 name:" + name + " age:" + age);
        return "Class01";
    }

    @Override
    public void method1() {
        System.out.println(">>>>class01 method 1");
    }
}
