package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srtcrm.dao.UserDao;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserInfo> implements UserService {
    @Override
    public UserInfo login(String openid) {
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("openid",openid);
        return getOne(qw);
        //return userDao.selectOne(qw);
    }

    @Override
    public boolean register(String openid, String name, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(openid);
        userInfo.setPermissions(0);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        return save(userInfo);
    }

    @Override
    public Integer getPermission(String openid) {
        return null;
    }

    @Override
    public Integer getIdByOpenid(String openid) {
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("openid",openid);
        if (getOne(qw) != null){
            return getOne(qw).getId();
        }
        else {
            return -1;
        }

    }

}
