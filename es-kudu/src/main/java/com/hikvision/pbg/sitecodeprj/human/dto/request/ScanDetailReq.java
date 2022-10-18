package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ScanDetailReq {
    @NotEmpty(message = "参数错误:必填参数pageNum为空")
    private int pageNum = 1;
    @NotEmpty(message = "参数错误:必填参数pageSize为空")
    private int pageSize = 10;
    @NotEmpty(message = "参数错误:必填参数certificateNumber为空")
    private String certificateNumber;
    @NotEmpty(message = "参数错误:必填参数startTime为空")
    private String startTime;
    @NotEmpty(message = "参数错误:必填参数endTime为空")
    private String endTime;

    private String regionName;
}
