package com.srtcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srtcrm.domain.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao extends BaseMapper<CustomerInfo> {
}
