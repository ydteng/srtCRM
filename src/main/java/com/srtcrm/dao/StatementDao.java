package com.srtcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srtcrm.domain.StatementInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatementDao extends BaseMapper<StatementInfo> {
}
