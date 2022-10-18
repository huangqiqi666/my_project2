package com.hikvision.pbg.sitecodeprj.kudu.config;

import com.hikvision.pbg.sitecodeprj.kudu.component.KuduApplyAndFlushComp;
import org.apache.kudu.client.KuduException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * @author xiaokai 2021/2/28
 */
@Component
@EnableScheduling
public class SchedulerTask {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    KuduApplyAndFlushComp kuduApplyAndFlushComp;

    @Scheduled(fixedDelay = Constants.SCHEDULE_INTERVAL_PERIOD)
    public void fixedDelayJob() throws KuduException {
        log.debug("开始执行 clinic data persistent：");
        kuduApplyAndFlushComp.consumeKuduOperation();
    }
}
