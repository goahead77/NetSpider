package spider.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Configurable
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
}
