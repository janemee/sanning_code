package com.huimi.core.service.personnel;

import cn.hutool.db.Page;
import com.huimi.core.po.personnel.Personnel;
import com.huimi.core.req.EquipmentRoleModelReq;
import com.huimi.core.service.base.GenericService;

/**
 * 人员信息管理
 */
public interface PersonnelService extends GenericService<Integer, Personnel> {

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 手机号
     * @return
     */
    Personnel findByPhone(String phone);

    /**
     * 根据用户id查询分页列表
     *
     * @param equipmentModelReq
     * @return
     */
    Page findUsersByPage(EquipmentRoleModelReq equipmentModelReq);


}
