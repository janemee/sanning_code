package com.huimi.admin.controller.comment;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.service.comment.CommentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * create by lja on 2020/7/30 15:21
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class CommentTemplateController extends BaseController {
    @Autowired
    private CommentTemplateService commentTemplateService;

    @RequestMapping("commentTemplate/list")
    public String list() {
        return getAdminTemplate("commentTemplate/list");
    }


    /**
     *  设备添加
     */
    @RequestMapping("commentTemplate/add")
    public String add() {
        return getAdminTemplate("commentTemplate/add");
    }

    /**
     * 编辑
     */
    @RequestMapping("commentTemplate/edit")
    public ModelAndView update(Long ids) {
        CommentTemplate commentTemplate = commentTemplateService.selectByPrimaryKey(ids);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("commentTemplate",commentTemplate);
        modelAndView.setViewName("commentTemplate/update");
        return modelAndView;
    }

}
