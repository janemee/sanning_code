package com.huimi.admin.controller.equipmentRole;


import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.equipmentRole.EquipmentRole;
import com.huimi.core.po.system.Admin;
import com.huimi.core.req.EquipmentReq;
import com.huimi.core.req.EquipmentRoleModelReq;
import com.huimi.core.service.equipmentRole.EquipmentRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * 设备用户授权
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentRoleJsonController extends BaseController {

    @Resource
    private EquipmentRoleService equipmentRoleService;

    @ResponseBody
    @RequestMapping("equipmentRole/json/list")
    @RequiresPermissions(":s:equipment:list")
    public DtGrid listJson(EquipmentReq equipmentReq, HttpServletRequest request) {
        DtGrid dtGrid = new DtGrid();
        dtGrid.setNowPage(equipmentReq.getRows());
        dtGrid.setPageSize(equipmentReq.getPage());
        List equipmentLogList = equipmentRoleService.findByPage(equipmentReq);
        long count = equipmentRoleService.findByPageCount(equipmentReq);
        dtGrid.setRecordCount(count);
        dtGrid.setExhibitDatas(equipmentLogList);
        return dtGrid;
    }

    /**
     * 添加设备
     */
    @ResponseBody
    @RequestMapping("/equipmentRole/json/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(EquipmentRoleModelReq equipmentRoleModelReq) {
        try {
            equipmentRoleService.add(equipmentRoleModelReq);
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }


    }

    /**
     * 删除参数配置
     */
    @ResponseBody
    @RequestMapping("/equipmentRole/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            EquipmentRole equipmentRole = equipmentRoleService.selectByPrimaryKey(Integer.valueOf(id));
            if (equipmentRole != null) {
                equipmentRole.setUpdateTime(new Date());
                equipmentRole.setDelFlag(Admin.DELFLAG.YES.code);
                equipmentRoleService.updateByPrimaryKeySelective(equipmentRole);
            }
        }
        return ResultEntity.success("解除授权");
    }

}
