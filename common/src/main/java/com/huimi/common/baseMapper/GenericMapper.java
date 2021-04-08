package com.huimi.common.baseMapper;


/**
 * Created by fengting on 2017/8/3
 */
public interface GenericMapper<PO extends GenericPo<PK>, PK>
        extends
        QueryMapper<PO, PK>,
        InsertMapper<PO, PK>,
        UpdateMapper<PO, PK>,
        DeleteMapper<PO, PK> {
}
