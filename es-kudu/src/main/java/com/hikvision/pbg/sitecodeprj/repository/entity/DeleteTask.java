package com.hikvision.pbg.sitecodeprj.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName DeleteTask
 * @Description
 * @Author xiaokai
 * @Date 14:20 2022/9/21
 * @Version 1.0
 **/
@Entity
@Table(name = "delete_flag_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 标签编码
    private String flagCode;

    //删除状态。0：待删除 1：已删除
    private Integer isDelete;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String creater;

    public DeleteTask(String flagCode, Integer isDelete, Date updateTime, String creater) {
        this.flagCode = flagCode;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
        this.creater = creater;
    }
}
