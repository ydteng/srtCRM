package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srtcrm.dao.UserDao;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserInfo> implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserInfo login(String openid) {
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("openid",openid);
        return userDao.selectOne(qw);
    }

    @Override
    public boolean register(String openid, String name, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(openid);
        userInfo.setPermissions(0);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        return userDao.insert(userInfo) > 0;
    }

    @Override
    public Integer getPermission(String openid) {
        return null;
    }

}
