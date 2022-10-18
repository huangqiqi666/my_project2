package com.hikvision.pbg.sitecodeprj.kudu.thread;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import com.hikvision.pbg.sitecodeprj.kudu.template.KuduTemplate;
import com.hikvision.pbg.sitecodeprj.kudu.utils.DateUtil;
import com.hikvision.pbg.sitecodeprj.kudu.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * @author xiaokai 2021-06-23 14:51:09
 */
public class InsertTaskAsyn implements Callable<Integer> {
    Logger log = LoggerFactory.getLogger(getClass());

    private final KuduTemplate kuduTemplate;
    private final int num;

    public InsertTaskAsyn(KuduTemplate kuduTemplate, int num) {
        this.kuduTemplate = kuduTemplate;
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        JSONObject data;
        List<JSONObject> dataList = Lists.newArrayList();

        for (int i = 0; i < num; i++) {
            data = new JSONObject();
            // data.put("id", kuduTemplate.getId());
            data.put("base_tag_id", "user_info");
            // data.put("user_id", kuduTemplate.getId());
            data.put("name", "郎鹏飞");
            data.put("phone", "13337825667");
            data.put("sex", "男");
            data.put("address", "");
            data.put("job", "研发");
            data.put("birthday", DateUtil.str2Long2("1986.09.20"));

            data.put("id", kuduTemplate.getId());
            data.put("user_id", kuduTemplate.getId());

            for (int k = 1; k <= 41; k++) {
                data.put("high_" + k, "123123");
            }

            dataList.add(data);

            if (i % Constants.MAX_KUDU_OPERATION_SIZE == 0) {
                kuduTemplate.batchInsertAsyn(Constants.TABLE_NAME, dataList);
                dataList = Lists.newArrayList();
            }
        }

        kuduTemplate.batchInsertAsyn(Constants.TABLE_NAME, dataList);
        return num;
    }
}
