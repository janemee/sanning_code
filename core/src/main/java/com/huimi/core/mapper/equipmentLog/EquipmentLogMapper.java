package com.huimi.core.mapper.equipmentLog;

import cn.hutool.db.Page;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.equipmentLog.EquipmentLog;
import com.huimi.core.req.BasePageReq;
import com.huimi.core.req.EquipmentModelReq;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipmentLogMapper extends GenericMapper<EquipmentLog, Integer> {

    @Select("<script> " +
            "SELECT " +
            " el.*, " +
            " e.address,\n" +
            " p.`name` as personnelName " +
            "FROM\n" +
            "\tequipment_log el\n" +
            "LEFT JOIN equipment e ON e.id = el.equipment_id\n" +
            "LEFT JOIN personnel p ON p.id = el.personnel_id" +
            " where 1 = 1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  e.device_code LIKE  CONCAT('%',#{search_val},'%') " +
            "  or   p.name  like  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " order by  el.id desc " +
            " limit #{nowPage}, #{pageSize}" +
            " </script>")
    List<EquipmentLog> findByPage(BasePageReq basePageReq);

    @Select("<script> " +
            "SELECT\n" +
            " count(*) " +
            " FROM\n" +
            "\tequipment_log el\n" +
            "LEFT JOIN equipment e ON e.id = el.equipment_id\n" +
            "LEFT JOIN personnel p ON p.id = el.personnel_id" +
            " where 1 = 1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  e.device_code LIKE  CONCAT('%',#{search_val},'%') " +
            "  or   p.name  like  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " </script>")
    long findByPageCount(BasePageReq basePageReq);

    @Select("<script> " +
            "SELECT " +
            " el.*, e.device_code as equipmentName, " +
            " e.address,\n" +
            " p.`name` as personnelName " +
            "FROM\n" +
            "\tequipment_log el\n" +
            "LEFT JOIN equipment e ON e.id = el.equipment_id\n" +
            "LEFT JOIN personnel p ON p.id = el.personnel_id" +
            " where 1 = 1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  e.device_code LIKE  CONCAT('%',#{search_val},'%') " +
            "  or   p.name  like  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " <if test=\"usersId != null  \"> " +
            "  and  e.users_id = #{usersId}" +
            "  </if> " +
            " order by  el.id desc " +
            " </script>")
    Page findUsersByPage(EquipmentModelReq equipmentModelReq);
}
