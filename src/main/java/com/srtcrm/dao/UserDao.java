package com.srtcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srtcrm.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserInfo> {
}
