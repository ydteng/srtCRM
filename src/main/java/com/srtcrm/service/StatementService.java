package com.srtcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.domain.UserInfo;

public interface StatementService extends IService<StatementInfo> {
    IPage<StatementInfo> getStatementPage(String openid, int currentPage, int pageSize);
    Boolean addStatement(String openid,String area,String data);
    Boolean updateStatement(Integer statement_id,String openid,String area,String data);
    Boolean deleteStatement(Integer statement_id,String openid);

}
