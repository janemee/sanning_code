package com.huimi.apis.config;

import com.huimi.common.utils.SpringContextUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.common.utils.ToolAES;
import com.huimi.common.utils.ToolDateTime;
import com.huimi.core.config.GenericController;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 默认拦截器 Created by fengting on 2016/10/21.
 */
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    public static String COOKIE_SESSION_ID = "cookie:user:sessionId:";

    private static String CACHE_UUID_ID = "cache:user:uuid:";

    //登录有效期 可配置 -1 永久 其他时间根据当前时间而定 单位 分钟
//    private static int maxAgeSeconds = -1;


    //登录有效期 可配置 -1 永久 其他时间根据当前时间而定 单位 分钟
    public static long getMaxAgeSeconds() {
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        //若后台没有配置 则有效期为永久
        return redisService.getLong(ConfigNID.login_USER_COOKIE_TIME);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BussinessException {

        // 在请求处理之前进行调用（Controller方法调用之前）
        // 登录校验
        this.doLoginAuth(request);

        return true;
    }

    private void doLoginAuth(HttpServletRequest request) {
        if (StringUtils.isBlank(getLoginUserUuid(request))) {
            throw new BussinessException(BussinessException.Code.E401);
        }
    }

    private boolean isNeedLoginAuth(String uri) {
        if ("".equals(uri) || uri == null) {
            return false;
        }

        //包含的uri集合，可以使用*作为通配符
        List<String> includeUris = new ArrayList<>();
        includeUris.add("/api/member/*");
        for (String str : includeUris) {
            //如果包含通配符
            if (str.contains("*")) {
                String prefix = str.substring(0, str.indexOf("*") - 1);
                if (uri.startsWith(prefix)) {
                    return true;
                }
            } else { //不包含通配符
                if (uri.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前登录用户的UUID
     *
     * @param request request
     * @return uuid
     */
    public static String getLoginUserUuid(HttpServletRequest request) {
        String sessionId = request.getParameter("sessionId");
        String uuid = request.getParameter("uuid");
        if (StringUtils.isBlank(uuid)) {
            return null;
        }
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        String redsUuid = redisService.get(CACHE_UUID_ID + uuid);
        if (StringUtils.isBlank(redsUuid)) {
            return null;
        }
        String encrypt = redisService.get(COOKIE_SESSION_ID + uuid + ":" + sessionId);
        if (StringUtils.isBlank(encrypt)) {
            return null;
        }
        String decryptData = ToolAES.decrypt(encrypt);
        if (null == decryptData || "".equals(decryptData)) {
            redisService.del(COOKIE_SESSION_ID + sessionId);
            return null;
        }
        String language = redisService.get(CacheID.LANGUAGE_TYPE + uuid);

        String[] data = decryptData.split(".#.");
        //2.分解认证数据
        String cacheUuid;
        String userAgent;
        try {
            cacheUuid = data[1];
            userAgent = data[2];
        } catch (Exception e) {
            redisService.del(COOKIE_SESSION_ID + sessionId);
            return null;
        }
        if (!uuid.equals(cacheUuid)) {
            return null;
        }
        // 重新生成认证cookie，目的是更新时间戳
        String token = String.valueOf(ToolDateTime.getDateByTime()) + ".#." + cacheUuid + ".#." + userAgent + ".#." + sessionId;
        String authMark = ToolAES.encrypt(token);
        // 保存用户uuid到redis中
        redisService.put(COOKIE_SESSION_ID + sessionId, authMark, getMaxAgeSeconds());

        //更新用户uuid为key保存用户id的时间
        redisService.put(CACHE_UUID_ID + cacheUuid, redisService.get(CACHE_UUID_ID + cacheUuid, Integer.class), getMaxAgeSeconds());
        redisService.put(CACHE_UUID_ID + cacheUuid, redisService.get(CACHE_UUID_ID + cacheUuid, Integer.class), getMaxAgeSeconds());
        //更新用户中英保存时间
        if (!StringUtils.isBlank(language)) {
            redisService.put(CacheID.LANGUAGE_TYPE + uuid, language, getMaxAgeSeconds());
        }
        return cacheUuid;
    }

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public static Integer getLoginUserId() {
        HttpServletRequest request = GenericController.getRequest();
        String loginUserUuid = getLoginUserUuid(request);
        if (StringUtils.isBlank(loginUserUuid)) {
            return null;
        }
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        return redisService.get(CACHE_UUID_ID + loginUserUuid, Integer.class);
    }


    /**
     * 删除之前的用户session信息
     */
    private static void removePreSessionInfo(String userUUid) {
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        Set<String> keys = redisService.getKeys(COOKIE_SESSION_ID + userUUid);
        for (String key : keys) {
            redisService.del(key);
        }
    }

    public static boolean isLogin() {
        HttpServletRequest request = GenericController.getRequest();
        String sessionId = request.getParameter("sessionId");
        String uuid = request.getParameter("uuid");
        if (StringUtils.isBlank(uuid)) {
            return false;
        }

        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        String redsUuid = redisService.get(CACHE_UUID_ID + uuid);
        if (StringUtils.isBlank(redsUuid)) {
            return false;
        }
        String encrypt = redisService.get(COOKIE_SESSION_ID + uuid + ":" + sessionId);
        if (StringUtils.isBlank(encrypt)) {
            return false;
        }
        String decryptData = ToolAES.decrypt(encrypt);
        if (null == decryptData || "".equals(decryptData)) {
            redisService.del(COOKIE_SESSION_ID + sessionId);
            return false;
        }

        String[] data = decryptData.split(".#.");
        //2.分解认证数据
        String cacheUuid;
        String userAgent;
        try {
            cacheUuid = data[1];
            userAgent = data[2];
        } catch (Exception e) {
            redisService.del(COOKIE_SESSION_ID + sessionId);
            return false;
        }
        if (!uuid.equals(cacheUuid)) {
            return false;
        }

        return true;
    }

    /**
     * 清除用户在缓存中登录信息
     *
     * @param uuid      登录用户uuid
     * @param sessionId 会话ID
     */
    public static void outLoginUser(String uuid, String sessionId) {
        RedisService redisService = SpringContextUtils.getBean(RedisService.class);
        // 清除用户在redis中密登录信息
        redisService.del(CACHE_UUID_ID + uuid);
        //清除用户uuid为key的用户id
        redisService.del(COOKIE_SESSION_ID + sessionId);
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
