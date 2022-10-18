package com.hikvision.myproject.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.hikvision.myproject.mybatis.entity.CdDCameraDelay;
import com.hikvision.myproject.mybatis.mapper.CdDCameraDelayMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname Controller
 * @Description TODO
 * @Date 2022/7/14 11:40
 * @Created by huangqiqi
 */
@Slf4j
@Api(value = "数据库测试接口")
@RestController
public class Controller {
    @Autowired
    private CdDCameraDelayMapper cameraDelayMapper;

    @ApiOperation(value = "时延表按照id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键")
    })
    @RequestMapping("/queryById")
    public String queryById(@Param(value = "id") String id){
        log.info("时延表按照id查询，id：{}",id);

        CdDCameraDelay cdDCameraDelay = cameraDelayMapper.selectByPrimaryKey(id);
        return JSON.toJSONString(cdDCameraDelay);
    }
}
