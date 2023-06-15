package com.xxx;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.xxx.v1.GenericWriteManager;
import com.xxx.v1.DataBatchCache;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author R4441-zxc
 * @date 2023/6/14 19:51
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info("start test...");
        var writeManager = new GenericWriteManager<String>(new HashMap<>(), "test01", "print") {
            @Override
            public DataBatchCache<String> dataTidy(List<String> batch) {
                //数据整理
                return new DataBatchCache<>(batch);
            }
        };

        //模拟数据写入
        IntStream.rangeClosed(0, 50)
                .forEach(i -> {
                    ThreadUtil.sleep(400);
                    //模拟数据不均匀速度
                    if (i % RandomUtil.randomInt(4, 7) == 3) {
                        ThreadUtil.sleep(RandomUtil.randomInt(1, 3) * 1000L);
                    }
                    writeManager.put(String.valueOf(i));
                    log.info("put>>>{}", i);
                });


        close(writeManager);
    }

    private static void close(GenericWriteManager<String> writeManager) {
        if (writeManager.isOpen()) {
            synchronized (Test.class) {
                if (writeManager.isOpen()) {
                    writeManager.close();
                }
            }
        }
    }
}
