package com.huimi.apis.controller.equipment;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.DateUtil;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.equipment.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * create by lja on 2020/8/4 10:16
 */
@RestController
@RequestMapping("/api/v1/douyin/device")
@Api(tags = "手机code-需要登录")
public class EquipmentController extends WebGenericController<Integer, Equipment> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);
    @Resource
    private EquipmentService equipmentService;

    /**
     * 手机上线
     *
     * @param deviceUid      账户唯一标识
     * @param deviceCode     账户名称
     * @param invitationCode 代理商邀请码
     * @return
     */
    @ApiOperation(value = "手机上线")
    @RequestMapping("/online")
    public ResultEntity updateOncCode(@RequestParam("device_id") String deviceUid,
                                      @RequestParam("device_code") String deviceCode,
                                      @RequestParam(value = "invitation_code", required = false) String invitationCode) {
        if (StringUtil.isBlank(deviceUid)) {
            return fail("设备信息获取异常，请切换网络后重试");
        }
        if (StringUtil.isBlank(deviceCode)) {
            deviceCode = DateUtil.dateStr(new Date(), "yyyyMMddHHmmss");
        }
        try {
            //查找并注册设备信息
            Equipment equipment = equipmentService.selectByUid(deviceUid, deviceCode, invitationCode,"");
            HashMap<String, Object> data = new HashMap<>();
            data.put("device_id", equipment.getDeviceUid());
            data.put("device_code", equipment.getDeviceCode());
            data.put("adminsId", equipment.getSysAdminId());
            return ok(data, equipment.getRemakes());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @ApiOperation(value = "手机下线")
    @RequestMapping("/offline")
    public ResultEntity updateDownCode(@RequestParam("device_id") String deviceUid, @RequestParam("device_code") String deviceCode) {
        try {
            equipmentService.offline(deviceUid, deviceCode);
        } catch (Exception e) {
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
        return ok(null, "success");
    }


}
