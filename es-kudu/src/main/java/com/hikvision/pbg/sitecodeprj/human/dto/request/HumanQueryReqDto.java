package com.hikvision.pbg.sitecodeprj.human.dto.request;

import com.hikvision.pbg.sitecodeprj.common.BasePageBo;
import lombok.Data;

import java.util.List;

@Data
public class HumanQueryReqDto extends BasePageBo {

    private String certificateType;

    private String certificateNumber;

    private String name;

    private String phone;

    private List<String> personFlag;

    private String humanId;

    private String province;

}
