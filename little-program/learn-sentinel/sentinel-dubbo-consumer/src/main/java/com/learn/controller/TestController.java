package com.learn.controller;

import com.learn.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: TestController
 * Description:
 * date: 2021/4/20 13:40
 *
 * @author WLSH
 */
@RestController
@RequestMapping("/sentinel/test")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping(value = "/sayHello")
    public String sayHello() {
        return testService.test();
    }

}
