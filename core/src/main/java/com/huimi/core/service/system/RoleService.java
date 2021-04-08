package com.huimi.core.service.system;


import com.huimi.common.entity.ZTreeNode;
import com.huimi.core.po.system.Role;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 角色表业务相关Service接口<br>
 *
 * @author fengting
 * @date 2018-09-29 04:30:19
 */
public interface RoleService extends GenericService<Integer, Role> {

    Role findById(String roleId);

    List<Role> selectByRoleIds(String roleIds);

    List<ZTreeNode> getZtreeData(String oldRoleids);

    List<ZTreeNode> getZTreeData(String[] oldRoleids);

    int doAuthorize(String menuids, final String id);

    void refreshRights(String roleId);
}
