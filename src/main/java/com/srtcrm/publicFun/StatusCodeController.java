package com.srtcrm.publicFun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class StatusCodeController {
    public Map<String,Object> code_404(String message){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "failed");
        response.put("message", message);
        return response;
    }
    public Map<String,Object> code_200(String message){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", message);
        return response;
    }

}
