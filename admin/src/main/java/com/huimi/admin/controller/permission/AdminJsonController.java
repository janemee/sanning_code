package com.huimi.admin.controller.permission;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.encode.SaltEncodeUtil;
import com.huimi.common.encode.SaltModel;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.BigDecimalUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.model.system.AdminModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Conf;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.ConfService;
import com.huimi.core.service.system.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 后台管理员
 */
@Slf4j
@Controller
@RequestMapping(BaseController.BASE_URI)
public class AdminJsonController extends GenericController<Integer, Admin> {

    @Resource
    AdminService adminService;
    @Resource
    ConfService confService;
    @Resource
    RoleService roleService;
    @Resource
    EquipmentService equipmentService;

    /**
     * 管理员列表json数据
     */
    @ResponseBody
    @RequestMapping("admin/json/list")
    @RequiresPermissions(":s:admin:list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.valueOf(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.valueOf(request.getParameter("page"));
        String search_val = request.getParameter("search_val");
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();

        //判断是否是一级代理商
        Integer adminId = this.findAdmin();
        Admin resultAdmin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(resultAdmin.getParentId())) {
            Integer parentId = Integer.valueOf(resultAdmin.getParentId());
            Admin parentAdmin = adminService.selectByPrimaryKey(parentId);
            if (StringUtils.isBlank(parentAdmin.getParentId())) {
                whereSql.append("and parent_id = '").append(adminId).append("'").append(" or id = ").append(adminId);
            } else {
                whereSql.append("and id =").append(adminId);
            }
        }
        if (!StringUtils.isBlank(search_val)) {
            whereSql.append(" and (username like '%").append(search_val).append("%' or ").append("mobile like '%").append(search_val).append("%')");
        }
        dtGrid.setWhereSql(whereSql.toString());
        DtGrid result = adminService.getDtGridList(dtGrid);
        List<Role> roleList = roleService.selectAll();
        result.getExhibitDatas().forEach(o -> {
            Map<String, Object> admin = (Map) o;
            if (!StringUtils.isBlank(admin.get("roleIds"))) {
                roleList.forEach(role -> {
                    if (role.getId().toString().equals(admin.get("roleIds"))) {
                        admin.put("roleName", role.getName());
                    }
                });
            }
            if (!StringUtils.isBlank(admin.get("parentId"))) {
                //直接根据id查set进去
                Object parentIdObject = admin.get("parentId");
                int parentId = Integer.parseInt(String.valueOf(parentIdObject));
                Admin parentAdmin = adminService.selectByPrimaryKey(parentId);
                admin.put("parentName", parentAdmin.getUsername());
            }
        });
        return result;
    }

    /**
     * 添加管理员
     */
    @ResponseBody
    @RequestMapping("admin/json/add")
    public ResultEntity addJson(String username, String fullname, String pwd, String password2, String mobile, Integer sex, Integer ports) throws Exception {
        if (StringUtils.isBlank(username)) {
            return fail("请输入用户名");
        }
        if (StringUtils.isBlank(fullname)) {
            return fail("请输入全称");
        }
        if (StringUtils.isBlank(pwd)) {
            return fail("请输入密码");
        }
        if (StringUtils.isBlank(password2)) {
            return fail("请输入确认密码");
        }
        if (StringUtils.isBlank(sex)) {
            return fail("请选择性别");
        }
        if (ports == null || ports <= 0) {
            return fail("请填写设备最大数量");
        }
        if(StringUtils.isBlank(mobile)){
            return fail("请输入手机号");
        }
        if (!pwd.equals(password2)) {
            return fail("两次密码不一致，请重新输入");
        }

        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            Integer subEquipmentNum = adminService.freeEquipmentNum(admin);
            //如果没有2级代理商
            if (StringUtils.isBlank(subEquipmentNum)) {
                subEquipmentNum = admin.getPorts();
            }
            Integer adminEquipmentNum = equipmentService.findByAdminId(adminId);
            if (ports > subEquipmentNum - adminEquipmentNum) {
                return fail("你的可用设备数量不足");
            }
        }

        try {
            adminService.createOne(username, fullname, pwd, mobile, sex, ports);
            return ok();
        } catch (BussinessException e) {
            log.error("{}", e.getMessage(), e);
            return fail(e.getMessage());
        }

    }

    /**
     * 编辑管理员
     */
    @ResponseBody
    @RequestMapping("/admin/json/edit")
    public ResultEntity editJson(AdminModel admin) throws Exception {
        if ((StringUtils.isBlank(admin.getPwd()) && !StringUtils.isBlank(admin.getPassword2())) ||
                (!StringUtils.isBlank(admin.getPwd()) && StringUtils.isBlank(admin.getPassword2()))) {
            return fail("请输入登录密码或重复密码");
        } else if (!StringUtils.isBlank(admin.getPwd()) && !StringUtils.isBlank(admin.getPassword2())) {
            if (!admin.getPwd().equals(admin.getPassword2())) {
                return fail("两次密码不一致，请重新输入");
            }
        }
        Admin sameNameAdmin = adminService.selectOne(new Admin(admin1 -> {
            admin1.setUsername(admin.getUsername());
        }));
        if (sameNameAdmin != null && !sameNameAdmin.getId().equals(admin.getId())) {
            return fail("用户名已被使用");
        }
        Admin currAdmin = adminService.findById(admin.getId());
        SaltModel saltModel = SaltEncodeUtil.saltEncode(admin.getPwd(), currAdmin.getSalt());
        admin.setPwd(saltModel.getPwd());
        return adminService.updateByPrimaryKeySelective(admin) > 0 ? ok() : fail();
    }

    /**
     * 管理员编辑资料
     */
    @ResponseBody
    @RequestMapping("admin/json/profile")
    public ResultEntity profileJson(Admin admin) throws Exception {
        ResultEntity resultEntity = new ResultEntity();

        resultEntity.setCode(ResultEntity.SUCCESS);
        return resultEntity;
    }

    /**
     * 删除管理员
     */
    @ResponseBody
    @RequestMapping("admin/json/del")
    @RequiresPermissions("sys:admin:del")
    public ResultEntity delJson(int id) throws Exception {
        if (id == 1) {
            return fail("超级管理员不允许删除");
        }
        return adminService.deleteByPrimaryKey(id) > 0 ? ok() : fail();
    }

    /**
     * 更新用户角色
     */
    @ResponseBody
    @RequestMapping("admin/json/updateRole")
    public ResultEntity updateRole(String ids, String roleids) {
        Admin currAdmin = adminService.selectOne(new Admin(admin -> admin.setId(Integer.valueOf(ids))));
        if (StringUtils.isNotBlank(currAdmin.getRoleIds())) {
            return fail("已经有权限的角色无权改变权限");
        }
        Role role = new Role();
        role.setName(EnumConstants.roleName.ADMIN.desc);
        Role testRole = roleService.selectOne(role);
        Conf conf = new Conf();
        conf.setNid(EnumConstants.invitationCode.INVITATION_CODE.code);
        Conf resultConf = confService.selectOne(conf);
        if (roleids.equals(testRole.getId().toString())) {
            return fail("不能授权为管理员");
        } else {
            currAdmin.setRoleIds(roleids);
        }


        return adminService.updateAdmin(currAdmin) > 0 ? ok() : fail();
    }

    /**
     * 修改密码
     */
    @ResponseBody
    @RequestMapping("admin/json/modifyDefaultPwd")
    public ResultEntity modifyDefaultPwd(String ids, String ports) {
        if (StringUtils.isBlank(ids) || StringUtils.isBlank(ports)) {
            return fail();
        }
        Admin admin = adminService.selectOne(new Admin(admin1 -> {
            admin1.setId(Integer.valueOf(ids));
        }));
        SaltModel saltModel = SaltEncodeUtil.saltEncode(ports, admin.getSalt());
        admin.setPwd(saltModel.getPwd());
        return adminService.updateAdmin(admin) > 0 ? ok() : fail();
    }

    /**
     * 禁用后台用户
     */
    @ResponseBody
    @RequestMapping("admin/json/forbidden")
    public ResultEntity forbidden(String ids) {
        Integer adminId = this.findAdmin();

        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        if (adminId.equals(Integer.parseInt(ids))) {
            return fail("无法禁用自己");
        }
        String[] id = ids.split(",");
        List<Admin> adminList = adminService.selectAll();
        for (String currId : id) {
            for (Admin admin : adminList) {
                if (admin.getId().toString().equals(currId)) {
                    admin.setState(0);
                    adminService.updateAdmin(admin);
                    break;
                }
            }
        }
        return ok();
    }

    /**
     * 禁用后台用户
     */
    @ResponseBody
    @RequestMapping("admin/json/startUsing")
    public ResultEntity startUsing(String ids) {
        if (StringUtils.isBlank(ids)) {
            return fail();
        }
        String[] id = ids.split(",");
        List<Admin> adminList = adminService.selectAll();
        for (String currId : id) {
            for (Admin admin : adminList) {
                if (admin.getId().toString().equals(currId)) {
                    admin.setState(1);
                    adminService.updateAdmin(admin);
                    break;
                }
            }
        }
        return ok();
    }

    /**
     * 更新或者保存
     * 2016年1月21日 下午7:45:42
     */
    public ResultEntity saveOrUpdate(Admin admin, String passwd, String confirmPassword) {
        Admin currAdmin = AdminSessionHelper.getCurrAdmin();
        boolean b = adminService.saveOrUpdate(admin, passwd, confirmPassword, currAdmin);

        return b ? ok() : fail();
    }

    /**
     * 用户删除（软删除）
     * 2015年12月4日 下午3:47:45
     */
    public ResultEntity delete(Integer id) throws BussinessException {

        return adminService.deleteById(id) > 0 ? ok() : fail();
    }

    /**
     * 分配子用户端口数量
     */
    @PostMapping("system/admin/json/assignEquipment")
    @ResponseBody
    public ResultEntity assignEquipment(Integer ports, String username) {
        return adminService.updatePorts(ports, username) > 0 ? ok() : fail();
    }

    /**
     * 修改密码
     */
    @PostMapping("system/admin/modifyPassword")
    @ResponseBody
    public ResultEntity modifyPassword(String oldPwd, String newPwd, String newConfirmPwd) {
        if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd) || StringUtils.isBlank(newConfirmPwd)) {
            return fail("参数错误");
        }
        Admin currAdmin = AdminSessionHelper.getCurrAdmin();
        Admin sysAdmin = adminService.findById(currAdmin.getId());
        if (sysAdmin == null) {
            return fail("用户不存在");
        }
        SaltModel saltModel = SaltEncodeUtil.saltEncode(oldPwd, sysAdmin.getSalt());
        if (saltModel == null) {
            return fail("加密出错");
        }
        if (!sysAdmin.getPwd().equals(saltModel.getPwd())) {
            return fail("原密码不正确，请重新输入");
        }
        if (!newPwd.equals(newConfirmPwd)) {
            return fail("两次密码不一致，请重新输入");
        }
        SaltModel newSaltModel = SaltEncodeUtil.saltEncode(newPwd, sysAdmin.getSalt());
        if (saltModel == null) {
            return fail("加密出错");
        }
        int c1 = adminService.update(new Admin(t -> {
            t.setPwd(newSaltModel.getPwd());
            t.setId(sysAdmin.getId());
        }));
        if (c1 != 1) {
            return fail("修改失败");
        }
        return ok();
    }

    /**
     * 查询用户id
     *
     * @return
     */

    public Integer findAdmin() {
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return resultAdmin.getId();
    }
    /*
     *//**
     * 管理员个人信息页面
     *
     * @author xxx
     *//*
    public void toPersonalInfoPage() {
        Admin administrator = getCurrAdmin(); // cookie认证自动登陆处理
        if (null != administrator) {
            String roleIds = administrator.getRoleIds();
            StringBuilder roleNames = new StringBuilder();
            if (StrKit.notBlank(roleIds)) {
                String ids[] = roleIds.split(",");
                for (String s : ids) {
                    roleNames.append(sysRoleService.cacheGet(s).getName()).append(",");
                }
            }
            administrator.put("rolenames", roleNames.substring(0, roleNames.lastIndexOf(",")));
            SysAdminInfo administratorInfo = sysAdministratorInfoService.findByAdminId(administrator.getId());
            setAttr("admin", administrator);
            setAttr("adminInfo", administratorInfo);
            render("personalInfo.html");
        }
    }

    *//**
     * 修改管理员密码页面
     *
     * @author xxx
     *//*
    public void toModifyPwdPage() {
        render("modifyPwd.html");
    }

    *//**
     * 修改管理员密码
     *
     * @author xxx
     *//*
    public void modifyPassword() {
        SysAdmin administrator = getCurrAdmin(); // cookie认证自动登陆处理
        if (null != administrator) {
            String oldPwd = getPara("oldPwd");
            String newPwd = getPara("newPwd");
            String newConfirmPwd = getPara("newConfirmPwd");
            boolean b = adminService.modifyPassword(administrator.getId(), oldPwd, newPwd, newConfirmPwd);
            if (b) {
                adminService.addCacheById(administrator.getId());
                data.put("result", true);
            } else {
                data.put("result", false);
            }
            renderJson(data);
        }

    }

    *//**
     * 禁用操作
     *
     * @author xxx
     *//*
    public void forbidden() {
        String ids = getPara("ids");
        Boolean b = adminService.forbidden(ids);
        renderJsonForUpdate(b);
    }

    *//**
     * 启用
     *
     * @author xxx
     *//*
    public void startUsing() {
        String ids = getPara("ids");
        Boolean b = adminService.startUsing(ids);
        renderJsonForUpdate(b);
    }

    *//**
     * 重置管理员密码页面
     *
     * @author xxx
     *//*
    public void toModifyAdminLoginPwdPage() {
        setAttr("id", getPara("ids"));
        render("modifyPwd,html");
    }

    *//**
     * 重置管理员登录密码
     *
     * @author xxx
     *//*
    public void modifyAdminLoginPwd() {
        String pwd = getPara("pwd");
        boolean b = adminService.modifyAdminLoginPwd(ids, pwd);
        if (b) {
            data.put("result", true);
        } else {
            data.put("result", false);
        }
        renderJson(data);
    }

    *//**
     * 重置默认密码000000
     *
     * @author xxx
     *//*
    public void modifyDefaultPwd() {
        String id = getPara("ids");
        String pass = getPara("pass");
        boolean b = adminService.modifyAdminLoginPwd(id, pass);
        if (b) {
            data.put("result", true);
        } else {
            data.put("result", false);
        }
        renderJson(data);
    }*/
}