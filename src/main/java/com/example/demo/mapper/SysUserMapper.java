package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> selectByParam(Page<SysUser> pageInfo);
}
