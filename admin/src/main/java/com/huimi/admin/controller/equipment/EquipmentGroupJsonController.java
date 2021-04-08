package com.huimi.admin.controller.equipment;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * create by lja on 2020/7/30 13:04
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentGroupJsonController extends BaseController {

    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("equipmentGroup/json/list")
    @RequiresPermissions(":s:equipmentGroup:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.parseInt(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            whereSql.append(" and state = ").append(EnumConstants.HistoryState.YES.value).append(" or ").append(" sys_admin_id = ").append(adminId);
        }
        String search_val = request.getParameter("search_val");
        if (StringUtils.isNotBlank(search_val)) {
            whereSql.append(" and name like '%").append(search_val).append("%'");
        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return equipmentGroupService.getDtGridList(dtGrid);
    }


    @ResponseBody
    @RequestMapping("/equipmentGroup/json/saveOrUpdate")
    public ResultEntity addJson(EquipmentGroup equipmentGroup) {
        if (CollectionUtils.isNotEmpty(equipmentGroupService.select(equipmentGroup))) {
            return fail("分组名称已存在");
        }

        equipmentGroup.setDelFlag(GenericPo.DELFLAG.NO.code);
        equipmentGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Integer adminId = AdminSessionHelper.getAdminId();
        equipmentGroup.setSysAdminId(adminId);
        return equipmentGroupService.insert(equipmentGroup) > 0 ? ResultEntity.success("添加成功") : fail("添加失败");
    }

    /**
     * 编辑设备分组
     */
    @ResponseBody
    @RequestMapping("/equipmentGroup/json/edit")
//    @RequiresPermissions("sys:config:edit")
    public ResultEntity editJson(EquipmentGroup equipmentGroup) {
        equipmentGroup.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int insert = equipmentGroupService.updateByPrimaryKeySelective(equipmentGroup);
        return insert > 0 ? ResultEntity.success() : fail();
    }


    /**
     * 删除分组
     */
    @ResponseBody
    @RequestMapping("/equipmentGroup/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity del(String ids) {
        if (StringUtils.isBlank(ids)) {
            return fail("请选择需要删除的分组信息");
        }
        try {
            equipmentService.deleteByIds(ids);
            return ResultEntity.success("删除成功");
        } catch (Exception e) {
            return fail(e.getMessage());
        }


    }
}
