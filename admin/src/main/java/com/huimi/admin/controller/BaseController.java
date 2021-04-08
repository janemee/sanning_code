package com.huimi.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.annotation.SysLog;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigConsts;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面转跳类controller基础实现
 */
@Slf4j
public class BaseController {

    @Autowired
    AdminService adminService;


    protected static final String ADMIN_TEMPLATE_ROOT = "";
    /**
     * 接口根路径
     */
    public static final String BASE_URI = "/s";

    @Autowired
    protected RedisService redisService;

    private String path = "";

    /**
     * 手工设置选中菜单
     */
    public void setMenuPath(String path) {
        this.path = path;
    }

    protected String ok(String template) {
        String path = ADMIN_TEMPLATE_ROOT + template;
        log.info(path);
        return path;
    }


    /**
     * 过滤表单
     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder, WebRequest request) {
//        myWebBindingInitializer.initBinder(binder, request);
//    }
    protected String getAdminTemplate(String template) {
        log.info(ADMIN_TEMPLATE_ROOT + template);
        return ADMIN_TEMPLATE_ROOT + template;
    }

    /**
     * 管理员用户id
     */
    @SysLog(false)
    @ModelAttribute("adminId")
    public Integer getAdminId() {
        return AdminSessionHelper.getAdminId();
    }

    /**
     * 管理员用户名
     */
    @SysLog(false)
    @ModelAttribute("adminName")
    public String getAdminName() {
        return AdminSessionHelper.getAdminName();
    }

    /**
     * 管理员组
     */
    @SysLog(false)
    @ModelAttribute("adminGroupName")
    public String getAdminGroupName() {
        return AdminSessionHelper.getAdminGroup();
    }

    /**
     * 管理员头像URl
     */
    @SysLog(false)
    @ModelAttribute("adminAvatarUrl")
    public String getAdminAvatarUrl() {
        String avatarUrl = AdminSessionHelper.getAdminAvatarUrl();
        if (StrUtil.isBlank(avatarUrl)) avatarUrl = null;
        return avatarUrl;
    }

    /**
     * 后台菜单
     */
    @SysLog(false)
    @ModelAttribute("adminMainMenu")
    public List<MenuModel> getMenu() {
        return AdminSessionHelper.getAdminMenu();
    }


    /**
     * 前台根路径
     */
    @SysLog(false)
    @ModelAttribute("webRoot")
    public String webRoot() {
        return redisService.get(ConfigNID.ADMIN_SEVER_URL);
    }

    /**
     * 前台根路径
     */
    @SysLog(false)
    @ModelAttribute("copyRight")
    public String copyRight() {
        return redisService.get(ConfigNID.COPY_RIGHT);
    }

    /**
     * 网站名称
     */
    @SysLog(false)
    @ModelAttribute("webName")
    public String webName() {
        return redisService.get(ConfigNID.WEB_NAME);
    }

    /**
     * 后台根路径
     */
    @SysLog(false)
    @ModelAttribute("adminRoot")
    public String adminRoot() {
        return redisService.get(ConfigNID.ADMIN_SEVER_URL);
    }

    /**
     * 文件服务器路径
     */
    @SysLog(false)
    @ModelAttribute("fileRoot")
    public String fileRoot() {
        return redisService.get(ConfigNID.IMAGE_SEVER_URL);
    }

    /**
     * 后台JS路径
     */
    @SysLog(false)
    @ModelAttribute("jsRoot")
    public String jsRoot() {
        return redisService.get(ConfigNID.STATIC_SERVER_URL) + "static/js/";
    }

    /**
     * 后台CSS路径
     */
    @SysLog(false)
    @ModelAttribute("cssRoot")
    public String cssRoot() {
        return redisService.get(ConfigNID.STATIC_SERVER_URL) + "static/css/";
    }

    /**
     * 后台图片资源路径
     */
    @SysLog(false)
    @ModelAttribute("imgRoot")
    public String imgRoot() {
        return redisService.get(ConfigNID.STATIC_SERVER_URL) + "ui/img/";
    }

    /**
     * 后台插件路径
     */
    @SysLog(false)
    @ModelAttribute("pluginsRoot")
    public String pluginsRoot() {
        return redisService.get(ConfigNID.STATIC_SERVER_URL) + "static/plugins/";
    }

    /**
     * 公共资源路径
     */
    @SysLog(false)
    @ModelAttribute("publicRoot")
    public String publicRoot() {
        return redisService.get(ConfigNID.STATIC_SERVER_URL) + "public/";
    }

    /**
     * 添加RSA公钥
     */
    @SysLog(false)
    @ModelAttribute("publicKey")
    public String publicKey() {
        return redisService.get(ConfigConsts.PUBLIC_KEY);
    }

    /**
     * 后台顶部代办事项提醒
     */
    @SysLog(false)
    @ModelAttribute("countPrompt")
    public HashMap<String, Long> countPrompt() {
        HashMap<String, Long> map = new HashMap<>();
        // 消息数量
        long msgCount = 0L;

        return map;
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler({UnavailableSecurityManagerException.class})
    public String unavailableSecurityManagerException(UnavailableSecurityManagerException e) {
        return "redirect:/";
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler({Exception.class})
    public String exception(Exception e) {
        e.printStackTrace();
        return "redirect:/";
    }

    protected void getFastSearchParameter(HttpServletRequest request, Map<String, String> map) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> kv : parameterMap.entrySet()) {
                String k = kv.getKey();
                String v = kv.getValue()[0];
                if (null != v && !"".equals(v) && k.startsWith("*")) {
                    map.put(k.substring(1), v);
                }
            }
        } catch (Exception e) {
            map.clear();
        }
    }

    protected void getFastSearchWhereSql(HttpServletRequest request, StringBuilder whereSql) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> keys : parameterMap.entrySet()) {
            String key = keys.getKey();
            String value = keys.getValue()[0];
            if (value == null || "".equals(value) || !key.startsWith("*")) {
                continue;
            }
            whereSql.append(" and ");
            if (key.startsWith("*_")) {
                whereSql.append(key.substring(2)).append(" like '%").append(value).append("%' ");
            } else if (key.startsWith("*start_")) {
                whereSql.append("DATE_FORMAT(t.").append(key.substring(7)).append(",'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            } else if (key.startsWith("*end_")) {
                whereSql.append("DATE_FORMAT(t.").append(key.substring(5)).append(",'%Y-%m-%d')").append(" <= ").append("'").append(value).append("'");
            } else if (key.startsWith("*=")) {
                whereSql.append("t.").append(key.substring(2)).append(" = ").append(value);
            }else if (key.startsWith("*dateStart_")) {
                whereSql.append("DATE_FORMAT(t.create_time,'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            }else if (key.startsWith("*dateEnd_")) {
                whereSql.append("DATE_FORMAT(t.create_time,'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            }else {
                whereSql.append("t.").append(key.substring(1)).append(" like '%").append(value).append("%' ");
            }
        }
    }

//    protected void checkDevice(String deviceStyle, String[] equipmentGroups, String[] equipments) {
//        if(StringUtils.isBlank(deviceStyle)) {
//            throw new BussinessException("设备方式请选择");
//        }
//        if (StringUtils.isBlank(equipmentGroups) && StringUtils.isBlank(equipments)){
//            throw new BussinessException("请选择设备或者分组");
//        }

//    }
}
