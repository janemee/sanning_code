package com.huimi.core.service.equipment.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.JsonUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.equipment.EquipmentMapper;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Conf;
import com.huimi.core.po.system.Role;
import com.huimi.core.req.EquipmentModelReq;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.ConfService;
import com.huimi.core.service.system.RoleService;
import com.huimi.core.service.task.TaskDetailsService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.service.task.impl.TaskServiceImpl;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskAdminPramsModel;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * create by lja on 2020/7/28 17:33
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class EquipmentServiceImpl implements EquipmentService {
    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private AdminService adminService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private ConfService confService;
    @Resource
    private RoleService roleService;
    @Resource
    private TaskDetailsService taskDetailsService;
    @Resource
    private LiveHotSubTaskService liveHotSubTaskService;

    @Override
    public DtGrid findAll(DtGrid dtGrid) {
        Date date1 = new Date();
        System.out.println("开始时间----" + DateUtils.dateStr(date1, "HHmmss"));
        HashMap<String, String> hashMap = new HashMap<>();
        if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
            for (String key : dtGrid.getNcColumnsType().keySet()) {
                for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                    hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                }
                dtGrid.setNcColumnsTypeList(hashMap);
            }
        }
        // 表格查询参数处理
//            QueryUtils.parseDtGridSql(dtGrid);
        // 获取查询条件Sql
        String whereSql = dtGrid.getWhereSql();
        // 获取排序Sql
        String sortSql = dtGrid.getSortSql();

        Map<String, Object> params = new HashMap<>();
        params.put("whereSql", whereSql);
        params.put("sortSql", sortSql);
        long recordCount = equipmentMapper.selectAllCount(params);
        int pageSize = dtGrid.getPageSize();
        int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

        dtGrid.setPageCount(pageNum);
        dtGrid.setRecordCount(recordCount);

        params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
        params.put("pageSize", pageSize);

        List<Equipment> list = equipmentMapper.selectAllParams(params);
        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
        }));
        System.out.println("结束时间----" + DateUtils.dateStr(new Date(), "HHmmss"));
        return dtGrid;
    }

    @Override
    public DtGrid findSysAdminGroup(DtGrid dtGrid) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (dtGrid.getNcColumnsType() != null && dtGrid.getNcColumnsType().size() > 0) {
            for (String key : dtGrid.getNcColumnsType().keySet()) {
                for (int i = 0; i < dtGrid.getNcColumnsType().get(key).size(); i++) {
                    hashMap.put((String) dtGrid.getNcColumnsType().get(key).get(i), key);
                }
                dtGrid.setNcColumnsTypeList(hashMap);
            }
        }
        // 表格查询参数处理
