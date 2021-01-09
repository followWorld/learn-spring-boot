package com.learn.util;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务监听类 统计任务时间,该监听器为分布式监听器
 * TODO 暂时仅支持spring配置作业时添加监听器，后续支持api配置作业添加监听器。
 *
 * @author WLSH
 */
public class JobListener extends AbstractDistributeOnceElasticJobListener {

    private static final Logger log = LoggerFactory.getLogger(JobListener.class);
    private long start = 0;

    public JobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        start = System.currentTimeMillis();
        log.info("分布式任务监听开始，任务名：" + shardingContexts.getJobName());
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info("分布式任务监听结束，任务名：" + shardingContexts.getJobName() + ",耗时：" + (endTime - start) + "ms");
    }
}
