package com.xxx.mq.config;

import lombok.Data;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 9:53
 */
@Data
public class InitConfig<T1> {

    Collection<T1> collection;

    /**
     * 可以更换为kafka等其他队列
     */
    public InitConfig() {
        this.collection = new LinkedBlockingDeque<>();
    }
}
