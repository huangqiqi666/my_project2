package com.hikvision.myproject.mybatis.mapper;


import com.hikvision.myproject.mybatis.entity.CdDCameraQuality;

public interface CdDCameraQualityMapper {
    int deleteByPrimaryKey(String id);

    int insert(CdDCameraQuality record);

    int insertSelective(CdDCameraQuality record);

    CdDCameraQuality selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CdDCameraQuality record);

    int updateByPrimaryKey(CdDCameraQuality record);
}