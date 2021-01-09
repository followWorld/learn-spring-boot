package com.learn.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyElasticJob implements SimpleJob {
    private static final Logger log = LoggerFactory.getLogger(MyElasticJob.class);


    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            // 配置 sharding-item-parameters="0=A,1=B,2=C"
            case 0:
                log.info("=========elastic-job 0=========");
                String shardingParameter = context.getShardingParameter();
                log.info("打印参数【{}】", shardingParameter);
                // do something by sharding item 0
                break;
            case 1:
                log.info("=========elastic-job 1=========");
                shardingParameter = context.getShardingParameter();
                log.info("打印参数【{}】", shardingParameter);
                // do something by sharding item 1
                break;
            case 2:
                log.info("=========elastic-job 2=========");
                shardingParameter = context.getShardingParameter();
                log.info("打印参数【{}】", shardingParameter);
                // do something by sharding item 2
                break;
            // case n: ...
            default:
                // do something by default
        }
    }
}
