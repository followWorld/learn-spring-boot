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
    
}
