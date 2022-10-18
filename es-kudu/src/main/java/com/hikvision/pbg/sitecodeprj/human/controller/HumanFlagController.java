package com.hikvision.pbg.sitecodeprj.human.controller;

import com.alibaba.fastjson2.JSON;
import com.hikvision.pbg.sitecodeprj.common.AnalyspBigdataErrorCode;
import com.hikvision.pbg.sitecodeprj.common.BaseResult;
import com.hikvision.pbg.sitecodeprj.human.dto.request.PersonFlagReq;
import com.hikvision.pbg.sitecodeprj.human.service.HumanFlagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @ClassName HumanFlagController
 * @Description 人员标签操作相关接口
 * @Author xiaokai
 * @Date 18:55 2022/9/21
 * @Version 1.0
 **/
@RestController
@RequestMapping("/humanFlag")
public class HumanFlagController {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private HumanFlagService humanFlagService;

    /**
     * 批量删除人员与标签的绑定关系
     */
    @PostMapping("/deleteFlagBatch")
    public BaseResult<?> deleteBatch(@RequestBody PersonFlagReq reqDto) {
        LOGGER.info("human flag delete request come in, param:{}", JSON.toJSONString(reqDto));
        try {
            humanFlagService.deleteBatch(Arrays.asList(reqDto.getPersonFlag().split(",")));
        } catch (Exception e) {
            return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_INSERT.getCode(), e.getMessage());
        }
        return BaseResult.success(null);
    }
}
