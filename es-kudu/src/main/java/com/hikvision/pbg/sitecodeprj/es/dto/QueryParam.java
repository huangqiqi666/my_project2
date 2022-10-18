package com.hikvision.pbg.sitecodeprj.es.dto;

import com.hikvision.pbg.sitecodeprj.common.BasePageBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam extends BasePageBo {
    private String humanId;
    private String certificateNumber;
    private String name;
    private String phone;
    private String personFlag;



}