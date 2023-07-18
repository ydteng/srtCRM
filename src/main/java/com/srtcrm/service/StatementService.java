package com.srtcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.domain.UserInfo;

public interface StatementService extends IService<StatementInfo> {
    IPage<StatementInfo> getStatementPage(String token, int currentPage, int pageSize);
    String getStatementName(Integer statement_id);
    Boolean addStatement(JsonNode jsonNode);
    Boolean updateStatement(JsonNode jsonNode);
    Boolean deleteStatement(JsonNode jsonNode);
    Integer getIdByStatementId(Integer statementId);

}
