package com.huimi.core.mapper.equipmentRole;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.equipmentRole.EquipmentRole;
import com.huimi.core.req.EquipmentReq;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipmentRoleMapper extends GenericMapper<EquipmentRole, Integer> {

    @Select("<script> " +
            "SELECT " +
            " el.*, e.device_code as equipmentName, " +
            " e.address,\n" +
            " p.`name` as personnelName " +
            "FROM\n" +
            "\tequipment_role el\n" +
            "LEFT JOIN equipment e ON e.id = el.equipment_id\n" +
            "LEFT JOIN personnel p ON p.id = el.personnel_id" +
            " where 1 = 1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  e.device_code LIKE  CONCAT('%',#{search_val},'%') " +
            "  or   p.name  like  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " <if test=\"personnelId != null and personnelId !='' \"> " +
            "  and  el.personnel_id = #{personnelId} " +
            "  </if> " +
            " <if test=\"delFlag != null \"> " +
            "  and  el.del_flag = #{delFlag} " +
            "  </if> " +
            " order by  el.id desc " +
            " limit #{nowPage}, #{pageSize}" +
            " </script>")
    List<EquipmentRole> findByPage(EquipmentReq basePageReq);

    @Select("<script> " +
            "SELECT\n" +
            " count(*) " +
            " FROM\n" +
            "\tequipment_role el\n" +
            "LEFT JOIN equipment e ON e.id = el.equipment_id\n" +
            "LEFT JOIN personnel p ON p.id = el.personnel_id" +
            " where 1 = 1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  e.device_code LIKE  CONCAT('%',#{search_val},'%') " +
            "  or   p.name  like  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " <if test=\"personnelId != null and personnelId !='' \"> " +
            "  and  el.personnel_id = #{personnelId} " +
            "  </if> " +
            " <if test=\"delFlag != null \"> " +
            "  and  el.del_flag = #{delFlag} " +
            "  </if> " +
            " </script>")
    long findByPageCount(EquipmentReq basePageReq);

    @Delete("delete equipment_role where equipment_id = #{equipmentId}  and personnel_id =   #{personnelId}")
    int delRole(@Param("equipmentId") Integer equipmentId, @Param("personnelId") Integer personnelId);


    @Delete("delete equipment_role where  and personnel_id = #{personnelId}")
    int delEquipmentAll(@Param("personnelId") Integer personnelId);


    @Delete("update equipment_role set  del_flag = 1  where   equipment_id = #{equipmentId}")
    int delAllByEquipmentId(@Param("equipmentId") Integer equipmentId);

}
