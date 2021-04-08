package com.huimi.core.service.msg;

import com.huimi.common.utils.StringUtils;
import com.huimi.core.mapper.system.NoticeTemplateMapper;
import com.huimi.core.po.system.NoticeTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 短信模版数据渲染
 */
@Slf4j
@Component
@Transactional(rollbackFor = Throwable.class)
public class MessageTemplateManage {

    @Autowired
    private NoticeTemplateMapper noticeTemplateMapper;

    /**
     * 查询并格式化模版
     *
     * @param noticeNid
     * @param params
     * @return
     */
    private String renderContent(String noticeNid, Map<String, Object> params) {
        NoticeTemplate template = noticeTemplateMapper.selectOne(new NoticeTemplate(model -> {
            model.setNid(noticeNid);
        }));
        return StringUtils.filleTemplate(template.getTemplate(), params);
    }


    /**
     * 查询并格式化模版
     *
     * @param noticeNid
     * @param consumer
     * @return
     */
    public String renderContent(String noticeNid, Consumer<Map<String, Object>> consumer) {
        Map<String, Object> param = new HashMap<>();
        consumer.accept(param);
        return renderContent(noticeNid, param);
    }


}
