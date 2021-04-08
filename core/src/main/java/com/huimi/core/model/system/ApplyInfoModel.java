package com.huimi.core.model.system;

import com.huimi.common.utils.DateUtils;
import lombok.Data;

import java.util.Date;

@Data
public class ApplyInfoModel {

    private Integer id;

    private Integer userId;

    private String typeTitle;

    private String content;

    private String url;

    private String creataTime;

    public ApplyInfoModel(Integer id, String typeTitle, String content, String url, Date creataTime) {
        this.id = id;
        this.typeTitle = typeTitle;
        this.content = content;
        this.url = url;
        if(creataTime != null){
            this.creataTime = "<i class=\"fa fa-clock-o\"></i> ";
            this.creataTime += DateUtils.dateStr( creataTime,"yyyy-MM-dd HH:mm:ss");
        }

    }
}
