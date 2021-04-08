package com.huimi.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huimi.admin.exception.CaptchaException;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.entity.dtgrid.QueryUtils;
import com.huimi.common.utils.LogUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.exception.DtGridException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * json数据类controller 基础实现
 *
 * @param <PK> 对象主键类型
 * @param <PO> 对象
 */
@Slf4j
public class GenericController<PK, PO extends GenericPo<PK>> {

    /**
     * 操作成功
     */
    protected <T> ResultEntity<T> ok() {
        return result(null, "操作成功", ResultEntity.SUCCESS);
    }

    /**
     * 操作成功
     *
     * @param po 返回数据
     */
    protected <T> ResultEntity<T> ok(T po) {
        return result(po, "操作成功", ResultEntity.SUCCESS);
    }

    /**
     * 操作成功
     *
     * @param po 返回数据
     */
    protected <T> ResultEntity<T> ok(T po, String message) {
        return result(po, message, ResultEntity.SUCCESS);
    }

    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail(String msg) {
        return result(null, msg, ResultEntity.FAIL);
    }

    /**
     * 操作错误
     */
    protected <T> ResultEntity<T> fail(int code, String msg) {
        return result(null, msg, code);
    }

    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail() {
        return result(null, "操作失败", ResultEntity.FAIL);
    }

    String getSession(String key) {
        return AdminSessionHelper.getSession(key);
    }

    void setSession(String key, String val) {
        if (val != null) {
            AdminSessionHelper.setSession(key, val);
        }

    }


