package com.huimi.common.baseMapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface InsertMapper<PO extends GenericPo<PK>, PK> {

    /**
     * 新增对象
     *
     * @param PO 需要保存的对象
     * @return result != 1 则保存失败
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = GenericMapperProvider.class, method = "insert")
    int insert(PO PO);

    /**
     * 新增对象
     *
     * @param PO 需要保存的对象
     * @return result != 1 则保存失败
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = GenericMapperProvider.class, method = "insertList")
    int insertList(List<PO> PO);

}
