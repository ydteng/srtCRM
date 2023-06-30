package com.srtcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srtcrm.dao.StatementDao;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl extends ServiceImpl<StatementDao, StatementInfo> implements StatementService {
    @Autowired
    private StatementDao statementDao;
    @Override
    public IPage<StatementInfo> getPage(String openid, int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);
        statementDao.selectPage(page,null);
        return page;

    }
}
