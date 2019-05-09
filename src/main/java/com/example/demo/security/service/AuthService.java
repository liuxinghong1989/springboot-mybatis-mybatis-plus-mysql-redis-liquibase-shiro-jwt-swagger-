package com.example.demo.security.service;

/**
 * @author liuxinghong
 * @Description:
 * @date 2018/6/27/02718:28
 */
public interface AuthService {

   void cleanToken(String mobile, String loginType);
}
