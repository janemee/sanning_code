package com.huimi.core.mapper.task;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskAllModel;
import com.huimi.core.task.TaskDetails;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/30 16:37
 */
@Repository
public interface TaskMapper extends GenericMapper<Task, Integer> {
    @Select("<script>" +
            "select t.* from task t where 1=1" +
            "  <if test=\"0==isLiveHot  \"> " +
            "   and t.is_live_hot = 0 " +
            "  </if> " +
            "  <if test=\"1==isLiveHot  \"> " +
            "   and t.is_live_hot = 1 " +
            "  </if> " +
            " ORDER BY t.create_time DESC" +
            "</script>")
    List<Task> findAll(@Param("isLiveHot") Integer isLiveHot);

    @Select("<script>" +
            "SELECT " +
            " task.* " +
            " FROM " +
            " `task_details` " +
            " LEFT JOIN task ON task.id = task_details.task_id " +
            " WHERE " +
            " task_details.state = 1 " +
            " AND task.task_type = 'live_hot' " +
            " and task.platform = #{platform} " +
            "<if test=\"sysAdminCode != null and sysAdminCode !='' \"> " +
            " AND task_details.device_uid IN ( " +
            " SELECT " +
            " equipment.device_uid " +
            " FROM " +
            " equipment " +
            " WHERE" +
            " sys_admin_code = #{sysAdminCode} " +
            " ) " +
            " </if>" +
            " AND task_start_time &lt;= NOW() " +
            " AND task_end_time &gt;= NOW() " +
            " GROUP BY " +
            " task.id" +
            "</script>")
    List<Task> findLiveHeart(@Param("sysAdminCode") String sysAdminCode, @Param("platform") String platform);

    @Select("<script>" +
            "SELECT " +
            " task.* " +
            " FROM " +
            " `task_details` " +
            " LEFT JOIN task ON task.id = task_details.task_id " +
            " WHERE " +
            " task_details.state = 1 " +
            " AND task.task_type = 'live_hot' " +
            " <foreach collection=\"sysAdminCodeList\" item=\"id\" open=\"and sys_admin_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            " AND task_start_time &lt;= NOW() " +
            " AND task_end_time &gt;= NOW() " +
            "<if test=\"platform != null and platform !='' \"> " +
            " AND task.platform = #{platform} " +
            "</if>"+
            " GROUP BY " +
            " task.id" +
            "</script>")
    List<Task> findLiveHeartAdmin(@Param("sysAdminCodeList") List<String> sysAdminCodeList, @Param("platform") String platform);

    @Select("<script>" +
            "SELECT " +
            " task.* " +
            " FROM " +
            " `task_details` " +
            " LEFT JOIN task ON task.id = task_details.task_id " +
            " WHERE " +
            " task_details.state = 1 " +
            " AND task.task_type = 'live_hot' " +
            "<if test=\"sysAdminCode != null and sysAdminCode !='' \"> " +
            " AND task_details.device_uid IN ( " +
            " SELECT " +
            " equipment.device_uid " +
            " FROM " +
            " equipment " +
            " WHERE" +
            " sys_admin_code = #{sysAdminCode} " +
            " ) " +
            " </if>" +
            " GROUP BY " +
            " task.id" +
            "</script>")
    List<Task> findLiveTrobleHeart(@Param("sysAdminCode") String sysAdminCode);

    @Select(" SELECT " +
            " td.*,task.task_start_time,task.task_content,task.task_run_code" +
            " ,e.device_code as equipmentName,eg.`name` as equipmentGroupName, td.send_state, td.send_time,td.send_took_millis " +
            " FROM task_details AS td " +
            " LEFT JOIN task ON task.id = td.task_id" +
            " left join equipment e on e.device_uid = td.device_uid " +
            " LEFT JOIN equipment_group eg ON e.group_id = eg.id " +
            " WHERE " +
            " ${whereSql} ${sortSql} limit ${nowPage}, ${pageSize}")
    List<TaskDetails> findOneDetailed(Map<String, Object> params);

    @Select(" SELECT " +
            " count(*)" +
            " FROM " +
            " task_details AS td " +
            " LEFT JOIN task ON task.id = td.task_id" +
            " left join equipment e on e.device_uid = td.device_uid  " +
            " WHERE " +
            " ${whereSql}")
    long findOneDetailedCount(Map<String, Object> params);


    @Select(" SELECT " +
            " ts.*, " +
            " td.task_detail_uuid,td.number,td.letter_type, td.key_word," +
            " ct.* " +
            " FROM " +
            " task ts " +
            " LEFT JOIN task_details td ON ts.id = td.task_id " +
            " LEFT JOIN comment_template ct  on ct.id = ts.comment_template_id " +
            " WHERE " +
            " td.device_uid =  #{deviceUid}  " +
            " AND ts.task_type = #{taskType}  " +
            " and td.state =  #{state} " +
            " and td.platform = #{platform}" +
            " ORDER BY ts.id asc  " +
            " LIMIT 1  "
    )
    TaskAllModel findCodeTask(@Param("deviceUid") String deviceUid, @Param("taskType") String taskType, @Param("state") String state, @Param("platform") String platform);

