package com.learn.config;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WLSH
 */
@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class ElasticRegCenterConfig {

    @Bean(name = "myRegistryCenter", initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    /**
     * 操作作业api，可以使用该api在系统运行时动态操作作业
     *
     * @param serverList 服务器列表
     * @param namespace  命名空间
     * @param digest     注册中心凭证（可为null）
     * @return 操作作业api对象
     */
    @Bean
    public JobOperateAPI getJobOperatorApi(@Value("${regCenter.serverList}") final String serverList,
                                           @Value("${regCenter.namespace}") final String namespace,
                                           @Value("${regCenter.digest}") final String digest) {
        return JobAPIFactory.createJobOperateAPI(serverList, namespace,
                StringUtils.isEmpty(digest) ? Optional.absent() : Optional.of(digest));
    }

}
