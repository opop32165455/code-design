package com.xxx.jdk;

import com.xxx.bean.Class01;
import com.xxx.bean.Class02;
import com.xxx.bean.Interface0;
import com.xxx.bean.VersionSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author zhangxuecheng4441
 * @date 2022/1/17/017 19:44
 */
@Slf4j
public class JdkProxy {

    public static void main(String[] args) {
        //为某个对象进行代理 对方法增强
        proxy4Class();

        log.warn("========================================================");
        //通过代理 获取接口信息 重写接口逻辑
        proxy2Reflect();

        log.warn("========================================================");
        //基于代理模式 实现多版本代理
        Interface0 v1Class = JdkProxy.create(Interface0.class, "v1");
        v1Class.method1();
        v1Class.method0("v1", 10);
        log.info("================");
        Interface0 v2Class = JdkProxy.create(Interface0.class, "v2");
        v2Class.method1();
        v2Class.method0("v2", 30);
    }

    /**
     * 根据版本生成代理类
     *
     * @param interfaceClass 执行逻辑接口
     * @param serviceVersion 代理版本
     * @param <T>            接口范形
     * @return 执行接口
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //利用Proxy获取执行的方法参数等 直接根据不通版本执行
                        return VersionSelector.handle(serviceVersion, method, args);
                    }
                });
    }


    private static void proxy4Class() {
        Interface0 class01 = new Class01();
        Interface0 interface0 = (Interface0) Proxy.newProxyInstance(class01.getClass().getClassLoader(), class01.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before invoke >>>>>");
                //确定被代理的类
                Object invoke = method.invoke(class01, args);
                System.out.println("after invoke >>>>>");

                return invoke;
            }
        });
        String result = interface0.method0("x", 2);
        System.out.println("result = " + result);
        interface0.method1();
    }

    private static void proxy2Reflect() {
        Interface0 interface0 = (Interface0) Proxy.newProxyInstance(Interface0.class.getClassLoader(), new Class[]{Interface0.class}, new InvocationHandler() {
            /**
             * @param proxy  代理对象
             * @param method 目标执行的方法
             * @param args   目标类执行参数
             * @return 代理执行输出结果
             * @throws Throwable 异常
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                System.out.println("name = " + name);
                System.out.println("args = " + Arrays.asList(args));

                Object result = "invoke result:" + name + " arg:" + Arrays.asList(args);
                System.out.println("finish invoke");
                return result;
            }
        });
        String name = interface0.method0("name", 1);
        System.out.println("name = " + name);
    }
}
