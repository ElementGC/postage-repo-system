package com.example.reposystem.mapper;

import com.example.reposystem.basicentity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);//根据用户名查询用户（通过上面的sql语句注释实现，后面都同）

    @Insert("insert into user(username,password,create_time,update_time)" +
            " value(#{username},#{password},now(),now())")
    void add(String username, String password);//将用户名与密码添加进入数据库，同时调用函数生成数据库本地时间

    @Update("update user set email=#{email}, phone=#{phone}, update_time=now()" +
            " where id=#{id}")
    void update(User u);//数据更新，在更新时间处的数值选择使用方法 now() 获取数据库本地时间

    @Update("update user set user_pic=#{url}, update_time=now()" +
            " where id=#{id}")
    void avaupdae(String url,Integer id);//头像更新,基本和数据更新差不多

    @Update("update user set password=#{password}, update_time=now()" +
            " where id=#{id}")
    void pwdupdate(String password, Integer id);//密码更新,基本和数据更新差不多
}
