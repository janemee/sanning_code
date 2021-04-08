package com.huimi.common.baseMapper;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.base.BaseSelectMapper;

import java.util.List;
import java.util.Map;

/**
 * 基础查询
 * Created by Donfy on 2017/8/3
 */
public interface QueryMapper<PO extends GenericPo<PK>, PK> extends BaseSelectMapper<PO> {

    /**
     * DtGrid 总数查询
     */
    @SelectProvider(type = GenericMapperProvider.class, method = "countBySql")
    long countBySql(Map<String, Object> param);

    /**
     * DtGrid分页查询
     */
    @SelectProvider(type = GenericMapperProvider.class, method = "findListBySql")
    List<PO> findListBySql(Map<String, Object> params);

    /**
     * DtGrid分页查询
     */
    @SelectProvider(type = GenericMapperProvider.class, method = "countBySqlJoin")
    long countBySqlJoin(Map<String, Object> params);

    /**
     * DtGrid分页查询
     */
    @SelectProvider(type = GenericMapperProvider.class, method = "findListBySqlJoin")
    List<?> findListBySqlJoin(Map<String, Object> params);

    /**
     * 更新操作数据重复性校验
     */
    @SelectProvider(type = GenericMapperProvider.class, method = "checkRepeatSql")
    int checkRepeatSql(PO model);
}
