package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HumanAddReq {

    private String personFlag;
    @NotEmpty(message = "参数错误:必填参数certificateType为空")
    private String certificateType;
    @NotEmpty(message = "参数错误:必填参数certificateNumber为空")
    private String certificateNumber;
    @NotEmpty(message = "参数错误:必填参数name为空")
    private String name;
    @NotEmpty(message = "参数错误:必填参数gender为空")
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

    private String humanId;

}
