package com.srtcrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.srtcrm.domain.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface UserService extends IService<UserInfo> {
    UserInfo login(String token) throws JsonProcessingException, NoSuchAlgorithmException;
    String register(String token, String name, String phone) throws JsonProcessingException, NoSuchAlgorithmException;
    Boolean verifyPermission(String token, String permissionLevel);
    Integer getIdByToken(String token);
}
