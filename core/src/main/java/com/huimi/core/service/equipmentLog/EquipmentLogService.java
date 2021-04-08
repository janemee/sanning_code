package com.huimi.core.service.equipmentLog;

import cn.hutool.db.Page;
import com.huimi.core.po.equipmentLog.EquipmentLog;
import com.huimi.core.req.BasePageReq;
import com.huimi.core.req.EquipmentModelReq;
import com.huimi.core.service.base.GenericService;

import java.util.List;

public interface EquipmentLogService extends GenericService<Integer, EquipmentLog> {


    List<EquipmentLog> findByPage(BasePageReq basePageReq);

    long findByPageCount(BasePageReq basePageReq);

    Page findUsersByPage(EquipmentModelReq equipmentModelReq);
}
