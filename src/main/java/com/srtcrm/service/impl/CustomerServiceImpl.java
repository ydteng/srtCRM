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
import com.srtcrm.service.StatementService;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, CustomerInfo> implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private UserService userService;
    @Autowired
    private StatementService statementService;
    @Override
    public IPage<CustomerInfo> getCustomerNamePage(String token, int statement_id,int currentPage, int pageSize) {
        IPage<CustomerInfo> page = new Page<CustomerInfo>(currentPage,pageSize);
        //去user_info表格查询对应的表ID
        Integer id = userService.getIdByToken(token);
        if (id == -1) return null;
        //再回到statement_info中找到数据
        QueryWrapper<CustomerInfo> qw = new QueryWrapper<>();
        qw.eq("statement_id",statement_id).select("id","customer_name","update_time");
        customerDao.selectPage(page,qw);
        //循环处理数据

        Object[] customerInfoList = page.getRecords().toArray();
        for(int i = 0; i < customerInfoList.length; i++){
            String update_time = ((CustomerInfo) customerInfoList[i]).getUpdate_time();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dbTimestamp = LocalDateTime.parse(update_time, formatter);
            // 获取当前时间
            LocalDateTime currentTime = LocalDateTime.now();
            // 计算时间差
            Duration duration = Duration.between(dbTimestamp, currentTime);
            long days = duration.toDays();
            ((CustomerInfo) customerInfoList[i]).setUpdate_interval(days);
            ((CustomerInfo) customerInfoList[i]).setUpdate_time(null);
        }

        return page;
    }

    @Override
    public CustomerInfo getCustomerDetailPage(String token, int customer_id) {
        Integer id = userService.getIdByToken(token);
        if (id == -1) return null;
        //再回到statement_info中找到数据
        CustomerInfo customerInfo = getById(customer_id);
        String update_time = customerInfo.getUpdate_time();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dbTimestamp = LocalDateTime.parse(update_time, formatter);
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 计算时间差
        Duration duration = Duration.between(dbTimestamp, currentTime);
        long days = duration.toDays();
        customerInfo.setUpdate_interval(days);

        customerInfo.setUpdate_time(null);
        return customerInfo;
    }

    @Override
    public List<CustomerInfo> getAllByStatementId(Integer statement_id) {
        QueryWrapper<CustomerInfo> qw = new QueryWrapper<>();
        qw.eq("statement_id",statement_id);
        return customerDao.selectList(qw);
    }

    @Override
    public Boolean addCustomer(JsonNode jsonNode) {
        CustomerInfo customerInfo = new CustomerInfo();

        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        Integer statement_id = jsonNode.get("statement_id").asInt();
        customerInfo.setStatement_id(statement_id);
        customerInfo.setCustomer_name(jsonNode.get("customer_name").asText());
        customerInfo.setProduct_id(jsonNode.get("product_id").asText());
        Integer consumption = jsonNode.get("consumption").asInt();
        customerInfo.setConsumption(consumption);
        customerInfo.setTransaction_description(jsonNode.get("transaction_description").asText());
        customerInfo.setPlan_description(jsonNode.get("plan_description").asText());

        String transactionStatus = jsonNode.get("transaction_status").asText();
        if (transactionStatus.equals("已成交")) customerInfo.setTransaction_status(1);
        else if (transactionStatus.equals("未成交"))customerInfo.setTransaction_status(0);
        customerInfo.setRemarks(jsonNode.get("remarks").asText());


        StatementInfo statementInfo = statementService.getById(statement_id);
        Integer newConsumption = statementInfo.getTotal_usage() + consumption;
        statementInfo.setTotal_usage(newConsumption);

        if (transactionStatus.equals("已成交")){
            Integer newTotalTransaction = (statementInfo.getTotal_transaction() + consumption);
            statementInfo.setTotal_transaction(newTotalTransaction);
        }


        LocalDateTime currentTime = LocalDateTime.now();
        // 定义格式化模式
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化为时间戳字符串
        String update_time = currentTime.format(f);
        customerInfo.setUpdate_time(update_time);

        return save(customerInfo) && statementService.updateById(statementInfo);
    }

    @Override
    public Boolean updateCustomer(JsonNode jsonNode) {
        CustomerInfo customerInfo = new CustomerInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        Integer customer_id = jsonNode.get("customer_id").asInt();
        customerInfo.setId(customer_id);
        customerInfo.setCustomer_name(jsonNode.get("customer_name").asText());
        customerInfo.setProduct_id(jsonNode.get("product_id").asText());
        Integer originConsumption = getById(customer_id).getConsumption();
        Integer consumption = jsonNode.get("consumption").asInt();
        customerInfo.setConsumption(consumption);
        customerInfo.setTransaction_description(jsonNode.get("transaction_description").asText());
        customerInfo.setPlan_description(jsonNode.get("plan_description").asText());
        String newTransactionStatus = jsonNode.get("transaction_status").asText();
        if (newTransactionStatus.equals("已成交")) customerInfo.setTransaction_status(1);
        else if (newTransactionStatus.equals("未成交")) customerInfo.setTransaction_status(0);
        customerInfo.setRemarks(jsonNode.get("remarks").asText());

        Integer originTransactionStatus = getById(customer_id).getTransaction_status();


        Integer statement_id = getById(customer_id).getStatement_id();
        StatementInfo statementInfo = statementService.getById(statement_id);
        Integer flag = null;
        if (newTransactionStatus.equals("已成交")) flag = 1;
        else if (newTransactionStatus.equals("未成交")) flag = 0;
        //可优化*******************这样可读性好，但是不简洁。记住：只要更新，total_usage都要更新一下
        if (!Objects.equals(flag, originTransactionStatus)){
            int newTotalTransaction;
            switch (flag){
                case 0: //从已成交变成未成交
                    newTotalTransaction = (statementInfo.getTotal_transaction() - originConsumption);//需要减去的是原来的用量
                    statementInfo.setTotal_usage(statementInfo.getTotal_usage() - originConsumption + consumption);
                    break;
                case 1://从未成交变成已成交
                    newTotalTransaction = (statementInfo.getTotal_transaction() + consumption);
                    statementInfo.setTotal_usage(statementInfo.getTotal_usage() - originConsumption + consumption);
                    break;
                default:return false;
            }
            statementInfo.setTotal_transaction(newTotalTransaction);
        }
        else {//成交状态没变
            if (originTransactionStatus == 1 ){//原来就是已成交
                statementInfo.setTotal_transaction(statementInfo.getTotal_transaction() - originConsumption + consumption);
                statementInfo.setTotal_usage(statementInfo.getTotal_usage() - originConsumption + consumption);
            } else if (originTransactionStatus == 0) {//原来是未成交
                statementInfo.setTotal_usage(statementInfo.getTotal_usage() - originConsumption + consumption);
            }

        }

        statementService.updateById(statementInfo);
        return updateById(customerInfo);
    }

    @Override
    public Boolean deleteCustomer(JsonNode jsonNode) {
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        Integer customer_id = jsonNode.get("customer_id").asInt();
        CustomerInfo customerInfo = getById(customer_id);
        Integer statement_id = customerInfo.getStatement_id();
        Integer consumption = customerInfo.getConsumption();
        Integer transactionStatus = customerInfo.getTransaction_status();
        StatementInfo statementInfo = statementService.getById(statement_id);
        if (transactionStatus==1) statementInfo.setTotal_transaction(statementInfo.getTotal_transaction()-consumption);
        statementInfo.setTotal_usage(statementInfo.getTotal_usage()-consumption);
        return statementService.updateById(statementInfo) && removeById(jsonNode.get("customer_id").asInt());
    }

    @Override
    public Boolean deleteCustomerByStatementId(Integer statement_id) {
        QueryWrapper<CustomerInfo> qw = new QueryWrapper<>();
        qw.eq("statement_id",statement_id);
        return remove(qw);
    }
}
