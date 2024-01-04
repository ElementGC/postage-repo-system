package com.example.reposystem.service;

import com.example.reposystem.basicentity.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);

    //用户信息更新
    void update(User u);

    //用户头像更新
    void avaupdate(String url);

    //用户密码更新
    void pwdupdate(String newPwd);
}
