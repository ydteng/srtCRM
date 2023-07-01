package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public IPage<StatementInfo> getStatementPage(String openid, int currentPage, int pageSize) {
        IPage<StatementInfo> page = new Page<StatementInfo>(currentPage,pageSize);
        //去user_info表格查询对应的表ID
        Integer id = userService.getIdByOpenid(openid);
        if (id == -1) return null;
        //再回到statement_info中找到数据
        QueryWrapper<StatementInfo> qw = new QueryWrapper<>();
        qw.eq("user_id",id);
        statementDao.selectPage(page,qw);
        return page;

    }

    @Override
    public Boolean addStatement(String openid, String area, String data) {
        StatementInfo statementInfo = new StatementInfo();
        Integer id = userService.getIdByOpenid(openid);
        if (id == -1) return false;
        statementInfo.setUser_id(id);
        statementInfo.setArea(area);
        statementInfo.setDate(data);
        return save(statementInfo);
    }

    @Override
    public Boolean updateStatement(Integer statement_id, String openid,  String area, String data) {
        StatementInfo statementInfo = new StatementInfo();
        Integer id = userService.getIdByOpenid(openid);
        if (id == -1) return false;
        statementInfo.setId(statement_id);
        statementInfo.setArea(area);
        statementInfo.setDate(data);
        return updateById(statementInfo);
    }

    @Override
    public Boolean deleteStatement(Integer statement_id, String openid) {
        Integer id = userService.getIdByOpenid(openid);
        if (id == -1) return false;
        return removeById(statement_id);
    }
}
