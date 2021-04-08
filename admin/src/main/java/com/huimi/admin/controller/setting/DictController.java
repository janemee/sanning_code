package com.huimi.admin.controller.setting;

import com.huimi.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据字典Controller
 *
 * @author liweidong
 * @date 2017年03月15日 22:58
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class DictController extends BaseController {


    /**
     * 资金模板列表
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/dict/list")
    public String list(ModelMap modelMap) {
        return ok("setting/dict/list");
    }
}
