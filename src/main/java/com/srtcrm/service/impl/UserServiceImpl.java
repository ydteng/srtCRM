package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.srtcrm.controller.utils.ToolFunction;
import com.srtcrm.dao.UserDao;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.service.UserService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserInfo> implements UserService {
    @Override
    public UserInfo login(String code) throws JsonProcessingException, NoSuchAlgorithmException {
        ToolFunction toolFunction = new ToolFunction();
        String openid = toolFunction.getOpenid(code);
        String token = toolFunction.generateTokenWithOpenid(openid);
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("token",token);
        return getOne(qw);
        //return userDao.selectOne(qw);
    }

    @Override
    public String register(String code, String name, String phone) throws JsonProcessingException, NoSuchAlgorithmException {
        ToolFunction toolFunction = new ToolFunction();
        String openid = toolFunction.getOpenid(code);
        String token = toolFunction.generateTokenWithOpenid(openid);
        if (token == null) return null;
        if (getIdByToken(token) != null) return null;
        UserInfo userInfo = new UserInfo();
        userInfo.setToken(token);
        userInfo.setPermissions(0);
        userInfo.setName(name);
        userInfo.setPhone(phone);
        if (save(userInfo)){
            return token;
        }
        else return null;
    }

    @Override
    public Boolean verifyPermission(String token, String permissionLevel) {
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("token",token);
        UserInfo userInfo = getOne(qw);
        if(userInfo != null){
            Integer permissionCode = userInfo.getPermissions();
            if ((permissionLevel.equals("low")) && (permissionCode>=0)){
                return true;
            }else if ((permissionLevel.equals("middle")) && (permissionCode>=1)) {
                return true;
                
            }else if ((permissionLevel.equals("high") && (permissionCode>=2))) {
                return true;

            }else {
                return false;
            }

        } else {return false;}
    }

    @Override
    public Integer getIdByToken(String token) {
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        qw.eq("token",token);
        if (getOne(qw) != null){
            return getOne(qw).getId();
        }
        else {
            return -1;
        }

    }

}
