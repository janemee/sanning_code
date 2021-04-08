package com.huimi.admin.config;

import com.huimi.admin.config.shiro.AdminRealm;
import com.huimi.admin.config.shiro.QuartzSessionValidationSchedulerAdmin;
import com.huimi.admin.controller.BaseController;
import com.huimi.core.config.TokenTag;
import freemarker.template.utility.XmlEscape;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class ShiroConfig {

    @Bean
    public TokenTag tokenTag() {
        return new TokenTag();
    }

    @Bean
    public XmlEscape fmXmlEscape() {
        return new XmlEscape();
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setContentType("text/html;charset=utf-8");
        resolver.setSuffix(".html");
        resolver.setCache(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setExposeSpringMacroHelpers(true);
        //redirectHttp10Compatible:解决https环境下使用redirect重定向地址变为http的协议，无法访问服务的问题
        //                   设置为false,即关闭了对http1.0协议的兼容支持
        resolver.setRedirectHttp10Compatible(false);
        return resolver;
    }

    @Bean
    public MyFreeMarkerConfigurer freemarkerConfig(TokenTag tokenTag, XmlEscape xmlEscape) {

        MyFreeMarkerConfigurer myFreeMarkerConfigurer = new MyFreeMarkerConfigurer();
        myFreeMarkerConfigurer.setTemplateLoaderPath("classpath:/webapp/template/views/");
        myFreeMarkerConfigurer.setDefaultEncoding("utf-8");
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("token", tokenTag);
        variables.put("xml_escape", xmlEscape);
        myFreeMarkerConfigurer.setFreemarkerVariables(variables);
        Properties properties = new Properties();
        properties.put("default_encoding", "UTF-8");
        properties.put("output_encoding", "UTF-8");
        properties.put("locale", "zh_CN");
        properties.put("number_format", "#.##");
        properties.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.put("date_format", "yyyy-MM-dd");
        properties.put("time_format", "HH:mm:ss");
        properties.put("object_wrapper", "freemarker.ext.beans.BeansWrapper");
        myFreeMarkerConfigurer.setFreemarkerSettings(properties);

        return myFreeMarkerConfigurer;
    }

    /**
     * 监听器增加
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /*
     * 权限过滤器实现
     *
     * @return
     */
//    @Bean
//    public FormAuthenticationCaptchaFilter captchaFilter() {
//        return new FormAuthenticationCaptchaFilter();
//    }

    /**
     * 缓存管理
     */
    @Bean
    public EhCacheManager cacheManager() {
        return new EhCacheManager();
    }

    /**
     * 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的AdminRealm.java
     */
    @Bean
    public AdminRealm adminRealm() {
        return new AdminRealm();
    }

    /**
     * Shiro安全管理器
     * Shiro默认会使用Servlet容器的Session,可通过sessionMode属性来指定使用Shiro原生Session
     * 即<property name="sessionMode" value="native"/>,详细说明见官方文档
     * 这里主要是设置自定义的单Realm应用,若有多个Realm,可使用'realms'属性代替
     */
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, DefaultWebSessionManager defaultWebSessionManager, EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(adminRealm);
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        return defaultWebSecurityManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        shiroFilterFactoryBean.setLoginUrl(BaseController.BASE_URI + "/login");
        shiroFilterFactoryBean.setUnauthorizedUrl(BaseController.BASE_URI + "/login");
        Map<String, Filter> filters = new LinkedHashMap<>();

        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> chains = new LinkedHashMap<>();
//        chains.put(BaseController.BASE_URI + "/logout", "logout");
        chains.put(BaseController.BASE_URI + "/doLogin", "anon");
        chains.put(BaseController.BASE_URI + "/**", "authc");
        chains.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("aid");
        simpleCookie.setHttpOnly(true);
        //登录有效期限
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator javaUuidSessionIdGenerator) {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator);
        return enterpriseCacheSessionDAO;
    }

    @Bean
    public QuartzSessionValidationSchedulerAdmin sessionValidationScheduler(DefaultWebSessionManager defaultWebSessionManager) {
        QuartzSessionValidationSchedulerAdmin quartzSessionValidationSchedulerAdmin = new QuartzSessionValidationSchedulerAdmin();
        quartzSessionValidationSchedulerAdmin.setSessionManager(defaultWebSessionManager);
        quartzSessionValidationSchedulerAdmin.setSessionValidationInterval(1800000);
        return quartzSessionValidationSchedulerAdmin;
    }

    @Bean
    public DefaultWebSessionManager sessionManagerAdmin(EnterpriseCacheSessionDAO enterpriseCacheSessionDAO, SimpleCookie simpleCookie) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);

        defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setSessionIdCookie(simpleCookie);
        return defaultWebSessionManager;
    }

    /**
     * 开启aop注解支持  启动很慢
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
