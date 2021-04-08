package com.huimi.admin.controller.template;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.system.NoticeTemplate;
import com.huimi.core.service.system.NoticeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


@Controller
@RequestMapping(BaseController.BASE_URI)
public class NoticeTemplateController extends BaseController {

    @Resource
    NoticeTemplateService noticeTemplateService;

    /**
     * 通知记录
     *
     * @return
     */
    @RequestMapping("/noticeTemplate/list")
    public String list() {
        return ok("system/noticeTemplate/list");
    }

    /**
     * 添加
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/noticeTemplate/add")
    public String add(ModelMap modelMap) {
        return "system/noticeTemplate/add";
    }

    /**
     * 更新
     *
     * @param ids
     * @return
     */
    @RequestMapping("/noticeTemplate/edit")
    public ModelAndView update(Integer ids) {
        NoticeTemplate noticeTemplate = noticeTemplateService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("noticeTemplate", noticeTemplate);
        modelAndView.setViewName("system/noticeTemplate/update");
        return modelAndView;
    }
}
