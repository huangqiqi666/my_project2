package com.hikvision.pbg.sitecodeprj.human.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class HumanQueryReq {

    @NotEmpty(message = "参数错误:必填参数pageNum为空")
    private int pageNum = 1;
    @NotEmpty(message = "参数错误:必填参数pageSize为空")
    private int pageSize = 10;

    private String certificateNumber;

    private String name;

    private String phone;

    private List<String> personFlag;

    private String humanId;



}
