package com.srtcrm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.controller.utils.R;
import com.srtcrm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("name/{openid}/{statement_id}/{currentPage}/{pageSize}")
    public ResponseEntity<?> getCustomerNameByPage(@PathVariable String openid, @PathVariable int statement_id, @PathVariable int currentPage, @PathVariable int pageSize){
        if (customerService.getCustomerNamePage(openid, statement_id, currentPage, pageSize) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,customerService.getCustomerNamePage(openid, statement_id, currentPage, pageSize),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"获取失败，可能是用户不存在"));
        }

    }

    @GetMapping("/{openid}/{customer_id}")
    public ResponseEntity<?> getCustomerDetailByPage(@PathVariable String openid, @PathVariable int customer_id){
        if (customerService.getCustomerDetailPage(openid, customer_id) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,customerService.getCustomerDetailPage(openid, customer_id),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"获取失败，可能是用户不存在"));
        }

    }
    @PostMapping()
    public ResponseEntity<?> addCustomerWithStatementId(@RequestBody JsonNode jsonNode){
        if (customerService.addCustomer(jsonNode)){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"新增失败，可能是用户不存在"));
        }
    }
    @DeleteMapping()
    public ResponseEntity<?> updateCustomerWithCustomerId(@RequestBody JsonNode jsonNode){
        if (customerService.deleteCustomer(jsonNode)){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"删除失败，可能是用户不存在"));
        }
    }

}
