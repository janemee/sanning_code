package com.huimi.core.service.base;

import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.baseMapper.UpdateMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional(rollbackFor = Exception.class)
public interface UpdateService<PK, PO extends GenericPo<PK>> {

    UpdateMapper<PO, PK> _getUpdateMapper();

    /**
     * 更新对象 非空值
     *
     * @param po 待更新的对象 id!=null
     * @return
     */
    default int updateByPrimaryKeySelective(PO po) {
        po.setUpdateTime(new Date());
        return _getUpdateMapper().updateByPrimaryKeySelective(po);
    }


    /**
     * 软删除
     *
     * @param po 待更新的对象 id!=null
     * @return
     */
    default int removeById(PO po) {
        po.setDelFlag(PO.DELFLAG.YES.code);
        po.setUpdateTime(new Date());
        return _getUpdateMapper().updateByPrimaryKeySelective(po);
    }

    /**
     * 带版本号更新
     *
     * @param po 待更新的对象 id!=null, version !=null
     * @return
     */
    default int updateByIdAndVersionSelective(PO po) {
        return _getUpdateMapper().updateByIdAndVersionSelective(po);
    }


}
