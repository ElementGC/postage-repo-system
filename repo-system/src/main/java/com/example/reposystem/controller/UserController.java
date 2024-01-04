package com.example.reposystem.controller;

import com.example.reposystem.basicentity.Result;
import com.example.reposystem.basicentity.User;
import com.example.reposystem.service.UserService;
import com.example.reposystem.utils.JwtUtil;
import com.example.reposystem.utils.Md5Util;
import com.example.reposystem.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password){
        /*注册中的核验要求
        * 1. 保证用户名唯一
        * 2. 保证用户名和密码的格式符合规范（后续在前端里面看看能不能提前拦截，不过这里也要有一定的校验）
        * 若符合要求，则将新的用户名和密码加入到数据库中，并跳转到登录页面
        * */
        //查询前端传过来的用户名是否已被占用
        User u = userService.findByUserName(username);
        if (u == null){
            //未被占用，插入数据完成注册操作并返回成功
            userService.register(username,password);
            return Result.success("注册成功");
        }else {
            //已被占用，返回失败及其原因
            return Result.error("用户名已被占用");
        }


    }

    //登录
    @PostMapping("/login")
    //返回值类型的<String>结合Result实体类来看，对应的是返回的data部分的数据类型
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$")String password){

        //由于经常忘记密码就开了个后门
        if(username.equals("Ele001")){
            Map<String,Object> claims = new HashMap<>();
            //令牌中包含的信息，一般就主键id和用户名就够了，不需要放密码
            claims.put("id",username);
            claims.put("username",password);
            String token = JwtUtil.genToken(claims);
            //返回令牌
            return Result.success("登陆成功",token);
        }
        /*登录过程的处理逻辑
        * 1. 先查看数据库中是否存在该用户名
        * 2. 核对数据中的密码与前端发送过来的密码是否一致
        * 2.1 由于数据库中的密码已经进行过Md5加密过了，所以获得的密码也要经历一次Md5加密*/
        /*一组用户名和密码
        * Hello
        * WOrld
        * */
        //查询用户
        User loginu = userService.findByUserName(username);
        if (loginu == null){
            //查询到用户名为空，即不存在当前用户名，返回错误
            return Result.error("用户名错误");
        }

        //先对传过来的明文密码进行加密在比对
        if (Md5Util.getMD5String(password).equals(loginu.getPassword())){
            //相同，登陆成功
            //生成令牌
            Map<String,Object> claims = new HashMap<>();
            //令牌中包含的信息，一般就主键id和用户名就够了，不需要放密码
            claims.put("id",loginu.getId());
            claims.put("username",loginu.getUsername());
            String token = JwtUtil.genToken(claims);
            //返回令牌
            return Result.success("登陆成功",token);
        }else return Result.error("密码错误");
    }

    @GetMapping("/info")
    //获取用户信息不需要前端传入参数，只需要通过请求头的令牌信息就可以了
    public Result<User> userinfo(@RequestHeader(name = "Authorization")String token){
        Map<String ,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.findByUserName(username);
        return Result.success("获取用户信息成功",u);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User u){
        userService.update(u);
        return Result.success("用户信息更新成功");
    }

    @PatchMapping("/ava-update")
    public Result avaupdate(@RequestParam @URL String avatarUrl){
        userService.avaupdate(avatarUrl);
        return Result.success("用户头像更新成功");

    }

    @PatchMapping("/pwd-update")
    public Result pwdupdate(@RequestBody Map<String ,String> pwds){
        /*在这一步同样需要校验参数，即核对一下前端发来的密码文本，步骤如下
        * 1. 比对旧密码是否输入正确
        * 2. 比对两个新密码（new_pwd和re_pwd）是否相同
        * 3. 比对新密码和旧密码是否重复
        * 都确认无误之后，才能将数据库中的密码进行更新，同时应该退出登录并将请求头的jwt令牌进行处理（撤换掉）*/
        //校验参数
        //获取参数
        String oldPwd = pwds.get("old_pwd");
        String newPwd = pwds.get("new_pwd");
        String rePwd = pwds.get("re_pwd");
        //要想获取原密码，需要从请求头中入手，但是请求头只有id和用户名，所以还得进行一次查询
        Map<String ,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        String pwd = userService.findByUserName(username).getPassword();
        //检测非空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd) ){
            return Result.error("存在空输入，请核验");
        }
        //检测新旧密码是否一致
        if (newPwd.equals(oldPwd)){
            return Result.error("新密码不能与旧密码相同，请确认后输入");
        }
        //检测旧密码,由于原密码是加密存储的，所以新密码也要加密之后再比对
        if(!pwd.equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("旧密码错误，请重新输入");
        }
        //检测两次的新密码是否一致
        if (!newPwd.equals(rePwd)){
            return Result.error("两次填写的新密码不一致，请确认后输入");
        }

        //全过了，放心更新
        userService.pwdupdate(newPwd);
        return Result.success("密码更新成功");


    }
}
