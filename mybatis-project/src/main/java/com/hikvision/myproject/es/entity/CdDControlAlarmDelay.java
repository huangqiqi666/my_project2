package com.hikvision.myproject.mybatis.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * cd_d_camera_delay
 * @author 
 */
@ApiModel
@Data
public class CdDControlAlarmDelay implements Serializable {
    private String id;

    private String build_type;

    private String camera_function_type;

    private Long cre_time;

    private Integer face_avg_pass;

    private Integer face_cloud_avg_pass;

    private Integer face_cloud_pass_all;

    private Integer face_cloud_second_avg_pass;

    private Integer face_cloud_second_pass_all;

    private Integer face_cloud_second_today_pass;

    private Integer face_cloud_today_pass;

    private Integer face_model_avg_pass;

    private Integer face_model_today_pass;

    private Integer face_overtime_avg_pass;

    private Integer face_overtime_today_pass;

    private Integer face_today_pass;

    private Integer hour;

    private String hour_str;

    private String install_addr;

    private String key_type;

    private String keyboard_code;

    private String location_type;

    private String manage_unit;

    private String name;

    private Integer nonmotor_avg_pass;

    private Integer nonmotor_cloud_avg_pass;

    private Integer nonmotor_cloud_pass_all;

    private Integer nonmotor_cloud_second_avg_pass;

    private Integer nonmotor_cloud_second_pass_all;

    private Integer nonmotor_cloud_second_today_pass;

    private Integer nonmotor_cloud_today_pass;

    private Integer nonmotor_model_avg_pass;

    private Integer nonmotor_model_today_pass;

    private Integer nonmotor_overtime_avg_pass;

    private Integer nonmotor_overtime_today_pass;

    private Integer nonmotor_today_pass;

    private Integer person_avg_pass;

    private Integer person_cloud_avg_pass;

    private Integer person_cloud_pass_all;

    private Integer person_cloud_second_avg_pass;

    private Integer person_cloud_second_pass_all;

    private Integer person_cloud_second_today_pass;

    private Integer person_cloud_today_pass;

    private Integer person_model_avg_pass;

    private Integer person_model_today_pass;

    private Integer person_overtime_avg_pass;

    private Integer person_overtime_today_pass;

    private Integer person_today_pass;

    private String project;

    private Integer prtday;

    private String region;

    private String res_id;

    private Integer status;

    private Long status_chg_time;

    private Integer statusc;

    private Integer vehicle_avg_pass;

    private Integer vehicle_cloud_avg_pass;

    private Integer vehicle_cloud_pass_all;

    private Integer vehicle_cloud_second_avg_pass;

    private Integer vehicle_cloud_second_pass_all;

    private Integer vehicle_cloud_second_today_pass;

    private Integer vehicle_cloud_today_pass;

    private Integer vehicle_model_avg_pass;

    private Integer vehicle_model_today_pass;

    private Integer vehicle_overtime_avg_pass;

    private Integer vehicle_overtime_today_pass;

    private Integer vehicle_today_pass;

    private static final long serialVersionUID = 1L;
}