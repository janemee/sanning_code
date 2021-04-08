package com.huimi.admin.controller.setting;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.system.ConfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 后台参数配置
 *
 * @author liweidong
 * @date 2017年03月10日 15:28
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class ConfigureController extends BaseController {

    @Resource
    ConfService confService;

    /**
     * 参数配置列表
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/config/list")
    public String list(ModelMap modelMap) {
        return "system/conf/list";
    }

    /**
     * 全局控制列表
     */
    @RequestMapping("/global/list")
    public String globalList(ModelMap modelMap) {
        return ok("setting/global/list");
    }

    /**
     * 添加
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/config/add")
    public String add(ModelMap modelMap) {
        return "system/conf/add";
    }

    /**
     * 更新
     *
     * @param ids
     * @return
     */
    @RequestMapping("/config/edit")
    public ModelAndView update(Integer ids) {
        Conf conf = confService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("conf",conf);
        modelAndView.setViewName("system/conf/update");
        return modelAndView;
    }

}