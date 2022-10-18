package com.hikvision.myproject;

import com.alibaba.fastjson.JSON;
import com.hikvision.myproject.mybatis.MyWebApp;
import com.hikvision.myproject.mybatis.dto.CameraDelayDto;
import com.hikvision.myproject.mybatis.entity.CdDCameraDelay;
import com.hikvision.myproject.mybatis.mapper.CdDCameraDelayMapper;
import com.hikvision.myproject.mybatis.util.LocalDateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2022/8/2 14:36
 * @Created by huangqiqi
 */
@SpringBootTest(classes = MyWebApp.class)
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    CdDCameraDelayMapper cameraDelayMapper;

    @Test
    public void insert() {
        //插入
        CdDCameraDelay cameraDelay = CdDCameraDelay.builder()
//                .id("11111")
                .name("测试111").cre_time(LocalDateTimeUtils.covertLdt2TimeStamp(LocalDateTime.now())).face_today_pass(100).prtday(20220802)
                .build();
        CdDCameraDelay cameraDelay2 = CdDCameraDelay.builder()
                .id("2222").name("测试222").cre_time(LocalDateTimeUtils.covertLdt2TimeStamp(LocalDateTime.now())).face_today_pass(100).prtday(20220802)
                .build();
        cameraDelayMapper.insert(cameraDelay);

    }

    @Test
    public void select() {
        //查询
        List<CameraDelayDto> result = cameraDelayMapper.queryCameraDelay(CdDCameraDelay.builder().name("测试").prtday(20220802).build());
        System.out.println("查询结果：" + JSON.toJSONString(result));
    }
}
