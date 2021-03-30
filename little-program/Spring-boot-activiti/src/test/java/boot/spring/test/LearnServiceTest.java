package boot.spring.test;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import boot.spring.service.LeaveService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnServiceTest {
    @Resource
    ProcessEngine processEngine;

    @Resource
    private LeaveService LeaveServiceImpl;

    @Test
    public void learnTest(){
        
        int getalldepttask = LeaveServiceImpl.getalldepttask("31");
        
        System.out.println(processEngine);
        
        System.out.println(processEngine.getName());
        
        System.out.println(LeaveServiceImpl);
        
    }
}
