package com.xred.service.config.scc.client.service.impl;

import com.xred.service.config.scc.client.service.IEnvDemoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * Env Service
 *
 * @author songyh
 * @date 2022/6/4 11:47 PM
 */
@Service
@RefreshScope
public class EnvDemoServiceImpl implements IEnvDemoService {
    @Value("${custom.user.id}")
    private String userId = null;
    @Value("${custom.user.name}")
    private String userName = null;
    @Value("${git.test.id}")
    private String gitTestId;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getGitTestId() {
        return gitTestId;
    }
}
