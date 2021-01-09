package com.learn.config;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.learn.job.MyElasticJob;
import com.learn.util.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Description: 测试使用javaAPI配置启动作业 <br/>
 * date: 2021/1/9 14:54<br/>
 *
 * @author WLSH<br />
 */
@Component
public class JobConfig {

    private static final Logger log = LoggerFactory.getLogger(JobConfig.class);
    @Resource
    private ZookeeperRegistryCenter myRegistryCenter;

    @PostConstruct
    public void testSimpleJob() {
        // 启动作业配置
        new JobScheduler(myRegistryCenter, MySimpleJob1()).init();
    }

    /**
     * 定义Lite作业根配置
     *
     * @return
     */
    public LiteJobConfiguration MySimpleJob1() {
        return JobUtil.simpleJobConfigBuilder("mySimpleJob1",
                MyElasticJob.class, 3,
                "0/10 * * * * ?", "0=北京, 1=上海, 2=广州", "1");
    }
}
