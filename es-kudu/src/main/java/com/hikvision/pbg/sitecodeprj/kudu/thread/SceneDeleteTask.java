package com.hikvision.pbg.sitecodeprj.kudu.thread;

import com.alibaba.fastjson.JSONObject;

import com.hikvision.pbg.sitecodeprj.kudu.template.KuduTemplate;
import com.hikvision.pbg.sitecodeprj.kudu.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * forkjoin
 * @author xiaokai 2021-06-23 14:51:09
 */
public class SceneDeleteTask implements Callable<Integer> {
    Logger log = LoggerFactory.getLogger(getClass());

    private final KuduTemplate kuduTemplate;
    private final List<JSONObject> dataList;

    public SceneDeleteTask(KuduTemplate kuduTemplate,
                           List<JSONObject> dataList) {
        this.kuduTemplate = kuduTemplate;
        this.dataList = dataList;
    }

    @Override
    public Integer call() throws Exception {
        log.info("start batch delete ...");
        kuduTemplate.batchDelete(Constants.SCENE_TABLE_NAME, dataList);
        return dataList.size();
    }
}
