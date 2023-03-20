package com.xxxx.server.service;

import com.xxxx.server.pojo.RespPageBean;

public interface LogService {

    RespPageBean getLogByPage(Integer currentPage, Integer size);
}
