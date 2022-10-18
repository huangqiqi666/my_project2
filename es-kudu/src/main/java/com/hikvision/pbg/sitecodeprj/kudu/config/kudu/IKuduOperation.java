package com.hikvision.pbg.sitecodeprj.kudu.config.kudu;

import org.apache.kudu.client.Operation;

public interface IKuduOperation {
    Operation getOperation();
}
