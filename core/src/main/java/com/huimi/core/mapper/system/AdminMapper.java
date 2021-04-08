package com.huimi.core.mapper.system;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.system.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 系统管理员表相关Dao接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Repository
public interface AdminMapper extends GenericMapper<Admin, Integer> {
    @Select(" SELECT a.ports - b.ports2 AS result from (SELECT ports FROM sys_admin where id = #{parentId}) as a ,(SELECT SUM(ports) as ports2 FROM `sys_admin` where parent_id = #{parentId}) as b;")
    Integer findSubEquipmentNum(@Param("parentId") Integer parentId);

    @Update("UPDATE sys_admin SET ports=#{ports} where username = #{username}")
    Integer updatePortByUsername(@Param("ports") Integer ports, @Param("username") String username);

    @Update("UPDATE sys_admin  SET `code` = #{code},role_ids = #{roleIds},parent_id = NULL where id = #{id}")
    Integer updateAdminParent(@Param("code") String code, @Param("id") Integer id, @Param("roleIds") Integer roleIds);

    @Select("SELECT * FROM `sys_admin` WHERE role_ids = #{roleIds}")
    List<String> findRoleAdmin(@Param("roleIds") String roleIds);

    @Select("SELECT * FROM `sys_admin` WHERE code = #{code}")
    Admin selectByInviteCode(@Param("code") String code);
}
