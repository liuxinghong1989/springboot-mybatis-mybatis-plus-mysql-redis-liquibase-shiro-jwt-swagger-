package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.base_security.VO.RoleAddVO;
import com.example.demo.base_security.VO.RoleUpdateVO;
import com.example.demo.base_security.commons.Constant;
import com.example.demo.entity.SysRes;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysRoleRes;
import com.example.demo.entity.SysUserRole;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.base_security.commons.PublicResultConstant;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.service.SysResService;
import com.example.demo.service.SysRoleResService;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.SysUserRoleService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleResService roleToMenuService;

    @Autowired
    private SysUserRoleService userToRoleService;

    @Autowired
    private SysResService menuService;

    @Override
    public boolean addRoleAndPermission(RoleAddVO roleModel) throws Exception{
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleModel,role);
        boolean result = this.save(role);
        if (! result) {
            throw  new RuntimeException(PublicResultConstant.UPDATE_ROLEINFO_ERROR);
        }
        result = roleToMenuService.saveAll(role.getId(), roleModel.getMenuIds());
        return result;
    }

    @Override
    public boolean updateRoleInfo(RoleUpdateVO roleModel) throws Exception{
        SysRole role1 = this.getById(roleModel.getId());
        if (Constant.ROLE_TYPE_ADMIN.equals(role1.getType())){
            throw  new RuntimeException(PublicResultConstant.UPDATE_SYSADMIN_INFO_ERROR);
        }
        if (ComUtil.isEmpty(role1)) {
            return false;
        }
        BeanUtils.copyProperties(roleModel,role1);
        role1.setUpdateTime(DateTime.now().toDate());
        boolean result = this.updateById(role1);
        if (! result) {
            throw  new RuntimeException(PublicResultConstant.UPDATE_ROLEINFO_ERROR);
        }
        result = roleToMenuService.remove(new QueryWrapper<SysRoleRes>().eq("role_id",roleModel.getId()));
        if (! result) {
            throw  new RuntimeException("删除权限信息失败");
        }
        result = roleToMenuService.saveAll(role1.getId(), roleModel.getMenuIds());
        if (! result) {
            throw  new RuntimeException("更新权限信息失败");
        }
        return result;

    }

    @Override
    public void getRoleIsAdminByUserNo(String userNo) throws Exception {
        List<SysUserRole> userToRole = userToRoleService.selectByUserNo(userNo);
        if (CollectionUtils.isNotEmpty(userToRole)){
            List<String> ids = userToRole.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
            List<SysRole> role = this.listByIds(ids).stream().collect(Collectors.toList());
            role.forEach(item->{
                if(item.getType().equals(Constant.ROLE_TYPE_ADMIN)){
                    throw new RuntimeException(PublicResultConstant.UPDATE_SYSADMIN_INFO_ERROR);
                }

            });

        }
    }

    @Override
    public Map<String, Object> selectByRoleCode(String roleCode) throws Exception {
        SysRole role = this.getById(roleCode);
        if(ComUtil.isEmpty(role)){
            throw new RuntimeException(PublicResultConstant.INVALID_ROLE);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("role", role);
        //权限信息
        result.put("nodes", this.getMenuByRoleCode(role.getId()));
        return result;
    }

    @Override
    public void deleteByRoleCode(String roleCode) throws Exception {
        if (ComUtil.isEmpty(this.getById(roleCode))) {
            throw new RuntimeException("角色不存在");
        }
        if(!ComUtil.isEmpty(userToRoleService.list(new QueryWrapper<SysUserRole>().eq("role_id",roleCode)))){
            throw new RuntimeException("角色存在相关用户,请先删除相关角色的用户");
        }
        this.remove(new QueryWrapper<SysRole>().eq("role_code",roleCode));
    }


    @Override
    public Map<String, Object> getMenuByRoleCode(String roleCode) {
        Map<String,Object> retMap   =new HashMap<>();
        LinkedList<String> ids = Lists.newLinkedList();
        ids.add(roleCode);
        List<SysRes> menuList = menuService.findMenuByRoleCode(ids);
        List<SysRes> buttonList = new ArrayList<SysRes>();
        List<SysRes> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        for (SysRes buttonMenu : menuList) {
            if(buttonMenu.getType() == Constant.TYPE_BUTTON){
                buttonList.add(buttonMenu);
            }
        }
        retMap.put("menuList",retMenuList);
        retMap.put("buttonList",buttonList);
        return retMap;
    }
}
