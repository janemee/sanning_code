package com.huimi.core.service.users;

import com.huimi.core.po.user.Users;
import com.huimi.core.service.base.GenericService;

/**
 * create by lja on 2020/7/28 17:31
 */
public interface UsersService extends GenericService<Integer, Users> {

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 手机号
     * @return
     */
    public Users findByPhone(String phone);

    /**
     * 登录或注册
     *
     * @param phone 手机号
     * @return
     */
    Users doLoginOrRegister(String phone, String ip);

    /**
     * 根据uuid查询用户信息
     *
     * @return
     */
    Users findByUuid(String uuid);
}
