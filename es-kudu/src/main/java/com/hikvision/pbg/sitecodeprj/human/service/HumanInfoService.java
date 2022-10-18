package com.hikvision.pbg.sitecodeprj.human.service;

import com.hikvision.pbg.sitecodeprj.common.BaseResult;
import com.hikvision.pbg.sitecodeprj.common.ListBean;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanQueryReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.response.HumanInfoDto;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanQueryReq;
import com.hikvision.pbg.sitecodeprj.kudu.config.kudu.OperationEnum;

import java.util.List;

public interface HumanInfoService {

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    ListBean<HumanInfoDto> getHumanInfoPage(HumanQueryReqDto param);

    /**
     * 新增 && 修改
     *
     * @param humanReqDto
     * @param operationEnum
     */
    BaseResult<?> save(HumanReqDto humanReqDto, OperationEnum operationEnum);

    /**
     * 批量删除
     *
     * @param idList
     */
    void delete(List<String> idList);
}
