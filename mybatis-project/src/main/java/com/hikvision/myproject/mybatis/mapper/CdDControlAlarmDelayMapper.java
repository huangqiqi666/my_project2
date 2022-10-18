package com.hikvision.myproject.mybatis.mapper;


import com.hikvision.myproject.mybatis.entity.CdDControlAlarmDelay;

public interface CdDControlAlarmDelayMapper {
    int deleteByPrimaryKey(String id);

    int insert(CdDControlAlarmDelay record);

    int insertSelective(CdDControlAlarmDelay record);

    CdDControlAlarmDelay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CdDControlAlarmDelay record);

    int updateByPrimaryKey(CdDControlAlarmDelay record);
}