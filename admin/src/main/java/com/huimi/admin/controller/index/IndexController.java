package com.huimi.admin.controller.index;

import cn.hutool.core.date.DateUtil;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;


import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigConsts;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.service.apkHistory.BizApkHistoryService;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(BaseController.BASE_URI)
public class IndexController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private MenuService menuService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;
    @Autowired
    private BizApkHistoryService bizApkHistoryService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(BASE_URI + "/main", true, false));
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("statTime", DateUtil.now());
        modelMap.put("update", "");
//        return ok("bussiness/chart/chart");
        return getAdminTemplate("index");
    }

    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public ModelAndView content() {
        ModelAndView modelAndView = new ModelAndView();
        //获取启用的apk下载二维码图片路径
        BizApkHistory bizApkHistory = bizApkHistoryService.findByStateOne(EnumConstants.HistoryState.YES.value);
        if (bizApkHistory == null) {
            modelAndView.addObject("qrCodeUrlMsg", "请在站点管理->系统管理->apk文件管理中上传apk文件");
            modelAndView.addObject("qrCodeUrl", "");
        } else {
            modelAndView.addObject("qrCodeUrlMsg", "");
            modelAndView.addObject("qrCodeUrl", bizApkHistory.getQrCodeUrl());
        }
        //网站名称
        modelAndView.addObject("webName", redisService.get(ConfigNID.WEB_NAME));
        modelAndView.addObject("favIcoUrl",redisService.get(ConfigNID.FAV_ICO_URL));
        modelAndView.setViewName("content");
        return modelAndView;
    }

    /**
     * 首页
     */
    @RequestMapping(value = BASE_URI + "/main", method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        // cookie认证自动登陆处理
        Admin admin = AdminSessionHelper.getCurrAdmin();
        if (null != admin) {
            //获取水平菜单 即一级菜单
            List<MenuModel> horizontalMenuList = menuService.findByRights(admin, 0);

            if (null != horizontalMenuList) {
                if (horizontalMenuList.size() > 0) {
                    //获取垂直菜单树 先从redis中获取，没有获取到则，查询数据库
                    List<MenuModel> menuList = redisService.getList(CacheID.MENU_RIGHTS_CACHE + admin.getId() + ":" +
                            horizontalMenuList.get(0).getId(), MenuModel.class);

                    if (menuList == null || menuList.size() <= 0) {
//                menuList = menuService.findByRights(admin, horizontalMenuList.get(0).getId());
                        menuList = menuService.getVerticalMenuTree(admin, horizontalMenuList.get(0).getId().toString());
                    }
                    modelAndView.addObject("menuList", menuList);
                }
            }
            //获取纵向菜单树
            modelAndView.addObject("transverseMenuList", horizontalMenuList);
            Integer subEquipmentNum = adminService.freeEquipmentNum(admin);
            Integer byAdminId = equipmentService.findByAdminId(admin.getId());
            //  管理员页面展示数据不一样
            if (StringUtils.isBlank(admin.getParentId())) {
                List<Equipment> equipmentList = equipmentService.selectAll();
                modelAndView.addObject("maxEquipment", equipmentList.size());
                modelAndView.addObject("useEquipment", equipmentService.findByAgentEquipment());
                modelAndView.addObject("freeEquipment", "无限量");
            } else if (StringUtils.isNotBlank(subEquipmentNum)) {
                modelAndView.addObject("maxEquipment", admin.getPorts());
                //查找一级代理商的空闲设备数
                int useEquipment = admin.getPorts() - subEquipmentNum + byAdminId;
                modelAndView.addObject("useEquipment", useEquipment);
                modelAndView.addObject("freeEquipment", admin.getPorts() - useEquipment);
            } else {

                modelAndView.addObject("maxEquipment", admin.getPorts());
                modelAndView.addObject("useEquipment", byAdminId);
                modelAndView.addObject("freeEquipment", admin.getPorts() - byAdminId);
            }
            modelAndView.addObject("currAdmin", admin);

            //开启异步线程，保存该用户的菜单权限到redis中
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(() -> {
                try {
                    //获取水平菜单 即一级菜单
                    List<MenuModel> horizontalMenuList1 = menuService.findByRights(admin, 0);
                    for (Menu m : horizontalMenuList1) {
                        //获取垂直菜单树
                        List<MenuModel> menuList1 = menuService.findByRights(admin, m.getId());
                        //保存权限菜单数据到redis,有效期为15天
                        redisService.put(CacheID.MENU_RIGHTS_CACHE + admin.getId() + ":" + m.getId(), menuList1, 15 * 24 * 3600);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            modelAndView.setViewName("index");
        } else {
            modelAndView.setView(new RedirectView(BASE_URI + "/main", true, false));
        }
        modelAndView.addObject("logUrl", redisService.get(ConfigNID.WEB_LOG_IMG_URL));
        modelAndView.addObject("copyRight",redisService.get(ConfigNID.COPY_RIGHT));
        modelAndView.addObject("companyName",redisService.get(ConfigNID.COMPANY_NAME));
        modelAndView.addObject("companyIndexUrl",redisService.get(ConfigNID.COMPANY_INDEX_URL));
        modelAndView.addObject("systemTitle",redisService.get(ConfigNID.SYSTEM_TITLE));
        modelAndView.addObject("favIcoUrl",redisService.get(ConfigNID.FAV_ICO_URL));
        return modelAndView;

    }
}