package com.xxx.v1;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 数据写入任务
 * 对数据进行写入
 *
 * @author R4441-zxc
 * @date 2023/6/15 14:52
 */
@Slf4j
public abstract class AbstractWriterTask<T> implements WriteTask {
    public final WriterConfig writerConfig;
    public final BlockingQueue<DataBatchCache<T>> queue;
    public final String tableName;
    public final int retryTimes;
    public final String localFailPath;
    public final CacheLocal cache;
    /**
     * task线程是否一直运行
     */
    private volatile boolean isWorking;

    public AbstractWriterTask(WriterConfig dataSource, BlockingQueue<DataBatchCache<T>> queue, String tableName, CacheLocal cache) {
        this.writerConfig = dataSource;
        this.queue = queue;
        this.tableName = tableName;
        this.retryTimes = dataSource.getReTry();
        this.localFailPath = dataSource.getFailedPath();
        this.cache = cache;
    }

    @Override
    public void run() {
        boolean isConnection = false;
        do {
            try {
                isWorking = true;
                //尝试连接db
                isConnection = connect();

                if (isConnection) {
                    log.info("thread [{}] start writer task", Thread.currentThread().getName());
                    // 用或 因为如果writer close了但是 queue里面还有数据,会丢数据
                    // 只要到达监听队列 则只要不是强行关闭线程 会消费完数据
                    while (isWorking || queue.size() > 0) {
                        DataBatchCache<T> batch = null;
                        try {
                            batch = queue.poll(2, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            log.warn("thread [{}] is interrupted cause:{}",Thread.currentThread().getName(), e.getCause(), e);
                        }
                        if (batch != null) {
                            flush(batch);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("Fatal error in writer task:{}", e.getCause(), e);
                // 其他一场就不循环了
                isConnection = true;
            } catch (Exception e) {
                isConnection = false;
                log.error("Clickhouse connection error...", e);
                ThreadUtil.sleep(2000L);
            }
            // 只有连接问题才重试
        } while (!isConnection && isWorking);
    }

    /**
     * todo 可优化使用连接池 如果连接池连接不够或者移除 需增加处理逻辑
     * <p>
     * connect db
     *
     * @return is connect db
     * @throws SQLException           sql error
     * @throws ClassNotFoundException can not find driver class
     */
    public abstract boolean connect() throws SQLException, ClassNotFoundException;

    /**
     * 数据发送到db
     *
     * @param batch 数据批次
     */
    public abstract void flush(DataBatchCache<T> batch);


    @Override
    public void stop() {
        isWorking = false;
    }
}
