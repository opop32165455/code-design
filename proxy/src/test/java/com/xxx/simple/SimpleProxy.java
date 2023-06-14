package com.xxx.simple;

import com.xxx.bean.Class01;
import com.xxx.bean.ClassProxy;
import com.xxx.bean.Interface0;
import com.xxx.bean.VersionSelector;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author zhangxuecheng4441
 * @date 2022/1/17/017 19:44
 */
@Slf4j
public class SimpleProxy {

    public static void main(String[] args) {
        //代理对象 和原对象解耦 不需要修改原对象代码
        //其实这里使用继承也可以 但是个人觉得代理对象应该是在原对象更上层的封装 而不是继承部分能力
        //创建代理对象
        val classProxy = new ClassProxy();
        //选择代理的对象
        classProxy.setInterface0(new Class01());

        //执行被强化过的方法
        classProxy.method0("proxy-simple",1000);
        classProxy.method1();
    }
}