//            QueryUtils.parseDtGridSql(dtGrid);
        // 获取查询条件Sql
        String whereSql = dtGrid.getWhereSql();
        // 获取排序Sql
        String sortSql = dtGrid.getSortSql();
        Integer adminId = this.findAdmin();
        Map<String, Object> params = new HashMap<>();
        params.put("whereSql", whereSql);
        params.put("sortSql", sortSql);
        params.put("sysAdminId", adminId);
        long recordCount = equipmentMapper.selectAllGroupEquipmentCount(params);
        int pageSize = dtGrid.getPageSize();
        int pageNum = (int) recordCount / dtGrid.getPageSize() + (recordCount % dtGrid.getPageSize() > 0 ? 1 : 0);

        dtGrid.setPageCount(pageNum);
        dtGrid.setRecordCount(recordCount);

        params.put("nowPage", (dtGrid.getNowPage() - 1) * pageSize);
        params.put("pageSize", pageSize);

        List<Equipment> list = equipmentMapper.selectAllGroupEquipment(params);
        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(list), new TypeReference<List<Object>>() {
        }));
        return dtGrid;
    }

    @Override
    public GenericMapper<Equipment, Integer> _getMapper() {
        return equipmentMapper;
    }

    @Override
    public List<Equipment> selectByState(Integer state) {
        Integer adminId = this.findAdmin();
        Admin admin = adminService.selectByPrimaryKey(adminId);
        Role role = new Role();
        role.setName(EnumConstants.roleName.ADMIN.desc);
        role = roleService.selectOne(role);
        List<String> roleAdmin = adminService.findRoleAdmin(role.getId().toString());
        List<Equipment> list;
        if (StringUtils.isBlank(admin.getParentId())) {
            //管理员只能看自己的设备或者没有邀请码的设备
            list = equipmentMapper.selectAdminEquipment(roleAdmin, state);
        } else {
            list = equipmentMapper.selectByState(admin.getCode());
        }
        if (list != null) {
            for (Equipment equipment : list) {
                if (equipment.getState() == 1) {
                    equipment.setStatus("online");
                }
            }
        }
        return list;
    }

    @Override
    public ArrayList<Equipment> selectLiveHotGroup(Long id, List groupId) {
        return equipmentMapper.selectLiveHotGroup(id, groupId);
    }

    @Override
    public ArrayList<Equipment> findAllStateByGroup(ArrayList<Long> list, Integer sysAdminId) {
        return equipmentMapper.findAllStateByGroup(list, sysAdminId);
    }

    @Override
    public Integer updateByUid(Integer state, String deviceUid) {
        return equipmentMapper.updateByUid(state, deviceUid);
    }

    @Override
    public ArrayList<Equipment> findAllStateByGroupAgent(ArrayList<Long> list, List adminId) {
        return equipmentMapper.findAllStateByGroupAgent(list, adminId);
    }

    @Override
    public Equipment selectByUid(String uid) {
        return equipmentMapper.selectByUid(uid);
    }

    @Override
    public Equipment selectByUid(String deviceUid, String deviceCode, String invitationCode, String channelId) {
        //查看账户是否存在
        Equipment selectEquipment = new Equipment();
        selectEquipment.setDeviceUid(deviceUid);
        Equipment eq = equipmentMapper.selectOne(selectEquipment);
        //必填开关
        int codeFlag = redisService.getInt(ConfigNID.REGISTER_CODE_FLAG);
        if (codeFlag == EnumConstants.TaskRunCode.NOW.value && StringUtil.isBlank(invitationCode)) {
            throw new BussinessException("请填写设备邀请码");
        }
        if (StringUtils.isNotBlank(invitationCode)) {
            //当添加的用户没有权限的时候
            Admin invitationAdmin = adminService.selectByInviteCode(invitationCode);
            if (null == invitationAdmin) {
                throw new BussinessException("用戶不存在");
            }
            if (invitationAdmin.getState() == 0) {
                throw new BussinessException("该用户已被禁用");
            }
            if (StringUtils.isBlank(invitationAdmin.getRoleIds())) {
                throw new BussinessException("邀请码不正确，请联系管理员");
            }
            Admin admin = adminService.findByCode(invitationCode);
            if (admin == null || !admin.getCode().equals(invitationCode)) {
                throw new BussinessException("邀请码错误");
            }
            //获取管理员默认分组
            EquipmentGroup equipmentGroup = equipmentGroupService.findByAdminId(admin.getId());
            if (equipmentGroup == null) {
                throw new BussinessException("当前账户未配置默认分组信息");
            }
            if (eq == null) {
                //判断是否为管理员如果是管理员不许校验设备数量
                Conf searchConf = new Conf();
                searchConf.setNid(EnumConstants.invitationCode.INVITATION_CODE.code);
                Conf resultConf = confService.selectOne(searchConf);
                //管理员没有设备上线
                if (!invitationCode.equals(resultConf.getValue())) {
                    //校验当前代理商添加设备数量
                    Integer count = equipmentMapper.findByAdminId(admin.getId());
                    //找到他下面2级代理商的设备总和
                    Admin agent2Admin = new Admin();
                    agent2Admin.setParentId(admin.getId());
                    List<Admin> selectAgentList = adminService.select(agent2Admin);
                    if (selectAgentList.size() > 0) {
                        Integer ports = admin.getPorts();
                        //2及代理商剩余的端口数量
                        Integer surplusPorts = adminService.freeEquipmentNum(admin);
                        int usePorts = ports - surplusPorts;
                        count += usePorts;
                    }
                    //邀请人的可邀请设备数量
                    int adminCount = admin.getPorts() == null ? 0 : admin.getPorts();
                    if (count >= adminCount) {
                        throw new BussinessException("可添加设备数量已上限，请联系管理员处理");
                    }
                }
                //注册
                return add(deviceUid, deviceCode, admin.getId(), equipmentGroup.getId(), channelId);
            } else {
                //当设备已注册时 更换所属用户
                if (eq.getSysAdminId() != null && !eq.getSysAdminId().equals(admin.getId())) {
                    //判断是否为管理员如果是管理员不许校验设备数量
                    Conf searchConf = new Conf();
                    searchConf.setNid(EnumConstants.invitationCode.INVITATION_CODE.code);
                    Conf resultConf = confService.selectOne(searchConf);
                    //管理员没有设备上线
                    if (!invitationCode.equals(resultConf.getValue())) {
                        //校验当前代理商添加设备数量
                        Integer count = equipmentMapper.findByAdminId(admin.getId());
                        //找到他下面2级代理商的设备总和
                        Admin agent2Admin = new Admin();
                        agent2Admin.setParentId(admin.getId());
                        List<Admin> selectAgentList = adminService.select(agent2Admin);
                        if (selectAgentList.size() > 0) {
                            Integer ports = admin.getPorts();
                            //2及代理商剩余的端口数量
                            Integer surplusPorts = adminService.freeEquipmentNum(admin);
                            int usePorts = ports - surplusPorts;
                            count += usePorts;
                        }
                        //邀请人的可邀请设备数量
                        int adminCount = admin.getPorts() == null ? 0 : admin.getPorts();
                        if (count >= adminCount) {
                            throw new BussinessException("可添加设备数量已上限，请联系管理员处理");
                        }
                    }
                    //更新所属用户
                    return online(eq, deviceCode, admin.getId(), equipmentGroup.getId(), channelId);
                }
                //获取当前上线的设备数量
                if (eq.getSysAdminId() != null) {
                    Conf searchConf = new Conf();
                    searchConf.setNid(EnumConstants.invitationCode.INVITATION_CODE.code);
                    Conf resultConf = confService.selectOne(searchConf);
                    if (!invitationCode.equals(resultConf.getValue())) {
                        int onlineNumber = equipmentMapper.findByAdminIdAndState(eq.getSysAdminId(), EnumConstants.TaskRunCode.NOW.value);
                        admin = adminService.findById(eq.getSysAdminId());
                        if (admin != null && onlineNumber > admin.getPorts() && admin.getPorts() >= 0) {
                            throw new BussinessException("在线设备数量已上限，有问题请联系管理员");
                        }
                    }
                }
                //设备上线
                assert admin != null;
                return online(eq, deviceCode, admin.getId(), null, channelId);
            }
        }
        //邀请码非必填时 没有邀请码 默认为0
        int flag = 0;
        if (eq == null) {
            //注册
            return add(deviceUid, deviceCode, 2, flag, channelId);
        }
        //上线
        return online(eq, deviceCode, null, null, channelId);
    }

    /**
     * 设备注册
     *
     * @param deviceUid  设备唯一标识
     * @param deviceCode 设备名称
     * @param adminId    代理商/管理员id
     * @param groupId    人员设备分组id
     */
    public Equipment add(String deviceUid, String deviceCode, Integer adminId, Integer groupId, String channelId) {
        //新增账户
        Equipment equipment = new Equipment();
        equipment.setDeviceUid(deviceUid);
        equipment.setDeviceCode(deviceCode);
        equipment.setType(Integer.parseInt(EnumConstants.equipmentType.E版本.value));
        equipment.setSysAdminId(adminId);
        //如果设备没有邀请码
        String code = null;
        if (adminId != 0) {
            code = adminService.selectByPrimaryKey(adminId).getCode();
        }
        equipment.setSysAdminCode(code);
        //如果该设备没有分组给他默认分组
        if (groupId == 0) {
            groupId = 1;
        }
        equipment.setChannelId(channelId);
        equipment.setGroupId(groupId);
        equipment.setState(EnumConstants.DoLoginFlag.YES.value);
        equipment.setLastTime(new Timestamp(System.currentTimeMillis()));
        equipment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        equipment.setRemakes("注册成功");
        if (0 == equipmentMapper.insert(equipment)) {
            throw new BussinessException("设备注册失败，请检查后重试");
        }
        return equipment;
    }

    /**
     * 设备上线
     */
    public Equipment online(Equipment equipment, String deviceCode, Integer adminId, Integer groupId, String channelId) {
        //新增账户
        equipment.setDeviceCode(deviceCode);
        if (adminId != null) {
            equipment.setSysAdminId(adminId);
            Admin admin = adminService.selectByPrimaryKey(adminId);
            equipment.setSysAdminCode(admin.getCode());
        } else {
            //默认是管理员的设备
            Conf conf = new Conf();
            conf.setNid(EnumConstants.invitationCode.INVITATION_CODE.code);
            Conf resultConf = confService.selectOne(conf);
            Admin testAdmin = new Admin();
            testAdmin.setRoleIds(resultConf.getValue());
            equipment.setSysAdminId(2);
            String inviteCode = resultConf.getValue();
            equipment.setSysAdminCode(inviteCode);
        }
        if (groupId != null) {
            equipment.setGroupId(groupId);
        }
        equipment.setChannelId(channelId);
        equipment.setState(EnumConstants.DoLoginFlag.YES.value);
        equipment.setLastTime(new Timestamp(System.currentTimeMillis()));
        equipment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (0 == equipmentMapper.updateByPrimaryKeySelective(equipment)) {
            throw new BussinessException("上线失败，请检查后重试");
        }
        equipment.setRemakes("设备已上线");
        return equipment;
    }

    @Override
    public List<Equipment> findListToTaskType(Long equipmentId, String taskType) {
        return equipmentMapper.findListToTaskType(equipmentId, taskType);
    }

    @Override
    public void offline(String deviceUid, String deviceCode) {
        Equipment equipment = equipmentMapper.selectByUid(deviceUid);
        if (equipment == null) {
            throw new BussinessException("设备信息不存在");
        }
        equipment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        equipment.setState(EnumConstants.DoLoginFlag.NO.value);
        //更新设备信息
        updateByPrimaryKeySelective(equipment);
        //下线之后是否需要主动清楚设备所有任务
        Integer flag = redisService.getInt(ConfigNID.ActiveOverFlag);
        log.info("是否开启停止任务开关" + flag);
        if (flag != null && EnumConstants.HistoryState.YES.value == flag) {
            String msg = "客户端主动发起结束任务..........原因：手动停止客户端或某些原因导致客户端停止";
            log.info(msg);
            flag = taskDetailsService.updateTaskOverByDeviceUid(deviceUid, msg);
            log.info("抖音任务停止完成" + flag);
            flag = liveHotSubTaskService.updateTaskOverByDeviceUid(deviceUid, msg);
            log.info("直播任务以及子任务停止完成" + flag);
            log.info("任务已结束");
        }
    }

    @Override
    public Integer updateBatchGrouping(String groupId, String[] ids) {
        Long[] answerIds = (Long[]) ConvertUtils.convert(ids, Long.class);
        return equipmentMapper.updateBatchGrouping(Long.parseLong(groupId), answerIds);

    }

    /**
     * 获取设备数量
     *
     * @param sysAdminId 登录人id
     */
    @Override
    public Integer findWorkNumber(int type, Integer sysAdminId) {
        List<Equipment> list = equipmentMapper.findWorkNumberByBusy(sysAdminId);
        int number = 0;
        if (type == 1) {
            for (Equipment e : list) {
                if (e.getTaskNumber() == 0) {
                    number = number + 1;
                }
            }
        } else {
            for (Equipment e : list) {
                if (e.getTaskNumber() > 0) {
                    number = number + 1;
                }
            }
        }
        return number;
    }

    @Override
    public List<Equipment> findByAll(String sysAdminCode) {
        return equipmentMapper.findByAll(sysAdminCode);
    }

    @Override
    public Integer findSubEquipmentNum(Integer parentId) {
        return equipmentMapper.findSubEquipmentNum(parentId);
    }

    @Override
    public DtGrid findEquipmentTask(Integer equipmentId, Integer state) {
        List<Task> retList = equipmentMapper.findEquipmentTask(equipmentId, state);

        DtGrid dtGrid = new DtGrid();


        dtGrid.setExhibitDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Object>>() {
        }));
        dtGrid.setExportDatas(JsonUtils.toGenericObject(JsonUtils.toJson(retList), new TypeReference<List<Map<String, Object>>>() {
        }));
        return dtGrid;
    }

    @Override
    public Integer findByAgentEquipment() {
        return equipmentMapper.findByAgentEquipment();
    }

    @Override
    public Integer findByAdminId(Integer adminId) {
        return equipmentMapper.findByAdminId(adminId);
    }


    /**
     * 查询用户id
     */

    public Integer findAdmin() {
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return resultAdmin.getId();
    }

    @Override
    public int updateStateByChannelId(String channelId) {
        return equipmentMapper.updateStateByChannelId(channelId);
    }

    @Override
    public HashMap findWorkNumberByGroup(Integer groupId) {
        List<Equipment> list = equipmentMapper.findByGroupList(groupId);
        return findEquipmentNumberByGroup(list);
    }

    @Override
    public ArrayList<Equipment> selectByIds(List ids) {

        return equipmentMapper.selectByIds(ids);
    }

    @Override
    public ArrayList<Equipment> getEquipmentList(TaskAdminPramsModel taskAdminPramsModel) {
        if (StringUtils.isBlank(taskAdminPramsModel.getDeviceStyle())) {
            throw new BussinessException("设备方式请选择");
        }
        if (EnumConstants.DevicesOrGroupsId.EQUIPEMENT.code.equals(taskAdminPramsModel.getDeviceStyle())) {
            if (StringUtils.isBlank(taskAdminPramsModel.getEquipments())) {
                throw new BussinessException("请选择设备或");
            }
            //按设备id查找设备列表
            return selectByIds(new ArrayList(Arrays.asList(taskAdminPramsModel.getEquipments())));
        } else {
            if (StringUtils.isBlank(taskAdminPramsModel.getEquipmentGroups())) {
                throw new BussinessException("请选择分组");
            }
            //添加分组下所有在线的设备
            ArrayList<Long> groupsId = new ArrayList(Arrays.asList(taskAdminPramsModel.getEquipmentGroups()));
            if (StringUtils.isNotBlank(taskAdminPramsModel.getAdmin().getParentId())) {
                return findAllStateByGroup(groupsId, taskAdminPramsModel.getAdmin().getId());
            } else {
                return findAllStateByGroupAgent(groupsId, null);
            }
        }
    }

    /**
     * 统计分析设备状态 运行 空闲 总数量
     *
     * @param list 设备列表
     * @return
     */
    public HashMap findEquipmentNumberByGroup(List<Equipment> list) {
        long start = System.currentTimeMillis();
        HashMap<String, Integer> data = new HashMap<>();
        //总数
        int count = 0;
        //在线设备
        int onLine = 0;
        //下线设备
        int downLine = 0;
        //正在运行
        int busy = 0;
        //空闲
        int free = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            count = list.size();
            //过滤下线设备
            list = list.stream().filter((Equipment e) -> e.getState() == 1).collect(Collectors.toList());
            onLine = list.size();
            //正在运行设备
            List<Equipment> arrayListFree = list.stream().filter((Equipment e) -> e.getTaskNumber() > 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(arrayListFree)) {
                busy = arrayListFree.size();
            }
            //空闲设备
            if (CollectionUtils.isNotEmpty(list)) {
                free = list.size() - busy;
            }
        }
        data.put("count", count);
        data.put("busy", busy);
        data.put("free", free);
        data.put("onLine", onLine);
        data.put("downLine", count - onLine);
        long end = System.currentTimeMillis();
        System.out.println("设备分析耗时：" + (end - start));
        log.info("");
        return data;
    }


    @Override
    public List<Equipment> findByGroupList(Integer groupId) {
        return equipmentMapper.findByGroupList(groupId);
    }

    @Override
    public Page findUsersByPage(EquipmentModelReq equipmentModelReq) {
        return equipmentMapper.findUsersByPage(equipmentModelReq);
    }

    @Override
    public void deleteByIds(String ids) {
        String[] idArr = ids.split(",");
        Equipment equipment = new Equipment();
        for (String id : idArr) {
            equipment.setGroupId(Integer.parseInt(id));
            if (1 == equipment.getGroupId().intValue()) {
                throw new BussinessException("默认分组不能删除");
            }
            List<Equipment> selectEquipemnt = this.select(equipment);
            if (selectEquipemnt.size() > 0) {
                for (Equipment resultEquipment : selectEquipemnt) {
                    resultEquipment.setGroupId(EnumConstants.DevicesOrGroupsId.EQUIPEMENT.value);
                    this.updateByPrimaryKeySelective(resultEquipment);
                }
            }
            int insert = equipmentGroupService.deleteByPrimaryKey(equipment.getGroupId());
            if (insert == 0) {
                throw new BussinessException("删除失败，分组id：" + equipment.getGroupId());
            }
        }
    }
}
