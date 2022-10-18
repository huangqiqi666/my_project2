package com.hikvision.pbg.sitecodeprj.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageInfo<T> {
    private Long TotalPage;
    private Long TotalNum;
    private int PageSize;
    private int PageNow;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean havePerPage;
    private boolean haveNexPage;
    private List<T> list;

}