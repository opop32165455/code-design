package com.xxx.v1;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author R4441-zxc
 * @date 2023/6/14 19:57
 */
@Slf4j
public class WriteManager<T> implements AutoCloseable {
    private final List<T> values;
    private final String tableName;
    private final DataWriter<T> writer;
    private final WriterConfig writerConfig;
    private int maxBufferSize;
    private int bufferTimeoutSec;
    private volatile boolean isClosed;
    private ScheduledThreadPoolExecutor service;


    public WriteManager(Map<String, String> config, String tableName) {
        initManager(config);
        values = new ArrayList<>();
        this.tableName = tableName;
        writerConfig = new WriterConfig();
        writer = new DataWriter<>(writerConfig, tableName);
        isClosed = false;
    }

    private void initManager(Map<String, String> config) {
        //todo 配置转换


        // 缓冲区
        initBuffer(config);

        //
        initQueueCleaner();
    }

    private void initQueueCleaner() {

        this.service = ThreadUtil.schedule(new ScheduledThreadPoolExecutor(1, ThreadUtil.createThreadFactory("flush-scheduler")),
                () -> tryAddToQueue(true),
                5,
                bufferTimeoutSec,
                TimeUnit.SECONDS,
                true
        );
    }

    private void tryAddToQueue(boolean isSchedule) {
        log.info("start add to queue ...");
        if (flushCondition() || isSchedule) {
            addToQueue();
        }
    }

    private boolean flushCondition() {
        return values.size() > 0 && checkSize();
    }

    private boolean checkSize() {
        return values.size() >= this.maxBufferSize;
    }

    private void addToQueue() {
        List<T> deepCopy = Collections.unmodifiableList(new ArrayList<>(values));
        val request = new DataBatchRequest<>(deepCopy);

        log.info("Build blank with request: buffer size = {}, target table  = {}", request.getRequests().size(), this.tableName);
        writer.put(request);
        values.clear();
    }


    public void put(T data) {
        tryAddToQueue(false);
        values.add(data);
    }

    private void initBuffer(Map<String, String> config) {
        maxBufferSize = Integer.parseInt(config.getOrDefault("maxBufferSize", "10000"));
        bufferTimeoutSec = Integer.parseInt(config.getOrDefault("bufferTimeoutSec", "10"));
    }

    public boolean isManagerClose() {
        return !isClosed;
    }

    @Override
    public void close() {
        this.writer.close();
        this.service.shutdown();
        isClosed = true;
    }


}
