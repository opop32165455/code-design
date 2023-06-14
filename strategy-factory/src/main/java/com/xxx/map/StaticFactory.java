package com.xxx.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.xxx.common.MethodHandler;
import com.xxx.common.Strategy1;
import com.xxx.common.Strategy2;
import com.xxx.common.StrategyAnno;
import lombok.val;

import javax.xml.stream.events.StartDocument;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author R4441-zxc
 * @date 2023/6/14 14:47
 */
public class StaticFactory {
    /**
     * 服务启动时 就生成一个存放策略方法的map （会一直占据内存，但是调用确实很快）
     */
    private static final Map<String, MethodHandler> STRATEGY_MAP = new HashMap<String, MethodHandler>() {
        //初始化注册
        {
            put("strategy1", new Strategy1());
        }
    };


    //静态代码块注册
    static {
        //也可以读取JDBC获取配置
        //Spring代码块需要使用@PostConstruct
        STRATEGY_MAP.put("strategy2", new Strategy2());

        val packagePath = "com.xxx.common";
        Set<Class<?>> classes = ClassUtil.scanPackage(packagePath);
        if (CollUtil.isNotEmpty(classes)) {
            classes.forEach(clazz -> {
                //非普通类 过滤
                if (!ClassUtil.isNormalClass(clazz)) {
                    return;
                }
                StrategyAnno annotation = clazz.getAnnotation(StrategyAnno.class);

                //类上没有策略注解过滤
                if (annotation == null) {
                    return;
                }

                //value为策略标记
                String strategyName = annotation.value();
                try {
                    Object obj = clazz.newInstance();

                    //实例注册
                    if (obj instanceof MethodHandler) {
                        STRATEGY_MAP.put(strategyName, (MethodHandler) obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }
    }


    public static MethodHandler getHandler(String name) {
        MethodHandler methodHandler = STRATEGY_MAP.get(name);

        if (methodHandler != null) {
            return methodHandler;
        } else {
            throw new RuntimeException("can not find strategy");
        }
    }
}
