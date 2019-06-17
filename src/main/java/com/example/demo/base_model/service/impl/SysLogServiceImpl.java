package com.example.demo.base_model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.base_model.entity.SysLog;
import com.example.demo.base_model.mapper.SysLogMapper;
import com.example.demo.base_model.service.SysLogService;
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
