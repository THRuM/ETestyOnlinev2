package etestyonline.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class ETestyOnlineWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String LOCATION = "/tmp";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5;
    private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 7;
    private static final int FILE_SIZE_THRESHOLD = 0;

    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MongoDBConfig.class, SecurityConfig.class, UtilConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        MultipartConfigElement multipartConfig = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE,
                MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        registration.setMultipartConfig(multipartConfig);
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}
