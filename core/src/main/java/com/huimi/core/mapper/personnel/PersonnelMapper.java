package com.huimi.core.mapper.personnel;

import cn.hutool.db.Page;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.personnel.Personnel;
import com.huimi.core.req.EquipmentRoleModelReq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonnelMapper extends GenericMapper<Personnel, Integer> {

    @Select("select * from personnel  where phone = #{phone}")
    Personnel findByPhone(@Param("phone") String phone);


    @Select("SELECT " +
            " p.*, " +
            " case when\n" +
            "(SELECT er.id from equipment_role er where er.personnel_id =p.id " +
            " <if test=\"equipmentId != null \"> " +
            "  and er.equipment_id = #{equipmentId} " +
            "  </if> " +
            ") is null\n" +
            "then \n" +
            "0\n" +
            "ELSE\n" +
            "1\n" +
            "end\n" +
            " as roleState " +
            " FROM " +
            "  personnel p " +
            " WHERE " +
            " 1=1 " +
            " order by p.group_id desc ,p.id desc")
    Page findUsersByPage(EquipmentRoleModelReq equipmentModelReq);
}
