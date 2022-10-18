package com.hikvision.pbg.sitecodeprj.human.service;

import java.util.List;

public interface HumanFlagService {

    /**
     * 批量删除标签
     *
     * @param flagList
     */
    void deleteBatch(List<String> flagList);
}
