package com.learn.service;

import com.learn.globelTrans.service.SentinelDubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: TestService
 * Description:
 * date: 2021/4/20 13:38
 *
 * @author WLSH
 */
@Service
public class TestService {
    @Resource
    private SentinelDubboService dubboService;

    public String test() {
        return dubboService.sayHello();
    }
}
