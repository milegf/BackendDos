package com.loopie.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SimpleAuthInterceptor implements HandlerInterceptor {

    @org.springframework.beans.factory.annotation.Autowired
    private com.loopie.backend.modules.auth.AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            java.util.Optional<com.loopie.backend.modules.user.User> userOpt = authService.getUserByToken(token);

            if (userOpt.isPresent()) {
                request.setAttribute("userId", userOpt.get().getId());
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Missing or invalid token");
        return false;
    }
}
