package com.huimi.core.service.base;

import com.huimi.common.baseMapper.DeleteMapper;
import com.huimi.common.baseMapper.GenericPo;
import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor = Exception.class)
public interface DeleteService<PK, PO extends GenericPo<PK>> {

    DeleteMapper<PO, PK> _getDeleteMapper();

    default int deleteByPrimaryKey(PK pk) {
        return _getDeleteMapper().deleteByPrimaryKey(pk);
    }
}
