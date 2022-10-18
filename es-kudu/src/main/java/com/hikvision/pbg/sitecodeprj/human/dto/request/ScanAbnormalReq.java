package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ScanAbnormalReq {

    @NotEmpty(message = "参数错误:必填参数certificateNumber为空")
    private String certificateNumber;
}
