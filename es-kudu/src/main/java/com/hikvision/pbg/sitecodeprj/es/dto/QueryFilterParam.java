package com.hikvision.pbg.sitecodeprj.es.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class QueryFilterParam {

    private Integer filterCount;
    private Integer filterDays;
    private Float filterSimilarity;
    private Integer filterDeviceCount;
}