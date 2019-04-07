package by.naakcii.adminka.ui.views;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class UploadConfig implements WebMvcConfigurer {

    @Value("${images.static.resource}")
    private String staticResource;

    @Value("${images.path.pattern}")
    private String pathPattern;


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pathPattern + "**").addResourceLocations(staticResource,
                "classpath:/META-INF/resources/static/");
    }
}