package com.hikvision.pbg.sitecodeprj.human.dto.request;

import com.hikvision.pbg.sitecodeprj.common.BasePageBo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


@Data
public class HumanReqDto {

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

    private String humanId;

    private String note;

    private String city;

    private String province;

    public boolean isValid(){
        return StringUtils.isNotBlank(certificateNumber) && StringUtils.isNotBlank(certificateType)
                && StringUtils.isNotBlank(name) && StringUtils.isNotBlank(gender);
    }

}
