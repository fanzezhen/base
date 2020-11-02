package com.github.fanzezhen.base.logbiz.foundation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fanzezhen.base.logbiz.foundation.entity.LogOperation;
import com.github.fanzezhen.base.logbiz.foundation.mapper.LogOperationMapper;
import com.github.fanzezhen.base.logbiz.foundation.service.ILogOperationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author fanzezhen
 * @since 2020-06-11
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements ILogOperationService {

}
