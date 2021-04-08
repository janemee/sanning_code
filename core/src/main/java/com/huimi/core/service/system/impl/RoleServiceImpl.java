package com.huimi.core.service.system.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.entity.ZTreeNode;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.mapper.system.AdminMapper;
import com.huimi.core.mapper.system.MenuMapper;
import com.huimi.core.mapper.system.RoleMapper;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.system.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 角色表业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private
    AdminService adminService;
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RedisService redisService;

    @Override
    public GenericMapper<Role, Integer> _getMapper() {
        return roleMapper;
    }

    @Override
    public Role findById(String roleId) {
        return roleMapper.selectOne(new Role(role -> {
            role.setId(Integer.valueOf(roleId));
        }));
    }

    @Override
    public List<Role> selectByRoleIds(String roleIds) {
        return null;
    }

    @Override
    public List<ZTreeNode> getZtreeData(String oldRoleids) {
        Integer ids = this.findAdminRoleId();
        Role adminRole = roleMapper.selectByPrimaryKey(ids);
        List<Role> list = roleMapper.selectAll();
        //一级分类的列表
        if (adminRole.getName().equals(EnumConstants.roleName.ADMIN.code)){
            for (Role role : list) {
                if (role.getName().equals(EnumConstants.roleName.TWOAGENT.code)){
                    list.remove(role);
                }
            }

        }
        //2及分类能控制的权限
        if (adminRole.getName().equals(EnumConstants.roleName.ONEAGENT.code)){
            Role role = new Role();
            role.setName(EnumConstants.roleName.TWOAGENT.code);
          list = roleMapper.select(role);
        }
        //ztree数据封装
        List<ZTreeNode> nodeList = new ArrayList<>();
        ZTreeNode node;
        for (Role role : list) {
            node = new ZTreeNode();
            node.setId(role.getId().toString());
            node.setName(role.getName());
            node.setPId(null);
            node.setOpen(true);
            if (role.getId().toString().equals(oldRoleids)) {
                node.setChecked(true);
            }
            nodeList.add(node);
        }
        return nodeList;
    }


    public List<ZTreeNode> getZTreeData(String[] menuIds) {
        if (null == menuIds) {
            menuIds = new String[0];
        }
        //查询一级菜单
        List<Menu> menu0List = menuMapper.select(new Menu(menu -> {
            menu.setDelFlag(0);
            menu.setParentId(0);
        }));
        //ztree数据封装
        List<ZTreeNode> nodeList = new ArrayList<>();
        if (null != menu0List) {
            for (Menu menu0 : menu0List) {
                //把该1级菜单放到ztree 中
                nodeList.add(generateNode(menu0, menuIds));
                //递归执行 获取所有的菜单节点
                generateNextNodes(nodeList, menu0, menuIds);
            }
        }

        return nodeList;
    }

    /**
     * 递归生成下级ztree节点
     */
    private void generateNextNodes(List<ZTreeNode> nodeList, Menu menu, String[] menuIdArr) {
//        List <Menu> menuList = SysMenu.dao.find(sql, menu.getId(), Constant.DELFLAG_NO);
        List<Menu> menuList = menuMapper.select(new Menu(menu1 -> {
            menu1.setDelFlag(0);
            menu1.setParentId(menu.getId());
        }));
        if (null != menuList) {
            for (Menu m : menuList) {
                nodeList.add(generateNode(m, menuIdArr));
                //如果该级菜单有下级菜单则继续，否则跳出循环
                if (m.getIsParent() == 1) {
                    generateNextNodes(nodeList, m, menuIdArr);
                }
            }
        }

    }

    /**
     * 生成节点
     *
     * @param menu      菜单对象
     * @param menuIdArr menuIdArr
     * @return 节点对象
     */
    private ZTreeNode generateNode(Menu menu, String[] menuIdArr) {
        ZTreeNode node = new ZTreeNode();
        node.setId(menu.getId().toString());
        node.setName(menu.getName());
        node.setPId(menu.getParentId().toString());
        node.setOpen(true);
        for (String str : menuIdArr) {
            if (str.equals(menu.getId().toString())) {
                node.setChecked(true);
            }
        }
        return node;
    }


    /**
     * 授权操作，保存该角色选择的菜单
     *
     * @param menuids 菜单id集合
     * @param id      角色id
     * @return 是否成功
     */
    public int doAuthorize(String menuids, final String id) {
        if (null == menuids) {
            return 0;
        }
        Role role = findById(id);
        if (role == null) {
            return 0;
        }
        role.setMenuIds(menuids);
        int insert = 0;
        insert = updateByPrimaryKeySelective(role);
        return insert;
    }

    /**
     * 刷新操作员权限
     *
     * @param roleId 角色ID
     */
    public void refreshRights(String roleId) {
        //如果roleId为null，则刷新所有操作员的权限
        if (roleId == null) {
            roleId = "";
        } else {
            //如果为自动刷新，则先更新该角色的缓存
//            this.addCache(roleId);
        }
        //更新所有与此角色相关的操作的权限
        Admin admin = new Admin();
        admin.setRoleIds(roleId);
        admin.setDelFlag(0);
        List<Admin> list = adminMapper.select(admin);
        for (Admin a : list) {
            adminService.updateRoleIds(a.getId().toString(), a.getRoleIds());
        }
    }

    /**
     * 添加或者更新缓存
     */
//    private void addCache(String id) {
//        LOGGER.info("添加或者更新缓存");
//        redisService.put();
//        ToolCache.set(SysRoleVO.CACHE_PREFIX + id, dao.findById(id));
//    }

    /**
     * 查询用户id
     * @return
     */

    public Integer findAdminRoleId (){
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        Admin admin = new Admin();
        admin.setUsername(principal);
        Admin resultAdmin = adminService.selectOne(admin);
        return  Integer.valueOf(resultAdmin.getRoleIds());
    }
}
