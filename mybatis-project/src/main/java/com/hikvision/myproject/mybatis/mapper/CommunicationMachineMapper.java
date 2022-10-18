package com.hikvision.myproject.mybatis.mapper;


import com.hikvision.myproject.mybatis.entity.CommunicationMachine;

public interface CommunicationMachineMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommunicationMachine record);

    int insertSelective(CommunicationMachine record);

    CommunicationMachine selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommunicationMachine record);

    int updateByPrimaryKey(CommunicationMachine record);
}