package com.huimi.admin.config;

import com.huimi.core.constant.CacheID;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.ConfService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring boot启动初始化数据
 *
 * @author zhangfoshou
 * @date 2018/10/20 10:11
 */
@Component
@Order(value = 1)
public class InitializationRunner implements CommandLineRunner {
    @Resource
    private RedisService redisService;
    @Resource
    private ConfService confService;

    @Override
    public void run(String... strings) throws Exception {
        initRedisData();
    }

    /**
     * 初始化redis数据
     */
    private void initRedisData() {
        // 加载系统参数
        List<Conf> confList = confService.select(new Conf(model -> {
            model.setState(Conf.STATE.ENABLED.code);
        }));
        confList.forEach(conf -> redisService.put(CacheID.CONFIG_PREFIX + conf.getNid(), conf.getValue()));
    }
}
