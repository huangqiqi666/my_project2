package com.hikvision.myproject.mybatis.mapper;


import com.hikvision.myproject.mybatis.entity.ScDCamera;

public interface ScDCameraMapper {
    int deleteByPrimaryKey(String id);

    int insert(ScDCamera record);

    int insertSelective(ScDCamera record);

    ScDCamera selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ScDCamera record);

    int updateByPrimaryKey(ScDCamera record);
}