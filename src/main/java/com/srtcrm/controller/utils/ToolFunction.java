package com.srtcrm.controller.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Component
public class ToolFunction {
    @Value("${wechat.app-id}")
    private String AppID;
    @Value("${wechat.app-secret}")
    private String AppSecret;
    public ObjectNode objectToJson(Object ob) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(ob);
        JsonNode jsonNode = objectMapper.readTree(str);
        return (ObjectNode) jsonNode;
    }
    public String getOpenid(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+AppID+"&secret="+AppSecret+"&js_code="+code+"&grant_type=authorization_code ";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println("Response: " + responseBody);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            if (jsonNode.has("openid")) {
                String openid = jsonNode.get("openid").asText();
                System.out.println(openid);
                return openid;
            } else {
                System.out.println("No 'openid' parameter found in the response JSON.");
                return null;
            }
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return null;
        }


    }

    public String generateTokenWithOpenid(String openid) throws NoSuchAlgorithmException {
        if(openid == null){
            return null;
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(openid.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
