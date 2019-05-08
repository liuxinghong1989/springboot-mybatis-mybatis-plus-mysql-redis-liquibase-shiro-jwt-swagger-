package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.SysLog;
import com.example.demo.mapper.SysLogMapper;
import com.example.demo.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志信息表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
