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
    @GetMapping("name/{token}/{statement_id}/{currentPage}/{pageSize}")
    public ResponseEntity<?> getCustomerNameByPage(@PathVariable String token, @PathVariable int statement_id, @PathVariable int currentPage, @PathVariable int pageSize){
        if (customerService.getCustomerNamePage(token, statement_id, currentPage, pageSize) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,customerService.getCustomerNamePage(token, statement_id, currentPage, pageSize),"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"获取失败，可能是用户不存在"));
        }

    }

    @GetMapping("/{token}/{customer_id}")
    public ResponseEntity<?> getCustomerDetailByPage(@PathVariable String token, @PathVariable int customer_id){
        if (customerService.getCustomerDetailPage(token, customer_id) != null){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,customerService.getCustomerDetailPage(token, customer_id),"ok"));
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
    @PutMapping()
    public ResponseEntity<?> updateCustomerWithStatementId(@RequestBody JsonNode jsonNode){
        if (customerService.updateCustomer(jsonNode)){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"修改失败，可能是用户不存在"));
        }
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteCustomerWithCustomerId(@RequestBody JsonNode jsonNode){
        if (customerService.deleteCustomer(jsonNode)){
            return ResponseEntity.status(HttpStatus.OK).body(new R(true,null,"ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new R(false,null,"删除失败，可能是用户不存在"));
        }
    }

}
