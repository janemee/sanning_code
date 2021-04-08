package com.huimi.admin.controller.users;


import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.MD5Utils;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.user.Users;
import com.huimi.core.service.equipment.EquipmentService;
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
 * create by lja on 2020/7/28 17:58
 */
@Controller
@RequestMapping(BaseController.BASE_URI)
public class UsersJsonController extends BaseController {

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AdminService adminService;
    @Resource
    private UsersService usersService;

    @ResponseBody
    @RequestMapping("users/json/list")
    @RequiresPermissions(":s:equipment:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        StringBuilder whereSql = new StringBuilder();
        String search_val = request.getParameter("search_val");
        String search_val2 = request.getParameter("search_val2");
        String queryType = request.getParameter("queryType");
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
        dtGrid = usersService.selectByPage(dtGrid);
        return dtGrid;
    }

    /**
     * 添加用户
     */
    @ResponseBody
    @RequestMapping("/users/json/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(Users users) {
        String pwd = "123456";
        String salt = MD5Utils.getMd5(pwd);
        users.setPassword(pwd);
        users.setSalt(salt);
        users.setCreateTime(new Date());
        users.setDelFlag(GenericPo.DELFLAG.NO.code);
        try {
            usersService.insert(users);
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }


    }


    @ResponseBody
    @RequestMapping("/users/json/saveOrUpdata")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity edit(Users users) {
        Users editUsers = usersService.selectByPrimaryKey(users.getId());
        if (editUsers == null) {
            return fail("用户信息有误，请检查后重试");
        }
        editUsers.setUserName(users.getUserName());
        editUsers.setPhone(users.getPhone());
        editUsers.setSex(users.getSex());
        editUsers.setEmail(users.getEmail());
        editUsers.setType(users.getType());
        editUsers.setUpdateTime(new Date());
        editUsers.setLastLoginDate(new Date());
        int insert = usersService.updateByPrimaryKeySelective(editUsers);
        return insert > 0 ? ResultEntity.success() : fail();
    }

    /**
     * 编辑参数配置
     */
    @ResponseBody
    @RequestMapping("/users/json/edit")
//    @RequiresPermissions("sys:equipment:edit")
    public ResultEntity editJson(Users users) {
        int insert = usersService.updateByPrimaryKeySelective(users);
        return insert > 0 ? ResultEntity.success() : fail();
    }


    /**
     * 删除参数配置
     */
    @ResponseBody
    @RequestMapping("/users/json/del")
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
    @RequestMapping("/users/json/batchGrouping")
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
    @RequestMapping("/users/json/busy")
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
    @RequestMapping("/users/json/task")
    public DtGrid task(String ids) {
        DtGrid equipmentTask = equipmentService.findEquipmentTask(Integer.parseInt(ids), Integer.valueOf(EnumConstants.taskStatus.RUN.value));
        List<Object> exhibitDatas = equipmentTask.getExhibitDatas();
        exhibitDatas.removeIf(Objects::isNull);
        return equipmentTask;
    }
}
