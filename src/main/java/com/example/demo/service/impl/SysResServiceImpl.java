package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.base_security.commons.Constant;
import com.example.demo.entity.SysRes;
import com.example.demo.mapper.SysResMapper;
import com.example.demo.service.SysResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
@Transactional
public class SysResServiceImpl extends ServiceImpl<SysResMapper, SysRes> implements SysResService {

    @Autowired
    private SysResMapper menuMapper;

    @Override
    //redis方法级别的缓存，需要做缓存打开改注解即可
    //@Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public List<SysRes> selectByIds(List<Integer> permissionIds) {
        QueryWrapper<SysRes> ew = new QueryWrapper<>();
        ew.in("id", permissionIds);
        return this.list(ew);
    }

    @Override
//    @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public List<SysRes> findMenuByRoleCode(List<String> roleIds) {
        return menuMapper.findMenuByRoleCode(roleIds);
    }

    @Override
    public  List<SysRes> treeMenuList(String pId, List<SysRes> list) {
        List<SysRes> IteratorMenuList = new ArrayList<>();
        for (SysRes m : list) {
            if (String.valueOf(m.getPid()).equals(pId)) {
                List<SysRes> childMenuList = treeMenuList(String.valueOf(m.getId()), list);
                m.setChildRes(childMenuList);
                if(m.getType() == Constant.TYPE_MENU){
                    IteratorMenuList.add(m);
                }
            }
        }
        return IteratorMenuList;
    }
}
