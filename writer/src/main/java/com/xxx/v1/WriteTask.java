package com.xxx.v1;

/**
 * @author R4441-zxc
 * @date 2023/6/15 16:00
 */
public interface WriteTask extends Runnable {

    /**
     * 任务安全停止
     */
    void stop();

}
