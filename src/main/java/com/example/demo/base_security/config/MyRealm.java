package com.example.demo.base_security.config;

import com.example.demo.base_model.entity.SysRes;
import com.example.demo.base_model.entity.SysUser;
import com.example.demo.base_model.entity.SysUserRole;
import com.example.demo.base_model.service.SysResService;
import com.example.demo.base_model.service.SysRoleService;
import com.example.demo.base_model.service.SysUserRoleService;
import com.example.demo.base_model.service.SysUserService;
import com.example.demo.base_security.commons.Constant;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.base_security.util.JWTUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liugh
 * @since 2018-05-03
 */
@Configuration
public class MyRealm extends AuthorizingRealm {
    private SysUserService sysUserService;
    private SysUserRoleService userToRoleService;
    private SysResService menuService;
    private SysRoleService roleService;

    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        MyRealm.redisTemplate = redisTemplate;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        if (userToRoleService == null) {
//            this.userToRoleService = SpringContextBeanService.getBean(SysUserRoleService.class);
//        }
//        if (menuService == null) {
//            this.menuService = SpringContextBeanService.getBean(SysResService.class);
//        }
//        if (roleService == null) {
//            this.roleService = SpringContextBeanService.getBean(SysRoleService.class);
//        }
//
//        String userId = JWTUtil.getUserId(principals.toString());
//        SysUser user = sysUserService.getById(userId);
//        List<SysUserRole> roleList = userToRoleService.selectByUserId(user.getId());
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        /*
//        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
//        //添加控制角色级别的权限
//        Set<String> roleNameSet = new HashSet<>();
//        roleNameSet.add(role.getRoleName());
//        simpleAuthorizationInfo.addRoles(roleNameSet);
//        */
//        //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
//        ArrayList<String> pers = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(roleList)){
//            List<String> roleIds = roleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
//            List<SysRes> menuList = menuService.findMenuByRoleCode(roleIds);
//            for (SysRes per : menuList) {
//                if (!ComUtil.isEmpty(per.getId())) {
//                    pers.add(String.valueOf(per.getId()));
//                }
//            }
//
//        }
//        Set<String> permission = new HashSet<>(pers);
//        simpleAuthorizationInfo.addStringPermissions(permission);
//        return simpleAuthorizationInfo;
//}

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (sysUserService == null) {
            this.sysUserService = SpringContextBeanService.getBean(SysUserService.class);
        }
        String token = (String) auth.getCredentials();
        if(Constant.isPass){
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和redis和数据库进行对比
        String userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new UnauthorizedException("token invalid");
        }
        //实现单点登录
        Object token1 = redisTemplate.opsForValue().get("token--"+userId);
        if (null !=token1 && StringUtils.isNotBlank(String.valueOf(token1))){
           if (!token.equals(token1)){
               throw new UnauthorizedException("登录重复，请重新登录");
           }
        }else {
            throw new UnauthorizedException("登录已过期，请重新登录");
        }
        SysUser userBean = sysUserService.getById(userId);
        if (userBean == null) {
            throw new UnauthorizedException("该用户不存在");
        }
        if (! JWTUtil.verify(token, userId,userBean.getLoginName(), userBean.getPwd())) {
            throw new UnauthorizedException("用户名或者密码错误！");
        }
        //更新redis里面的过期时间
        JWTUtil.saveRedis(userBean.getId(),token);
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
