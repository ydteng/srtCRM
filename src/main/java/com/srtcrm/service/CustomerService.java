package com.srtcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.domain.CustomerInfo;

import java.util.List;


public interface CustomerService extends IService<CustomerInfo> {
    IPage<CustomerInfo> getCustomerNamePage(String token, int statement_id,int currentPage, int pageSize);
    CustomerInfo getCustomerDetailPage(String token, int customer_id);
    List<CustomerInfo> getAllByStatementId(Integer Statement_id);
    Boolean addCustomer(JsonNode jsonNode);
    Boolean updateCustomer(JsonNode jsonNode);
    Boolean deleteCustomer(JsonNode jsonNode);
    Boolean deleteCustomerByStatementId(Integer statement_id);
}
