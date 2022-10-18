package com.hikvision.pbg.sitecodeprj.human.dto.response;

import com.hikvision.pbg.sitecodeprj.human.entity.HumanInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HumanInfo
 * @Description
 * @Author xiaokai
 * @Date 15:35 2022/9/21
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanInfoDto {

    //id主键(调用基线按照“证件号+证件类型”生成)
    private String humanId;

    private String certificateType;

    //证件号码
    private String certificateNumber;

    //姓名
    private String name;

    private String gender;
    //电话
    private String phone;

    //人员照片
    private String faceUrl;

    //居住地址
    private String residentAddress;

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

    private String personFlag;

    private String note;

    public HumanInfoDto(HumanInfo humanInfo) {
        this.humanId =humanInfo.getHumanId();
        this.certificateType = humanInfo.getCertificateType();
        this.certificateNumber = humanInfo.getCertificateNumber();
        this.name = humanInfo.getName();
        this.gender = humanInfo.getGender();
        this.phone = humanInfo.getPhone();
        this.faceUrl = humanInfo.getFaceUrl();
        this.residentAddress = humanInfo.getResidentAddress();
        this.source = humanInfo.getSource();
        this.nation = humanInfo.getNation();
        this.province = humanInfo.getProvince();
        this.city = humanInfo.getCity();
        this.district = humanInfo.getDistrict();
        this.town = humanInfo.getTown();
        this.village = humanInfo.getVillage();
        this.courtName = humanInfo.getCourtName();
        this.personFlag = humanInfo.getFlag();
        this.note = humanInfo.getNote();
    }
}
