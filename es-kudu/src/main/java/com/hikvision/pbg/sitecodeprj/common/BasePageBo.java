package com.hikvision.pbg.sitecodeprj.common;

import lombok.Data;

@Data
public class BasePageBo {

    private int pageNum = 1;

    private int pageSize = 10;
}