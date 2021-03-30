package com.learn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author furao
 * @desc
 * @date 2020/9/7
 * @package com.dfzt.dataMid.dataAdapter
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootPower.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootApplicationTests {
    private static final Log log = LogFactory.getLog(SpringbootApplicationTests.class);

    @Test
    public void test() {

        log.info("测试代码");
    }

}