    @Select(" SELECT " +
            " ts.*, " +
            " td.* " +
            " FROM " +
            " task ts " +
            " LEFT JOIN task_details td ON ts.id = td.task_id " +
            " WHERE " +
            " td.device_uid =  #{deviceUid}  " +
            " AND ts.task_type = #{taskType}  " +
            " and td.state =  #{state} " +
            " and td.platform = #{platform} " +
            " ORDER BY ts.id asc  " +
            " LIMIT 1  "
    )
    TaskAllModel findByTaskTypeAndDeviceUid(@Param("deviceUid") String deviceUid, @Param("taskType") String taskType, @Param("state") String state, @Param("platform") String platform);

    @Select(" SELECT " +
            " ts.*, " +
            " td.* " +
            " FROM " +
            " task ts " +
            " LEFT JOIN task_details td ON ts.id = td.task_id " +
            " WHERE " +
            " td.id =  #{taskDetailId}  " +
            " ORDER BY ts.id asc  " +
            " LIMIT 1  "
    )
    TaskAllModel findByTaskDetailId(@Param("taskDetailId") Integer taskDetailId);

    @Select(" SELECT b.id FROM" +
            " (SELECT task_id,COUNT(*) as taskDetailclose from  task_details where state = #{state} GROUP BY task_id)as a,( " +
            " SELECT " +
            " task.id,COUNT(*)taskDetail " +
            " FROM " +
            " `task` " +
            " LEFT JOIN task_details td ON td.task_id = task.id " +
            " GROUP BY task.id " +
            " ) AS b where a.task_id = b.id AND a.taskDetailclose = b.taskDetail")
    List<Integer> findCloseTask(@Param("state") Integer state);

