package com.learn.controller;

import com.learn.producer.TaskLogProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * ClassName: SentMessageController
 * Description:
 * date: 2021/8/8 14:35
 *
 * @author WLSH
 */
@RestController
public class SentMessageController {
    @Resource
    private TaskLogProducer taskLogProducer;


    @GetMapping(value = "/send")
    public String send(Integer num) {
        for (int i = 0; i < num; i++) {
            taskLogProducer.sendTaskLog();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "发送完成" + new Date();
    }

}
