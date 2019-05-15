package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.SysRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Mapper
public interface SysResMapper extends BaseMapper<SysRes> {

    List<SysRes> findMenuByRoleCode(@Param("roleCode") List<String> roleCode);

}
