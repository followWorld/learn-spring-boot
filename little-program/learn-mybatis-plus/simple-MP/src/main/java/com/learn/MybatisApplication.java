package com.learn;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassName: mybatisApplication <br/>
 * Description: <br/>
 * date: 2021/1/12 15:12<br/>
 *
 * @author WLSH<br />
 */
@SpringBootApplication(scanBasePackages = {"com.learn"})
@MapperScan(value = "com.learn.mapper")
public class MybatisApplication {

    private static final Logger log = LoggerFactory.getLogger(MybatisApplication.class);

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(MybatisApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("-------application DONE----------\n");
        log.info("\n----------------------------------------------------------\n\t"
                + "Application MybatisApplication is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:" + port + path
                + "/\n\t" + "External: \thttp://" + ip + ":" + port + path + "/\n"
                + "----------------------------------------------------------");

    }

}
