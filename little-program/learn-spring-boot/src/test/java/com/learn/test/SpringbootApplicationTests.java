package com.learn.test;

import com.learn.SpringBootPower;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootPower.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(SpringbootApplicationTests.class);

    @Test
    public void test() {
        log.info("测试代码");
    }

}
