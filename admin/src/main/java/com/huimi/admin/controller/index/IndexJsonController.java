package com.huimi.admin.controller.index;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.admin.utils.AdminSessionHelper;

import com.huimi.core.constant.CacheID;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(BaseController.BASE_URI)
public class IndexJsonController extends GenericController {

    @Autowired
    private RedisService redisService;


    @Autowired
    private MenuService menuService;




    /**
     * 根据水平菜单id获取垂直菜单树
     */
    @ResponseBody
    @RequestMapping(value = "/getVerMenuTreeByHorId", method = RequestMethod.GET)
    public Object getVerticalMenuTreeByHorizontalId(String menuId) {
        Admin admin = AdminSessionHelper.getCurrAdmin();
        String json = null;
        if (null != admin) {
            //获取垂直菜单树 先从redis中获取，没有获取到则，查询数据库
            List<MenuModel> menuList = redisService.getList(CacheID.MENU_RIGHTS_CACHE + admin.getId() + ":" + menuId, MenuModel.class);

            if (menuList == null || menuList.size() <= 0) {
                menuList = menuService.getVerticalMenuTree(admin, menuId);
            }
            return menuList;
        } else {
            return fail();
        }
    }

}