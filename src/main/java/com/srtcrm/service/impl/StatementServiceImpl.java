package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.dao.StatementDao;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.service.CustomerService;
import com.srtcrm.service.StatementService;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl extends ServiceImpl<StatementDao, StatementInfo> implements StatementService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StatementDao statementDao;
    @Autowired
    private UserService userService;

    @Override
    public IPage<StatementInfo> getStatementPage(String token, int currentPage, int pageSize) {
        IPage<StatementInfo> page = new Page<>(currentPage, pageSize);
        Integer id = userService.getIdByToken(token);
        if (id == -1) return null;
        //去user_info表格查询对应的表ID
        if (userService.verifyPermission(token,"middle")){
            //再回到statement_info中找到数据
            QueryWrapper<StatementInfo> qw = new QueryWrapper<>();
            qw.eq("user_id",id);
            statementDao.selectPage(page,qw);
            return page;
        } else if (userService.verifyPermission(token,"high")) {
            statementDao.selectPage(page,null);
            return page;
        }else return null;


    }

    @Override
    public String getStatementName(Integer statement_id) {
        return getById(statement_id).getStatement_name();
    }

    @Override
    public Boolean addStatement(JsonNode jsonNode) {
        StatementInfo statementInfo = new StatementInfo();
        String token = jsonNode.get("token").asText();
        Integer id = userService.getIdByToken(token);
        if (id == -1) return false;
        if (userService.verifyPermission(token,"middle")){
            statementInfo.setUser_id(id);
            statementInfo.setStatement_name(jsonNode.get("statement_name").asText());
            return save(statementInfo);
        } else if (userService.verifyPermission(token,"high")) {
            statementInfo.setUser_id(id);
            statementInfo.setStatement_name(jsonNode.get("statement_name").asText());
            return save(statementInfo);
        }else return false;
    }

    @Override
    public Boolean updateStatement(JsonNode jsonNode) {
        StatementInfo statementInfo = new StatementInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        statementInfo.setId(jsonNode.get("statement_id").asInt());
        statementInfo.setStatement_name(jsonNode.get("statement_name").asText());
        return updateById(statementInfo);
    }

    @Override
    public Boolean deleteStatement(JsonNode jsonNode) {
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        Integer statement_id = jsonNode.get("statement_id").asInt();
        if (id == -1) return false;
        if (customerService.getAllByStatementId(statement_id) != null){
            customerService.deleteCustomerByStatementId(statement_id);
        }
        return removeById(statement_id);
    }

    @Override
    public Integer getIdByStatementId(Integer statementId) {
        return getById(statementId).getId();
    }
}
