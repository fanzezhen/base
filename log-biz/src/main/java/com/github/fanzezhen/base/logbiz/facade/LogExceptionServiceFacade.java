package com.github.fanzezhen.base.logbiz.facade;

import com.github.fanzezhen.base.logbiz.model.dto.LogExceptionDto;

/**
 * @author zezhen.fan
 */
public interface LogExceptionServiceFacade {
    /**
     * 增
     *
     * @param logExceptionDto 异常日志信息
     * @return 布尔
     */
    boolean add(LogExceptionDto logExceptionDto);
}
