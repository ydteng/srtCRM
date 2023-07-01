package com.srtcrm.controller.utils;

import java.util.HashMap;
import java.util.Map;
//暂时不用了//暂时不用了//暂时不用了//暂时不用了//暂时不用了
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
