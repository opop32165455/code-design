package com.xxx.jdk;

import com.xxx.bean.Interface0;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author zhangxuecheng4441
 * @date 2023/3/17/017 15:44
 */
public class JdkProxy {

    public static void main(String[] args) {

        Interface0 interface0 = (Interface0) Proxy.newProxyInstance(
                Interface0.class.getClassLoader(),
                new Class[]{Interface0.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = method.getName();
                        System.out.println("name = " + name);
                        System.out.println("args = " + Arrays.asList(args));

                        Object invoke = method.invoke(proxy);
                        System.out.println("proxy invoke >>> ");

                        return invoke;
                    }
                }
        );
        String name = interface0.method0("name", 1);
        System.out.println("name = " + name);
    }
}
