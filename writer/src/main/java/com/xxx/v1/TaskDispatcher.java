package com.xxx.v1;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 将数据打包放入队列 开启子线程进行消费
 * 任务分发器
 *
 * @author R4441-zxc
 * @date 2023/6/14 20:01
 */
@Slf4j
public class TaskDispatcher<T> implements AutoCloseable {

    private final BlockingQueue<DataBatchCache<T>> queue;
    private final String tableName;
    private ExecutorService service;
    private List<WriteTask> tasks;


    public TaskDispatcher(Map<String, String> config, String tableName, String sourceType) {
        this.tableName = tableName;
        int maxCapacity = Integer.parseInt(config.getOrDefault(WriterSettings.QUEUE_MAX_CAPACITY, "1000"));
        queue = new LinkedBlockingQueue<>(maxCapacity);
        initWriter(config, sourceType);
    }

    private void initWriter(Map<String, String> config, String sourceType) {
        int numWriters = Integer.parseInt(config.getOrDefault(WriterSettings.NUM_WRITERS, "2"));
        service = ThreadUtil.newFixedExecutor(numWriters, String.format("%s-%s-writer-", sourceType, tableName), true);

        //配置转换
        val writerConfig = initWriterConf(config);
        tasks = new ArrayList<>();
        CacheLocal cache = new CacheLocal();
        for (int i = 0; i < numWriters; i++) {
            WriteTask task;
            switch (sourceType) {
                case "clickhouse":
                    //todo clickhouse因为分区的问题 最好根据分区字段 进行数据排序然后写入 可以大大提高写入速度 减少一次写入需要随机到多个节点
                    task = new ClickhouseWriteTask<>(writerConfig, queue, this.tableName, cache);
                    break;
                case "print":
                    task = new PrintWriteTask<>(writerConfig, queue, this.tableName, null);
                    break;
                default:
                    task = new PrintWriteTask<>(writerConfig, queue, this.tableName, cache);
            }

            tasks.add(task);
            service.submit(task);
        }
    }

    private WriterConfig initWriterConf(Map<String, String> config) {
        //todo
        return WriterConfig.builder()
                .database(config.get(WriterSettings.DATABASE))
                .build();
    }

    public void put(DataBatchCache<T> batchCache) {
        try {
            queue.put(batchCache);
        } catch (Exception e) {
            log.error("error while putting data to queue:{}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (this.service != null && !this.service.isShutdown()) {
            this.service.shutdown();
            int timeout = 10;
            try {
                if (!this.service.awaitTermination(timeout, TimeUnit.SECONDS)) {
                    this.service.shutdownNow();
                    log.info("success shutdown thread service");
                }
            } catch (Exception e) {
                log.error("error shutdown thread service:{}", e.getMessage(), e);
            }
        }
        this.tasks.forEach(WriteTask::stop);
    }
}
