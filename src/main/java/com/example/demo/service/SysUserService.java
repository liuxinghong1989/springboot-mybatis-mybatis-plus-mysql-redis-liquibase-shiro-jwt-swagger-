package com.example.demo.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.base_security.VO.registerVO;
import com.example.demo.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface SysUserService extends IService<SysUser> {


    Page<SysUser> pageList(Integer pageNO, Integer pageSize);
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    SysUser getUserByUserName(String username);

    SysUser getUserByMobile(String mobile);

    /**
     * 注册用户
     * @param user
     * @return
     */
    SysUser register(SysUser user);

    Map<String, Object> getLoginUserAndMenuInfo(SysUser user);

    void deleteByUserNo(String userId)throws Exception;

    Page<SysUser> selectPageByConditionUser(Page<SysUser> userPage, String info, Integer[] status, String startTime, String endTime);

    Map<String,Object> checkMobileAndPasswd(JSONObject requestJson)throws Exception;

    Map<String,Object> checkMobileAndCatcha(JSONObject requestJson)throws Exception;

    SysUser checkAndRegisterUser(registerVO vo)throws Exception;

    SysUser updateForgetPasswd(JSONObject requestJson)throws Exception;

    void resetMobile(SysUser currentUser, JSONObject requestJson)throws Exception;

    void resetPassWord(SysUser currentUser, JSONObject requestJson)throws Exception;

    boolean loginOut(String userId);

}
