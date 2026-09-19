package com.example.ecommerce.config;
import com.example.ecommerce.repository.util.JwtUtil;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) return true;
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401); return false;
        }
        try {
            Long userId = jwtUtil.parseUserId(authHeader.substring(7));
            request.setAttribute("userId", userId);
            return true;
        } catch (Exception e) {
            response.setStatus(401); return false;
        }
    }
}