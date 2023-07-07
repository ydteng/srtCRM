package com.srtcrm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.srtcrm.controller.UserController;
import com.srtcrm.controller.utils.ToolFunction;
import com.srtcrm.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class SrtCrmApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;
//    @Value("${wexin.AppID}")
//    private String AppID;
//    @Value("${wexin.AppSecret}")
//    private String AppSecret;
    @Autowired
    private Environment environment;
    @Test
    void contextLoads() throws JsonProcessingException {
        String code = "0d3TpYZv3YwcW03Wnx1w3xY3ss0TpYZC";
        ToolFunction toolFunction = new ToolFunction();
        toolFunction.getOpenid(code);
    }
    @Test
    void hashCodeTest() throws NoSuchAlgorithmException {
        String code = "0d3TpYZv3YwcW03Wnx1w3xY3ss0TpYZB";
        ToolFunction toolFunction = new ToolFunction();
        System.out.println(toolFunction.generateTokenWithOpenid(code));
    }
    @Test
    void verifyPermissionTest(){

    }
    @Test
    void otherTest(){
        String AppID = environment.getProperty("wechat.app-id");
        String AppSecret = environment.getProperty("wechat.app-secret");

        System.out.println(AppID+"/"+AppSecret);
    }

}
