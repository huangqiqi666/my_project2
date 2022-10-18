package com.hikvision.pbg.sitecodeprj.kudu.controller;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.pbg.sitecodeprj.kudu.config.CondType;
import com.hikvision.pbg.sitecodeprj.kudu.entity.ColumnCond;
import com.hikvision.pbg.sitecodeprj.kudu.entity.KuduColumn;
import com.hikvision.pbg.sitecodeprj.kudu.template.KuduTemplate;
import org.apache.kudu.Type;
import org.apache.kudu.client.KuduException;
import org.apache.kudu.client.KuduPredicate;
import org.apache.kudu.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaokai 2021-05-14 19:54:30
 */
@RestController
@RequestMapping("test")
public class TestController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    KuduTemplate kuduTemplate;

    @RequestMapping("hi")
    public String hi() {
        return "ok";
    }

    /* ====================================  kudu操作  ==================================== */
    @RequestMapping("create-table")
    public JSONObject createTable(@RequestParam String tableName) throws KuduException {
        JSONObject json = new JSONObject();

        int buckets = 3;
        int copies = 1;

        List<KuduColumn> kuduColumnList = Lists.newArrayList();
        kuduColumnList.add(KuduColumn.of("name", Type.STRING));
        kuduColumnList.add(KuduColumn.of("age", Type.INT32));

        boolean result = kuduTemplate.createTable(tableName, kuduColumnList, buckets, copies);

        log.info("创建表{} {}", tableName, result ? "成功" : "失败");
        json.put("result", result);

        return json;
    }

    @RequestMapping("delete-table")
    public JSONObject deleteTable(@RequestParam String tableName) throws KuduException {
        JSONObject json = new JSONObject();
        kuduTemplate.deleteTable(tableName);
        json.put("result", true);

        return json;
    }

    @RequestMapping("add-nullable-column")
    public JSONObject addNullableColumn(@RequestParam String tableName,
                                        @RequestParam String colName) throws KuduException, InterruptedException {
        JSONObject json = new JSONObject();
        Type type = Type.INT32;
        boolean result = kuduTemplate.addNullableColumn(tableName, colName, type);

        json.put("result", result);

        return json;
    }

    @RequestMapping("add-column")
    private JSONObject addColumn(@RequestParam String tableName,
                                 @RequestParam String colName) throws KuduException, InterruptedException {
        JSONObject json = new JSONObject();
        Type type = Type.INT32;
        Integer defaultVal = 12;

        boolean result = kuduTemplate.addDefaultValColumn(tableName, colName, type, defaultVal);

        json.put("result", result);

        return json;
    }

    @RequestMapping("exist-column")
    private JSONObject existColumn(@RequestParam String tableName, @RequestParam String colName) throws KuduException {
        JSONObject json = new JSONObject();

        boolean result = kuduTemplate.existColumn(tableName, colName);
        log.info("表{}中的列{} {}", tableName, colName, result ? "存在" : "不存在");
        json.put("result", result);

        return json;
    }

    @RequestMapping("insert")
    private JSONObject insert(@RequestParam String tableName) throws KuduException {
        JSONObject json = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("pid", "lp2425f1523");
        data.put("pname", "xiao42");
        data.put("psex", 0);
        data.put("page", 12);

        kuduTemplate.insert(tableName, data);
        json.put("result", true);

        return json;
    }

    @RequestMapping("upsert")
    private JSONObject upsert(@RequestParam String tableName) throws KuduException {
        JSONObject json = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("pid", "lp2425f");
        data.put("pname", "xiao111");
        data.put("psex", 0);
        data.put("page", 18);


        kuduTemplate.upsert(tableName, data);
        json.put("result", true);

        return json;
    }

    @RequestMapping("update")
    private JSONObject update() throws KuduException {
        JSONObject json = new JSONObject();

        String tableName = "test";
        JSONObject data = new JSONObject();
        data.put("name", "lpf");
        data.put("age", 13);
        data.put("money", 1333);

        kuduTemplate.update(tableName, data);
        json.put("result", true);

        return json;
    }

    @RequestMapping("query")
    private JSONObject query() throws KuduException {
        JSONObject json = new JSONObject();

        String tableName = "test";
        List<String> selectColumnList = Lists.newArrayList();

        List<ColumnCond> columnCondList = Lists.newArrayList();
        columnCondList.add(ColumnCond.of("money", KuduPredicate.ComparisonOp.EQUAL, 33));

        // is null 查询
        columnCondList.add(ColumnCond.of("age", KuduPredicate.ComparisonOp.EQUAL, CondType.IS_NULL));

        // is not null 查询
        columnCondList.add(ColumnCond.of("count", KuduPredicate.ComparisonOp.EQUAL, CondType.IS_NOT_NULL));

        List<JSONObject> dataList = kuduTemplate.query(tableName, selectColumnList, columnCondList, 10);

        json.put("data", dataList);
        json.put("result", true);

        return json;
    }

    @RequestMapping("delete")
    private JSONObject delete(@RequestParam String tableName) throws KuduException {
        JSONObject json = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("pid", "lp2425f");
        data.put("pname", "xiao111");
        data.put("psex", 0);
        data.put("page", 18);
        kuduTemplate.delete(tableName, data);
        json.put("result", true);

        return json;
    }
    /* ================================  impala kudu 操作  ================================ */
}
