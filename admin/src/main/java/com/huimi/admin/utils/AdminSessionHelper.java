package com.huimi.admin.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huimi.common.utils.JsonUtils;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.List;

/**
 * Created by dqw on 2015/12/29.
 */
public class AdminSessionHelper {
    /**
     * session前缀
     */
    private static String prefix = "session_";

    public static Integer getAdminId() {
        Session session = SecurityUtils.getSubject().getSession();
        Object adminId = session.getAttribute(prefix + "adminId");
        if (null != adminId) {
            return (Integer) adminId;
        } else {
            return null;
        }
    }

    public static void setAdminId(int adminId) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "adminId", adminId);
    }

    public static String getAdminName() {
        Session session = SecurityUtils.getSubject().getSession();
        return (String) session.getAttribute(prefix + "adminName");
    }

    public static void setAdminName(String adminName) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "adminName", adminName);
    }

    public static String getAdminGroup() {
        Session session = SecurityUtils.getSubject().getSession();
        return (String) session.getAttribute(prefix + "adminGroup");
    }

    public static void setAdminGroup(String adminGroup) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "adminGroup", adminGroup);
    }

    public static String getAdminAvatarUrl() {
        Session session = SecurityUtils.getSubject().getSession();
        return (String) session.getAttribute(prefix + "adminAvatarUrl");
    }

    public static void setAdminAvatarUrl(String adminAvatar) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "adminAvatarUrl", adminAvatar);
    }

    public static List<MenuModel> getAdminMenu() {
        Session session = SecurityUtils.getSubject().getSession();
        return JsonUtils.toGenericObject((String) session.getAttribute(prefix + "adminMenu"), new TypeReference<List<MenuModel>>() {
        });
    }

    public static void setAdminMenu(List<Menu> adminMenu) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "adminMenu", JsonUtils.toJson(adminMenu));
    }

    public static void setCurrAdmin(Admin admin) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(prefix + "currAdmin", admin);
    }

    public static Admin getCurrAdmin() {
        Session session = SecurityUtils.getSubject().getSession();
        return (Admin) session.getAttribute(prefix + "currAdmin");
    }

    public static void setSession(String key, String val) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(key, val);
    }

    public static String getSession(String key) {
        Session session = SecurityUtils.getSubject().getSession();
        Object obj = session.getAttribute(key);
        return obj == null ? null : obj.toString();
    }

}

