package com.example.demo.base_security.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base_controller.BaseController;
import com.example.demo.base_security.VO.registerVO;
import com.example.demo.base_security.captha.CaptchaClient;
import com.example.demo.base_security.captha.bean.CaptchaBean;
import com.example.demo.entity.SysUser;
import com.example.demo.base_security.annotation.Log;
import com.example.demo.base_security.annotation.Pass;
import com.example.demo.base_security.annotation.ValidationParam;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.service.SysUserService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CaptchaClient captchaClient;


    @ApiOperation(value="手机密码登录", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/login")
    @Log(action="SignIn",modelName= "Login",description="前台密码登录接口")
    public Object login(
            @ValidationParam("mobileNo,pwd")@RequestBody JSONObject requestJson) throws Exception{
        return success(userService.checkMobileAndPasswd(requestJson));
    }

    @ApiOperation(value="短信验证码登录", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/login/captcha")
    @Log(action="SignInByCaptcha",modelName= "Login",description="前台短信验证码登录接口")
    public Object loginBycaptcha(
            @ValidationParam("mobile,captcha")@RequestBody JSONObject requestJson) throws Exception{
        return success( userService.checkMobileAndCatcha(requestJson));
    }

    @ApiOperation(value="手机验证码注册", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/register")
    @Log(action="register",modelName= "Login",description="注册接口")
    public Object register(@Validated @RequestBody registerVO vo)throws Exception {
        return success( userService.checkAndRegisterUser(vo));
    }


    @ApiOperation(value="忘记密码", notes="body体参数,不需要Authorization",produces = "application/json")
    @PostMapping("/forget/password")
    public Object resetPassWord (@RequestBody JSONObject requestJson ) throws Exception{
        return success(userService.updateForgetPasswd(requestJson));
    }
    /**
     * 检查用户是否注册过
     * @param mobile
     * @return
     * @throws Exception
     */
    @GetMapping("/check/mobile")
    @ApiIgnore
    public Object loginBycaptcha(@RequestParam("mobile") String mobile) throws Exception{
        SysUser user = userService.getUserByMobile(mobile);
        return success(!ComUtil.isEmpty(user));
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/loginOut")
    public Object loginOut(@RequestParam("userId") String userId, HttpServletRequest request){
        //将将当前请求头设置为null
        request.setAttribute("currentUser",null);
        return success(userService.loginOut(userId));
    }


    @GetMapping("/captcha")
    @ApiOperation(value="获取验证码图片", notes="")
    public Object captcha() {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CaptchaBean captchaBean = captchaClient.generate();
            String capText = captchaBean.getResult();
            String uuid = UUID.randomUUID().toString();
            stringRedisTemplate.boundValueOps(uuid).set(capText,60, TimeUnit.SECONDS);//验证码过期时间60秒
            BufferedImage bi = captchaBean.getBufferedImage();
            ImageIO.write(bi, "jpg", baos);
            String imgBase64 = Base64.encodeBase64String(baos.toByteArray());

            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Origin","*")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                            ImmutableMap.of("data", ImmutableMap.of(uuid,"data:image/jpeg;base64,"+imgBase64)
                                    ,"code", 200
                                    ,"msg", "成功！"
                                    ,"timestamp", System.currentTimeMillis())
                    );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
