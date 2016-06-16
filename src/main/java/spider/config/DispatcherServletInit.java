package spider.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{Root.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
