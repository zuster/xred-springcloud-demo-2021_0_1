package com.xred.service.config.scc.server.service.impl;

import com.xred.service.config.scc.server.service.IEnvService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 环境变量查询 Service 实现
 *
 * @author songyh
 * @date 2022/5/30 11:27 PM
 */
@Service
@Profile(value = "prod")
public class EnvServiceImpl implements IEnvService {
    @Value("${custom.welcome}")
    private String welcome;

    @Override
    public String customWelcome() {
        return welcome;
    }
}
