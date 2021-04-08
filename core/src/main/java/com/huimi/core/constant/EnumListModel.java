package com.huimi.core.constant;

import lombok.Data;

@Data
public class EnumListModel {

    //主键
    private String key;
    //值
    private String value;
    //描述
    private String desc;

    public EnumListModel() {
    }

    public EnumListModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public EnumListModel(String key, String value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }
}
