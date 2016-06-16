package spider.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Configurable
@ComponentScan("cn.spider")
@Import({MvcConfig.class,JpaConfig.class})
public class Root {
}
