package com.learn.util;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;

public class JobUtil {
    /**
     * 简单任务
     *
     * @return
     */
    public static LiteJobConfiguration simpleJobConfigBuilder(
            String jobName,
            Class<? extends SimpleJob> jobClass,
            int shardingTotalCount, // 分片个数
            String cron,// 运行周期配置
            String shardingItemParameters,// 分片参数
            String id
    ) {
        // 定义作业核心配置
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .jobParameter(id)
                .failover(true)
                .build();
        // 使用核心配置，定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(coreConfig,
                jobClass.getCanonicalName());
        // 定义Lite作业根配置
        return LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                .overwrite(true) // 设置本地配置是否覆盖配置中心配置，如果为true每次启动都使用本地配置为准
                .build();
    }

    /**
     * 创建流式作业配置
     */
    public static LiteJobConfiguration getDataFlowJobConfiguration(
            String jobName,
            final Class<? extends DataflowJob> jobClass, // 任务类
            final String cron, // 运行周期配置
            final int shardingTotalCount, // 分片个数
            final String shardingItemParameters,// 分片参数
            final Boolean streamingProcess // 是否是流式作业
    ) {
        // 定义作业核心配置
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .build();
        // 使用核心配置，定义Dataflow类型配置
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfig,
                jobClass.getCanonicalName(),
                // true为流式作业,除非fetchData返回数据为null或者size为0,否则会一直执行
                // false 非流式,只会按配置时间执行一次
                streamingProcess);
        return LiteJobConfiguration.newBuilder(dataflowJobConfiguration)
                .overwrite(true) // 设置本地配置是否覆盖配置中心配置，如果为true每次启动都使用本地配置为准
                .build();
    }

    /**
     * 创建脚本作业配置
     */
    public static LiteJobConfiguration getScriptJobConfiguration(
            final String jobName, // 任务名字
            final String cron, // 运行周期配置
            final int shardingTotalCount, // 分片个数
            final String shardingItemParameters,// 分片参数
            final String scriptCommandLine // 是脚本路径或者命令
    ) {
        // 任务核心配置
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).build();
        // 使用核心配置，定义Script类型配置
        ScriptJobConfiguration scriptJobConfiguration = new ScriptJobConfiguration(coreConfig
                // 此处配置文件路径或者执行命令
                , scriptCommandLine);
        return LiteJobConfiguration.newBuilder(scriptJobConfiguration).overwrite(true).build();
    }
}
