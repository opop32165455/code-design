package com.xxx.v1;

import java.util.List;

/**
 * 批量化数据 减少IO次数
 * @author R4441-zxc
 * @date 2023/6/14 20:34
 */
public class DataBatchCache<T> {
    private final List<T> batch;

    public DataBatchCache(List<T> dataList) {
        this.batch = dataList;
    }

    public List<T> getBatch() {
        return batch;
    }
}
