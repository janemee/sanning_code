package com.huimi.admin.config.shiro;

import com.huimi.admin.exception.CaptchaException;
import com.huimi.admin.exception.ForbiddenException;
import com.huimi.admin.exception.UnAuthorityException;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.encode.SaltEncodeUtil;
import com.huimi.common.encode.SaltModel;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.MenuService;
import com.huimi.core.service.system.RoleService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dqw on 2015/11/3.
 */
@Configuration
public class AdminRealm extends AuthorizingRealm {

    protected final Logger L = Logger.getLogger(AdminRealm.class);


    @Resource
    private AdminService adminService;
    @Resource
    private RedisService redisService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;


    public String getName() {
        return "adminRealm";
    }

    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordCaptchaToken;
    }

    /**
     * 为当前登录的Subject授予角色和权限
     * 经测试:本例中该方法的调用时机为需授权资源被访问时
     * 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String name = (String) principals.getPrimaryPrincipal();
//        // 从数据库中获取登录用户
        Admin model = new Admin();
        model.setUsername(name);
        model.setDelFlag(GenericPo.DELFLAG.NO.code);
        Admin admin = adminService.selectOne(model);
        if (admin == null) {
            throw new AuthorizationException();
        }
        Role role = roleService.selectOne(new Role(t -> t.setId(Integer.valueOf(admin.getRoleIds()))));
        List<Menu> menuList = menuService.selectMenusByIds(role.getMenuIds());
        Set<String> permissionsSet = menuList.stream()
                .filter(menu -> StringUtils.isNotBlank(menu.getUrl()))
                .map(menu -> menu.getUrl().replaceAll("/", ":"))
                .collect(Collectors.toSet());
        // 获取登录用户的权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissionsSet);
        //若该方法什么都不做直接返回null的话,就会导致任何用户访问时都会自动跳转到unauthorizedUrl指定的地址
        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        // 两个token的引用都是一样的
        UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authenticationToken;
        String username = (String) token.getPrincipal();
        if (StringUtils.isBlank(token.getCredentials())) {
            throw new IncorrectCredentialsException();
        }
        String password = new String((char[]) token.getCredentials());
        // 验证用户输入的验证码
        String captcha = token.getCaptcha();
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute("captcha");
        // 是否开通万能验证码
        Integer superValidate = redisService.get(ConfigNID.SUPER_VALIDATE_OPEN, Integer.class);
        //获取配置的万能验证码
        String superValiDataCode = redisService.get(ConfigNID.SUPER_VALIDATE_OPEN_CODE);
        // 开通万能验证码，可使用万能验证码
        if ((superValidate != null && superValidate == 1) && superValiDataCode.equals(captcha)) {
            L.info("已开启万能验证码，图形验证码不进行校验！");
        } else {
            if (null == captcha || !captcha.equalsIgnoreCase(sessionCaptcha)) {
                throw new CaptchaException();
            }
        }

        Admin admin = adminService.selectOne(new Admin(a -> a.setUsername(username)));
        if (admin == null) {
            System.out.println("用户名：" + username);
            throw new UnknownAccountException(username);
        }
        SaltModel saltModel = SaltEncodeUtil.saltEncode(password, admin.getSalt());
        if (!saltModel.getPwd().equals(admin.getPwd())) {
            throw new IncorrectCredentialsException();
        }
        if (admin.getState() != 1) {
            throw new ForbiddenException();
        }

        Role role = roleService.selectOne(new Role(r -> {
            r.setId(Integer.valueOf(admin.getRoleIds()));
            r.setDelFlag(GenericPo.DELFLAG.NO.code);
        }));
        if (role == null) {
            throw new UnAuthorityException();
        }
        AdminSessionHelper.setAdminId(admin.getId());
        AdminSessionHelper.setAdminName(admin.getUsername());
        AdminSessionHelper.setAdminGroup(role.getName());
        AdminSessionHelper.setCurrAdmin(admin);

        /*此处无需比对, 比对的逻辑Shiro会做, 我们只需返回一个和令牌相关的正确的验证信息
        说白了就是第一个参数填登录用户名, 第二个参数填合法的登录密码
        这样一来, 在随后的登录页面上就只有这里指定的用户和密码才能通过验证*/
        return new SimpleAuthenticationInfo(username, password, getName());
    }


    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    private void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    private void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
