package com.xxxx.server.controller;

import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.LogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system/log")
public class LogController {
    @Autowired
    private LogService logService;
    @ApiOperation(value = "获取日志")
    @GetMapping("/")
    public RespPageBean getLogByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size){
        return logService.getLogByPage(currentPage,size);
    }
}
