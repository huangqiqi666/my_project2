package com.hikvision.pbg.sitecodeprj.utils;

import com.hikvision.pbg.sitecodeprj.common.PageInfo;

import java.util.List;

/**
 * @Classname JpaPageHelper
 * @Description 分页插件
 * @Date 2021/6/18 17:21
 * @Created by xiaokai5
 */
public class JpaPageHelper<T> {
    public PageInfo SetStartPage(List<T> list, int pageNow, int Size) {
        if (null == list) return new PageInfo();
        boolean isFirstPage = false;
        boolean isLastPage = false;
        boolean haveNexPage = false;
        boolean havePerPage = false;
        int pageSize = 0;
        int fromIndex = (pageNow - 1) * Size;
        int toIndex = pageNow * Size;
        if (fromIndex == 0) {
            isFirstPage = true;
        } else {
            havePerPage = true;
        }
        if (toIndex >= list.size()) {
            toIndex = list.size();
            isLastPage = true;
        } else {
            haveNexPage = true;
        }
        if (list.size() % Size == 0) {
            pageSize = list.size() / Size;
        } else {
            pageSize = list.size() / Size + 1;
        }
        PageInfo<T> pageInfo = new PageInfo<T>();
        pageInfo.setPageNow(pageNow);
        pageInfo.setTotalPage((long) pageSize);
        pageInfo.setPageSize(Size);
        pageInfo.setTotalNum((long) list.size());
        pageInfo.setFirstPage(isFirstPage);
        pageInfo.setLastPage(isLastPage);
        pageInfo.setHaveNexPage(haveNexPage);
        pageInfo.setHavePerPage(havePerPage);
        pageInfo.setList(list.subList(fromIndex, toIndex));
        return pageInfo;
    }
}