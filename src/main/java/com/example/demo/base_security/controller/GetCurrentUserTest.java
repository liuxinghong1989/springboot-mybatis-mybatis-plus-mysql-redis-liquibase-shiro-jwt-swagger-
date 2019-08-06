package com.example.demo.base_security.controller;

import com.example.demo.base_model.utils.CurrentUserUtils;
import com.example.demo.commons.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuxinghong
 * @Description:
 * @date 2019/8/6 000615:10
 */
@RestController
@RequestMapping("/test")
public class GetCurrentUserTest extends BaseController {
    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(){
        return success(CurrentUserUtils.getCurrentUser());
    }
}
