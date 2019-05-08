package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
