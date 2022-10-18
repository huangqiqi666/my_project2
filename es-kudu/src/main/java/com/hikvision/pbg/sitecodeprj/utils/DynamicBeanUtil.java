package com.hikvision.pbg.sitecodeprj.utils;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.CaseFormat;
import com.hikvision.pbg.sitecodeprj.common.DynamicBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DynamicBeanUtil
 * @Description 动态实体生成工具类，xx_xx -> xxXx
 * @Author xiaokai
 * @Date 13:24 2022/9/22
 * @Version 1.0
 **/
public class DynamicBeanUtil {

    public DynamicBeanUtil() {
    }

    /**
     * 生成动态实体
     *
     */
    public static List<?> getDynamicBean(List<Map<String, Object>> maps, Class<?> classz) throws InstantiationException, IllegalAccessException {
        List<Object> list = new ArrayList<>();
        DynamicBean bean = null;
        Object obj = null;
        Map<String, Object> propertyMap = new HashMap<>();
        for (Map<String, Object> map : maps) {
            // 设置类成员属性
            map.forEach((key, value) -> propertyMap.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key),
                    value.getClass()));
            // 生成动态 Bean
            bean = new DynamicBean(propertyMap);
            // 给 Bean 设置值
            for (String s : propertyMap.keySet()) {
                bean.setValue(s, map.get(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s)));
            }
            obj = classz.newInstance();
            obj = BeanUtil.mapToBean(bean.getBeanMap(), classz, true);
            list.add(obj);
        }
        return list;
    }
}
