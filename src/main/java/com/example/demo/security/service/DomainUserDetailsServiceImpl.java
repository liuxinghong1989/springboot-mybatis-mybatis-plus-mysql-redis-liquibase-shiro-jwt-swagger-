package com.example.demo.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.security.Dto.UserInfoDTO;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


/**
 * @author zhuyanwei
 */
@Service("userDetailsService")
public class DomainUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInfo = (User)authentication.getPrincipal();


        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",username);
        List<SysUser> userList = sysUserMapper.selectList(wrapper);
        if(userList == null || userList.isEmpty()) {
            throw new UsernameNotFoundException("用户" + username + "不存在!");
        }

        if(userList.size() > 1) {
            throw new UsernameNotFoundException("用户" + username + "不唯一!");
        }

        SysUser user = userList.get(0);

//        List<AuthMarkingDTO> auths = sysUserMapper.findAuthMarkingByUserId(user.getId());
        Set<GrantedAuthority> authorities = Sets.newHashSet();
//        if(auths != null && !auths.isEmpty()) {
//            for(AuthMarkingDTO authMarking : auths) {
//                authorities.add(new SimpleGrantedAuthority(authMarking.getAuthRole()));
//                authorities.add(new SimpleGrantedAuthority(authMarking.getPermission()));
//            }
//        }

        /**
         * 增加系统用户登陆日志
         */
        //this.addSysLoginLog(user.getLoginName());
        return new UserInfoDTO(String.valueOf(user.getId()),user.getName(),user.getLoginName(),user.getPwd(),authorities);
    }

    /**
     * 添加系统用户登陆日志
     * @param
     */
//    private void addSysLoginLog(String user){
//
//        SysLog sysLog = new SysLog();
//        Date date = new Date();
//        String ip = ClientRequestInfoUtil.getClientIp(request);
//        String browser = ClientRequestInfoUtil.getBrowser(request);
//        String area = "";
//        if(!ip.isEmpty()){
//            area = ClientRequestInfoUtil.getIpInfo(ip);
//        }
//
//        sysLog.setId(IdWorker.getId());
//        sysLog.setCreateUser(user);
//        sysLog.setAdr(ip);
//        sysLog.setDesc(browser);
//        sysLog.setCreateTime(date);
//        sysLog.setStatus(1);
//        sysLog.setRemark(area);
//        sysLog.setType(5);
//        sysLogService.insert(sysLog);
//    }



}
