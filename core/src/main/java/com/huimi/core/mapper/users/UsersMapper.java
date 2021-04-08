package com.huimi.core.mapper.users;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.user.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersMapper extends GenericMapper<Users, Integer> {

    @Select("select * from users  where phone = #{phone}")
    Users findByPhone(@Param("phone") String phone);
}
