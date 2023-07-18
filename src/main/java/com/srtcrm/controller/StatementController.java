package com.srtcrm.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.controller.utils.ExcelTool;
import com.srtcrm.controller.utils.R;
import com.srtcrm.domain.CustomerInfo;
import com.srtcrm.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/statement")
public class StatementController {
    @Autowired
    private StatementService statementService;
    @Autowired
    private ExcelTool excelTool;
    @GetMapping("/{token}/{currentPage}/{pageSize}")
    public ResponseEntity<?> getStatementByPage(@PathVariable String token,@PathVariable int currentPage,@PathVariable int pageSize){
        if (statementService.getStatementPage(token, currentPage, pageSize) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,statementService.getStatementPage(token, currentPage, pageSize),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"获取失败！可能是用户不存在或者权限不足。"));
        }

    }
    @GetMapping("/export/{token}/{statement_id}")
    public ResponseEntity<?> exportExcel(@PathVariable String token, @PathVariable Integer statement_id,HttpServletResponse response) throws IOException {
        String fileName = statementService.getStatementName(statement_id);
        List<CustomerInfo> customerInfoList = excelTool.getData(statement_id);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String exportName = URLEncoder.encode( fileName, "UTF-8").replaceAll( "\\+","%2");
        response.setHeader("Content-disposition", "attachment;filename=" + exportName + ".xlsx");

        EasyExcel.write(response.getOutputStream())
                .head(excelTool.head())
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("客户数据")
                .doWrite(customerInfoList);

        return null;
    }


    @PostMapping
    public ResponseEntity<?> addStatementWithToken(@RequestBody JsonNode jsonNode){
        if (statementService.addStatement(jsonNode)){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"新增失败！可能是用户不存在或者权限不足。"));
        }
    }
    @PutMapping
    //后期可优化，用sql验证报表是否属于此用户，更安全
    public ResponseEntity<?> updateStatementWithToken(@RequestBody JsonNode jsonNode) {
        if (statementService.updateStatement(jsonNode)) {
            return ResponseEntity.status(HttpStatus.OK).body(new R(true, null, "ok"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false, null, "修改失败！可能是用户不存在。"));
        }
    }
    @DeleteMapping
    //后期可优化，用sql验证报表是否属于此用户，更安全
    public ResponseEntity<?> deleteStatementWithStatementId(@RequestBody JsonNode jsonNode) {
        if (statementService.deleteStatement(jsonNode)) {
            return ResponseEntity.status(HttpStatus.OK).body(new R(true, null, "ok"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false, null, "删除失败！可能是用户不存在。"));
        }
    }
}
