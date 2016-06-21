package spider.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Configuration
@ComponentScan("spider")
@Import({MvcConfig.class,JpaConfig.class})
public class Root {


}
