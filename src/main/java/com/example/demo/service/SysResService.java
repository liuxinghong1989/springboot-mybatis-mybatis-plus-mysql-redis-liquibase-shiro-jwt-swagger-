package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.SysRes;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface SysResService extends IService<SysRes> {


    /**
     * 根据 Ids 查询
     * @param permissionIds ids
     * @return  权限List
     */
    List<SysRes> selectByIds(List<Integer> permissionIds);

    /**
     * 根据角色查询菜单
     * @param roleIds 角色主键S
     * @return
     */
    List<SysRes> findMenuByRoleCode(List<String> roleIds);

    /**
     * 获取菜单树形结构
     * @param pId
     * @param list
     * @return
     */
    List<SysRes> treeMenuList(String pId, List<SysRes> list);
}
