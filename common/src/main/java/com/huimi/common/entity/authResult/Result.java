package com.huimi.common.entity.authResult;

import lombok.Data;

@Data
public class Result {
    public String name;
    public String idcard;
    //认证结果1成功
    public String res;
    //证件图片
    public String photo;
    //认证结果备注
    public String description;
}
