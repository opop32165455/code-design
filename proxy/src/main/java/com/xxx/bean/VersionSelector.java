package com.xxx.bean;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author R4441-zxc
 * @date 2022/1/26 17:24
 */
public class VersionSelector {
    private static final HashMap<String, Object> CLASS_MAP = new HashMap<String, Object>() {
        {
            //spring直接Bean注册 这里直接代码块注册
            put("v1", new Class01());
            put("v2", new Class02());
        }
    };

    /**
     * 根据版本 选择执行对象 并执行方法返回
     *
     * @param version 版本
     * @param method  执行方法
     * @param args    执行参数
     * @return 返回结果
     * @throws InvocationTargetException 异常
     */
    public static Object handle(String version, Method method, Object[] args) throws InvocationTargetException {
        Object obj = getVersionClass(version);
        //jdk 反射执行方法 需要指定参数比较麻烦
        //cglib 反射 执行
        FastClass fastClass = FastClass.create(obj.getClass());
        FastMethod fastMethod = fastClass.getMethod(method);
        return fastMethod.invoke(obj, args);
    }

    /**
     * get class form map by version
     * @param version version
     * @return obj
     */
    public static Object getVersionClass(String version) {
        Object obj = CLASS_MAP.get(version);
        if (obj == null) {
            throw new RuntimeException("class map can not find this version");
        }
        return obj;
    }
}

