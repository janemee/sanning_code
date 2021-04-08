package com.huimi.core.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.system.MenuMapper;
import com.huimi.core.mapper.system.RoleMapper;
import com.huimi.core.model.system.MenuModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Menu;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.MenuService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huimi.common.utils.StringUtils.sqlIn;

/**
 * 交易手续费率模板业务相关Service接口实现<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisService redisService;

    @Override
    public GenericMapper<Menu, Integer> _getMapper() {
        return menuMapper;
    }


    @Override
    public List<MenuModel> findByRights(Admin admin, int pid) {
        if (null == admin) {
            return null;
        }
        long beforeMilli = System.currentTimeMillis();

        //根据角色查询权限
        String roleIds = admin.getRoleIds();
        StringBuilder menuIdsStr = this.getMenuIdsStr(roleIds);
        //转换成过滤sql
        String filter = sqlIn(menuIdsStr.toString());
        //如果是pid=0，则表示查询水平导航菜单
        if (pid == Menu.LEVEL.TOP_MENU.code) {
            if (StringUtils.isBlank(filter)){
                return null;
            }else {
                return menuMapper.findMenuByPidInMenuIds(Menu.LEVEL.TOP_MENU.code, pid, filter);
            }
        } else {
            //初始化纵向菜单树,显示横向第一个的子菜单
            List<MenuModel> menuList = menuMapper.findMenuByPidInMenuIds(Menu.LEVEL.TOP_MENU.code, pid, filter);
            for (MenuModel m : menuList) {
                recursionMenu(m, filter);
            }

            long afterMilli = System.currentTimeMillis();
            System.out.println("获取初始化纵向菜单树,耗时:" + (afterMilli - beforeMilli) + " mills ");
            return menuList;
        }
    }

    private void recursionMenu(MenuModel model, String filter) {
        List<MenuModel> menuList = menuMapper.findMenuByPidInMenuIds(Menu.TYPE.MENU.code, model.getId(), filter);
        if (null != menuList && menuList.size() > 0) {
            model.setSubMenu(menuList);
            model.setIsParent(Menu.ISPARENT.YES.code);
            for (MenuModel m : menuList) {
                recursionMenu(m, filter);
            }
        } else {
            model.setIsParent(Menu.ISPARENT.NO.code);
        }
    }


    @Override
    public List<MenuModel> getVerticalMenuTree(Admin admin, String menuId) {
        long beforeMilli = System.currentTimeMillis();

        //根据角色查询权限
        String roleIds = admin.getRoleIds();
        StringBuilder menuIdsStr = this.getMenuIdsStr(roleIds);
        //转换成过滤sql
        String filter = sqlIn(menuIdsStr.toString());

        //初始化纵向菜单树,显示横向第一个的子菜单
        List<MenuModel> top_1_List;
//        top_1_List = this.findByParentIdAndMenuIds(menuId, filter);
        top_1_List = menuMapper.findMenuByPidInMenuIds(Menu.LEVEL.TOP_MENU.code, Integer.valueOf(menuId), filter);

        for (MenuModel m : top_1_List) {
            //如果菜单是叶子菜单，则跳出循环
            m.setUrl(null == m.getUrl() ? "" : m.getUrl());
            if (m.getIsParent() == 0) {
                continue;
            }
//            List<MenuModel> top_2_List = this.findByParentIdAndMenuIds(m.getId(), filter);
            List<MenuModel> top_2_List = menuMapper.findMenuByPidInMenuIds(Menu.LEVEL.TOP_MENU.code, m.getId(), filter);
            if (null != top_2_List && top_2_List.size() > 0) {
                m.setSubMenu(top_2_List);
                for (MenuModel mm : top_2_List) {
                    //如果菜单是叶子菜单，则跳出循环
                    mm.setUrl(null == mm.getUrl() ? "" : mm.getUrl());
                    if (mm.getIsParent() == 0) {
                        continue;
                    }
//                    List<SysMenu> top_3_List = this.findByParentIdAndMenuIds(mm.getId(), filter);
                    List<MenuModel> top_3_List = menuMapper.findMenuByPidInMenuIds(Menu.LEVEL.TOP_MENU.code, mm.getId(), filter);
                    if (null != top_3_List && top_3_List.size() > 0) {
                        mm.setSubMenu(top_3_List);
                        for (MenuModel mmm : top_3_List) {
                            mmm.setUrl(null == mmm.getUrl() ? "" : mmm.getUrl());
                        }
                    } else {
                        mm.setIsParent(0);
                    }
                }
            } else {
                m.setIsParent(0);
            }
        }

        long afterMilli = System.currentTimeMillis();
        System.out.println("获取初始化纵向菜单树,耗时:" + (afterMilli - beforeMilli) + " mills ");
        return top_1_List;
    }

    @Override
    public void delMenuIncludeChild(int id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        if (menu != null) {
            List<Menu> childMenu = menuMapper.select(new Menu(menu1 -> {
                menu1.setParentId(menu.getId());
                menu1.setDelFlag(0);
            }));
            childMenu.add(menu);
            childMenu.forEach(menu1 -> {
                menu1.setDelFlag(1);
                menuMapper.updateByPrimaryKeySelective(menu1);
            });
        }
    }

    @Override
    public List<MenuModel> findByMenuIds(int roleId) {
        return null;
    }

    @Override
    public List<Menu> selectMenusByIds(String menuIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("tableName", Menu.TABLE_NAME);
        param.put("nowPage", 0);
        param.put("pageSize", Integer.MAX_VALUE);
        int length = menuIds.length();
        if (menuIds.lastIndexOf(",") == length - 1) {
            param.put("whereSql", " and id in (" + menuIds.substring(0, length - 1) + ")");
        } else {
            param.put("whereSql", " and id in (" + menuIds + ")");
        }
        return menuMapper.findListBySql(param);
    }

    @Override
    public List<Menu> getTreeTableData() {
        List<Menu> parentMenu = menuMapper.select(new Menu(menu -> {
            menu.setDelFlag(0);
            menu.setParentId(0);
        }));
        List<Menu> list = new ArrayList<>();
        for (Menu menu : parentMenu) {
            menu.setIsFirst("0");
            recursion(menu, list);
        }
        return list;
    }

    /**
     * 递归查询菜单管理列表中的菜单数据
     *
     * @param menu 上级menu
     * @param list 处理过的菜单集合
     */
    private void recursion(Menu menu, List<Menu> list) {
        //如果菜单为子菜单或者，菜单类型为操作，则不逊要继续递归查询
        if (menu.getType() == 1) {
            menu.setIsParent(0);
            list.add(menu);
        } else {
            List<Menu> menuList = menuMapper.select(new Menu(menu1 -> {
                menu1.setDelFlag(0);
                menu1.setParentId(menu.getId());
            }));
            if (menuList.size() > 0) {
                menu.setIsParent(1);
            } else {
                menu.setIsParent(0);
            }
            list.add(menu);
            for (Menu m : menuList) {
                recursion(m, list);
            }
        }
    }

    private StringBuilder getMenuIdsStr(String roleIds) {
        //基于缓存查询
        StringBuilder menuIdsStr = new StringBuilder();
        //根据角色查询权限
        if (null != roleIds) {
            String[] roleIdsArr = roleIds.split(",");
            for (String roleId : roleIdsArr) {
                Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(roleId));
                if (role == null) {
                    throw new BussinessException("操作员角色不存在,请检查数据...");
                }
                String menuIds = role.getMenuIds();
                menuIdsStr.append(menuIds);
            }
        }
        return menuIdsStr;
    }

    public StringBuilder getMenuIdsStr1(String roleIds) {
        //基于缓存查询
        StringBuilder menuIdsStr = new StringBuilder();
        //根据角色查询权限
        if (null != roleIds) {
            String[] roleIdsArr = roleIds.split(",");
            for (String temp : roleIdsArr) {

//                Role role = SysRoleService.SERVICE.cacheGet(temp);
                Role role = JSONObject.parseObject(redisService.get(CacheID.CONFIG_PREFIX + temp), Role.class);
                if (role == null) {
                    throw new BussinessException("操作员角色不存在,请检查数据...");
                }
                String menuIds = role.getMenuIds();
                menuIdsStr.append(menuIds);
            }
        }
        return menuIdsStr;
    }

    /**
     * 汇总查询某个操作员拥有的菜单权限值
     *
     * @param filter 菜单集合
     * @return 仅带权限的菜单集合
     */
    public List<Menu> sumAdminRights(String filter) {
        return menuMapper.sumAdminRights(1, 0, filter);

//        Kv cond = Kv.by("filter", filter).set("delFlag", Constant.DELFLAG_NO).set("type", SysMenuVO.TYPE_OPT);
//        return dao.find(dao.getSqlPara("menu.sumAdminRights", cond));
    }
}
