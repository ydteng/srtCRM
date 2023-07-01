package com.srtcrm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.controller.utils.R;
import com.srtcrm.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/statement")
public class StatementController {
    @Autowired
    private StatementService statementService;
    @GetMapping("/{openid}/{currentPage}/{pageSize}")
    public ResponseEntity<?> getStatementByPage(@PathVariable String openid,@PathVariable int currentPage,@PathVariable int pageSize){
        if (statementService.getStatementPage(openid, currentPage, pageSize) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,statementService.getStatementPage(openid, currentPage, pageSize),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"获取失败，可能是用户不存在"));
        }

    }
    @PostMapping
    public ResponseEntity<?> addStatementWithOpenid(@RequestBody JsonNode jsonNode){
        if (statementService.addStatement(jsonNode.get("openid").asText(), jsonNode.get("area").asText(), jsonNode.get("date").asText())){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"新增失败，可能是用户不存在"));
        }
    }
    @PutMapping
    //后期可优化，用sql验证报表是否属于此用户，更安全
    public ResponseEntity<?> updateStatementWithOpenid(@RequestBody JsonNode jsonNode) {
        if (statementService.updateStatement(jsonNode.get("statement_id").asInt(), jsonNode.get("openid").asText(), jsonNode.get("area").asText(), jsonNode.get("date").asText())) {
            return ResponseEntity.status(HttpStatus.OK).body(new R(true, null, "ok"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false, null, "修改失败，可能是用户不存在"));
        }
    }
    @DeleteMapping
    //后期可优化，用sql验证报表是否属于此用户，更安全
    public ResponseEntity<?> deleteStatementWithStatementId(@RequestBody JsonNode jsonNode) {
        if (statementService.deleteStatement(jsonNode.get("statement_id").asInt(), jsonNode.get("openid").asText())) {
            return ResponseEntity.status(HttpStatus.OK).body(new R(true, null, "ok"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false, null, "删除失败，可能是用户不存在"));
        }
    }
}
