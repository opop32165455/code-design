package com.xxx.springstrategy;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * @Desciption:
 * @author   ZhangXueCheng4441
 * @Date:2020/12/12/012 16:28
 */
public class Factory {

    /**
     * 服务启动时 就生成一个存放策略方法的map （会一直占据内存，但是调用确实很快）
     */
    private static final Map<String, Handler> STRATEGY_MAP = MapUtil.newHashMap();

    /**
     * @param str map-key 策略方法名字
     * @return map-value 对应名字的策略方法
     */
    public static Handler getInvokeStrategy(String str) {
        return STRATEGY_MAP.get(str);
    }

    /**
     * 把策略方法 注册到工厂里面
     * @param str map-key 策略方法名字
     * @param handler map-value 对应名字的策略方法
     */
    public static void register(String str, Handler handler) {
        if (StrUtil.isEmpty(str) || null == handler) {
            return;
        }
        STRATEGY_MAP.put(str, handler);
    }

}
