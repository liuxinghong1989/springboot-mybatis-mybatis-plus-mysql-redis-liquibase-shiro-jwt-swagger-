package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.base_security.VO.registerVO;
import com.example.demo.base_security.commons.Constant;
import com.example.demo.entity.SysRes;
import com.example.demo.entity.SysUser;
import com.example.demo.entity.SysUserRole;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.base_security.commons.PublicResultConstant;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.base_security.util.JWTUtil;
import com.example.demo.base_security.util.StringUtil;
import com.example.demo.service.SysResService;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.SysUserRoleService;
import com.example.demo.service.SysUserService;
import com.example.demo.utils.CurrentUserUtils;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
@Slf4j
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${defaultPwd}")
    private String defaultPwd;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysUserRoleService userToRoleService;
    @Autowired
    private SysResService menuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService roleService;

    @Override
    public Page<SysUser> pageList(Integer pageNO, Integer pageSize) {
        Page<SysUser> pageInfo = new Page<>(pageNO, pageSize);//创建分页
        List<SysUser> list = userMapper.selectByParam(pageInfo);
        pageInfo.setRecords(list);
        SysUser user = CurrentUserUtils.getUser();

        return pageInfo;

    }

    @Override
    public SysUser getUserByUserName(String username) {
        return null;
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        QueryWrapper<SysUser> ew = new QueryWrapper<>();
        ew.eq("mobile", mobile);
        ew.eq("status", Constant.ENABLE);
        return this.getOne(ew);
    }

    @Override
    public SysUser register(SysUser user) {
        user.setId(IdWorker.getIdStr());
        user.setUserNo(user.getId());
        user.setCreateTime(DateTime.now().toDate());
        user.setStatus(Constant.ENABLE);
        user.setCreateTime(DateTime.now().toDate());
        user.setUpdateTime(DateTime.now().toDate());
        boolean result = this.save(user);
//        if (result) {
//            UserToRole userToRole  = UserToRole.builder().userNo(user.getUserNo()).roleCode(roleCode).build();
//            userToRoleService.insert(userToRole);
//        }
        return user;
    }

    @Override
    public Map<String, Object> getLoginUserAndMenuInfo(SysUser user) {
        Map<String, Object> result = new HashMap<>();
        List<SysUserRole> userToRoleList = sysUserRoleService.selectByUserNo(user.getId());
        String token = JWTUtil.sign(user.getId(),user.getLoginName(), user.getPwd());
        result.put("token", token);
        result.put("user", user);
        List<SysRes> buttonList = new ArrayList<>();
        //根据角色主键查询启用的菜单权限
        if (CollectionUtils.isNotEmpty(userToRoleList)) {
            List<String> roleIds = userToRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
            List<SysRes> menuList = menuService.findMenuByRoleCode(roleIds);
            //去重
            if (CollectionUtils.isNotEmpty(menuList)) {
                HashSet<String> ids = Sets.newHashSet();
                menuList.forEach(item -> {
                    ids.add(item.getId());
                });
                List<SysRes> sysRes = menuService.listByIds(ids).stream().collect(Collectors.toList());
                List<SysRes> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, sysRes);
                for (SysRes buttonMenu : menuList) {
                    if (buttonMenu.getType() == Constant.TYPE_BUTTON) {
                        buttonList.add(buttonMenu);
                    }
                }
                result.put("menuList", retMenuList);
                result.put("buttonList", buttonList);
            }
        }
        return result;
    }

    @Override
    public void deleteByUserNo(String userId) throws Exception {
        SysUser user = this.getById(userId);
        if (ComUtil.isEmpty(user)) {
            throw new RuntimeException(PublicResultConstant.INVALID_USER);
        }
        //todo: 暂未实现
    }

    @Override
    public Page<SysUser> selectPageByConditionUser(Page<SysUser> userPage, String info, Integer[] status, String startTime, String endTime) {
        return null;
    }

    @Override
    public Map<String, Object> checkMobileAndPasswd(JSONObject requestJson) throws Exception {
        String mobileNo = String.valueOf(requestJson.get("mobileNo"));
        String pwd = String.valueOf(requestJson.get("pwd"));
        if (StringUtils.isBlank(mobileNo) || StringUtils.isBlank(pwd)) {
            throw new RuntimeException("入参不能为空！");
        }
        if (!StringUtil.checkMobileNumber(mobileNo)) {
            throw new RuntimeException(PublicResultConstant.MOBILE_ERROR);
        }
        SysUser user = this.getOne(new QueryWrapper<SysUser>().eq("mobile_no", mobileNo).eq("status", 1));
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(requestJson.getString("pwd"), user.getPwd())) {
            throw new RuntimeException(PublicResultConstant.INVALID_USERNAME_PASSWORD);
        }
        return this.getLoginUserAndMenuInfo(user);
    }

    @Override
    public Map<String, Object> checkMobileAndCatcha(JSONObject requestJson) throws Exception {
        String mobile = requestJson.getString("mobile");
        if (!StringUtil.checkMobileNumber(mobile)) {
            throw new RuntimeException(PublicResultConstant.MOBILE_ERROR);
        }
        SysUser user = this.getUserByMobile(mobile);
        //如果不是启用的状态
        if (!ComUtil.isEmpty(user) && user.getStatus() != Constant.ENABLE) {
            throw new RuntimeException("该用户状态不是启用的!");
        }
        //todo:验证码校验 暂未处理
//        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,
//                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.AUTH.name()));
//        if(ComUtil.isEmpty(smsVerifies)){
//            throw new RuntimeException(PublicResultConstant.VERIFY_PARAM_ERROR);
//        }
//        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
//            throw new RuntimeException(PublicResultConstant.VERIFY_PARAM_PASS);
//        }
        if (ComUtil.isEmpty(user)) {
            //设置默认密码
            SysUser userRegister = SysUser.builder().pwd(BCrypt.hashpw(defaultPwd, BCrypt.gensalt()))
                    .mobileNo(mobile).loginName(mobile).build();
            user = this.register(userRegister);
        }
        return this.getLoginUserAndMenuInfo(user);
    }

    @Override
    public SysUser checkAndRegisterUser(registerVO vo) throws Exception {
        //可直接转为java对象,简化操作,不用再set一个个属性
        SysUser userRegister = new SysUser();
        BeanUtils.copyProperties(vo, userRegister);
        //todo：验证码相关逻辑暂未处理

//        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(userRegister.getMobile(),
//                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.REG.name()));
//        if(ComUtil.isEmpty(smsVerifies)){
//            throw new BusinessException(PublicResultConstant.VERIFY_PARAM_ERROR);
//        }
        //验证码是否过期
//        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
//            throw new BusinessException(PublicResultConstant.VERIFY_PARAM_PASS);
//        }
        userRegister.setPwd(BCrypt.hashpw(vo.getPwd(), BCrypt.gensalt()));
        //默认注册普通用户
        return this.register(userRegister);
    }

    @Override
    public SysUser updateForgetPasswd(JSONObject requestJson) throws Exception {
        String mobile = requestJson.getString("mobile");
        if (!StringUtil.checkMobileNumber(mobile)) {
            throw new RuntimeException(PublicResultConstant.MOBILE_ERROR);
        }
        if (!requestJson.getString("password").equals(requestJson.getString("rePassword"))) {
            throw new RuntimeException(PublicResultConstant.INVALID_RE_PASSWORD);
        }
        SysUser user = this.getUserByMobile(mobile);
        roleService.getRoleIsAdminByUserNo(user.getUserNo());
        if (ComUtil.isEmpty(user)) {
            throw new RuntimeException(PublicResultConstant.INVALID_USER);
        }
        //todo：短信验证码校验暂未设置
//        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,
//                requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.FINDPASSWORD.name()));
//        if(ComUtil.isEmpty(smsVerifies)){
//            throw new BusinessException(PublicResultConstant.VERIFY_PARAM_ERROR);
//        }
//        if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
//            throw new BusinessException(PublicResultConstant.VERIFY_PARAM_PASS);
//        }
        user.setPwd(BCrypt.hashpw(requestJson.getString("password"), BCrypt.gensalt()));
        this.updateById(user);
        return user;
    }

    @Override
    public void resetMobile(SysUser currentUser, JSONObject requestJson) throws Exception {
        String newMobile = requestJson.getString("newMobile");
        if (!StringUtil.checkMobileNumber(newMobile)) {
            throw new RuntimeException(PublicResultConstant.MOBILE_ERROR);
        }
        /**todo: 短信验证码 暂未处置
         List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(newMobile,
         requestJson.getString("captcha"), SmsSendUtil.SMSType.getType(SmsSendUtil.SMSType.MODIFYINFO.name()));
         if(ComUtil.isEmpty(smsVerifies)){
         throw  new RuntimeException(PublicResultConstant.VERIFY_PARAM_ERROR);
         }
         if(SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())){
         throw  new RuntimeException(PublicResultConstant.VERIFY_PARAM_PASS);
         }
         **/
        currentUser.setMobileNo(newMobile);
        this.updateById(currentUser);
    }

    @Override
    public void resetPassWord(SysUser currentUser, JSONObject requestJson) throws Exception {
        if (!requestJson.getString("password").equals(requestJson.getString("rePassword"))) {
            throw new RuntimeException(PublicResultConstant.INVALID_RE_PASSWORD);
        }
        if (!BCrypt.checkpw(requestJson.getString("oldPassword"), currentUser.getPwd())) {
            throw new RuntimeException(PublicResultConstant.INVALID_USERNAME_PASSWORD);
        }
        currentUser.setPwd(BCrypt.hashpw(requestJson.getString("password"), BCrypt.gensalt()));
        this.updateById(currentUser);
    }

    @Override
    public boolean loginOut(String userId) {
         stringRedisTemplate.opsForHash().delete("token", userId);
        return true;
    }
}
