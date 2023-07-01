package com.srtcrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srtcrm.domain.UserInfo;

public interface UserService extends IService<UserInfo> {
    UserInfo login(String openid);
    boolean register(String openid, String name, String phone);
    Integer getPermission(String openid);
    Integer getIdByOpenid(String openid);
}
