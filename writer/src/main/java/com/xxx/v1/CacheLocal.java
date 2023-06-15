package com.xxx.v1;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author R4441-zxc
 * @date 2023/6/15 14:48
 */
@Slf4j
public class CacheLocal {

    public synchronized void cache(String cachePath, String tableName, DataBatchCache<?> request) {
        File localDir = new File(cachePath);
        if (StrUtil.isBlank(cachePath) || localDir.isFile()) {
            for (Object data : request.getBatch()) {
                log.error("Error Data: [{}]", data.toString());
            }
        } else {
            String fileName = System.currentTimeMillis() + ".error";
            File localFile = new File(cachePath + File.separator + tableName + File.separator + fileName);
            if (!localFile.getParentFile().exists()) {
                var isMk = localFile.getParentFile().mkdirs();
                log.info("is mk local cache dir {} :{}", localFile.getParentFile().getName(), isMk);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(localFile))) {
                for (Object data : request.getBatch()) {
                    writer.write(JSONUtil.toJsonStr(data) + "\n");
                }
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