    @Delete("<script>" +
            "DELETE FROM task where 1 = 1  " +
            " <foreach collection=\"taskIds\" item=\"id\" open=\"and id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "</script>")
    void deleteByTaskId(@Param("taskIds") List<Integer> taskIds);

    @Delete("<script>" +
            "DELETE FROM task_details where 1 = 1  " +
            " <foreach collection=\"taskIds\" item=\"id\" open=\"and task_id  in(\" close=\")\" separator=\",\">  \n" +
            "   #{id} " +
            " </foreach> " +
            "</script>")
    void deleteTaskDetail(@Param("taskIds") List<Integer> taskIds);


    @Select("<script>" +
            " SELECT " +
            " t.id,t.task_name,t.task_type,t.task_content,t.platform,t.task_run_code,t.task_start_time,t.task_end_time,t.task_expect_running,t.create_time " +
            " FROM task t " +
            " WHERE " +
            " 1=1" +
            "<if test=\"pramMap.task_type != null and pramMap.task_type !='' \"> " +
            " AND t.task_type !=#{pramMap.task_type} " +
            "</if>  " +
            "<if test=\"pramMap.platform != null and pramMap.platform !='' \"> " +
            " AND t.platform =#{pramMap.platform} " +
            "</if>  " +
            "<if test=\"pramMap.task_name != null and pramMap.task_name !='' \"> " +
            " AND t.task_name like  concat('%',#{pramMap.task_name},'%') " +
            "</if>  " +
            "<if test=\"pramMap.sys_admin_id != null and pramMap.sys_admin_id !='' \"> " +
            " AND t.id IN ( " +
            " SELECT td.task_id FROM task_details td " +
            " LEFT JOIN equipment e ON e.device_uid=td.device_uid " +
            " WHERE " +
            "  e.sys_admin_id = #{pramMap.sys_admin_id}  " +
            "<if test=\"pramMap.task_type != null and pramMap.task_type !='' \"> " +
            " AND td.task_type !=#{pramMap.task_type} " +
            "</if>  " +
            "<if test=\"pramMap.task_type1 != null and pramMap.task_type1 !='' \"> " +
            " AND td.task_type = #{pramMap.task_type1} " +
            "</if>  " +
            ") " +
            "</if> " +


            "<if test=\"pramMap.task_name != null and pramMap.task_name !='' \"> " +
            " AND t.task_name like  concat('%',#{pramMap.task_name},'%') " +
            "</if>  " +
            "<if test=\"pramMap.task_type1 != null and pramMap.task_type1 !='' \"> " +
            " AND t.task_type = #{pramMap.task_type1} " +
            "</if>  " +
            "<if test=\"pramMap.task_content != null and pramMap.task_content !='' \"> " +
            " AND t.task_content like  concat('%',#{pramMap.task_content},'%') " +
            "</if>  " +
            "<if test=\"pramMap.task_run_code != null and pramMap.task_run_code !='' \"> " +
            " AND t.task_run_code =  #{pramMap.task_run_code} " +
            "</if>  " +
            "<if test=\"pramMap.task_time_start != null and pramMap.task_time_start !='' \"> " +
            " AND t.task_start_time &gt;=   #{task_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.task_time_end != null and pramMap.task_time_end !='' \"> " +
            " AND t.task_start_time  &lt;=    #{task_time_end}" +
            "</if>  " +
            "<if test=\"pramMap.task_expect_running != null and pramMap.task_expect_running !='' \"> " +
            " AND t.task_expect_running =  #{task_expect_running}" +
            "</if>  " +
            "<if test=\"pramMap.create_time_start != null and pramMap.create_time_start !='' \"> " +
            " AND t.create_time &gt;=   #{create_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.create_time_end != null and pramMap.create_time_end !='' \"> " +
            " AND t.create_time  &lt;=    #{create_time_end}" +
            "</if>  " +
            "<if test=\"pramMap.task_end_time_start != null and pramMap.task_end_time_start !='' \"> " +
            " AND t.task_end_time &gt;=   #{task_end_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.task_end_time_start != null and pramMap.task_end_time_start !='' \"> " +
            " AND t.task_end_time  &lt;=    #{task_end_time_end}" +
            "</if>  " +

            " ORDER BY t.id DESC" +
            " limit #{page}, #{rows}" +
            " </script>")
    List<Task> findByTaskList(@Param("pramMap") Map<String, Object> pramMap, @Param("page") int page, @Param("rows") int rows);

    @Select("<script>" +
            " SELECT " +
            " count(1)" +
            " FROM task t " +
            " WHERE " +
            " 1=1" +
            "<if test=\"pramMap.task_type != null and pramMap.task_type !='' \"> " +
            " AND t.task_type !=#{pramMap.task_type} " +
            "</if>  " +
            "<if test=\"pramMap.platform != null and pramMap.platform !='' \"> " +
            " AND t.platform =#{pramMap.platform} " +
            "</if>  " +
            "<if test=\"pramMap.task_name != null and pramMap.task_name !='' \"> " +
            " AND t.task_name like  concat('%',#{pramMap.task_name},'%') " +
            "</if>  " +
            "<if test=\"pramMap.sys_admin_id != null and pramMap.sys_admin_id !='' \"> " +
            " AND t.id IN ( " +
            " SELECT td.task_id FROM task_details td " +
            " LEFT JOIN equipment e ON e.device_uid=td.device_uid " +
            " WHERE " +
            "  e.sys_admin_id = #{pramMap.sys_admin_id}  " +
            "<if test=\"pramMap.task_type != null and pramMap.task_type !='' \"> " +
            " AND td.task_type !=#{pramMap.task_type} " +
            "</if>  " +
            "<if test=\"pramMap.task_type1 != null and pramMap.task_type1 !='' \"> " +
            " AND td.task_type =#{pramMap.task_type1} " +
            "</if>  " +
            ") " +
            "</if> " +


            "<if test=\"pramMap.task_name != null and pramMap.task_name !='' \"> " +
            " AND t.task_name like  concat('%',#{pramMap.task_name},'%') " +
            "</if>  " +
            "<if test=\"pramMap.task_type1 != null and pramMap.task_type1 !='' \"> " +
            " AND t.task_type = #{pramMap.task_type1} " +
            "</if>  " +
            "<if test=\"pramMap.task_content != null and pramMap.task_content !='' \"> " +
            " AND t.task_content like  concat('%',#{pramMap.task_content},'%') " +
            "</if>  " +
            "<if test=\"pramMap.task_run_code != null and pramMap.task_run_code !='' \"> " +
            " AND t.task_run_code =  #{pramMap.task_run_code} " +
            "</if>  " +
            "<if test=\"pramMap.task_time_start != null and pramMap.task_time_start !='' \"> " +
            " AND t.task_start_time &gt;=   #{task_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.task_time_end != null and pramMap.task_time_end !='' \"> " +
            " AND t.task_start_time  &lt;=    #{task_time_end}" +
            "</if>  " +
            "<if test=\"pramMap.task_expect_running != null and pramMap.task_expect_running !='' \"> " +
            " AND t.task_expect_running =  #{task_expect_running}" +
            "</if>  " +
            "<if test=\"pramMap.create_time_start != null and pramMap.create_time_start !='' \"> " +
            " AND t.create_time &gt;=   #{create_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.create_time_end != null and pramMap.create_time_end !='' \"> " +
            " AND t.create_time  &lt;=    #{create_time_end}" +
            "</if>  " +
            "<if test=\"pramMap.task_end_time_start != null and pramMap.task_end_time_start !='' \"> " +
            " AND t.task_end_time &gt;=   #{task_end_time_start}" +
            "</if>  " +
            "<if test=\"pramMap.task_end_time_start != null and pramMap.task_end_time_start !='' \"> " +
            " AND t.task_end_time  &lt;=    #{task_end_time_end}" +
            "</if>  " +
            " </script>")
    int findByTaskListCount(@Param("pramMap") Map<String, Object> pramMap);
}
