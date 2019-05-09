package com.example.demo.controller;

import com.example.demo.baseclass.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 *
 * @author lxh
 * @date 2018/2/05
 */
@RestController
public class AuthController extends BaseController {


    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

    public Object getToken(){

        return success();
    }




}
