package com.hikvision.myproject.mybatis.mapper;


import com.hikvision.myproject.mybatis.dto.CameraDelayDto;
import com.hikvision.myproject.mybatis.entity.CdDCameraDelay;

import java.util.List;

public interface CdDCameraDelayMapper {
    int deleteByPrimaryKey(String id);

    int insert(CdDCameraDelay record);

    int insertSelective(CdDCameraDelay record);

    CdDCameraDelay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CdDCameraDelay record);

    int updateByPrimaryKey(CdDCameraDelay record);


    List<CameraDelayDto> queryCameraDelay(CdDCameraDelay record);

    int updateById(CameraDelayDto cameraDelayDto);
}