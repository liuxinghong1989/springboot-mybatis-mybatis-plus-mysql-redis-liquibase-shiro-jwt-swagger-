package com.example.demo.base_security.config;

import com.example.demo.base_security.commons.Constant;
import com.example.demo.entity.SysUser;
import com.example.demo.service.*;
import com.example.demo.base_security.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
//        String userNo = JWTUtil.getUserNo(principals.toString());
//        SysUser user = SysUserService.getById(userNo);
//        SysUserRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());
//
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
//        List<SysRes> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
//        for (SysRes per : menuList) {
//             if (!ComUtil.isEmpty(per.getCode())) {
//                  pers.add(String.valueOf(per.getCode()));
//              }
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
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }
        //实现单点登录
        Object token1 = redisTemplate.opsForHash().get("token", userNo);
        if (null !=token1 && StringUtils.isNotBlank(String.valueOf(token1))){
           if (!token.equals(token1)){
               throw new UnauthorizedException("登录重复，请重新登录");
           }
        }
        SysUser userBean = sysUserService.getById(userNo);
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo,userBean.getLoginName(), userBean.getPwd())) {
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
