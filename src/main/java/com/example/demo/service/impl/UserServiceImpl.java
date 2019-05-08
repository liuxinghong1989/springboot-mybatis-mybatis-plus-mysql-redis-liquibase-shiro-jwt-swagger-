package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * app用户表 服务实现类
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Page<User> pageList(Integer pageNO, Integer pageSize) {
        Page<User> pageInfo = new Page<>(pageNO, pageSize);//创建分页
        List<User> list = userMapper.selectByParam(pageInfo);
        pageInfo.setRecords(list);
        return pageInfo;

    }
}
