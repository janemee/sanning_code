package com.huimi.core.model.system;

import com.huimi.core.po.system.Admin;
import com.huimi.core.po.system.Role;
import lombok.Data;

@Data
public class AdminModel extends Admin {

    /**
     * 二次密码
     **/
    private String password2;

    private Role r;
}
