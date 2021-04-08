package com.huimi.admin.controller.setting;

import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.core.po.system.Dict;
import com.huimi.core.service.system.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据字典Controller
 *
 * @author liweidong
 * @date 2017年03月15日 22:59
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class DictJsonController extends GenericController<Integer, Dict> {

    @Autowired
    DictService dictService;

    /**
     * 数据字典列表json数据
     *
     * @param dtGridPager
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/list")
    @RequiresPermissions("sys:dict:list")
    public DtGrid listJson(String dtGridPager) throws Exception {
        return dictService.getDtGridList(dtGridPager);
    }

    /**
     * 添加数据字典
     *
     * @param dict
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/add")
    @RequiresPermissions("sys:dict:save")
    public ResultEntity addJson(Dict dict) {
        dictService.insert(dict);
        return ok();
    }

    /**
     * 编辑数据字典
     *
     * @param dict
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/edit")
    @RequiresPermissions("sys:dict:edit")
    public ResultEntity editJson(Dict dict) {
        dictService.updateByPrimaryKeySelective(dict);
        return ok();
    }

    /**
     * 启用数据字典
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/open")
    @RequiresPermissions("sys:dict:open")
    public ResultEntity openJson(int id) throws Exception {
        Dict dict = new Dict();
        dict.setId(id);
        dict.setState(Dict.STATE.ENABLE.code);
        dictService.updateByPrimaryKeySelective(dict);
        return ok();
    }

    /**
     * 停用数据字典
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/close")
    @RequiresPermissions("sys:dict:close")
    public ResultEntity closeJson(int id) {
        Dict dict = new Dict();
        dict.setId(id);
        dict.setState(Dict.STATE.DISABLE.code);
        dictService.updateByPrimaryKeySelective(dict);
        return ok();
    }

    /**
     * 删除数据字典
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("dict/json/del")
    @RequiresPermissions("sys:dict:del")
    public ResultEntity delJson(int id) {
        dictService.removeById(new Dict(d -> d.setId(id)));
        return ok();
    }
}
