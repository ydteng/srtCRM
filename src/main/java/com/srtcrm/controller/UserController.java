package com.srtcrm.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.controller.utils.R;
import com.srtcrm.domain.UserInfo;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@JsonIgnoreProperties(value = {"token","permissions"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("login/{code}")
    public ResponseEntity<?> login(@PathVariable String code) throws IOException, NoSuchAlgorithmException {
        UserInfo userInfo = userService.login(code);
        if (userInfo != null){
//            Map<String, Object> data = new HashMap<>();
//            data.put("name", "John Doe");
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,userInfo,"登陆成功"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new R(false,null,"用户不存在,或者是获取token失败"));
        }
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody JsonNode jsonNode) throws NoSuchAlgorithmException, JsonProcessingException {
        String token = userService.register(jsonNode.get("code").asText(), jsonNode.get("name").asText(), jsonNode.get("phone").asText());
        if (token != null){
            Map<String, Object> tokenJson = new HashMap<>();
            tokenJson.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,tokenJson,"注册成功"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new R(false,null,"注册失败,可能是：1.用户已存在。2.获取token失败"));
        }
    }
}
