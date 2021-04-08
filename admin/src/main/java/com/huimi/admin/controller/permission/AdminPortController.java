package com.huimi.admin.controller.permission;

import com.huimi.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * create by lja on 2020/8/27 18:36
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class AdminPortController extends BaseController {


    /**
     * 管理员列表
     */
    @RequestMapping("adminPort/list")
    public String list() {
        return "system/admin/adminPortList";
    }
}
