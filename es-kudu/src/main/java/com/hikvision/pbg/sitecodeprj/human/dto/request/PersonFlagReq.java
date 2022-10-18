package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PersonFlagReq {

    @NotEmpty(message = "参数错误:必填参数personFlag为空")
    private String personFlag;
}
