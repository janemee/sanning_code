package com.huimi.core.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.encode.SaltEncodeUtil;
import com.huimi.common.encode.SaltModel;
import com.huimi.common.tools.BigDecimalUtil;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.equipment.EquipmentMapper;
import com.huimi.core.mapper.system.AdminMapper;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.AdminPort;
import com.huimi.core.po.system.Menu;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.AdminPortService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.MenuService;
import com.huimi.core.service.system.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.huimi.common.utils.StringUtils.sqlIn;

/**
 * 系统管理员表业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private MenuService menuService;
    @Resource
    private RedisService redisService;
    @Resource
    private AdminPortService adminPortService;
    @Resource
    private EquipmentMapper equipmentMapper;


    @Override
    public GenericMapper<Admin, Integer> _getMapper() {
        return adminMapper;
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public boolean saveOrUpdate(Admin admin, String passwd, String confirmPassword, Admin currAdmin) {

        return false;
    }

    @Override
    public int update(Admin admin) {
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int deleteById(Integer id) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setDelFlag(Admin.DELFLAG.YES.code);
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Admin> findByRoleIds(String rodeIds) {
        Admin admin = new Admin();
        admin.setRoleIds(rodeIds);
        admin.setDelFlag(Admin.DELFLAG.YES.code);
        return adminMapper.select(admin);
    }

    /**
     * 更新用户的角色
     *
     * @param ids     用户id
     * @param roleIds 角色id
     * @return 是否成功
     */
    @Override
    public boolean updateRoleIds(String ids, String roleIds) {
        //基于缓存查询
        //根据角色查询权限
        StringBuilder menuIdsStr = menuService.getMenuIdsStr1(roleIds);
        //转换成过滤sql
        String filter = sqlIn(menuIdsStr.toString());
        //查询权限值
        List<Menu> menus = menuService.sumAdminRights(filter);
        Map<Integer, BigDecimal> map = new LinkedHashMap<>();
        for (Menu m : menus) {
            map.put(m.getRightPos(), new BigDecimal(m.getRightVal()));
        }
        String json = JSON.toJSONString(map);

        Boolean b = this.updateRole(roleIds, json, ids);
        //更新缓存中的用户信息
        addCacheById(ids);
        return b;
    }

    @Override
    public void createOne(String username, String fullname, String pwd, String mobile, Integer sex,Integer ports) {
        Admin sameNameAdmin = adminMapper.selectOne(new Admin(admin1 -> {
            admin1.setUsername(username);
        }));
        if (sameNameAdmin != null) {
            throw new BussinessException("用户名已被使用");
        }

        Admin.SEX[] sexes = Admin.SEX.values();
        Admin.SEX sexEnum = null;
        for (Admin.SEX ele : sexes) {
            if (sex == ele.code) {
                sexEnum = ele;
            }
        }
        if (null == sexEnum) {
            throw new BussinessException("性别参数错误");
        }

        Integer adminId = this.findAdmin();
        Admin nowAdmin = adminMapper.selectByPrimaryKey(adminId);
        Admin admin = new Admin();
        if (StringUtils.isNotBlank(nowAdmin.getParentId())){
            admin.setRoleIds(EnumConstants.roleName.TWOAGENT.value);
        }
        admin.setSex(sex);
        admin.setUsername(username);
        admin.setFullname(fullname);
        admin.setMobile(mobile);
        admin.setPorts(ports);
        admin.setParentId(adminId);

        Admin testCodeAdmin = new Admin();
        String code = null;
        while (true){
             code = BigDecimalUtil.code();
            testCodeAdmin.setCode(code);
            List<Admin> select = adminMapper.select(testCodeAdmin);
            if (select.size()==0){
                break;
            }
        }
        admin.setCode(code);
        SaltModel saltModel = SaltEncodeUtil.encode(pwd);
        //解密密码
        admin.setPwd(saltModel.getPwd());
        admin.setSalt(saltModel.getSalt());
        int insertCount = adminMapper.insert(admin);
        if (insertCount != 1) {
            throw new BussinessException("新建管理员失败");
        }
    }

    @Override
    public Admin findByCode(String invitationCode) {
        if (StringUtil.isBlank(invitationCode)) {
            return null;
        }
        Admin admin = new Admin();
        admin.setCode(invitationCode);
        return adminMapper.selectOne(admin);
    }

    @Override
    public Integer freeEquipmentNum(Admin admin) {

       return adminMapper.findSubEquipmentNum(admin.getId());

    }

    @Override
    public int updatePorts(Integer ports, String username) {
        //判断1级代理商的最大分配数量是否满足
        Integer adminId = this.findAdmin();
        //自己的用户
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        Admin selectAdmin = new Admin();

        selectAdmin.setUsername(username);
        //点击的用户
        Admin resultAdmin = adminMapper.selectOne(selectAdmin);

        Integer parentId = resultAdmin.getParentId();
        Admin parentAdmin = adminMapper.selectByPrimaryKey(parentId);
        try {
            if (StringUtils.isNotBlank(admin.getParentId())){
                if (resultAdmin.getId()-adminId==0){
                    throw  new BussinessException("无法对自己进行分配");
                }
                Integer subEquipmentNum = adminMapper.findSubEquipmentNum(admin.getId());
                Integer adminEquipmentNum = equipmentMapper.findByAdminId(adminId);
                if (ports>resultAdmin.getPorts()+subEquipmentNum-adminEquipmentNum){
                    throw new BussinessException("设备可用数量不够");
                }
                Integer agent2Equipment = equipmentMapper.findByAdminId(resultAdmin.getId());
                if (ports<agent2Equipment){
                    throw new BussinessException("分配数量小于2级代理商的设备数量");
                }
            }else {
                //管理员不能给2级代理商设备
                Admin selectAdmin2 = new Admin();
                selectAdmin2.setParentId(resultAdmin.getId());
                List<Admin> admins = adminMapper.select(selectAdmin2);
                //必须是2级用户管理员可以给自己设备数量
                if (StringUtils.isNotBlank(parentAdmin)) {
                    if (parentAdmin.getParentId() != null) {
                        if (admins.size() == 0) {
                            throw new BussinessException("二级代理商无权增加设备数量");
                        }
                    } else {
                        //管理员给一级管理商减少时不能少于一级管理商的可用数量
                        Integer subEquipmentNum = adminMapper.findSubEquipmentNum(resultAdmin.getId());
                        if (StringUtils.isNotBlank(subEquipmentNum)) {
                            if (resultAdmin.getPorts() - subEquipmentNum > ports) {
                                throw new BussinessException("一级代理商空闲的数量不够");
                            }
                        }
                    }
                }

            }
        }catch (BussinessException e) {
            throw new BussinessException(e.getMessage());
        }
        AdminPort adminPort = new AdminPort();
        adminPort.setOperatAdmin(admin.getId());
        adminPort.setSysAdminId(resultAdmin.getId());
        adminPort.setStartPort(resultAdmin.getPorts());
        adminPort.setUpdatePort(ports);
        adminPort.setCreateTime(new Date());
        adminPortService.insert(adminPort);
        return  adminMapper.updatePortByUsername(ports,username);
    }

    @Override
    public int updateAdminParent(String code, Integer id,Integer roleIds) {
        return adminMapper.updateAdminParent(code,id,roleIds);
    }

    @Override
    public List<String> findRoleAdmin(String roles) {
        return adminMapper.findRoleAdmin(roles);
    }


    @Override
    public Admin selectByInviteCode(String code) {
        return adminMapper.selectByInviteCode(code);
    }

    private boolean updateRole(String roleIds, String json, String ids) {
        return adminMapper.updateByPrimaryKeySelective(new Admin(admin -> {
            admin.setId(Integer.valueOf(ids));
            admin.setRoleIds(roleIds);
            admin.setRightJson(json);
        })) == 1;
//        return Db.update(dao.getSql("admin.updateRole").trim(), roleIds, json, ids) > 0;
    }

    /**
     * 添加或者更新用户缓存
     *
     * @param id 用户ID
     */
    public void addCacheById(String id) {
        LOGGER.info("添加或者更新用户缓存");
        Admin admin = this.findById(Integer.valueOf(id));
//        ToolCache.set(SysAdminVO.CACHE_ADMIN + id, admin);
        redisService.put(CacheID.CONFIG_PREFIX + id, admin);
    }

    /**
     * 查询用户id
     * @return
     */

    public Integer findAdmin (){
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminMapper.selectOne(admin);
        return  resultAdmin.getId();
    }
}
