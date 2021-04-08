package com.huimi.admin.config;

import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认拦截器 Created by donfy on 2016/10/21.
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 在请求处理之前进行调用（Controller方法调用之前）
//        checkUserRquest(request,response);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
        // 初始化数据
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）

    }

    private void formatData(HttpServletRequest request, Object handler, HttpServletResponse response, ModelAndView modelAndView) {
        // 初始化基础数据
        ModelMap mm = new ModelMap();
        mm.addAttribute("webConfig", WebModel.initWebModel());

        modelAndView.addAllObjects(mm);

    }


    private void checkUserRquest(HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<MenuModel> menuModels =  AdminSessionHelper.getAdminMenu();
        try {
            String uri = request.getRequestURI();
            if(!(uri.contains("/s/login")
                    ||uri.contains("/s/main")
                    ||uri.contains("/captcha/getCaptcha")
                    ||uri.contains("/s/admin/aws/uploadPic")
                    ||uri.contains("/captcha/check")
                    || uri.contains("/s/api/web/aws/getPic")
                    || uri.contains("/s/logout")
                    ||uri.contains("/s/admin/json/profile"))){
                if(uri.contains("/s/")){
                    uri = uri.substring(3,uri.length());
                }
                boolean b = true;
                for(Menu menu: menuModels){
                    if(menu.getUrl().contains(uri)){
                        b = false;
                        break;
                    }
                }
                String isAjax = request.getHeader("X-Requested-With");
                /* 如果请求为ajax请求，则返回json ;否则返回到提示页面 */
                Map<String,Object> map = new HashMap<>();
                if (isAjax != null && isAjax.equalsIgnoreCase("XMLHttpRequest")) {
                    map.put("result", false);
                    map.put("msg", "无权限访问");

                } else {
                    response.setCharacterEncoding("UTF-8");
                    response.sendRedirect("/common/errorMsg.html");
                }
                if(b){
                    throw new  BussinessException("无权限访问");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