    /**
     * 返回消息记录
     *
     * @param resultData 返回实体(可空)
     * @param msg        返回消息
     * @param code       成功/错误
     */
    protected <T> ResultEntity<T> result(T resultData, String msg, int code) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(resultData);
        resultEntity.setCode(code);
        resultEntity.setMsg(msg);
        return resultEntity;
    }

    /**
     * 后台列表异常全局处理
     */
    @ResponseBody
    @ExceptionHandler({DtGridException.class})
    public DtGrid dtGridException(DtGridException e) {
        log.error(e.toString(), e);
        DtGrid dtGrid = new DtGrid();
        dtGrid.setIsSuccess(false);
        return dtGrid;
    }

    /**
     * 全局异常处理
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResultEntity exception(Exception e) {
        LogUtil.error("", e, GenericController.class);
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEntity.FAIL);
        if (e instanceof BussinessException) {
            resultEntity.setMsg(e.getMessage());
        } else if (e instanceof IncorrectCredentialsException) {
            resultEntity.setMsg("登录密码错误");
        } else if (e instanceof ExcessiveAttemptsException) {
            resultEntity.setMsg("登录失败次数过多");
        } else if (e instanceof LockedAccountException) {
            resultEntity.setMsg("帐号已被锁定");
        } else if (e instanceof DisabledAccountException) {
            resultEntity.setMsg("帐号已被禁用");
        } else if (e instanceof ExpiredCredentialsException) {
            resultEntity.setMsg("帐号已过期");
        } else if (e instanceof UnknownAccountException) {
            resultEntity.setMsg("帐号不存在");
        } else if (e instanceof UnauthorizedException) {
            resultEntity.setMsg("您没有得到相应的授权");
        } else if (e instanceof DataIntegrityViolationException) {
            resultEntity.setMsg("参数命名重复");
        } else if (e instanceof CaptchaException) {
            resultEntity.setMsg("验证码输入错误");
        } else if (e instanceof AuthenticationException) {
            resultEntity.setMsg("用户名或密码错误");
        } else if (e instanceof AuthorizationException) {
            resultEntity.setMsg("用户名或密码错误");
        }else {
            e.printStackTrace();
            resultEntity.setMsg("系统繁忙");
        }
        return resultEntity;
    }


    protected DtGrid dataAuthority(String dtGridPager, String mark) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DtGrid dtgrid = mapper.readValue(dtGridPager, DtGrid.class);
//        Map<String, Object> parameters = dtgrid.getFastQueryParameters();
//        Admin admin = AdminSessionHelper.getCurrAdmin();
//        if (StringUtils.isNotBlank(mark)) {//加标识的部分为需关联查询
//            if(mark.equals(Constants.AG)){
//                if(admin.getType() == Admin.TYPE.T3.code){Z
//                    parameters.put("eq_u.agent_id", admin.getBussId());
//                    dtgrid.setFastQueryParameters(parameters);
//                }
//            }else if(mark.equals(Constants.USER)){
//                if(admin.getType() == Admin.TYPE.T3.code){
//                    parameters.put("eq_t.agent_id", admin.getBussId());
//                    dtgrid.setFastQueryParameters(parameters);
//                }
//            }
//
//        } else {//未加标识的部分为自带关联字段
//            if (admin.getType() == Admin.TYPE.T3.code) {
//                parameters.put("eq_agentId", admin.getBussId());
//                dtgrid.setFastQueryParameters(parameters);
//            } else if (admin.getType() == Admin.TYPE.T2.code) {
//                parameters.put("eq_investId", admin.getBussId());
//                dtgrid.setFastQueryParameters(parameters);
//            }
//        }
        return dtgrid;
    }


    protected String getAgentDownUserId() {
//        try {
//            Admin admin = adminService.selectByPrimaryKey(this.getAdminId());
//            //判断此登陆用户是否是代理商
//            if (null != admin && admin.getType() == Admin.Type.T2.code) {
//                Agent agent = agentService.selectByPrimaryKey(admin.getAgentId());
//                if (null != agent) {
//                    UserInvitePath userInvitePath = userInvitePathService.selectOne(new UserInvitePath(uip -> {
//                        uip.setUserId(agent.getUserId());
//                        uip.setDelFlag(UserInvitePath.DELFLAG.NO.code);
//                    }));
//                    String path = userInvitePath.getPath();
//                    if (path.length() != 7) {
//                        String[] split = path.split("\\.");
//                        path = split[split.length - 1];
//                    }
//                    return userInvitePathMapper.findAgentUserId(path);
//                }
//            }
//        } catch (Exception e) {
//            return null;
//        }
        return null;
    }

    protected void getFastSearchParameter(HttpServletRequest request, Map<String, String> map) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> kv : parameterMap.entrySet()) {
                String k = kv.getKey();
                String v = kv.getValue()[0];
                if (null != v && !"".equals(v) && k.startsWith("*")) {
                    map.put(k.substring(1), v);
                }
            }
        } catch (Exception e) {
            map.clear();
        }
    }

    /**
     * 快速查询
     */
    protected void getFastSearchWhereSql(HttpServletRequest request, StringBuilder whereSql) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> keys : parameterMap.entrySet()) {
            String key = keys.getKey();
            String value = keys.getValue()[0];
            if (value == null || "".equals(value) || !key.startsWith("*")) {
                continue;
            }
            whereSql.append(" and ");
            if (key.startsWith("*_")) {
                whereSql.append(key.substring(2)).append(" like '%").append(value).append("%' ");
            } else if (key.startsWith("*start_")) {
                whereSql.append("DATE_FORMAT(t.").append(key.substring(7)).append(",'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            } else if (key.startsWith("*end_")) {
                whereSql.append("DATE_FORMAT(t.").append(key.substring(5)).append(",'%Y-%m-%d')").append(" <= ").append("'").append(value).append("'");
            } else if (key.startsWith("*=")) {
                whereSql.append("t.").append(key.substring(2)).append(" = ").append(value);
            } else if (key.startsWith("*dateStart_")) {
                whereSql.append("DATE_FORMAT(t.create_time,'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            } else if (key.startsWith("*dateEnd_")) {
                whereSql.append("DATE_FORMAT(t.create_time,'%Y-%m-%d')").append(" >= ").append("'").append(value).append("'");
            } else if (key.startsWith("*")){
                whereSql.append("t.").append(key.substring(1)).append(" = ").append("'").append(value).append("'");
            }
            else {
                whereSql.append("t.").append(key.substring(1)).append(" like '%").append(value).append("%' ");
            }
        }
    }

    protected String getSortSql(HttpServletRequest request) {
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        if (StringUtils.isBlank(sidx)) {
            sidx = "id";
        }
        if (StringUtils.isBlank(sord)) {
            sord = "desc";
        }
        sidx = QueryUtils.camelToUnderline(sidx);
        return " order by " + sidx + " " + sord;
    }

}
