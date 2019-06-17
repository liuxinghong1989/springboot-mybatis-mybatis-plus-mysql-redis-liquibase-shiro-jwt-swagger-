package com.example.demo.base_model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.base_model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
