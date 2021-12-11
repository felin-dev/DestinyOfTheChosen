package game.destinyofthechosen.config;

import game.destinyofthechosen.web.interceptors.UserCreationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new UserCreationInterceptor())
                .addPathPatterns("/users/register");
    }
}
