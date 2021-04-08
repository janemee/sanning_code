package com.huimi.core.mapper.liveHotSubTask;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.entity.LiveHotSubTask;
import com.huimi.core.model.LiveHotSubTask.LiveHotSubTaskModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LiveHotSubTaskMapper extends GenericMapper<LiveHotSubTask, Integer> {


    @Select("SELECT " +
            " * " +
            " FROM " +
            " live_hot_sub_task t " +
            " LEFT JOIN task_details td ON t.task_detail_id = td.id " +
            " WHERE " +
            " td.task_detail_uuid = #{liveHotUuid} " +
            " AND t.state = 1 " +
            " AND t.task_type = #{taskType} " +
            " AND t.platform = #{platform}" +
            " ORDER BY " +
            " t.id ASC " +
            " LIMIT 1 ")
    LiveHotSubTaskModel findByLiveHotTaskUuid(@Param("taskType") String taskType, @Param("liveHotUuid") String liveHotUuid,@Param("platform") String platform);


    @Select("select * from  live_hot_sub_task where uuid = #{uuid} and device_uid = #{deviceUid} LIMIT 1 ")
    LiveHotSubTask findByUuid(@Param("uuid") String uuid, @Param("deviceUid") String deviceUid);


    @Update("update  live_hot_sub_task set  state = #{state}, update_time = now() ,  remarks = #{remarks} where  device_uid = #{deviceUid}  and  state != 2 ")
    void updateByEquipmentUid(@Param("deviceUid") String deviceUid, @Param("state") String state, @Param("remarks") String remarks);

    @Update("update  live_hot_sub_task set  state = #{state}, update_time = now() ,  remarks = #{remarks} where  task_detail_id = #{taskDetailId} and task_type != #{taskType}  and  state != 2 ")
    Integer updateByTaskDetailsId(@Param("taskDetailId") Integer taskDetailId, @Param("state") String state, @Param("remarks") String remarks, @Param("taskType") String taskType);

    @Select("SELECT * FROM `live_hot_sub_task` where task_detail_id = #{taskDetailId} and state = #{state} and task_type != #{taskType}")
    List<LiveHotSubTask> findToTaskDetailsId(@Param("taskDetailId") Integer taskDetailId, @Param("state") String state, @Param("taskType") String taskType);

    @Select("SELECT * FROM live_hot_sub_task where task_type = #{taskType} and task_detail_id = #{taskDetailId} and state = #{state} and content is null")
    List<LiveHotSubTask> findToDistantOver(@Param("taskType") String taskType, @Param("taskDetailId") Integer taskDetailId, @Param("state") String state);

    @Update("update live_hot_sub_task set send_state = #{state}, update_time = now(), send_time = #{dateStrMillis}, send_took_millis = #{millis} where uuid = #{uuid} ")
    Integer updateByUuid(@Param("uuid") String uuid, @Param("millis") long l, @Param("dateStrMillis") String dateStrMillis, @Param("state") int state);


    @Update("update  live_hot_sub_task set  state = #{state}, update_time = now() ,  remarks = #{remarks}  where  uuid = #{uuid} and device_uid = #{deviceUid}")
    Integer updateByLiveHotTaskState(@Param("deviceUid")String deviceUid ,@Param("uuid") String uuid, @Param("state") String state, @Param("remarks") String remarks);

    @Update("update live_hot_sub_task set state = 3, update_time = now(), remarks = #{remarks} where device_uid = #{deviceUid} and state = 1 ")
    int updateTaskOverByDeviceUid(@Param("deviceUid") String deviceUid, @Param("remarks") String remarks);
}
