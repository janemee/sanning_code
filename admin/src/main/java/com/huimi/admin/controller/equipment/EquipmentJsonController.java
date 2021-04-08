package com.huimi.admin.controller.equipment;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
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
 * create by lja on 2020/7/28 17:58
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class EquipmentJsonController extends BaseController {

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("equipment/json/list")
    @RequiresPermissions(":s:equipment:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        String search_val = request.getParameter("search_val");
        String search_val2 = request.getParameter("search_val2");
        String queryType = request.getParameter("queryType");
        String group_id = request.getParameter("groupId");
        String orderBySqlStr = "order by state desc, device_code asc, create_time DESC";
        //如果主判断条件有子判断条件不触发
        if (StringUtils.isNotBlank(search_val)) {
            search_val2 = search_val;
            queryType = EnumConstants.selectType.EQUIPMENTANAME.code;
            //从输入条件后一位截取全部字符
            int length = search_val.length() + 1;
            orderBySqlStr = " ORDER BY state desc, CONVERT(substring(device_code, " + length + ",CHAR_LENGTH(device_code)),SIGNED)  asc , id desc ";
        }
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.parseInt(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            whereSql.append(" and (");
            whereSql.append(" sys_admin_code = '").append(admin.getCode()).append("'");
            Admin findSonAdmin = new Admin();
            findSonAdmin.setParentId(adminId);
            //找到所有2级对象的邀请码
            List<Admin> sonAdminList = adminService.select(findSonAdmin);
            for (Admin son : sonAdminList) {
                whereSql.append("or sys_admin_code = '").append(son.getCode()).append("'");
            }
            whereSql.append(")");
        }
        if (StringUtils.isNotBlank(search_val2)) {
            if (queryType.equals(EnumConstants.selectType.EQUIPMENTANAME.code)) {
                whereSql.append(" and device_code like '%").append(search_val2).append("%'");
            } else if (queryType.equals(EnumConstants.selectType.EQUIPMENTGROUPNAME.code)) {
                whereSql.append("and name like '%").append(search_val2).append("%'");
            } else {
                whereSql.append("and username like '%").append(search_val2).append("%'");
            }
        }
        //分组查询条件
        if (StringUtil.isNotBlank(group_id)) {
            whereSql.append(" and group_id = " + group_id);
        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql(orderBySqlStr);
        dtGrid = equipmentService.findAll(dtGrid);
        return dtGrid;
    }

    /**
     * 添加设备
     */
    @ResponseBody
    @RequestMapping("/equipment/json/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(String code, String name, Integer groupId, Integer type, Integer state) {
        if (StringUtils.isBlank(code)) {
            return fail("参数不能为空");
        }
        if (StringUtils.isBlank(name)) {
            return fail("参数不能为空");
        }
        if (StringUtils.isBlank(type)) {
            return fail("参数不能为空");
        }
        if (StringUtils.isBlank(state)) {
            return fail("参数不能为空");
        }
        Equipment equipment = new Equipment();
        equipment.setDeviceUid(code);
        equipment.setDeviceCode(name);
        equipment.setSysAdminId(1);
        equipment.setGroupId(groupId);
        equipment.setType(type);
        equipment.setState(state);
        equipment.setLastTime(new Date());
        equipment.setDelFlag(GenericPo.DELFLAG.NO.code);

        try {
            equipmentService.insert(equipment);

            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }


    }


    @ResponseBody
    @RequestMapping("/equipment/json/saveOrUpdata")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(Equipment equipment) {
//        conf.setUuid(RandomUtils.randomCustomUUID());
        int insert = 0;
        if (equipment.getId() != null) {

            equipment.setSysAdminId(1);
            equipment.setLastTime(new Date());
            equipment.setDelFlag(GenericPo.DELFLAG.NO.code);
            insert = equipmentService.updateByPrimaryKeySelective(equipment);

        } else {
            equipment.setSysAdminId(1);
            equipment.setLastTime(new Date());
            equipment.setDelFlag(GenericPo.DELFLAG.NO.code);
            insert = equipmentService.insert(equipment);
        }

        return insert > 0 ? ResultEntity.success() : fail();
    }

    /**
     * 编辑参数配置
     */
    @ResponseBody
    @RequestMapping("/equipment/json/edit")
//    @RequiresPermissions("sys:equipment:edit")
    public ResultEntity editJson(Equipment equipment) {
        int insert = equipmentService.updateByPrimaryKeySelective(equipment);
        return insert > 0 ? ResultEntity.success() : fail();
    }


    /**
     * 删除参数配置
     */
    @ResponseBody
    @RequestMapping("/equipment/json/del")
//    @RequiresPermissions("sys:config:del")
    public ResultEntity delJson(String ids) {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] idArr = ids.split(",");
        int insert = 0;
        for (String id : idArr) {
            insert += equipmentService.deleteByPrimaryKey(Integer.valueOf(id));
        }
//        int insert = confService.removeById(new Conf(c -> c.setId(id)));
        return insert == idArr.length ? ResultEntity.success("删除成功") : fail();
    }

    /**
     * 批量分组
     */
    @ResponseBody
    @RequestMapping("/equipment/json/batchGrouping")
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
    @RequestMapping("/equipment/json/busy")
    public ResultEntity busy() {
        HashMap<String, Object> data = new HashMap<>();
        Integer adminId = AdminSessionHelper.getAdminId();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        int free;
        int busy;
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
     * 获取正在运行和空闲的设备数量 (根据分组)
     */
    @ResponseBody
    @RequestMapping("/equipment/json/groupBusy")
    public ResultEntity groupBusy(Integer groupId) {
        HashMap<String, Object> data = new HashMap<>();
//        int free = equipmentService.findWorkNumberByGroup(1, groupId);
//        int busy = equipmentService.findWorkNumberByGroup(0, groupId);
        data = equipmentService.findWorkNumberByGroup(groupId);

//        data.put("free", free);
//        data.put("busy", busy);
//        data.put("count", count);
        return ResultEntity.success("获取成功", data);
    }

    /**
     * 查看设备正在运行的详情
     */
    @ResponseBody
    @RequestMapping("/equipment/json/task")
    public DtGrid task(String ids) {
        DtGrid equipmentTask = equipmentService.findEquipmentTask(Integer.parseInt(ids), Integer.valueOf(EnumConstants.taskStatus.RUN.value));
        List<Object> exhibitDatas = equipmentTask.getExhibitDatas();
        exhibitDatas.removeIf(Objects::isNull);
        return equipmentTask;
    }
}
