package com.hikvision.pbg.sitecodeprj.human.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName HumanInfo
 * @Description ES 表实体
 * @Author xiaokai
 * @Date 15:35 2022/9/21
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanInfo {

    //id主键(调用基线按照“证件号+证件类型”生成)
    private String humanId;

    //证件号码
    private String certificateNumber;

    //
    private String certificateType;

    //姓名
    private String name;

    //电话
    private String phone;

    private String gender;

    //人员照片
    private String faceUrl;

    //居住地址
    private String residentAddress;

    //标签 text类型，支持分词查询，多个标签code以@间隔
    private String flag;

    //来源 标记人员信息来源
    private String source;

    //国家/地区
    private String nation;

    //省
    private String province;

    //市
    private String city;

    //区或县
    private String district;

    //街道乡镇
    private String town;

    //居（村）委会
    private String village;

    //小区名称（单位）
    private String courtName;

    //最新扫码日期(仅模型计算用)
    private String lastestDate;

    //异常扫码时间(仅模型计算用)
    private String errorTime;

    //备注
    private String note;

}
