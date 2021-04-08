package com.huimi.admin.controller.tikTok.liveHotSubTask;

import cn.hutool.core.collection.CollectionUtil;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.tools.ThreadPoolUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.common.utils.ToolDateTime;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.task.TaskDetailsService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.Task;
import com.huimi.core.task.TaskAdminPramsModel;
import com.huimi.core.util.TaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * 超级热度子任务控制类
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(BaseController.BASE_URI)
public class LiveHotSubTaskJsonController extends BaseController {

    @Resource
    private TaskService taskService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private TaskDetailsService taskDetailsService;
    @Resource
    private LiveHotSubTaskService liveHotSubTaskService;
    @Resource
    private AdminService adminService;
    @Resource
    private CommentTemplateService commentTemplateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveHotSubTaskJsonController.class);

    @ResponseBody
    @RequestMapping("task/json/subHotTaskList")
    public DtGrid listJson(HttpServletRequest request, int rows, int page, Integer id) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("search_val");

        whereSql.append(" and t.task_detail_id = ").append(id);
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and t.id = ").append(nid);
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = liveHotSubTaskService.getDtGridList(dtGrid);

        return dtGrid;
    }


    /**
     * 添加超级热度
     */
    @RequestMapping(value = "/task/superHeat/json/add", method = RequestMethod.POST)
    public ResultEntity addSuperHeat(TaskAdminPramsModel taskAdminPramsModel, Task task) throws BussinessException {
        Admin admin = AdminSessionHelper.getCurrAdmin();
        try {
            //检查直播链接是否正确
            if (StringUtil.isNotBlank(taskAdminPramsModel.getLiveInContent()) && StringUtil.isBlank(TaskUtil.saveLiveInContent(taskAdminPramsModel.getLiveInContent()))) {
                return fail("直播链接信息有误，请检查后重试");
            }
            long s1 = System.currentTimeMillis();
            //定时开始时间不能小于当前时间
            if (StringUtil.isNotBlank(taskAdminPramsModel.getTaskStartTime())) {
                Date date = DateUtils.getDateByFullDateStr(taskAdminPramsModel.getTaskStartTime());
                assert date != null;
                if (date.before(new Date())) {
                    throw new BussinessException("定时日期开始不能小于当前时间");
                }
            }
            taskAdminPramsModel.setAdmin(admin);
            //获取设备列表
            ArrayList<Equipment> allEquipments = equipmentService.getEquipmentList(taskAdminPramsModel);
            if (CollectionUtil.isEmpty(allEquipments)) {
                return fail("未选择设备或选择的设备不存在，请选择设备");
            }
            //添加任务
            task.setTaskType(EnumConstants.TaskType.LIVE_HOT.value);
            task.setDelFlag(GenericPo.DELFLAG.NO.code);
            task.setIsLiveHot(1);
            task.setSysAdminId(admin.getId());
            String taskRunCode = task.getTaskRunCode();
            Integer taskExpectRunning = task.getTaskExpectRunning();
            task.setTaskExpectRunning(taskExpectRunning);
            boolean sendFlag = taskRunCode.equals(EnumConstants.TaskRunCode.RANDOM.code) || taskRunCode.equals(EnumConstants.TaskRunCode.NOW.code);
            if (sendFlag) {
                task.setTaskStartTime(new Date());
                Calendar nowTime = Calendar.getInstance();
                nowTime.add(Calendar.MINUTE, taskExpectRunning);
                task.setTaskEndTime(nowTime.getTime());
            } else {
                task.setTaskStartTime(ToolDateTime.parse(taskAdminPramsModel.getTaskStartTime(), "yyyy-MM-dd HH:mm:ss"));
                Calendar instance = Calendar.getInstance();
                instance.setTime(task.getTaskStartTime());
                instance.add(Calendar.MINUTE, taskExpectRunning);
                task.setTaskEndTime(instance.getTime());
            }
            System.out.println("check params took: " + (System.currentTimeMillis() - s1));
            if (StringUtils.isNotBlank(task.getLiveInContent())) {
                task.setAnalysisContent(TaskUtil.saveLiveInContent(task.getLiveInContent()));
            }
            long s2 = System.currentTimeMillis();
            String millis = System.currentTimeMillis() + "";
            //开启线程 放入线程池 异步处理
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            executorService.execute(new Thread(() -> taskService.addSuperHeatTask(task, allEquipments, millis)));
            System.out.println("add task to database took: " + (System.currentTimeMillis() - s2));
            return ResultEntity.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }

    }

    /**
     * 查找超级热度下所有的设备
     */
    @ResponseBody
    @RequestMapping(value = "task/json/getEquipmentsByTaskId")
    public DtGrid getByTaskId(HttpServletRequest request, String id, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        if (!id.equals("undefined")) {
            dtGrid = taskDetailsService.findByAllDevCode(Long.valueOf(id));
        }
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return dtGrid;

    }

    /**
     * 查找超级热度下所有的设备分组
     */
    @ResponseBody
    @RequestMapping(value = "task/json/getEquipmentGroupsByTaskId")
    public DtGrid getByGroupsTaskId(HttpServletRequest request, String id, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        if (!id.equals("undefined")) {
            dtGrid = equipmentGroupService.findtaskGroup(Long.valueOf(id));
            List<Object> exhibitDatas = dtGrid.getExhibitDatas();
            exhibitDatas.removeIf(Objects::isNull);
        }
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return dtGrid;

    }


    /**
     * 添加实时互动任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/interaction/json/add")
    public ResultEntity addImmediatelyTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            //根据任务类型获取对应的枚举
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加加入粉丝团任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/fans/json/add")
    public ResultEntity addFans(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }


    /**
     * 添加关注打榜任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/makeList/json/add")
    public ResultEntity addMakeListTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加红包任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/redEnvelope/json/add")
    public ResultEntity addRedEnvelopeTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加查看商店任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/lookShopping/json/add")
    public ResultEntity addLookShoppingTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            if (taskAdminPramsModel.getTaskExpectRunning() == null || taskAdminPramsModel.getTaskExpectRunning() < 1) {
                return fail("请填写正确的任务时间，任务时间不能小于1分钟");
            }
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送礼物任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/giveGift/json/add")
    public ResultEntity addGiveGiftTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送礼物任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/fansGroup/json/add")
    public ResultEntity addFansGroupTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加关注PK对手任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/followPk/json/add")
    public ResultEntity addFollowPkTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加疯狂点屏任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/insaneClick/json/add")
    public ResultEntity addInsaneClickTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkSuperHeatTime(taskAdminPramsModel.getHeart().intValue(), taskAdminPramsModel.getTaskExpectRunning());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加关注任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/follow/json/add")
    public ResultEntity addFollowTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //找到全部设备
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加抢福袋
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/grabFuBag/json/add")
    public ResultEntity addGrabFuBagTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkedPrams(taskAdminPramsModel);
            //找到全部设备
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加停止任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/overSubTask/json/add")
    public ResultEntity addOverSubTaskTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            this.checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送灯牌任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/giveLightPlateTask/json/add")
    public ResultEntity addGiveLightPlate(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            this.checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //获取线程池
            ExecutorService executorService = ThreadPoolUtil.getPool(taskAdminPramsModel.getTaskName());
            //通过redis发布/订阅模式发送任务
            executorService.execute(new Thread(() -> taskService.addImmediatelyTask(allEquipments, taskAdminPramsModel.getETaskType(), taskAdminPramsModel)));
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 添加任务参数校验
     */
    public void checkedPrams(TaskAdminPramsModel taskAdminPramsModel) {
        EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
        //设置为抖音任务
        taskAdminPramsModel.setPlatform(EnumConstants.PLAT_FROM_TYPE.TIKTOK.value);
        if (taskType == null) {
            throw new BussinessException("任务类型有误");
        }
        taskAdminPramsModel.setETaskType(taskType);
        if (StringUtils.isBlank(taskAdminPramsModel.getDevicesOrGroupsId())) {
            throw new BussinessException("请选择设备或分组后重试");
        }
        //实时互动
        if (taskType.equals(EnumConstants.TaskType.LIVE_CHAT)) {
            if (StringUtil.isBlank(taskAdminPramsModel.getContent())) {
                throw new BussinessException("话术内容不能为空");
            }
            //复制文本内容
            taskAdminPramsModel.setLiveInContent(taskAdminPramsModel.getContent());
        }
        //关注打榜
        if (taskType.equals(EnumConstants.TaskType.FOLLOW_MAKER_A_LIST)) {
            if (taskAdminPramsModel.getMakeListNum() == null || taskAdminPramsModel.getMakeListNum() < 0) {
                throw new BussinessException("请输入正确打榜关注数量");
            }
        }

        //抢红包
        if (taskType.equals(EnumConstants.TaskType.GRAB_A_RED_ENVELOPE)) {
            if (taskAdminPramsModel.getRedEnvelopeTime() == null || taskAdminPramsModel.getRedEnvelopeTime() < 0) {
                throw new BussinessException("请输入抢红包时间");
            }
        }

        //赠送礼物
        if (taskType.equals(EnumConstants.TaskType.GIVE_GIFT) || taskType.equals(EnumConstants.TaskType.FANS_GROUP)) {
            if (taskAdminPramsModel.getGiftNumber() == null || taskAdminPramsModel.getGiftNumber() < 0) {
                throw new BussinessException("请输入正确的礼物数量");
            }
            if (taskAdminPramsModel.getGiftPage() == null || taskAdminPramsModel.getGiftPage() < 0) {
                throw new BussinessException("请输入正确的礼物页数");
            }
            if (StringUtil.isBlank(taskAdminPramsModel.getGiftBox())) {
                throw new BussinessException("请选择正确的礼物格子");
            }
        }
        //疯狂点屏幕
        if (taskType.equals(EnumConstants.TaskType.INSANE_CLICK)) {
            if (taskAdminPramsModel.getClickNumber() == null || taskAdminPramsModel.getClickNumber() < 0) {
                throw new BussinessException("请输入正确点击次数");
            }
            if (taskAdminPramsModel.getCommentInterval() == null || taskAdminPramsModel.getCommentInterval() < 0) {
                throw new BussinessException("请输入正确的任务运行间隔时间");
            }
            if (taskAdminPramsModel.getTaskExpectRunning() == null || taskAdminPramsModel.getTaskExpectRunning() < 0) {
                throw new BussinessException("请输入正确的任务时间");
            }
        }

    }

    /**
     * 获取热度任务设备列表
     *
     * @param taskAdminPramsModel
     * @return
     */
    public ArrayList<Equipment> allEquipments(TaskAdminPramsModel taskAdminPramsModel) {
        ArrayList<Equipment> allEquipments = new ArrayList<>();
        String devicesOrGroupsId = taskAdminPramsModel.getDevicesOrGroupsId();
        String[] devicesOrGroupsIdSplit = devicesOrGroupsId.split(",");
        if (taskAdminPramsModel.getDeviceStyle().equals(EnumConstants.DevicesOrGroupsId.EQUIPEMENT.code)) {
            allEquipments = equipmentService.selectByIds(Arrays.asList(devicesOrGroupsIdSplit));
        } else {
            allEquipments = equipmentService.selectLiveHotGroup(taskAdminPramsModel.getHeart(), Arrays.asList(devicesOrGroupsIdSplit));
        }
        return allEquipments;
    }

    /**
     * 校验子任务运行时间不能超过主任务时间
     *
     * @param superHeartId      主任务id
     * @param taskExpectRunning 子任务时间
     * @return
     */
    public void checkSuperHeatTime(Integer superHeartId, Integer taskExpectRunning) {
        try {

            Task task = taskService.selectByPrimaryKey(superHeartId);
            if (task == null) {
                throw new BussinessException("超级热度任务不存在，请刷新后重试");
            }
            //疯狂点屏任务时间
            if (null == taskExpectRunning) {
                throw new BussinessException("请输入任务时间");
            }

            Date date = EnumConstants.TaskRunCode.DELAY.code.equals(task.getTaskRunCode()) ? task.getCreateTime() : task.getTaskStartTime();
            //子任务时间不能大于主任务运行时间
            Date superHartTime = DateUtils.rollMinute(date, task.getTaskExpectRunning());
            //子任务时间
            Date giveLightPlateTime = DateUtils.rollMinute(new Date(), taskExpectRunning);
            System.out.println(DateUtils.dateStr(superHartTime, "yyyy-MM-dd HH:mm:ss") + "--" + DateUtils.dateStr(giveLightPlateTime, "yyyy-MM-dd HH:mm:ss"));
            if (superHartTime.before(giveLightPlateTime)) {
                long surplusTime = DateUtils.minutesBetween(new Date(), superHartTime);
                if (surplusTime <= 0) {
                    throw new BussinessException("超级热度任务是否已结束，请刷新后重试");
                }
                String msg = "超级热度任务时间为" + DateUtils.dateStr(superHartTime, "yyyy-MM-dd HH:mm:ss");
                msg += ",任务时间不能大于" + surplusTime + "分钟";
                throw new BussinessException("任务时间不能超过超级热度任务时间," + msg);
            }
            System.out.println("校验通过");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussinessException(e.getMessage());
        }

    }
}

