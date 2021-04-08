package com.huimi.admin.controller.equipmentLog;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.system.Admin;
import com.huimi.core.req.BasePageReq;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.equipmentLog.EquipmentLogService;
import com.huimi.core.service.system.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.huimi.common.entity.ResultEntity.fail;

@Controller
@RequestMapping(BaseController.BASE_URI+"/equipmentLog/json")
public class EquipmentLogJsonController extends BaseController {

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;
    @Resource
    private EquipmentLogService equipmentLogService;

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(":s:equipmentLog:list")
    public DtGrid listJson(BasePageReq basePageReq) {
        DtGrid dtGrid = new DtGrid();
        dtGrid.setNowPage(basePageReq.getRows());
        dtGrid.setPageSize(basePageReq.getPage());
        List equipmentLogList = equipmentLogService.findByPage(basePageReq);
        long count = equipmentLogService.findByPageCount(basePageReq);
        dtGrid.setRecordCount(count);
        dtGrid.setExhibitDatas(equipmentLogList);
        return dtGrid;
    }
}
