package com.srtcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.srtcrm.domain.StatementInfo;
import com.srtcrm.domain.UserInfo;

public interface StatementService extends IService<StatementInfo> {
    IPage<StatementInfo> getPage(String openid, int currentPage, int pageSize);

}
