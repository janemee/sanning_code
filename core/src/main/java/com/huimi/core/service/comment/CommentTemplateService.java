package com.huimi.core.service.comment;


import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * create by lja on 2020/7/30 15:10
 */
public interface CommentTemplateService extends GenericService<Long, CommentTemplate> {
    DtGrid findAll();

    List<CommentTemplate> findByOpen();

    CommentTemplate findById(Long commentTemplateId);

}
