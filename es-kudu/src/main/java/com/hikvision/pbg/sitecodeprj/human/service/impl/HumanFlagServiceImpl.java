package com.hikvision.pbg.sitecodeprj.human.service.impl;

import com.alibaba.fastjson.JSON;
import com.hikvision.pbg.sitecodeprj.enums.FlagDeleteType;
import com.hikvision.pbg.sitecodeprj.human.service.HumanFlagService;
import com.hikvision.pbg.sitecodeprj.repository.DeleteTaskRepository;
import com.hikvision.pbg.sitecodeprj.repository.entity.DeleteTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HumanFlagServiceImpl
 * @Description 人员标签操作服务
 * @Author xiaokai
 * @Date 20:00 2022/9/21
 * @Version 1.0
 **/
@Service
public class HumanFlagServiceImpl implements HumanFlagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanFlagService.class);

    @Autowired
    private DeleteTaskRepository deleteTaskRepository;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void deleteBatch(List<String> flagList) {
        List<DeleteTask> deleteTasks = new ArrayList<>();
        for (String flag : flagList) {
            deleteTasks.add(new DeleteTask(flag, FlagDeleteType.WAIT.getType(), new Date(), applicationName));
        }

        // 下发删除标签任务到数据库
        LOGGER.info("HumanFlagService.deleteBatch.saveAll:{}", JSON.toJSONString(flagList));
        deleteTaskRepository.saveAll(deleteTasks);
    }
}
