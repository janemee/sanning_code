package com.huimi.core.service.users.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.common.utils.MD5Utils;
import com.huimi.core.mapper.users.UsersMapper;
import com.huimi.core.po.user.Users;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.users.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;


@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class UsersServiceImpl implements UsersService {

    @Resource
    private RedisService redisService;
    @Resource
    private UsersMapper usersMapper;


    @Override
    public GenericMapper<Users, Integer> _getMapper() {
        return usersMapper;
    }


    @Override
    public Users findByPhone(String phone) {

        return usersMapper.findByPhone(phone);
    }

    /**
     * 登录注册
     *
     * @param phone 手机号
     * @param ip
     * @return
     */
    @Override
    public Users doLoginOrRegister(String phone, String ip) {
        Users users = usersMapper.findByPhone(phone);
        if (users == null) {
            //注册
            users.setPhone(phone);
            users.setNickName(phone);
            users.setUserName("用户" + phone);
            users.setType(Users.TYPE.GENERAL.code);
            String plaintext = "123456";
            //密码 目前只用普通加密
            users.setPassword(MD5Utils.getMd5(plaintext));
            users.setSalt(plaintext);
            users.setCreateTime(new Timestamp(System.currentTimeMillis()));
            users.setLastLoginDate(users.getCreateTime());
            users.setLastLoginIp(ip);
            usersMapper.insert(users);

            //邀请码
            String inviteCode = 10000 + users.getId() + "";
            users.setInviteCode(inviteCode);
            updateByPrimaryKeySelective(users);

            return users;
        }
        users.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        users.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
        users.setLastLoginIp(ip);
        updateByPrimaryKeySelective(users);
        return users;
    }

    @Override
    public Users findByUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return null;
        }
        Users users = new Users();
        users.setUuid(uuid);
        return selectOne(users);
    }
}
