package game.destinyofthechosen.config;

import game.destinyofthechosen.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private MethodSecurityExpressionHandler methodSecurityExpressionHandler;

    @Override
    protected org.springframework.security.access.expression.method.MethodSecurityExpressionHandler createExpressionHandler() {
        return methodSecurityExpressionHandler;
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler(UserService userService) {
        return new MethodSecurityExpressionHandler(userService);
    }
}
