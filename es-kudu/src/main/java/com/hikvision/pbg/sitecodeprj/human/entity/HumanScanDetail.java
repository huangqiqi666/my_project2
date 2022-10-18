package com.hikvision.pbg.sitecodeprj.human.entity;

import lombok.Data;

import java.util.Objects;

/**
 * @ClassName HumanScanDetail
 * @Description
 * @Author xiaokai
 * @Date 15:37 2022/9/21
 * @Version 1.0
 **/
@Data
public class HumanScanDetail {

    private String id;

    //访问者姓名
    private String visitorName;

    //访问者身份证号
    private String visitorIdNum;

    //创建时间
    private String createTime;

    //单位名称
    private String regionName;

    //单位地址
    private String regionAddress;

    //健康码
    private String greenCode;

    //疫苗
    private String ym;

    //1分局,2市局
    private Integer dataType;

    //最新核酸结果
    private String latestNucleicAcidResults;

    //最新核酸时间
    private String latestNucleicAcidTime;

    //检测机构
    private String detectionOrganization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanScanDetail that = (HumanScanDetail) o;
        return Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName);
    }
}
