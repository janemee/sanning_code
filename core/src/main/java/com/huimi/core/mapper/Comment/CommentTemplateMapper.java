package com.huimi.core.mapper.Comment;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.comment.CommentTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by lja on 2020/7/30 15:02
 */
@Repository
public interface CommentTemplateMapper extends GenericMapper<CommentTemplate, Long> {
    @Select("select *  from comment_template ORDER BY comment_template.create_time DESC")
    List<CommentTemplate> findAll();

    @Select("<script>" +
            "select * from comment_template where open = 1 " +
            "<if test=\"sysAdminId != null and sysAdminId !='' \"> " +
            "and  sys_admin_id = #{sysAdminId} " +
            " </if>" +
            "</script>")
    List<CommentTemplate> findByOpen(@Param("sysAdminId") Integer sysAdminId);

}
