package com.huimi.common.baseMapper;

import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateMapper<PO extends GenericPo<PK>, PK> {

    /**
     * 更新记录,用于版本控制,id和version不能为空
     *
     * @param po id和version不能为空
     * @return 0则更新失败
     */
    @UpdateProvider(type = GenericMapperProvider.class, method = "updateByIdAndVersionSelective")
    int updateByIdAndVersionSelective(PO po);

    /**
     * 更新非空字段
     *
     * @param po
     * @return
     */
    @UpdateProvider(type = GenericMapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelective(PO po);
}
