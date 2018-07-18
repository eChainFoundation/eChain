package com.echain.config;

import com.echain.common.util.IdWorker;
import org.springframework.stereotype.Component;

/**
 * SnowFlake生成算法  初始化配置
 */
@Component
public class IdWorkerConfig {

    private IdWorkerConfig() throws Exception {
        int machineId = IdWorker.getMachineId();
        IdWorker.init(machineId);
    }
}
