package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.demo.VO.USerAddVO;
import com.example.demo.base_controller.BaseController;
import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Controller
@RequestMapping("/sysUser")
@Api("用户管理")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService userService;

    @PostMapping("/add")
    @ApiOperation(value="添加用户", notes="添加用户")
    public Object add(@RequestBody @Validated USerAddVO vo){
        SysUser user = new SysUser();
        user.setId(IdWorker.getIdStr());
        user.setPwd(BCrypt.hashpw(vo.getPwd(), BCrypt.gensalt()));
        BeanUtils.copyProperties(vo,user);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new SysUser());
        return success(userService.save(user));
    }

    @GetMapping("/list")
    @ApiOperation(value="用户列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNO", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "Integer")
    })
    public  Object list(@RequestParam("pageNO") Integer pageNO, @RequestParam("pageSize") Integer pageSize){
        return success(userService.pageList(pageNO,pageSize));
    }

}

