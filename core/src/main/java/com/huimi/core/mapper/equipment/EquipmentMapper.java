package com.huimi.core.mapper.equipment;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.req.EquipmentModelReq;
import com.huimi.core.task.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/28 17:36
 */
@Repository
public interface EquipmentMapper extends GenericMapper<Equipment, Integer> {
    @Select("SELECT\n" +
            " e.*,eg.`name` as groupName,sa.username as sysAdminName,sa.code as sysAdminCode " +
            "FROM\n" +
            "\t`equipment` as e\n" +
            " LEFT JOIN equipment_group as eg ON e.group_id = eg .id\n" +
            " LEFT JOIN sys_admin as sa ON e.sys_admin_id = sa.id " +
            " where 1 = 1 " +
            "${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<Equipment> selectAllParams(Map<String, Object> params);

    @Select("SELECT\n" +
            " count(*) " +
            " FROM\n" +
            "\t`equipment` as e\n" +
            " LEFT JOIN equipment_group as eg ON e.group_id = eg .id\n" +
            " LEFT JOIN sys_admin as sa ON e.sys_admin_id = sa.id " +
            " where 1=1" +
            " ${whereSql}")
    long selectAllCount(Map<String, Object> params);

    @Select("SELECT * FROM(SELECT\n" +
            "\te.*,eg.`name`\n" +
            " FROM\n" +
            "\t`equipment` as e\n" +
            " LEFT JOIN equipment_group as eg ON eg.id = e.group_id\n" +
            " WHERE e.sys_admin_id = #{sysAdminId})as t  where 1 = 1" +
            "${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<Equipment> selectAllGroupEquipment(Map<String, Object> params);


    @Select("SELECT count(*) FROM(SELECT\n" +
            "\te.*,eg.`name`\n" +
            " FROM\n" +
            "\t`equipment` as e\n" +
            " LEFT JOIN equipment_group as eg ON eg.id = e.group_id\n" +
            " WHERE e.sys_admin_id = ${sysAdminId})as t  where 1 = 1" +
            "${whereSql}")
    Long selectAllGroupEquipmentCount(Map<String, Object> params);


    @Select("<script> " +
            " SELECT equipment.*,equipment_group.`name` as groupName FROM `equipment` LEFT JOIN equipment_group ON equipment.group_id = equipment_group.id " +
            " where 1=1 " +
            " <if test=\"search_val != null and search_val !='' \"> " +
            "  and  device_code LIKE  CONCAT('%',#{search_val},'%')  " +
            "  </if> " +
            " ORDER BY `equipment`.create_time DESC " +
            " </script>")
    List<Equipment> selectIndexList(@Param("search_val") String search_val);

    @Select("<script> " +
            "SELECT id,device_code,state FROM equipment where state = 1 " +
            "<if test=\"sysAdminCode != null and sysAdminCode !='' \"> " +
            "and  sys_admin_code = #{sysAdminCode} " +
            " </if>" +
            "</script>")
    List<Equipment> selectByState(@Param("sysAdminCode") String sysAdminCode);

    @Select("<script> " +
            "SELECT * FROM" +
            "(SELECT id,device_code,sys_admin_id,state FROM equipment where sys_admin_code is NULL" +
            " <foreach collection=\"sysAdminList\" item=\"id\" open=\"OR sys_admin_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            ") as  t where 1=1 " +
            "<if test=\"state != null and state !='' \"> " +
            "and  state = #{state} " +
            " </if>" +
            "</script>")
    List<Equipment> selectAdminEquipment(@Param("sysAdminList") List<String> sysAdminList, @Param("state") Integer state);

    @Select("SELECT id, device_uid, state, channel_id FROM equipment where device_uid = #{device_uid} ")
    Equipment selectByUid(String device_uid);

    @Select("select id,device_uid from equipment where group_id = #{id}")
    List<Equipment> selectByGroup();


    @Select("<script>" +
            " SELECT\n" +
            "\t*\n" +
            " FROM\n" +
            "\tequipment\n" +
            " LEFT JOIN equipment_group ON equipment.group_id = equipment_group.id\n" +
            " LEFT JOIN task_details ON task_details.device_uid = equipment.device_uid\n" +
            " LEFT JOIN task ON task.id = task_details.task_id\n" +
            " where task.id = #{id} " +
            " <foreach collection=\"groupId\" item=\"id\" open=\"and equipment_group.id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "</script>"

    )
    ArrayList<Equipment> selectLiveHotGroup(@Param("id") Long id, @Param("groupId") List groupId);

    @Select("<script>" +
            "SELECT * FROM equipment" +
            " where state = 1 " +
            "<if test=\"sysAdminId != null and sysAdminId !='' \"> " +
            "and  equipment.sys_admin_id = #{sysAdminId} " +
            " </if>" +
            " <foreach collection=\"list\" item=\"id\" open=\"and group_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "</script>")
    ArrayList<Equipment> findAllStateByGroup(@Param("list") ArrayList<Long> list, @Param("sysAdminId") Integer sysAdminId);

    @Select("<script>" +
            " select * from equipment  where  state = 1 " +
            " <foreach collection=\"list\" item=\"id\" open=\" and group_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "<if test=\"adminId != null and adminId !='' \"> " +
            " <foreach collection=\"adminId\" item=\"id\" open=\" and  sys_admin_id   in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            " </if>" +
            "</script>")
    ArrayList<Equipment> findAllStateByGroupAgent(@Param("list") ArrayList<Long> list, @Param("adminId") List adminId);

    @Update("UPDATE equipment SET state = #{state}, update_time = now() WHERE device_uid = #{deviceUid}")
    Integer updateByUid(@Param("state") Integer state, @Param("deviceUid") String code);

    @Update("<script> " +
            "UPDATE equipment SET group_id = #{groupId} where 1 = 1  " +
            " <foreach collection=\"ids\" item=\"id\" open=\"and id  in(\" close=\")\" separator=\",\">  \n " +
            " #{id} " +
            " </foreach> " +
            "</script>"
    )
    Integer updateBatchGrouping(@Param("groupId") Long groupId, @Param("ids") Long[] ids);

    @Select("select * from task ")
    List<Equipment> findListToTaskType(@Param("equipmentId") Long equipmentId, @Param("taskType") String taskType);

    @Select("<script>" +
            "SELECT " +
            " ( " +
            " SELECT " +
            " count(*) " +
            " FROM " +
            " task_details " +
            " WHERE " +
            " device_uid = e.device_uid " +
            " AND state  IN (0, 1) " +
            " )  as taskNumber, " +
            " e.* " +
            "FROM " +
            " equipment e " +
            " where " +
            " e.state =  1 " +
            "<if test=\"sysAdminId != null and sysAdminId !='' \"> " +
            "and  sys_admin_id = #{sysAdminId} " +
            " </if>" +
            "</script>")
    List<Equipment> findWorkNumberByBusy(@Param("sysAdminId") Integer sysAdminId);

    @Select("<script>" +
            "select * from equipment where  1 = 1  " +
            "<if test=\"sysAdminCode != null and sysAdminCode !='' \"> " +
            "and  sys_admin_code = #{sysAdminCode} " +
            " </if>" +
            "</script>")
    List<Equipment> findByAll(@Param("sysAdminCode") String sysAdminCode);

    @Select("select COUNT(*)  from  equipment where  sys_admin_id = #{adminId}")
    Integer findByAdminId(@Param("adminId") Integer adminId);

    @Select("select COUNT(*)  from  equipment where  sys_admin_id = #{adminId} and  state = #{state}")
    Integer findByAdminIdAndState(@Param("adminId") Integer adminId, @Param("state") Integer state);

    @Select("SELECT COUNT(*) FROM `equipment` where sys_admin_id in (SELECT id FROM sys_admin where parent_id = #{parentId})")
    Integer findSubEquipmentNum(@Param("parentId") Integer parentId);

    @Select(" SELECT " +
            " task . *,td.state" +
            " FROM " +
            " `equipment` eq " +
            " LEFT JOIN task_details td ON eq.device_uid = td.device_uid " +
            " LEFT JOIN task on td.task_id = task.id  " +
            " where eq.id=#{equipmentId} and td.state = #{state};")
    List<Task> findEquipmentTask(@Param("equipmentId") Integer equipmentId, @Param("state") Integer state);

    @Select("SELECT COUNT(*) FROM equipment where sys_admin_id  not in (SELECT id FROM  sys_admin where parent_id IS NULL );")
    Integer findByAgentEquipment();

    @Update("UPDATE equipment SET state = 0, update_time = now() WHERE channel_id = #{channelId}")
    int updateStateByChannelId(@Param("channelId") String channelId);
//
//    @Select("<script>" +
//            "SELECT " +
//            " ( " +
//            " SELECT " +
//            " count(*) " +
//            " FROM " +
//            " task_details " +
//            " WHERE " +
//            " device_uid = e.device_uid " +
//            " AND state  IN (0, 1) " +
//            " )  as taskNumber, " +
//            " e.* " +
//            "FROM " +
//            " equipment e " +
//            " where " +
//            " 1  =  1 " +
//            "<if test=\"groupId != null and groupId !='' \"> " +
//            "and  group_id = #{groupId} " +
//            " </if>" +
//            "<if test=\"state != null and state !='' \"> " +
//            " and  e.state  = #{state} " +
//            " </if>" +
//            "</script>")
//    List<Equipment> findByGroupList(@Param("groupId") Integer groupId,@Param("state") Integer state);

    @Select("<script> " +
            " select * from equipment " +
            " <foreach collection=\"ids\" item=\"id\" open=\"where id  in(\" close=\")\" separator=\",\">  \n " +
            " #{id} " +
            " </foreach>" +
            " </script> ")
    ArrayList<Equipment> selectByIds(@Param("ids")List<String> ids);



    @Select("select * from equipment where  group_id =  #{groupId}")
    List<Equipment> findByGroupList(@Param("groupId") Integer groupId);

    @Select("select * from equipment where  users_id  = #{usersId} ")
    Page findUsersByPage(@RequestBody EquipmentModelReq equipmentModelReq);
}
