package com.huimi.core.service.system;


import com.huimi.core.po.system.Admin;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 系统管理员表业务相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface AdminService extends GenericService<Integer, Admin> {
    int updateAdmin(Admin admin);

    boolean saveOrUpdate(Admin admin, String passwd, String confirmPassword, Admin currAdmin);

    int update(Admin admin);

    int deleteById(Integer id);

    Admin findById(Integer id);

    List<Admin> findByRoleIds(String rodeIds);

    boolean updateRoleIds(String ids, String roleIds);

    void createOne(String username, String fullname, String pwd, String mobile, Integer sex,Integer ports);

    /**
     * 根据邀请码获取管理员信息
     *
     * @param invitationCode 邀请码
     * @return
     */
    Admin findByCode(String invitationCode);
    /**
     * 根据用户的id获一级代理商的去除2级代理商自己可用的数量
     */
    Integer freeEquipmentNum(Admin admin);

    /**
     * 更新用户的设备数量
     * @param ports 设备总数
     * @param username 用户名称
     * @return
     */
    int updatePorts(Integer ports,String username);
    /**
     * 给管理员授权的时候更新验证码和父级
     */
    int updateAdminParent(String code,Integer id,Integer roleIds);

    List<String> findRoleAdmin(String roles);

    Admin selectByInviteCode(String code);

}
