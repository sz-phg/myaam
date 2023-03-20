package com.xxxx.server.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@TableName("t_log")
public class LogBean {
    @TableField("Id")
    private int id;
    @TableField("username")
    private String username;
    @TableField("content")
    private String content;
    @TableField("ip")
    private String ip;
    @TableField("method")
    private String method;
    @TableField("time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate time;

    @TableField("exec_time")
    private int execTime;
    @TableField("status")
    private int status;

}
