package com.huimi.core.model.system;


import com.huimi.core.po.system.Menu;

import java.util.List;

/**
 * 菜单树结构Model
 *
 * @author fengting
 * @date 2018-06-06 20:16
 */
public class MenuModel extends Menu {
    /**
     * 子菜单列表
     */
    private List<MenuModel> subMenu;

    public List<MenuModel> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuModel> subMenu) {
        this.subMenu = subMenu;
    }
}
