package com.srtcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.domain.CustomerInfo;
import com.srtcrm.domain.StatementInfo;

public interface CustomerService extends IService<CustomerInfo> {
    IPage<CustomerInfo> getCustomerNamePage(String openid, int statement_id,int currentPage, int pageSize);
    CustomerInfo getCustomerDetailPage(String openid, int customer_id);
//    Boolean addCustomer(String openid, int statement_id, String customer_name, String product_id, int consumption, String transaction_status, String plan_status, String remarks);
    Boolean addCustomer(JsonNode jsonNode);
    Boolean updateCustomer(JsonNode jsonNode);
    Boolean deleteCustomer(JsonNode jsonNode);
}
