package com.huimi.admin.controller.login;

import com.huimi.admin.config.shiro.UsernamePasswordCaptchaToken;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.exception.CaptchaException;
import com.huimi.admin.exception.ForbiddenException;
import com.huimi.admin.exception.UnAuthorityException;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigConsts;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录相关操作
 */
@Log4j
@Controller
@RequestMapping(BaseController.BASE_URI)
public class LoginController extends BaseController {

    @Autowired
    private RedisService redisService;

    /**
     * 登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        //如果已经登录跳转到首页
        modelAndView.addObject("logUrl", redisService.get(ConfigNID.WEB_LOG_IMG_URL));
        modelAndView.addObject("copyRight",redisService.get(ConfigNID.COPY_RIGHT));
        modelAndView.addObject("companyName",redisService.get(ConfigNID.COMPANY_NAME));
        modelAndView.addObject("companyIndexUrl",redisService.get(ConfigNID.COMPANY_INDEX_URL));
        modelAndView.addObject("systemTitle",redisService.get(ConfigNID.SYSTEM_TITLE));
        modelAndView.addObject("favIcoUrl",redisService.get(ConfigNID.FAV_ICO_URL));
        if (SecurityUtils.getSubject().isAuthenticated()) {
            modelAndView.setView(new RedirectView("/s/s/main", true, false));
            return modelAndView;
        }
        response.addHeader("ADMIN_LOGIN_STATE", "0");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/s/login", true, false));
        return modelAndView;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView doLogin(HttpServletRequest request) {
        String authCode = request.getParameter("authCode");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        //登录有效期 单位：毫秒 根据后台配置参数而定 如果后台没有设置 则永久有效
        long timeOut = redisService.getLong(ConfigNID.login_USER_COOKIE_TIME) * 1000;
        subject.getSession().setTimeout(timeOut);
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (!subject.isAuthenticated()) {
                UsernamePasswordCaptchaToken token = new UsernamePasswordCaptchaToken(username, password, authCode);
                subject.login(token);
            }
            modelAndView.setView(new RedirectView("/s/s/main", true, false));
            return modelAndView;
        } catch (ForbiddenException lae) {
            modelAndView.addObject("msg", "该用户被禁用");
        } catch (UnAuthorityException lae) {
            modelAndView.addObject("msg", "无登录权限");
        } catch (UnknownAccountException c) {
            modelAndView.addObject("msg", "用户名不存在");
        } catch (CaptchaException une) {
            modelAndView.addObject("msg", "验证码不正确");
        } catch (IncorrectCredentialsException ine) {
            modelAndView.addObject("msg", "用户名或密码不正确");
        } catch (ExcessiveAttemptsException exe) {
            modelAndView.addObject("msg", "账户密码错误次数过多，账户已被限制登录1小时");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("msg", "服务异常，请稍后重试");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
