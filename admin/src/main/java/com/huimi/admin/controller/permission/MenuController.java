package com.huimi.admin.controller.permission;

import com.huimi.common.utils.StringUtils;
import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.system.Menu;
import com.huimi.core.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 菜单管理
 *
 * @author liweidong
 * @date 2017年03月05日 13:31
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    /**
     * 系统菜单列表
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/menu/list")
    public String list(ModelMap modelMap) {
        List<Menu> list = menuService.getTreeTableData();
        modelMap.addAttribute("menuList", list);
        return getAdminTemplate("system/menu/list");
    }

    /**
     * 添加系统菜单
     *测试
     * @param modelMap
     * @return
     */
    @RequestMapping("/menu/add")
    public String add(Integer ids, ModelMap modelMap) {
        if (!StringUtils.isBlank(ids)) {
            Menu currMenu = menuService.selectOne(new Menu(menu -> menu.setId(ids)));
            modelMap.addAttribute("menu", new Menu(menu -> {
                menu.setParentId(currMenu.getId());
                menu.setParentName(currMenu.getName());
            }));
        }
        return getAdminTemplate("system/menu/add");
    }

    /**
     * 添加系统菜单
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/menu/edit")
    public String edit(Integer ids, ModelMap modelMap) {
        if (!StringUtils.isBlank(ids)) {
            Menu currMenu = menuService.selectOne(new Menu(menu -> menu.setId(ids)));
            if (StringUtils.isBlank(currMenu.getParentName())) {
                currMenu.setParentName(null);
            }
            modelMap.addAttribute("menu", currMenu);
        }
        return getAdminTemplate("system/menu/update");
    }

    /**
     * 获取菜单图标集
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/menu/getIcons")
    public String getIcons(ModelMap modelMap) {
        return getAdminTemplate("system/menu/fontawesome");
    }
}
