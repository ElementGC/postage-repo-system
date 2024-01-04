package com.example.reposystem.service.serviceimpl;

import com.example.reposystem.basicentity.User;
import com.example.reposystem.mapper.UserMapper;
import com.example.reposystem.service.UserService;
import com.example.reposystem.utils.Md5Util;
import com.example.reposystem.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //通过用户名查找用户
    @Override
    public User findByUserName(String username) {

        return userMapper.findByUserName(username);
    }

    //用户注册
    @Override
    public void register(String username, String password) {
        //注册前先对密码文本进行加密处理（使用utils中的MD5工具类）
        String encoded = Md5Util.getMD5String(password);
        //将加密之后的密文添加进入数据库中
        userMapper.add(username,encoded);

    }

    //用户信息更新
    @Override
    public void update(User u) {
        /*获取更新时间的方法：
        * 1. 在这一步里面调用 u.setUpdateTime(LocalDateTime.now());
        * 2. 在向数据库进行性数据更新时使用 now() 代替 #{u.UpdateTime} */

        userMapper.update(u);
    }

    //用户头像更新
    @Override
    public void avaupdate(String url) {
        //获取需要更改的用户的id
        Map<String ,Object> map =  ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.avaupdae(url,id);
    }

    //用户密码更新
    @Override
    public void pwdupdate(String newPwd) {
        //获取需要更改的用户的id
        Map<String ,Object> map =  ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //新密码同样需要在加密之后再填入数据库
        String encoded = Md5Util.getMD5String(newPwd);
        userMapper.pwdupdate(encoded,id);
    }
}
