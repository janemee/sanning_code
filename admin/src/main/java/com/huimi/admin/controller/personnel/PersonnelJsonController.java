package com.huimi.admin.controller.personnel;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.personnel.Personnel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.personnel.PersonnelService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.users.UsersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.huimi.common.entity.ResultEntity.fail;


/**
 * 人员信息管理
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class PersonnelJsonController extends BaseController {

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;
    @Resource
    private UsersService usersService;
    @Resource
    private PersonnelService personnelService;

    @ResponseBody
    @RequestMapping("personnel/json/list")
    @RequiresPermissions(":s:equipment:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        StringBuilder whereSql = new StringBuilder();
        String search_val = request.getParameter("search_val");
        //如果主判断条件有子判断条件不触发
        if (StringUtils.isNotBlank(search_val)) {
            whereSql.append("and t.user_name like %" + search_val + "%  or t.phone like %" + search_val + "%");
        }
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.parseInt(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by t.id DESC");
        dtGrid = personnelService.selectByPage(dtGrid);
        return dtGrid;
    }

    /**
     * 添加用户
     */
    @ResponseBody
    @RequestMapping("/personnel/json/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(Personnel personnel) {
        personnel.setCreateTime(new Date());
        personnel.setDelFlag(GenericPo.DELFLAG.NO.code);
        try {
            personnelService.insert(personnel);
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }


    }


    @ResponseBody
    @RequestMapping("/personnel/json/edit")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity edit(Personnel personnel) {
        Personnel editPersonnel = personnelService.selectByPrimaryKey(personnel.getId());
        if (editPersonnel == null) {
            return fail("用户信息有误，请检查后重试");
        }
        editPersonnel.setName(personnel.getName());
        editPersonnel.setPhone(personnel.getPhone());
        editPersonnel.setWeigenCode(personnel.getWeigenCode());
        editPersonnel.setPicUrl(personnel.getPicUrl());
        editPersonnel.setUpdateTime(new Date());

        int insert = personnelService.updateByPrimaryKeySelective(editPersonnel);
        return insert > 0 ? ResultEntity.success() : fail();
    }


    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping("/personnel/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += personnelService.deleteByPrimaryKey(Integer.valueOf(id));
        }
        return insert == idArr.length ? ResultEntity.success("删除成功") : fail();
    }

    /**
     * 批量分组
     */
    @ResponseBody
    @RequestMapping("/personnel/json/batchGrouping")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity batchGroupingJson(String ids, String groupId) {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        Integer insert = equipmentService.updateBatchGrouping(groupId, idArr);
//        int insert = confService.removeById(new Conf(c -> c.setId(id)));
        return insert == idArr.length ? ResultEntity.success("更新成功") : fail();
    }

    /**
     * 获取正在运行和空闲的设备数量
     */
    @ResponseBody
    @RequestMapping("/personnel/json/busy")
    public ResultEntity busy() {
        HashMap<String, Object> data = new HashMap<>();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        int free = 0;
        int busy = 0;
        if (StringUtils.isNotBlank(admin.getParentId())) {
            free = equipmentService.findWorkNumber(1, adminId);
            busy = equipmentService.findWorkNumber(0, adminId);
        } else {
            free = equipmentService.findWorkNumber(1, null);
            busy = equipmentService.findWorkNumber(0, null);
        }
        data.put("free", free);
        data.put("busy", busy);
        return ResultEntity.success("获取成功", data);
    }

    /**
     * 查看设备正在运行的详情
     */
    @ResponseBody
    @RequestMapping("/personnel/json/task")
    public DtGrid task(String ids) {
        DtGrid equipmentTask = equipmentService.findEquipmentTask(Integer.parseInt(ids), Integer.valueOf(EnumConstants.taskStatus.RUN.value));
        List<Object> exhibitDatas = equipmentTask.getExhibitDatas();
        exhibitDatas.removeIf(Objects::isNull);
        return equipmentTask;
    }
}
