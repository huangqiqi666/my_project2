package com.hikvision.pbg.sitecodeprj.human.controller;

import com.alibaba.fastjson2.JSON;
import com.hikvision.pbg.sitecodeprj.common.AnalyspBigdataErrorCode;
import com.hikvision.pbg.sitecodeprj.common.BaseResult;
import com.hikvision.pbg.sitecodeprj.common.Constants;
import com.hikvision.pbg.sitecodeprj.common.Result;
import com.hikvision.pbg.sitecodeprj.human.dto.request.ScanDetailReqDto;
import com.hikvision.pbg.sitecodeprj.human.service.HumanScanDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HumanScanDetailController
 * @Description 人员扫码记录相关接口
 * @Author xiaokai
 * @Date 18:59 2022/9/21
 * @Version 1.0
 **/
@RequestMapping("/human")
@RestController
public class HumanScanDetailController {
    public static final Logger LOGGER = LoggerFactory.getLogger(HumanScanDetailController.class);
    @Autowired
    private HumanScanDetailService humanScanDetailService;

    /**
     * 人员扫码记录查询
     *
     * @param reqDto reqDto
     * @return
     */
    @PostMapping("/scanDetail")
    public BaseResult<?> query(@RequestBody ScanDetailReqDto reqDto) {
        LOGGER.info("human scan detail request come in, param:{}", JSON.toJSONString(reqDto));

        if (!reqDto.isValid()) {
            return BaseResult.error(Result.RECODE_ERROR, Constants.REQUEST_MISSING_REQUEST_PARAMETER);
        }
        try {
            return BaseResult.success(humanScanDetailService.queryPage(reqDto));
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_QUERY.getCode(), e.getMessage());
        }
    }

    /**
     * 异常扫码轨迹查询
     *
     * @param
     * @return
     */
    @PostMapping("/abnormalScanDetail")
    public BaseResult<?> abnormal(@RequestBody ScanDetailReqDto reqDto) {
        LOGGER.info("abnormal scan detail request come in, param:{}", JSON.toJSONString(reqDto));

        if (!reqDto.isAbnormalValid()) {
            return BaseResult.error(Result.RECODE_ERROR, Constants.REQUEST_MISSING_REQUEST_PARAMETER);
        }
        try {
            return BaseResult.success(humanScanDetailService.queryAbnormal(reqDto.getCertificateNumber()));
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_QUERY.getCode(), e.getMessage());
        }
    }
}
