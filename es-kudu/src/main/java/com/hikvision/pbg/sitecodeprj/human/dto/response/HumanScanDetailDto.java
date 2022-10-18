package com.hikvision.pbg.sitecodeprj.human.dto.response;

import com.hikvision.pbg.sitecodeprj.human.entity.HumanScanDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HumanScanDetail
 * @Description
 * @Author xiaokai
 * @Date 15:37 2022/9/21
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HumanScanDetailDto {

    private String id;

    //访问者姓名
    private String name;

    //访问者身份证号
    private String certificateNumber;

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

    public HumanScanDetailDto(HumanScanDetail detail) {
        this.id = detail.getId();
        this.name = detail.getVisitorName();
        this.certificateNumber = detail.getVisitorIdNum();
        this.createTime = detail.getCreateTime();
        this.regionName = detail.getRegionName();
        this.regionAddress = detail.getRegionAddress();
        this.greenCode = detail.getGreenCode();
        this.ym = detail.getYm();
        this.dataType = detail.getDataType();
        this.latestNucleicAcidResults = detail.getLatestNucleicAcidResults();
        this.latestNucleicAcidTime = detail.getLatestNucleicAcidTime();
        this.detectionOrganization = detail.getDetectionOrganization();
    }
}
