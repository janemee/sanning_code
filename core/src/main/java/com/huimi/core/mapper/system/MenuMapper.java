package com.huimi.core.mapper.system;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易手续费率模板相关Dao接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Repository
public interface MenuMapper extends GenericMapper <Menu, Integer> {


    @Select("SELECT m.* FROM sys_menu m WHERE m.type = #{type} AND m.parent_id = #{pid} AND m.id IN (${menuIds}) AND m.del_flag = 0 ORDER BY m.sort ASC;")
    List <MenuModel> findMenuByPidInMenuIds(@Param("type") int type, @Param("pid") int pid, @Param("menuIds") String menuIds);

    @Select("SELECT SUM(m.rightVal) as rightVal ,m.rightPos FROM sys_menu m WHERE m.type = #(type) AND m.delFlag = #(delFlag) AND m.id IN ($(filter)) GROUP BY m.rightPos;")
    List <Menu> sumAdminRights(@Param("type") int type, @Param("delFlag") int delFlag, @Param("filter") String filter);
}
