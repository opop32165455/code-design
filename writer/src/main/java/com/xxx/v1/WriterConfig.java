package com.xxx.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author R4441-zxc
 * @date 2023/6/15 15:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriterConfig {

    /**
     * hosts
     */
    private String hosts;

    /**
     * database
     */
    private String database;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * 重试次数
     */
    private int reTry;

    /**
     * 出错备份地址
     */
    private String failedPath;


}
