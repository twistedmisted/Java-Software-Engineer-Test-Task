package com.example.demo.inerceptor;

import com.example.demo.config.TenantContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        String tenantId = request.getHeader("tenantId");

        if (tenantId == null) {
            response.getWriter().write("Tenant Id is not present in the request header");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }

    }
}
