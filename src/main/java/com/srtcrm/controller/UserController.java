package com.srtcrm.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.srtcrm.controller.utils.R;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.controller.utils.StatusCodeController;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/user")
@JsonIgnoreProperties(value = {"openid","permissions"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("login/{openid}")
    public ResponseEntity<?> login(@PathVariable String openid) throws IOException {
        if (userService.login(openid) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,userService.login(openid),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new R(false,null,"用户不存在"));
        }
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo){
        if (userService.register(userInfo.getOpenid(), userInfo.getName(), userInfo.getPhone())){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"注册成功"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new R(false,null,"注册失败"));
        }
    }
}
