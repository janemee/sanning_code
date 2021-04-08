package com.huimi.core.service.system;

import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 系统菜单管理相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface MenuService extends GenericService <Integer, Menu> {

    /**
     * 通过权限查询登录用户菜单
     *
     * @param admin 登录用户
     * @param pid   父级菜单ID
     */
    List <MenuModel> findByRights(Admin admin, int pid);

    List <MenuModel> getVerticalMenuTree(Admin admin, String menuId);

    void delMenuIncludeChild(int id);

    List <MenuModel> findByMenuIds(int roleId);

    List <Menu> selectMenusByIds(String menuIds);

    List <Menu> getTreeTableData();

    public List <Menu> sumAdminRights(String filter);

    StringBuilder getMenuIdsStr1(String roleIds);
}
