package com.xxx.v1;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author R4441-zxc
 * @date 2023/6/15 16:54
 */
@Slf4j
public class PrintWriteTask<T> extends AbstractWriterTask<T> {
    public PrintWriteTask(WriterConfig dataSource, BlockingQueue<DataBatchCache<T>> queue, String tableName, CacheLocal cache) {
        super(dataSource, queue, tableName, cache);
    }

    @Override
    public boolean connect() {
        log.info("print write task connected ...");
        return true;
    }

    @Override
    public void flush(DataBatchCache<T> batch) {
        List<T> batch1 = batch.getBatch();
        ThreadUtil.sleep(1000);
        log.info("print write print:{}", batch1);

    }
}
