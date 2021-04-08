package com.huimi.admin.controller.permission;

import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.system.MenuService;
import com.huimi.core.service.system.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MenuJsonController
 *
 * @author liweidong
 * @date 2017年03月05日 13:49
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class MenuJsonController extends GenericController<Integer, Menu> {

    @Resource
    MenuService menuService;
    @Resource
    RoleService roleService;

    /**
     * 功能菜单列表json数据
     */
    @ResponseBody
    @RequestMapping("/menu/json/list")
    @RequiresPermissions("sys:menu:list")
    public DtGrid listJson(String dtGridPager) throws Exception {
        return menuService.getDtGridList(dtGridPager);
    }

    /**
     * 添加功能菜单
     */
    @ResponseBody
    @RequestMapping("/menu/json/saveOrUpdate")
    /*@RequiresPermissions("sys:menu:save")*/
    public ResultEntity addJson(Menu menu) {
        if (null == menu) {
            return fail();
        }
        ResultEntity resultEntity = new ResultEntity();
        // 添加
        if (StringUtils.isBlank(menu.getId())) {
            if (menu.getParentId() != null && menu.getParentId() > 0) {
                Menu parent = menuService.selectOne(new Menu(m -> {
                    m.setId(menu.getParentId());
                    menu.setDelFlag(Menu.DELFLAG.NO.code);
                }));
                menu.setParentName(parent.getName());
                if (parent.getType() == 0) {
                    menu.setLevel(parent.getLevel() + 1);
                }
                parent.setIsParent(1);
                menuService.updateByPrimaryKeySelective(parent);
            } else {
                menu.setParentName("根节点");
                menu.setParentId(0);
            }
            menuService.insert(menu);
        } else {
            Menu currMenu = new Menu();
            currMenu.setId(menu.getId());
            currMenu.setName(menu.getName());
            currMenu.setType(menu.getType());
            currMenu.setDescription(menu.getDescription());
            currMenu.setUrl(menu.getUrl());
            currMenu.setImages(menu.getImages());
            currMenu.setSort(menu.getSort());
            menuService.updateByPrimaryKeySelective(currMenu);
        }
        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

    /**
     * 编辑功能菜单
     */
    @ResponseBody
    @RequestMapping("/menu/json/edit")
    @RequiresPermissions("sys:menu:edit")
    public ResultEntity editJson(Menu menu) {
        ResultEntity resultEntity = new ResultEntity();
        menuService.updateByPrimaryKeySelective(menu);
        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

    /**
     * 删除功能菜单（同时删除所有子菜单）
     */
    @ResponseBody
    @RequestMapping("/menu/json/delete")
    /*@RequiresPermissions("sys:menu:del")*/
    public ResultEntity delJson(int ids) {
        ResultEntity resultEntity = new ResultEntity();
        menuService.delMenuIncludeChild(ids);
        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

    /**
     * 获取所有菜单信息
     */
    @ResponseBody
    @RequestMapping(value = "/menu/json/allMenu", method = RequestMethod.GET)
    public ResultEntity getAllMenuList() throws Exception {
        Admin admin = AdminSessionHelper.getCurrAdmin();
        List<Role> adminRoles = roleService.selectByRoleIds(admin.getRoleIds());
        if (adminRoles == null || adminRoles.size() < 1) {
            throw new Exception();
        }
        //TODO 查询角色拥有的权限
        List<Menu> list = new ArrayList<>();

        return ok(list);
    }


    /**
     * 获取角色拥有的菜单ID列表
     */
    @ResponseBody
    @RequestMapping(value = "/menu/json/roleMenuIds", method = RequestMethod.GET)
    public ResultEntity roleMenuIds(int roleId) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(menuService.findByMenuIds(roleId));
        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

    /**
     * 获取角色拥有的菜单ID列表
     */
    @ResponseBody
    @RequestMapping(value = "/menu/json/saveSort")
    public ResultEntity saveSort(Integer[] id, Integer[] sort) {
        List<Menu> menuList = menuService.selectAll();
        for (int i = 0; i < id.length && i < sort.length; i++) {
            for (Menu menu : menuList) {
                if (Objects.equals(id[i], menu.getId())) {
                    menu.setSort(sort[i]);
                    menuService.updateByPrimaryKeySelective(menu);
                    break;
                }
            }
        }
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

}
