package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.SysRoleRes;

import java.util.List;

/**
 * <p>
 * 角色资源表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface SysRoleResService extends IService<SysRoleRes> {
        /**
        * 根据角色ID查询
     * @param roleId    角色ID
     * @return  结果集
     */
    List<SysRoleRes> selectByRoleCode(String roleId);

    /**
     * 根据角色、权限集合录入数据
     * @param roleCode 角色ID
     * @param menuCodes 权限集合
     * @return 结果 true/false
     */
    boolean saveAll(String roleCode, List<String> menuCodes);

    boolean deleteAllByRoleCode(String roleCode);

}
