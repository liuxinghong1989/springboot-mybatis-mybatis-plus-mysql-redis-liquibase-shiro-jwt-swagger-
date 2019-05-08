package com.example.demo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;

import java.util.List;

/**
 * <p>
 * app用户表 服务类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public interface UserService extends IService<User> {

    Page<User> pageList(Integer pageNO, Integer pageSize);
}
