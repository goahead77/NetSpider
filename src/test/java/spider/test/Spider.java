package spider.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import spider.config.TestConfig;
import spider.entity.School;
import spider.repository.SchoolRepository;
import spider.service.Runner;
import spider.service.SpiderDataService;

import java.io.IOException;

/**
 * Created by Administrator on 2016/6/15 0015.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class Spider {

    @Autowired
    SpiderDataService spiderDataService;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    Runner runner;

    @Test
    public void get(){
        try {
            spiderDataService.spider();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save(){
        School school=new School();
        school.setSName("TEST");
        school.setSDesc("aaa");
        school.setAddress("aa" +
                "");
        schoolRepository.save(school);
    }

    @Test
    public void testRun(){
        runner.run();
    }
}
