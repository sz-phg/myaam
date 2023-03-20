package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.mapper.LogMapper;
import com.xxxx.server.pojo.LogBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public RespPageBean getLogByPage(Integer currentPage, Integer size) {
        //开启分页
        Page<LogBean> page = new  Page<>(currentPage,size);
        QueryWrapper<LogBean> eq = new QueryWrapper<LogBean>().eq("username", "admin");
//        System.out.println(logMapper.selectList(eq));
        IPage<LogBean> logByPage = logMapper.getLogByPage(page);
        System.out.println(logByPage);
        RespPageBean respPageBean = new RespPageBean(logByPage.getTotal(),logByPage.getRecords());
        return respPageBean;
    }
}
