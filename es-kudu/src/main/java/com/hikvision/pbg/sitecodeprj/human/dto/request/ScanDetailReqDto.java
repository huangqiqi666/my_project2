package com.hikvision.pbg.sitecodeprj.human.dto.request;

import com.hikvision.pbg.sitecodeprj.common.BasePageBo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName ScanDetailReqDto
 * @Description 扫码记录请求参数
 * @Author xiaokai
 * @Date 19:02 2022/9/21
 * @Version 1.0
 **/
@Data
public class ScanDetailReqDto extends BasePageBo {

    private String certificateNumber;
    private String startTime;
    private String endTime;
    private String regionName;

    public Boolean isValid() {
        return StringUtils.isNotBlank(certificateNumber) || StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime);
    }

    public Boolean isAbnormalValid() {
        return StringUtils.isNotBlank(certificateNumber);
    }
}
