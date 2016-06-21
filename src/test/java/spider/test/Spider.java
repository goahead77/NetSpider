package spider.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import spider.config.TestConfig;
import spider.repository.SchoolRepository;
import spider.service.SpiderDataService;

import java.io.IOException;

/**
 * Created by Administrator on 2016/6/15 0015.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class Spider{

    @Autowired
    SpiderDataService spiderDataService;

    @Autowired
    SchoolRepository schoolRepository;


    @Test
    public void get(){
        try {
            spiderDataService.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
