package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.demo.VO.USerAddVO;
import com.example.demo.baseclass.BaseController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


/**
 * <p>
 * app用户表 前端控制器
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Controller
@RequestMapping("/user")
@Api("用户管理")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value="添加用户", notes="添加用户")
    public Object add(@RequestBody @Validated USerAddVO vo){
        User user = new User();
        user.setId(IdWorker.getId());
        BeanUtils.copyProperties(vo,user);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new User());
        return success(userService.save(user));
    }

    @GetMapping("/list")
    @ApiOperation(value="用户列表", notes="")
    public  Object list(@RequestParam("pageNO") Integer pageNO,@RequestParam("pageSize") Integer pageSize){
           return success(userService.pageList(pageNO,pageSize));
    }

}

