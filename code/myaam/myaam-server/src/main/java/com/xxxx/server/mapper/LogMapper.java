package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.LogBean;
import org.springframework.web.bind.annotation.RestController;

@RestController

public interface LogMapper extends BaseMapper<LogBean> {
    IPage<LogBean> getLogByPage(Page<LogBean> page);


}
