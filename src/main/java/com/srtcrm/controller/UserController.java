package com.srtcrm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.publicFun.StatusCodeController;
import com.srtcrm.publicFun.ToolFunction;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("login/{openid}")
    public ResponseEntity<?> login(@PathVariable String openid) throws JsonProcessingException {
        if (userService.login(openid) != null){
            return ResponseEntity.ok().body(userService.login(openid));
        }
        else {
            StatusCodeController status = new StatusCodeController();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status.code_404("未找到对应用户"));
        }
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo){
        if (userService.register(userInfo.getOpenid(), userInfo.getName(), userInfo.getPhone())){
            StatusCodeController status = new StatusCodeController();
            return ResponseEntity.status(HttpStatus.OK).body(status.code_200("注册成功"));
        }
        else {
            StatusCodeController status = new StatusCodeController();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status.code_404("注册失败"));
        }
    }
}
