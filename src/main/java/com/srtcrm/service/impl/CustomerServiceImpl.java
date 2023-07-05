package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.dao.CustomerDao;
import com.srtcrm.domain.CustomerInfo;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.service.CustomerService;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, CustomerInfo> implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private UserService userService;
    @Override
    public IPage<CustomerInfo> getCustomerNamePage(String token, int statement_id,int currentPage, int pageSize) {
        IPage<CustomerInfo> page = new Page<CustomerInfo>(currentPage,pageSize);
        //去user_info表格查询对应的表ID
        Integer id = userService.getIdByToken(token);
        if (id == -1) return null;
        //再回到statement_info中找到数据
        QueryWrapper<CustomerInfo> qw = new QueryWrapper<>();
        qw.eq("statement_id",statement_id).select("id","customer_name");
        customerDao.selectPage(page,qw);
        return page;
    }

    @Override
    public CustomerInfo getCustomerDetailPage(String token, int customer_id) {
        Integer id = userService.getIdByToken(token);
        if (id == -1) return null;
        //再回到statement_info中找到数据
        return getById(customer_id);
    }

    @Override
    public Boolean addCustomer(JsonNode jsonNode) {
        CustomerInfo customerInfo = new CustomerInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        customerInfo.setStatement_id(jsonNode.get("statement_id").asInt());
        customerInfo.setCustomer_name(jsonNode.get("customer_name").asText());
        customerInfo.setProduct_id(jsonNode.get("product_id").asText());
        customerInfo.setConsumption(jsonNode.get("consumption").asInt());
        customerInfo.setTransaction_status(jsonNode.get("transaction_status").asText());
        customerInfo.setPlan_status(jsonNode.get("plan_status").asText());
        customerInfo.setRemarks(jsonNode.get("remarks").asText());
        return save(customerInfo);
    }

    @Override
    public Boolean updateCustomer(JsonNode jsonNode) {
        CustomerInfo customerInfo = new CustomerInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        customerInfo.setId(jsonNode.get("customer_id").asInt());
        customerInfo.setCustomer_name(jsonNode.get("customer_name").asText());
        customerInfo.setProduct_id(jsonNode.get("product_id").asText());
        customerInfo.setConsumption(jsonNode.get("consumption").asInt());
        customerInfo.setTransaction_status(jsonNode.get("transaction_status").asText());
        customerInfo.setPlan_status(jsonNode.get("plan_status").asText());
        customerInfo.setRemarks(jsonNode.get("remarks").asText());
        return updateById(customerInfo);
    }

    @Override
    public Boolean deleteCustomer(JsonNode jsonNode) {
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        return removeById(jsonNode.get("customer_id").asInt());
    }
}
