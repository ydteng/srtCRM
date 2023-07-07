package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.dao.StatementDao;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.service.StatementService;
import com.srtcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl extends ServiceImpl<StatementDao, StatementInfo> implements StatementService {
    @Autowired
    private StatementDao statementDao;
    @Autowired
    private UserService userService;
    @Override
    public IPage<StatementInfo> getStatementPage(String token, int currentPage, int pageSize) {
        IPage<StatementInfo> page = new Page<StatementInfo>(currentPage,pageSize);
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
    public Boolean addStatement(JsonNode jsonNode) {
        StatementInfo statementInfo = new StatementInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        statementInfo.setUser_id(id);
        statementInfo.setFile_name(jsonNode.get("file_name").asText());
        return save(statementInfo);
    }

    @Override
    public Boolean updateStatement(JsonNode jsonNode) {
        StatementInfo statementInfo = new StatementInfo();
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        statementInfo.setId(jsonNode.get("statement_id").asInt());
        statementInfo.setFile_name(jsonNode.get("file_name").asText());
        return updateById(statementInfo);
    }

    @Override
    public Boolean deleteStatement(JsonNode jsonNode) {
        Integer id = userService.getIdByToken(jsonNode.get("token").asText());
        if (id == -1) return false;
        return removeById(jsonNode.get("statement_id").asInt());
    }

    @Override
    public Integer getIdByStatementId(Integer statementId) {
        return getById(statementId).getId();
    }
}
