package com.hikvision.pbg.sitecodeprj.human.service;

import com.hikvision.pbg.sitecodeprj.common.ListBean;
import com.hikvision.pbg.sitecodeprj.human.dto.request.ScanDetailReqDto;

public interface HumanScanDetailService {

    /*
     * @Description 分页查询扫码详情记录
     * @Date 20:09 2022/9/21
     * @Param [scanDetailReqDto]
     * @return com.hikvision.pbg.sitecodeprj.common.PageBean<com.hikvision.pbg.sitecodeprj.human.entity.HumanScanDetail>
     * @Author xiaokai
     **/
    ListBean<?> queryPage(ScanDetailReqDto scanDetailReqDto);

    /*
     * @Description 查询异常扫码记录
     * @Date 20:10 2022/9/21
     * @Param [certificateNumber]
     * @return com.hikvision.pbg.sitecodeprj.common.ListBean<?>
     * @Author xiaokai
     **/
    ListBean<?> queryAbnormal(String certificateNumber);
}
