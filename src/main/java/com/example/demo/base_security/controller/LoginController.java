package com.example.demo.base_security.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base_controller.BaseController;
import com.example.demo.base_security.VO.registerVO;
import com.example.demo.entity.SysUser;
import com.example.demo.base_security.annotation.Log;
import com.example.demo.base_security.annotation.Pass;
import com.example.demo.base_security.annotation.ValidationParam;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  登录接口
 * @author liugh
 * @since 2018-05-03
 */
@RestController
@Api(description="身份认证模块")
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    private SysUserService userService;


    @ApiOperation(value="手机密码登录", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/login")
    @Log(action="SignIn",modelName= "Login",description="前台密码登录接口")
    @Pass
    public Object login(
            @ValidationParam("mobileNo,pwd")@RequestBody JSONObject requestJson) throws Exception{
        return success(userService.checkMobileAndPasswd(requestJson));
    }

    @ApiOperation(value="短信验证码登录", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/login/captcha")
    @Log(action="SignInByCaptcha",modelName= "Login",description="前台短信验证码登录接口")
    @Pass
    public Object loginBycaptcha(
            @ValidationParam("mobile,captcha")@RequestBody JSONObject requestJson) throws Exception{
        return success( userService.checkMobileAndCatcha(requestJson));
    }



    @ApiOperation(value="手机验证码注册", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/register")
    @Log(action="register",modelName= "Login",description="注册接口")
    @Pass
    public Object register(@Validated @RequestBody registerVO vo)throws Exception {
        return success( userService.checkAndRegisterUser(vo));
    }


    @ApiOperation(value="忘记密码", notes="body体参数,不需要Authorization",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"mobile\":\"17765071662\",\"captcha\":\"5780\",</br>" +
                    "\"password\":\"123456\",\"rePassword\":\"123456\"}"
                    , required = true, dataType = "String",paramType="body")
    })
    @PostMapping("/forget/password")
    @Pass
    public Object resetPassWord (@ValidationParam("mobile,captcha,password,rePassword")
                                               @RequestBody JSONObject requestJson ) throws Exception{
        return success(userService.updateForgetPasswd(requestJson));
    }

    /**
     * 检查用户是否注册过
     * @param mobile
     * @return
     * @throws Exception
     */
    @GetMapping("/check/mobile")
    @Pass
    @ApiIgnore
    public Object loginBycaptcha(@RequestParam("mobile") String mobile) throws Exception{
        SysUser user = userService.getUserByMobile(mobile);
        return success(!ComUtil.isEmpty(user));
    }
}
