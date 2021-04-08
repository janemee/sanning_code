/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.admin.config;

import com.huimi.common.utils.SpringContextUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigConsts;
import com.huimi.core.service.cache.RedisService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 枫亭
 * @date 2018-04-13 15:37.
 */
public class WebModel {

    /**
     * 网站名称
     */
    @Getter
    @Setter
    private String webName;

    /**
     * PC端访问地址
     */
    @Getter
    @Setter
    private String webPcUrl;

    /**
     * 图片资源访问地址
     */
    @Getter
    @Setter
    private String webImageUrl;

    /**
     * app访问地址
     */
    @Getter
    @Setter
    private String webAppUrl;

    /**
     * web接口地址
     */
    @Getter
    @Setter
    private String apiWebUrl;

    /**
     * 行情地址
     */
    @Getter
    @Setter
    private String apiQuoteUrl;

    /**
     * 管理后台地址
     */
    @Getter
    @Setter
    private String adminUrl;

    /**
     *管理后台 静态资源地址
     */
    @Getter
    @Setter
    private String staticAdminUrl;

    /**
     * 备案号
     */
    @Getter
    @Setter
    private String copyRight;


    /**
     * 初始化
     */
    public static WebModel initWebModel(){
        WebModel model = new WebModel();
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        model.setWebName(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.WEB_NAME));
        model.setAdminUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.ADMIN_SERVER_URL));
        model.setApiQuoteUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.API_quote_SERVER_URL));
        model.setApiWebUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.API_web_SERVER_URL));
        model.setWebPcUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.PC_SERVER_URL));
        model.setWebImageUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.IMAGE_SERVER_URL));
        model.setCopyRight(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.COPY_RIGHT));
        model.setStaticAdminUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.STATIC_SERVER_URL));
        model.setWebAppUrl(redisService.get(CacheID.CONFIG_PREFIX + ConfigConsts.APP_SERVER_URL));
        return model;
    }

}
