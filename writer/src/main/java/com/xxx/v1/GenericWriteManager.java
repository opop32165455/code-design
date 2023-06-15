package com.xxx.v1;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 写数据管理器
 * 缓冲数据 数据达到一定能量或者时间进行数据打包发送到任务分发器
 *
 * @author R4441-zxc
 * @date 2023/6/14 19:57
 */
@Slf4j
public class GenericWriteManager<T> implements AutoCloseable {
    private final List<T> buffer;
    private final String tableName;
    private final TaskDispatcher<T> writer;
    private int maxBufferSize;
    private int bufferTimeoutSec;
    private volatile boolean isClosed;
    private ScheduledThreadPoolExecutor service;


    public GenericWriteManager(Map<String, String> config, String tableName, String sourceType) {
        initManager(config);
        buffer = new ArrayList<>();
        this.tableName = tableName;
        writer = new TaskDispatcher<>(config, tableName, sourceType);
        isClosed = false;
    }

    private void initManager(Map<String, String> config) {
        //缓冲区初始化
        initBuffer(config);

        //初始化定时处理线程
        initScheduler();
    }


    /**
     * 定时处理线程 防止数据
     */
    private void initScheduler() {
        this.service = ThreadUtil.schedule(new ScheduledThreadPoolExecutor(1, ThreadUtil.createThreadFactory("flush-scheduler")),
                () -> tryAddToQueue(true),
                5,
                bufferTimeoutSec,
                TimeUnit.SECONDS,
                true
        );
    }

    private void tryAddToQueue(boolean isSchedule) {
        //log.info("start add to queue ...");
        if (flushCondition() || isSchedule) {
            addToQueue();
        }
    }

    private boolean flushCondition() {
        return buffer.size() > 0 && checkSize();
    }

    private boolean checkSize() {
        return buffer.size() >= this.maxBufferSize;
    }

    private void addToQueue() {
        List<T> deepCopy = Collections.unmodifiableList(new ArrayList<>(buffer));
        final DataBatchCache<T> batch = dataTidy(deepCopy);
        log.info("Build blank with batch: buffer size = {}, target table  = {}", batch.getBatch().size(), this.tableName);
        writer.put(batch);
        buffer.clear();
    }

    /**
     * 批数据整理
     * todo 可根据数据需求整理
     *
     * @param batch 数据批次
     * @return 打包后的数据
     */
    public DataBatchCache<T> dataTidy(List<T> batch) {
        return new DataBatchCache<>(batch);
    }


    public void put(T data) {
        //如果当前写入已经安全关闭 拒绝写入
        if (isOpen()) {
            tryAddToQueue(false);
            buffer.add(data);
        } else {
            throw new RuntimeException("write manager is closed can not put data");
        }
    }

    private void initBuffer(Map<String, String> config) {
        maxBufferSize = Integer.parseInt(config.getOrDefault("maxBufferSize", "10000"));
        bufferTimeoutSec = Integer.parseInt(config.getOrDefault("bufferTimeoutSec", "10"));
    }

    public boolean isOpen() {
        return !isClosed;
    }

    @Override
    public void close() {
        isClosed = true;
        //确保buffer数据被消费完成 到阻塞队列
        while (CollUtil.isNotEmpty(buffer)) {
            ThreadUtil.sleep(2 * 1000);
            log.info("it is cleaning the buffer and the buffer size {}", buffer.size());
        }

        //安全关闭定时消费缓存线程
        this.service.shutdown();
        //关闭消费完
        this.writer.close();

    }


}
