package com.xxx.enumstrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举的方式注册方法到工厂 枚举天然生成的单例
 * 便于浏览 注释 和查看
 *
 * @author zhangxuecheng4441
 * @date 2022/3/17/017 9:38
 */
@Getter
@AllArgsConstructor
public enum EnumFactory {
    /**
     * 注册策略方法
     */
    Strategy1("strategy1", "策略方法1", new EnumStrategy1()),
    Strategy2("strategy2", "策略方法2", new EnumStrategy2());

    final String name;
    final String desc;
    final EnumHandler enumHandler;

    public static EnumHandler getInvokeStrategy(String name) {
        for (EnumFactory factory : EnumFactory.values()) {
            if (factory.name.equals(name)) {
                return factory.enumHandler;
            }
        }
        //默认
        return Strategy1.enumHandler;
    }

    public interface EnumHandler {
        /**
         * method1
         */
        void method1();

        /**
         * method2
         */
        void method2();

        /**
         * method3
         */
        void method3();

        /**
         * method4
         */
        void method4();
    }
}
