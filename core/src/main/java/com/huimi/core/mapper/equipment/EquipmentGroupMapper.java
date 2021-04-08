package com.huimi.core.mapper.equipment;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create by lja on 2020/7/29 14:30
 */
@Repository
public interface EquipmentGroupMapper extends GenericMapper<EquipmentGroup, Integer> {

    @Select("SELECT * from equipment_group")
    List<EquipmentGroup> selectIndexList();

    @Select(" SELECT " +
            " equipment_group.id,equipment_group.`name` " +
            " FROM " +
            " `task` " +
            " LEFT JOIN task_details td ON task.id = td.task_id " +
            " LEFT JOIN equipment ON td.device_uid = equipment.device_uid " +
            " LEFT JOIN equipment_group on equipment.group_id = equipment_group.id " +
            " where td.state = 1 and task.id = #{id} " +
            " GROUP BY equipment_group.id ")
    List<EquipmentGroup> findtaskGroup(@Param("id") Long id);

    @Select("<script> " +
            " SELECT " +
            " equipment_group.*" +
            " FROM" +
            " `equipment` " +
            " LEFT JOIN equipment_group ON equipment.group_id = equipment_group.id " +
            " WHERE  1 = 1 " +
            " <foreach collection=\"ids\" item=\"id\" open=\"and equipment.id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            " GROUP BY " +
            " equipment_group.id" +
            "</script>")
    List<EquipmentGroup> findByAgent(@Param("ids") ArrayList<Integer> ids);

    @Select("select * from  equipment_group where state = #{state} order by state desc,id asc  limit 1 ")
    EquipmentGroup findByAdminId(@Param("adminId") Integer adminId,@Param("state") Integer state);

    @Select("SELECT * FROM `equipment_group` where state = #{state} OR sys_admin_id = #{sysAdminId}")
    List<EquipmentGroup> findAgentGroup(@Param("state")Integer state,@Param("sysAdminId")Integer sysAdminId);

    @Select("<script> "+
            "SELECT * FROM `equipment_group` where 1=1 "+
            " <foreach collection=\"sysAdminList\" item=\"id\" open=\"and sys_admin_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "</script>")
    List<EquipmentGroup> findAdminGroup(@Param("sysAdminId") List<Integer> sysAdminList);
}