package madstodolist.config;

import madstodolist.interceptor.AdminInterceptor;
import madstodolist.interceptor.LogeadoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration to register interceptors.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private LogeadoInterceptor logeadoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Apply AdminInterceptor to /registrados and /registrados/**
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/registrados/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/auth/**");

        // Apply LogeadoInterceptor to /logeados and /logeados/**
        registry.addInterceptor(logeadoInterceptor)
                .addPathPatterns("/logeados/**");
    }
}
