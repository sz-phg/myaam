package com.xxxx.server.exception;

import com.xxxx.server.pojo.RespBean;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
/**
 * 全局异常处理sql
 */
//@RestControllerAdvice
public class GlobalException {
//    @ExceptionHandler(SQLException.class)
    public RespBean mySQLException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
