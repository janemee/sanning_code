/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.apis.controller.common;

import com.huimi.apis.config.WebInterceptor;
import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.core.service.cache.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 枫亭
 * @date 2018-04-13 15:31.
 */
@RestController
@RequestMapping("/api/web/common")
public class WebController extends WebGenericController {


    @Autowired
    private RedisService redisService;

    @PostMapping("")
    public ResultEntity common() {
        ResultEntity resultEntity = new ResultEntity();
        Integer userId = -1;
        if (WebInterceptor.isLogin()) {
            userId = getLoginUserId();
        }
        try {
            Map<String, Object> data = new HashMap<>();
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setData(data);
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
    }
}
