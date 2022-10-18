package com.hikvision.pbg.sitecodeprj.kudu.controller;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.pbg.sitecodeprj.kudu.service.ScenePerformTestService;
import org.apache.kudu.client.KuduException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author xiaokai 2021-05-14 19:54:30
 */
@RestController
@RequestMapping("scene-perform-test")
public class ScenePerformTestController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ScenePerformTestService scenePerformTestService;

    /* ============================ 多条数据操作-同步 ============================ */
    @RequestMapping("batch-insert")
    private JSONObject batchInsert(int nThreads, int dataNum) throws Exception {
        return scenePerformTestService.batchInsert(nThreads, dataNum);
    }

    @RequestMapping("batch-delete")
    private JSONObject batchDelete(int nThreads, int dataNum) throws InterruptedException, KuduException, ExecutionException {
        return scenePerformTestService.batchDelete(nThreads, dataNum);
    }
}
