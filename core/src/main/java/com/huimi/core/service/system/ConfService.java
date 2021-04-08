package com.huimi.core.service.system;

import com.huimi.common.page.PageBean;
import com.huimi.core.po.system.Conf;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 系统参数配置表业务相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface ConfService extends GenericService<Integer, Conf> {

    PageBean<Conf> paginateByData(String search_val, Integer pageNumber, Integer pageSize);

    Integer updateConfByNid(String customer_qrcode_img, String url);
}
