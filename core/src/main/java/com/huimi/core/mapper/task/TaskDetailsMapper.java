package com.huimi.core.mapper.task;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.task.TaskDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lja on 2020/7/31 15:37
 */
@Repository
public interface TaskDetailsMapper extends GenericMapper<TaskDetails, Integer> {
    @Select("select * from task")
    List<TaskDetails> findAll();


    @Select(" select  * from  task_details " +
            " where  " +
            " task_type = #{taskType} " +
            " and  task_detail_uuid = #{taskId}  " +
            " and device_uid  = #{deviceUid} ")
    TaskDetails findByTaskDetail(@Param("deviceUid") String deviceUid, @Param("taskId") String taskId, @Param("taskType") String taskType);

    @Select("SELECT td.*  FROM `task_details` td" +
            " LEFT JOIN task on td.task_id = task.id where task.task_run_code = 'delay' AND now() >= task.task_start_time AND now() <= task.task_end_time " +
            " and td.state = 0")
    List<TaskDetails> findByDelay();

    @Select("SELECT " +
            " equipment.* " +
            " FROM " +
            " `task` " +
            " LEFT JOIN task_details td ON task.id = td.task_id " +
            " LEFT JOIN equipment ON td.device_uid = equipment.device_uid" +
            " where td.state = 1 and task.id = #{id}")
    List<Equipment> findByEquipment(@Param("id") Long id);

    @Update("update  task_details set  state = #{status}, update_time = now() where  task_detail_uuid = #{taskId}  ")
    int updateTaskDetailState(@Param("taskId") String taskId, @Param("status") String status);

    @Update("update  task_details set  state = #{state}, update_time = now() ,  remarks = #{remarks} where  device_uid = #{deviceUid}  and  state != 2 ")
    void updateByEquipmentUid(@Param("deviceUid") String deviceUid, @Param("state") String state, @Param("remarks") String remarks);

    @Select("select * from task_details where task_detail_uuid = #{taskUuid}  and device_uid = #{deviceUid}  LIMIT 1 ")
    TaskDetails findByUuid(@Param("taskUuid") String taskUuid,@Param("deviceUid")String deviceUid);

    @Select("SELECT " +
            " td.* " +
            " FROM " +
            " task_details td " +
            " LEFT JOIN task t ON t.id = td.task_id " +
            " WHERE " +
            " task_id = #{taskId} " +
            " AND device_uid = #{deviceUid} " +
            " and td.state = 1 " +
            " and td.task_type = 'live_hot' ")
    TaskDetails findByTaskId(@Param("taskId") Long taskId, @Param("deviceUid") String deviceUid);

    @Select("SELECT " +
            " td.* " +
            " FROM " +
            " task_details td " +
            " LEFT JOIN task t ON t.id = td.task_id " +
            " WHERE" +
            " 1 = 1  " +
            " and td.state = 1 " +
            " and t.task_start_time != t.task_end_time " +
            " and t.task_end_time < NOW() ")
    List<TaskDetails> findByOutTimeTaskList();

    @Select("<script>" +
            "SELECT " +
            " td.task_id " +
            " FROM " +
            " equipment " +
            " LEFT JOIN task_details as td ON equipment.device_uid = td.device_uid " +
            " WHERE 1 = 1 " +
            " <foreach collection=\"ids\" item=\"id\" open=\"and equipment.id  in(\" close=\")\" separator=\",\">  \n " +
            " #{id} " +
            " </foreach> " +
            "GROUP BY  td.task_id" +
            "</script>"
    )
    List<Integer> findByAgent(@Param("ids") ArrayList ids);

    @Update("update task_details set send_state = #{state}, update_time = now(), send_time = #{dateStrMillis}, send_took_millis = #{millis} where task_detail_uuid = #{taskId} ")
    void updateByUuid(@Param("taskId") String task_id, @Param("millis") long millis, @Param("dateStrMillis") String dateStrMillis, @Param("state") int state);

    @Update("update task_details set state = 3, update_time = now(), remarks = #{remarks} where device_uid = #{deviceUid} and state = 1 ")
    int updateTaskOverByDeviceUid(@Param("deviceUid") String deviceUid,  @Param("remarks") String remarks);
}
