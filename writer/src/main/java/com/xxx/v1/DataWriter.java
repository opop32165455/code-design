package com.xxx.v1;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author R4441-zxc
 * @date 2023/6/14 20:01
 */
@Slf4j
public class DataWriter<T> implements AutoCloseable {
    public DataWriter(WriterConfig config, String tableName) {
    }

    public void put(DataBatchRequest<T> request) {
        List<T> requests = request.getRequests();
        System.out.println("requests = " + requests);
    }

    @Override
    public void close() {
    }
}
