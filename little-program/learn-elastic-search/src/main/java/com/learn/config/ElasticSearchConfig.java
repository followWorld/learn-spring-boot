package com.learn.config;


import com.dfzt.iot.es.config.ESProperties;
import com.dfzt.iot.es.config.ElasticSearchCfg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("'${elasticsearch.host}'.length() > 0")
public class ElasticSearchConfig {

    @Bean(name = "elasticSearchCfg")
    public ElasticSearchCfg regist(
            @Value("${elasticsearch.host}") final String host,
            @Value("${elasticsearch.username}") final String username,
            @Value("${elasticsearch.password}") final String password,
            @Value("${elasticsearch.basePackage}") final String basePackage) {
        ElasticSearchCfg elasticSearchCfg = ElasticSearchCfg.regist(new ESProperties(host, username, password,
                basePackage));
        elasticSearchCfg.build();
        return elasticSearchCfg;
    }
}
