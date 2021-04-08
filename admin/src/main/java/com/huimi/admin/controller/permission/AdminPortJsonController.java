package com.huimi.admin.controller.permission;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.controller.GenericController;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.AdminPort;
import com.huimi.core.service.system.AdminPortService;
import com.huimi.core.service.system.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * create by lja on 2020/8/28 10:15
 */
@Slf4j
@Controller
@RequestMapping(BaseController.BASE_URI)
public class AdminPortJsonController extends GenericController<Integer, AdminPort> {

    @Resource
    private AdminPortService adminPortService;
    @Resource
    private AdminService adminService;
    /**
     * 管理员列表json数据
     */
    @ResponseBody
    @RequestMapping("adminPort/json/list")
    public DtGrid listJson(HttpServletRequest request) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 1 : Integer.valueOf(request.getParameter("rows"));
        Integer pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1 : Integer.valueOf(request.getParameter("page"));
        String search_val = request.getParameter("search_val");
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        Integer adminId = this.findAdmin();
        Admin nowAdmin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(nowAdmin.getParentId())){
            whereSql.append(" and operat_admin = ").append(adminId);
        }

        if (!StringUtils.isBlank(search_val)) {
            whereSql.append(" and (username like '%").append(search_val).append("%' or ").append("mobile like '%").append(search_val).append("%')");
        }
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid = adminPortService.getDtGridList(dtGrid);
        if (CollectionUtils.isNotEmpty(dtGrid.getExhibitDatas())) {
            for (Object o : dtGrid.getExhibitDatas()) {
                Map<String, Object> map = (Map<String, Object>) o;
                String sysAdminId = StringUtil.isBlank(map.get("sysAdminId") + "") ? "0" : map.get("sysAdminId") + "";
                String operatAdminId = StringUtil.isBlank(map.get("operatAdmin") + "") ? "0" : map.get("operatAdmin") + "";
                Admin admin = adminService.selectByPrimaryKey(Integer.parseInt(sysAdminId));
                Admin operatAdmin = adminService.selectByPrimaryKey(Integer.parseInt(operatAdminId));
                if (admin != null) {
                    map.put("adminUserName", admin.getUsername());
                }
                if (operatAdmin!=null){
                    map.put("operatAdminName",operatAdmin.getUsername());
                }
            }
        }
        return dtGrid;
    }

    /**
     * 查询用户id
     * @return
     */

    public Integer findAdmin (){
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return  resultAdmin.getId();
    }
}
