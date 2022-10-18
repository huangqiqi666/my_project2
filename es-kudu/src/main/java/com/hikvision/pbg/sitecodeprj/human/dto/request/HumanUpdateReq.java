package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HumanUpdateReq {

    private String personFlag;

    private String certificateType;

    private String certificateNumber;

    private String name;

    private String gender;

    private String phone;

    private String faceUrl;

    private String residentAddress;

    private String source;

    private String nation;

    private String district;

    private String town;

    private String village;

    private String courtName;
    @NotEmpty(message = "参数错误:必填参数humanId为空")
    private String humanId;

}
