package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户ID查询人员角色
     * @param id 用户ID
     * @return  结果
     */
    List<SysUserRole> selectByUserNo(String  id);

}
