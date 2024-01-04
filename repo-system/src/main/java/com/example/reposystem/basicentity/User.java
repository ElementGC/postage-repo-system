package com.example.reposystem.basicentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Integer id;//主键ID

    private String username;//用户名

    @JsonIgnore//作为json格式输出时进行忽略
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^[0-9]+\\.?[0-9]*$")//正则表达式，要求满足字符串必须全部是数字
    private String phone;//手机

    @Email
    @NotEmpty
    private String email;//邮箱

    @NotEmpty
    private String userPic;//用户头像地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将读取到的格式转化为指定样式
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
