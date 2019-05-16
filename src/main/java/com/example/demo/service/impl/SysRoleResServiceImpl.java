package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.SysRoleRes;
import com.example.demo.mapper.SysRoleResMapper;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.service.SysRoleResService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
public class SysRoleResServiceImpl extends ServiceImpl<SysRoleResMapper, SysRoleRes> implements SysRoleResService {

    @Override
    //redis生成key注解，以类名方法名和参数组成key
//    @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public List<SysRoleRes> selectByRoleCode(String roleCode) {
        QueryWrapper<SysRoleRes> ew = new QueryWrapper<>();
        ew.eq("role_id", roleCode);
        return this.list(ew);
    }

    @Override
    public boolean saveAll(String roleCode, List<String> menuCodes) {
        boolean result = true;
        if (!ComUtil.isEmpty(menuCodes)) {
            List<SysRoleRes> modelList = new ArrayList<>();
            for (String menuCode : menuCodes) {
                modelList.add(SysRoleRes.builder().id(IdWorker.getIdStr()).roleId(roleCode).resId(menuCode).build());
            }
            result = this.saveBatch(modelList);
        }
        return result;
    }

    @Override
    public boolean deleteAllByRoleCode(String roleCode) {
        QueryWrapper<SysRoleRes> ew = new QueryWrapper<>();
        ew.eq("role_id", roleCode);
        return this.remove(ew);
    }
}
