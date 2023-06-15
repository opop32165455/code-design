package com.xxx.v1;

/**
 * @author R4441-zxc
 * @date 2023/6/14 20:03
 */
public interface WriterSettings {

    /**
     * 通过 jdbc 方式访问db集群需要的参数
     */
    String HOSTS = "access.hosts";
    String USER = "access.user";
    String PASSWORD = "access.password";
    String DATABASE = "access.database";

    /**
     * 数据写入的参数
     */
    String BUFFER_SIZE = "sink.max-buffer-size";
    String NUM_WRITERS = "sink.num-writers";
    String QUEUE_MAX_CAPACITY = "sink.queue-max-capacity";
    String TIMEOUT_SEC = "sink.timeout-sec";
    String NUM_RETRIES = "sink.retries";
    String FAILED_RECORDS_PATH = "sink.failed-records-path";
}
