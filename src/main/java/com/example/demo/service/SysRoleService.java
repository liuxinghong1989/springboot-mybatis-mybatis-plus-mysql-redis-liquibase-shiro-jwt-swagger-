package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.base_security.VO.RoleAddVO;
import com.example.demo.base_security.VO.RoleUpdateVO;
import com.example.demo.entity.SysRole;

import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增角色以及角色权限信息
     * @param roleModel 角色以及角色权限信息
     * @return 新增结果 true/false
     */
    boolean addRoleAndPermission(RoleAddVO roleModel)throws Exception;

    /**
     * 更新色以及角色权限信息
     * @param roleModel
     * @return
     * @throws Exception
     */
    boolean updateRoleInfo(RoleUpdateVO roleModel)throws Exception;

    /**
     * 通过角色ID获取菜单列表
     * @param roleId
     * @return
     */
    Map<String,Object> getMenuByRoleCode(String roleId);

    /**
     * 判断当前是否是超级管理员,如果是,不能修改信息 抛出异常
     * @param userNo
     * @throws Exception
     */
    void getRoleIsAdminByUserNo(String userNo) throws Exception;

    Map<String, Object> selectByRoleCode(String roleCode)throws Exception;

    void deleteByRoleCode(String roleCode)throws Exception;
}
