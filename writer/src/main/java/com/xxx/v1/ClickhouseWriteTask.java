package com.xxx.v1;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * clickhouse 写入任务
 *
 * @author R4441-zxc
 * @date 2023/6/15 16:06
 */
@Slf4j
public class ClickhouseWriteTask<T> extends AbstractWriterTask<T> {

    /**
     * db连接
     */
    private Connection connection;

    /**
     * host arr
     */
    String[] hostArr;

    /**
     * is error data cache local
     */
    boolean isCache;

    public ClickhouseWriteTask(WriterConfig dataSource, BlockingQueue<DataBatchCache<T>> queue, String tableName, CacheLocal cache) {
        super(dataSource, queue, tableName, cache);
        String hosts = this.writerConfig.getHosts();
        hostArr = hosts.split(StrUtil.COMMA);
    }

    @Override
    public boolean connect() throws SQLException, ClassNotFoundException {
        //随机连接ck 防止某一台宕机 连接失败 走父类重连机制
        int index = new Random().nextInt(hostArr.length);
        String host = hostArr[index];
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        String database = this.writerConfig.getDatabase();
        String url = String.format("jdbc:clickhouse://%s/%s", host, database);
        String user = this.writerConfig.getUsername();
        String password = this.writerConfig.getPassword();
        connection = DriverManager.getConnection(url, user, password);
        return !connection.isClosed();
    }


    @Override
    public void flush(DataBatchCache<T> requestBlank) {
        int size = requestBlank.getBatch().size();
        log.info("Ready to load data to {}, size = {}", this.tableName, size);
        if (size == 0) {
            return;
        }
        // 批量写
        PreparedStatement preparedStatement = buildSql(requestBlank, connection);
        if (null == preparedStatement) {
            log.info("Build Sql error.");
            return;
        }
        int times = 0;
        do {
            try {
                long start = System.currentTimeMillis();
                preparedStatement.executeBatch();
                connection.commit();
                long end = System.currentTimeMillis();
                log.info("Flush ck count: {}, used: {}", requestBlank.getBatch().size(), end - start);
                return;
            } catch (Exception e) {
                times++;
                log.error("flush clickhouse error, retry time {}", times, e);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // 如果写入失败尝试重新执行
        } while (times < retryTimes);

        if (isCache) {
            // 超过多次失败的数据写入本地
            cache.cache(this.localFailPath, this.tableName, requestBlank);
        }
    }


    private PreparedStatement buildSql(DataBatchCache<T> requestBlank, Connection connection) {
        Class<?> clazz = requestBlank.getBatch().get(0).getClass();
        log.info("flush class {}.", clazz.getName());
        Field[] declaredFields = clazz.getDeclaredFields();
        Field[] fields1 = clazz.getFields();
        Set<Field> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(declaredFields));
        hashSet.addAll(Arrays.asList(fields1));
        List<Field> fields = hashSet.stream().filter(f -> {
            SinkField field = f.getAnnotation(SinkField.class);
            return field != null && StrUtil.isNotBlank(field.clickhouse());
        }).collect(Collectors.toList());
        List<String> ckColumns = fields.stream().map(f -> f.getAnnotation(SinkField.class).clickhouse())
                .collect(Collectors.toList());
        if (ckColumns.size() == 0) {
            log.info("No fields.");
            return null;
        }
        String[] dots = new String[ckColumns.size()];
        Arrays.fill(dots, "?");
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)",
                this.tableName,
                CollUtil.join(ckColumns, ","),
                CollUtil.join(Arrays.asList(dots), ","));
        log.info(query);
        try {
            PreparedStatement res = connection.prepareStatement(query);
            for (Object data : requestBlank.getBatch()) {
                for (int i = 0; i < ckColumns.size(); i++) {
                    Field f = fields.get(i);
                    f.setAccessible(true);
                    Object value = f.get(data);
                    res.setObject(i + 1, value);
                }
                res.addBatch();
            }
            return res;
        } catch (Exception e) {
            log.error("Build batch sql exception,", e);
            return null;
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("error stop connection:{}", e.getMessage(), e);
        }
    }

}
