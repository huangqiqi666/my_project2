package com.hikvision.myproject.mybatis.dto;

import lombok.Data;

/**
 * @Classname CameraDelayDto
 * @Description TODO
 * @Date 2022/8/2 9:28
 * @Created by huangqiqi
 */
@Data
public class CameraDelayDto {
    private String id;
    private String name;
    private Long creTime;
    private String buildYype;
    private String cameraFunctionType;

    private Integer faceTodayPass;
    private Integer personTodayPass;
    private Integer nonmotorTodayPass;
    private Integer vehicleTodayPass;
}
