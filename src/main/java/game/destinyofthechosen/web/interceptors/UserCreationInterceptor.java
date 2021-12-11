package game.destinyofthechosen.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCreationInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(UserCreationInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (request.getRemoteUser() != null) LOGGER.info("New registered user: {}.", request.getRemoteUser());
    }
}
