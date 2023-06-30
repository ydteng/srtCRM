package com.srtcrm.controller;

import com.srtcrm.service.StatementService;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Statement;

@RestController
@RequestMapping("/statement")
public class StatementController {
    @Autowired
    private StatementService statementService;
    @GetMapping("/{openid}/{currentPage}/{pageSize}")
    public ResponseEntity<?> getStatementByPage(@PathVariable String openid,@PathVariable int currentPage,@PathVariable int pageSize){
        return ResponseEntity.ok().body(statementService.getPage(openid, currentPage, pageSize));
    }
}
