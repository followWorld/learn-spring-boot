package com.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Optional;

@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class ElasticRegCenterConfig {

    @Bean(name = "wlsRegCenter", initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    @Bean
    public JobOperateAPI getJobOperatorAPI(@Value("${regCenter.serverList}") final String serverList,
                                    @Value("${regCenter.namespace}") final String namespace){
        return JobAPIFactory.createJobOperateAPI(serverList, namespace, Optional.fromNullable(null));
    }
}
