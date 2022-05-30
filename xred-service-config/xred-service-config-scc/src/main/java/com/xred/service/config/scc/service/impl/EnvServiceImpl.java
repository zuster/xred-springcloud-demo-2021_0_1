package com.xred.service.config.scc.service.impl;

import com.xred.service.config.scc.service.IEnvService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 环境变量查询 Service 实现
 *
 * @author songyh
 * @date 2022/5/30 11:27 PM
 */
@Service
public class EnvServiceImpl implements IEnvService {
    @Value("${custom.welcome}")
    private static String welcome;

    @Override
    public String customWelcome() {
        return welcome;
    }
}
