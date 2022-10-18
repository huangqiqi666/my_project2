package com.hikvision.pbg.sitecodeprj.human.controller;


import com.alibaba.fastjson2.JSON;
import com.hikvision.pbg.sitecodeprj.common.AnalyspBigdataErrorCode;
import com.hikvision.pbg.sitecodeprj.common.BaseResult;
import com.hikvision.pbg.sitecodeprj.common.Constants;
import com.hikvision.pbg.sitecodeprj.common.Result;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanQueryReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanReqDto;
import com.hikvision.pbg.sitecodeprj.human.service.HumanInfoService;
import com.hikvision.pbg.sitecodeprj.kudu.config.kudu.OperationEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @ClassName HumanInfoController
 * @Description 人员操作接口
 * @Author xiaokai
 * @Date 16:20 2022/9/21
 * @Version 1.0
 **/
@RequestMapping("/human")
@RestController
public class HumanInfoController {
    public static final Logger LOGGER = LoggerFactory.getLogger(HumanInfoController.class);
    @Autowired
    private HumanInfoService humanInfoService;

    /**
     * 人员信息新增
     *
     * @param reqDto
     * @return
     */
    @PostMapping("/add")
    public BaseResult<?> save(@RequestBody HumanReqDto reqDto) {
        LOGGER.info("human add request come in, param:{}", JSON.toJSONString(reqDto));
        if (!reqDto.isValid())
            return BaseResult.error(Result.RECODE_ERROR, Constants.REQUEST_MISSING_REQUEST_PARAMETER);

        try {
            return humanInfoService.save(reqDto, OperationEnum.INSERT);
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_INSERT.getCode(), e.getMessage());
        }
    }

    /**
     * 人员信息修改
     *
     * @param reqDto
     * @return
     */
    @PostMapping("/update")
    public BaseResult<?> update(@RequestBody HumanReqDto reqDto) {
        LOGGER.info("human update request come in, param:{}", JSON.toJSONString(reqDto));

        if (StringUtils.isBlank(reqDto.getHumanId()))
            return BaseResult.error(Result.RECODE_ERROR, Constants.REQUEST_MISSING_REQUEST_PARAMETER);
        try {
            return humanInfoService.save(reqDto, OperationEnum.UPDATE);
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_UPDATE.getCode(), e.getMessage());
        }
    }

    /**
     * 删除人员
     *
     * @param reqDto
     * @return
     */
    @PostMapping("/delete")
    public BaseResult<?> delete(@RequestBody HumanReqDto reqDto) {
        LOGGER.info("human delete request come in, param:{}", JSON.toJSONString(reqDto));

        if (StringUtils.isBlank(reqDto.getHumanId()))
            return BaseResult.error(Result.RECODE_ERROR, Constants.REQUEST_MISSING_REQUEST_PARAMETER);
        try {
            humanInfoService.delete(Arrays.asList(reqDto.getHumanId().split(",")));
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_DELETE.getCode(), e.getMessage());
        }
        return BaseResult.success(null);
    }

    /**
     * 人员信息查询
     *
     * @param reqDto
     * @return
     */
    @PostMapping("/query")
    public BaseResult<?> query(@RequestBody HumanQueryReqDto reqDto) {
        LOGGER.info("human info query request come in, param:{}", JSON.toJSONString(reqDto));

        try {
            return BaseResult.success(humanInfoService.getHumanInfoPage(reqDto));
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_QUERY.getCode(), e.getMessage());
        }
    }


}
