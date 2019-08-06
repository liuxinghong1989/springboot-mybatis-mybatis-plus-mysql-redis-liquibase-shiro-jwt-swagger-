package com.example.demo.base_model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.base_model.entity.SysUserRole;
import com.example.demo.base_model.mapper.SysUserRoleMapper;
import com.example.demo.base_model.service.SysUserRoleService;
import com.example.demo.base_security.util.ComUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

      @Override
//    @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
        public List<SysUserRole> selectByUserId(String userId) {
            QueryWrapper<SysUserRole> ew = new QueryWrapper<>();
            ew.eq("user_id", userId);
            List<SysUserRole> userToRoleList = this.list(ew);
            return ComUtil.isEmpty(userToRoleList)? null: userToRoleList;
        }
}
