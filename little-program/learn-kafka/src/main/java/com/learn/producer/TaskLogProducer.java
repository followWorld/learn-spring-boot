package com.learn.producer;

import com.alibaba.fastjson.JSON;
import com.learn.bean.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * ClassName: TaskLogProducer
 * Description:
 * date: 2021/8/7 16:14
 *
 * @author WLSH
 */
@Component
public class TaskLogProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 业务类型与总步骤映射关系
     */
    public static Map<String, Integer> busTypeStep = new HashMap<>();
    /**
     * 目標服务器地址集合
     */
    public static List<String> dsaList = new ArrayList<>();
    /**
     * 业务类型集合
     */
    public static List<String> busTypes = new ArrayList<>();

    @PostConstruct
    private void init() {
        busTypes.addAll(Arrays.asList("跳合闸", "厂休控", "时段控", "本地费控", "远程费控", "保电", "对时", "催费告警", "功率下浮控", "时段下发"));
//        busTypes.forEach(b -> busTypeStep.put(b, (int) (Math.random() * 6) + 1));
        busTypes.forEach(b -> busTypeStep.put(b, (busTypes.indexOf(b) + 3) / 2));
        dsaList.addAll(Arrays.asList("123000000321", "123123123123", "123456123456", "456456456456", "456000000654",
                "123456654321", "987654321000", "000000112233", "112233445566", "111222333444", "111122223333",
                "111111222222", "111111111111", "999999999999", "012345678901", "321321321321", "345123234465",
                "456234456123", "123453456234", "123435456567", "123123123354", "123345567687"));
    }

    /**
     * 随机发送任务日志数据
     */
    public void sendTaskLog() {
        TaskLog taskLog = new TaskLog();
        // 随机添加业务类型等信息
        randomTaskLog(taskLog);

        System.err.println("发送用户日志数据:" + JSON.toJSONString(taskLog));
        kafkaTemplate.send("test", JSON.toJSONString(taskLog));
    }

    private void randomTaskLog(TaskLog taskLog) {
        // 随机一个flowNo
        taskLog.setFlowNo(UUID.randomUUID().toString().replace("-", ""));
        // 选一个业务类型
        String busType = busTypes.get((int) (Math.random() * busTypes.size()));
        taskLog.setBusType(busType);
        // 随机一个目标主机
        taskLog.setDsa(dsaList.get((int) (Math.random() * dsaList.size())));
        // 获取业务类型对应最大步数
        int totalStep = busTypeStep.get(busType);
        int step = (int) (Math.random() * totalStep) + 1;
        taskLog.setStep(step);


        // 当前步骤结果
        if (Math.random() > 0.5d) {
            // 一般概率成功
            taskLog.setResult("success");
        } else if (Math.random() > 0.5d) {
            // 四分之一概率失败
            taskLog.setResult("failure");
        }


        if (step < totalStep) {
            // 没走到底4分之一概率完成，即失败
            //
            if (Math.random() > 0.75d) {
                // 当前步骤结果四分之一概率失败
                taskLog.setStatus("完成");
                taskLog.setResult("failure");
            } else {
                //
                taskLog.setStatus("进行中");
            }
        } else if (Math.random() > 0.5d) {
            // 一半为完成
            taskLog.setStatus("完成");
            if (Math.random() > 0.1d) {
                // 大部分成功
                taskLog.setResult("success");
            } else {
                // 剩余失败
                taskLog.setResult("failure");
            }
        } else {
            // 一半概率进行中
            taskLog.setStatus("进行中");
        }

        taskLog.setTime(new Date());

    }


}
