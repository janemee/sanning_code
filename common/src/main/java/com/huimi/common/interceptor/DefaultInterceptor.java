package com.huimi.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 默认拦截器 Created by donfy on 2016/10/21.
 */
@Slf4j
@Component
public class DefaultInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行调用（Controller方法调用之前）
        debugLog(request, handler);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
        // 初始化数据

//        log.info("test");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
//        log.info("默认");

    }

    private void debugLog(HttpServletRequest request, Object handler) {
        try {
            Class clazz = ((HandlerMethod) handler).getBean().getClass();
            Method m = ((HandlerMethod) handler).getMethod();
            StackTraceElement traces =
                    new StackTraceElement(clazz.getName(), m.getName(), clazz.getName(), 1);
            StringBuilder sb = new StringBuilder();
            sb.append("\n┏━━━━━")
                    .append("\n┣ 类  : ")
                    .append(traces)
                    .append("\n┣ 方法: ")
                    .append(m.getName())
                    .append("\n┣ 参数: ");
            request.getParameterMap().forEach((k, v) -> sb.append(k).append("=").append(v[0]).append(","));
            sb.append("\n┗━━━━━");
            log.info(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
