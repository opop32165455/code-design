package com.xxx;

import cn.hutool.core.thread.ThreadUtil;
import com.xxx.v1.WriteManager;
import lombok.var;

import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * @author R4441-zxc
 * @date 2023/6/14 19:51
 */
public class Test {
    public static void main(String[] args) {
        var writeManager = new WriteManager<String>(new HashMap<>(), "test01");
        IntStream.rangeClosed(0, 100)
                .forEach(i -> {
                    ThreadUtil.sleep(200);
                    writeManager.put(String.valueOf(i));
                });


        if (writeManager.isManagerClose()) {
            synchronized (Test.class) {
                if (writeManager.isManagerClose()) {
                    writeManager.close();
                }
            }
        }
    }
}
