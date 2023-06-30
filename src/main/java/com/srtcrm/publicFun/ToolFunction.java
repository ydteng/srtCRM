package com.srtcrm.publicFun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ToolFunction {
    public ObjectNode objectToJson(Object ob) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(ob);
        JsonNode jsonNode = objectMapper.readTree(str);
        return (ObjectNode) jsonNode;
    }
}
