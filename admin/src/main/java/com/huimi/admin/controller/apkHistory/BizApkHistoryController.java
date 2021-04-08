package com.huimi.admin.controller.apkHistory;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.service.apkHistory.BizApkHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件上传历史
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/apkHistory")
public class BizApkHistoryController extends BaseController {
    @Autowired
    private BizApkHistoryService bizApkHistoryService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("apkHistory/list");
    }


    @RequestMapping("/add")
    public String add() {
        return getAdminTemplate("apkHistory/add");
    }

    @RequestMapping("/update")
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BizApkHistory bizApkHistory = bizApkHistoryService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bizApkHistory);
        modelAndView.setViewName("apkHistory/update");
        return modelAndView;

    }

    /**
     * 跳转查看二维码图片页面
     *
     * @param ids
     * @return
     */
    @RequestMapping("/lookQeCodeImg")
    public ModelAndView lookQeCodeImg(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BizApkHistory bizApkHistory = bizApkHistoryService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bizApkHistory);
        modelAndView.setViewName("apkHistory/qrCodeImg");
        return modelAndView;
    }
}
