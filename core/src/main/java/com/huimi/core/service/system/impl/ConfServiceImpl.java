package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.page.PageBean;
import com.huimi.core.mapper.system.ConfMapper;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.system.ConfService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统参数配置表业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class ConfServiceImpl implements ConfService {

    @Resource
    private ConfMapper confMapper;

    @Override
    public GenericMapper<Conf, Integer> _getMapper() {
        return confMapper;
    }

    @Override
    public PageBean<Conf> paginateByData(String search_val, Integer pageNumber, Integer pageSize) {
//        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("tableName", Conf.TABLE_NAME);
//        sql.put("where", "name like %" + search_val + "% or " + "nid like %" + search_val + "%");
        params.put("whereSql", " and name like '%网%'");
        params.put("nowPage", (pageNumber - 1) * pageSize);
        params.put("pageSize", pageSize);
        List<Conf> confList = confMapper.findListBySql(params);
        PageBean<Conf> result = new PageBean<>();
        result.setItems(confList);
        return result;
    }

    @Override
    public Integer updateConfByNid(String customer_qrcode_img, String url) {
        return confMapper.updateConfByNid(customer_qrcode_img, url);
    }
}
