package com.huimi.core.service.base;

import com.huimi.common.baseMapper.*;
import com.huimi.common.baseMapper.*;

/**
 * Created by DFx-Dellbook on 2017/8/8
 */
public interface GenericService<PK, PO extends GenericPo<PK>>
        extends QueryService<PK, PO>,
        InsertService<PK, PO>,
        UpdateService<PK, PO>,
        DeleteService<PK, PO> {

    GenericMapper<PO, PK> _getMapper();

    @Override
    default QueryMapper<PO, PK> _getQueryMapper() {
        return _getMapper();
    }

    @Override
    default InsertMapper<PO, PK> _getInsertMapper() {
        return _getMapper();
    }

    @Override
    default UpdateMapper<PO, PK> _getUpdateMapper() {
        return _getMapper();
    }

    @Override
    default DeleteMapper<PO, PK> _getDeleteMapper() {
        return _getMapper();
    }
}
