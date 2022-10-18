package com.hikvision.pbg.sitecodeprj.kudu.config.kudu.impl;

import com.hikvision.pbg.sitecodeprj.kudu.config.kudu.IKuduOperation;
import org.apache.kudu.client.KuduTable;
import org.apache.kudu.client.Operation;

/**
 * @author xiaokai 2021-06-29 11:03:53
 */
public class UpsertOperation implements IKuduOperation {
    private final KuduTable ktable;

    public UpsertOperation(KuduTable ktable) {
        this.ktable = ktable;
    }

    @Override
    public Operation getOperation() {
        return ktable.newUpsert();
    }
}
