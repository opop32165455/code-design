package com.xxx.cglib;

import com.xxx.bean.Class01;
import com.xxx.bean.Interface0;
import com.xxx.bean.VersionSelector;
import com.xxx.jdk.JdkProxy;
import junit.runner.Version;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author R4441-zxc
 * @date 2022/1/26 17:05
 */
@Slf4j
public class CglibProxy {
    public static void main(String[] args) {

        //cglib 代码加强
        proxyEnhance();

        //基于代理模式 实现多版本代理
        Interface0 v1Class = CglibProxy.create(Interface0.class, "v1");
        v1Class.method1();
        v1Class.method0("v1", 10);
        log.info("================");
        Interface0 v2Class = CglibProxy.create(Interface0.class, "v2");
        v2Class.method1();
        v2Class.method0("v2", 30);

    }

    /**
     * 根版本创建代理类
     *
     * @param clazz   clazz
     * @param version version
     * @param <T>     clazz type
     * @return 代理类
     */
    @SuppressWarnings("unchecked")
    private static <T> T create(Class<T> clazz, String version) {
        Enhancer enhancer = new Enhancer();
        Object versionClass = VersionSelector.getVersionClass(version);
        enhancer.setSuperclass(versionClass.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * @param o: 代理对象
             * @param method: 被代理方法
             * @param objects: 方法入参
             * @param methodProxy: CGLIB方法
             **/
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("cglib proxy before >>>>>");
                return method.invoke(versionClass, objects);
            }
        });
        return (T) enhancer.create();
    }

    private static void proxyEnhance() {
        val class01 = new Class01();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Class01.class);
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * @param o: 代理对象
             * @param method: 被代理方法
             * @param objects: 方法入参
             * @param methodProxy: CGLIB方法
             **/
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("cglib proxy before >>>>>");
                return method.invoke(class01, objects);
            }
        });
        val cglibProxy = (Class01) enhancer.create();
        cglibProxy.method0("cglib", 10);
    }
}
