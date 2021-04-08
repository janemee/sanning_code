package com.huimi.admin.config;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.annotation.SysLog;
import com.huimi.common.utils.SpringContextUtils;
import com.huimi.core.po.system.SystemLog;
import com.huimi.core.service.system.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 添加操作日志
 * Created by DFx-Dellbook on 2017/8/9
 */
@Slf4j
@Aspect   //定义一个切面
@Configuration
public class ControllerResultAspect {
    // 定义切点Pointcut
    @Pointcut("execution(* com.huimi.consumer.admin.controller..*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(final ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        SysLog s = targetMethod.getAnnotation(SysLog.class);
        if (s == null || s.value()) {
            final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            final HttpServletRequest request = sra.getRequest();
            ThreadUtil.execute(() -> {
                final String method = request.getMethod();
                final String uri = request.getRequestURI();

                final String queryString = request.getParameterMap() != null ? JSONUtil.toJsonStr(request.getParameterMap()) : null;
                String _ip = request.getHeader("x-forwarded-for");
                if (_ip == null || _ip.length() == 0 || "unknown".equalsIgnoreCase(_ip)) {
                    _ip = request.getHeader("Proxy-Client-IP");
                }
                if (_ip == null || _ip.length() == 0 || "unknown".equalsIgnoreCase(_ip)) {
                    _ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (_ip == null || _ip.length() == 0 || "unknown".equalsIgnoreCase(_ip)) {
                    _ip = request.getRemoteAddr();
                    if ("127.0.0.1".equals(_ip)) {
                        //根据网卡取本机配置的IP
                        InetAddress inet = null;
                        try {
                            inet = InetAddress.getLocalHost();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        _ip = null == inet ? "" : inet.getHostAddress();
                    }
                }
                // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                if (_ip != null && _ip.length() > 15) {
                    if (_ip.indexOf(",") > 0) {
                        _ip = _ip.substring(0, _ip.indexOf(","));
                    }
                }
                final String ip = _ip;
                SystemLogService systemLogService = SpringContextUtils.getBean(SystemLogService.class);
                systemLogService.insert(new SystemLog(l -> {
                    l.setIp(ip);
                    l.setRequestPath(uri);
                    l.setMethod(method);
//                    l.setParams(queryString);
                    if (null != AdminSessionHelper.getCurrAdmin()) {
                        l.setCreator(AdminSessionHelper.getCurrAdmin().getUsername());
                    }
                }));
            });
        }

        // result的值就是被拦截方法的返回值
        return pjp.proceed();
    }
}
