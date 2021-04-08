package com.huimi.core.service.comment.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.JsonUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.mapper.Comment.CommentTemplateMapper;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.system.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/30 15:13
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class CommentTemplateServiceImpl implements CommentTemplateService {
    @Resource
    private CommentTemplateMapper commentTemplateMapper;
    @Resource
    private AdminService adminService;

    @Override
    public DtGrid findAll() {


        List<CommentTemplate> retList = commentTemplateMapper.findAll();
        DtGrid dtGrid = new DtGrid();


        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public List<CommentTemplate> findByOpen() {
        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        if (StringUtils.isNotBlank(admin.getParentId())) {
            return commentTemplateMapper.findByOpen(adminId);
        } else {
            return commentTemplateMapper.findByOpen(null);
        }
    }

    @Override
    public GenericMapper<CommentTemplate, Long> _getMapper() {
        return commentTemplateMapper;
    }

    /**
     * 查询用户id
     */

    public Integer findAdmin() {
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return resultAdmin.getId();
    }

    @Override
    public CommentTemplate findById(Long commentTemplateId) {
        if(StringUtils.isBlank(commentTemplateId)) {
            return null;
        }
        CommentTemplate commentTemplate = new CommentTemplate();
        commentTemplate.setId(commentTemplateId);
        return commentTemplateMapper.selectOne(commentTemplate);
    }
}
