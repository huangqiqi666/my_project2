package com.hikvision.pbg.sitecodeprj.kudu.thread;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hikvision.pbg.sitecodeprj.kudu.entity.ColumnCond;
import com.hikvision.pbg.sitecodeprj.kudu.template.KuduTemplate;
import com.hikvision.pbg.sitecodeprj.kudu.config.Constants;
import org.apache.kudu.client.KuduPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * @author xiaokai 2021-06-23 14:51:09
 */
public class QueryTask implements Callable<Integer> {
    Logger log = LoggerFactory.getLogger(getClass());

    private final KuduTemplate kuduTemplate;
    private final int num;

    public QueryTask(KuduTemplate kuduTemplate, int num) {
        this.kuduTemplate = kuduTemplate;
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        int totalNum = 0;
        List<JSONObject> dataList;
        List<ColumnCond> columnCondList = Lists.newArrayList();
        columnCondList.add(ColumnCond.of("id", KuduPredicate.ComparisonOp.EQUAL, 304491916957848542L));

        for (int i = 0; i < num; i++) {
            dataList = kuduTemplate.query(Constants.TABLE_NAME, null, columnCondList, 100);
            if ((null != dataList) && !dataList.isEmpty()) {
                totalNum += dataList.size();
            }
        }

        return totalNum;
    }
}
