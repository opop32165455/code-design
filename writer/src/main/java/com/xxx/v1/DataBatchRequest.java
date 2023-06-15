package com.xxx.v1;

import java.util.List;
import java.util.Map;

/**
 * @author R4441-zxc
 * @date 2023/6/14 20:34
 */
public class DataBatchRequest<T> {
    private final List<T> requests;

    public DataBatchRequest(List<T> deepCopy) {
        this.requests = deepCopy;
    }

    public List<T> getRequests() {
        return requests;
    }
}
