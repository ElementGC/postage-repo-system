package com.example.reposystem.service;

import com.example.reposystem.basicentity.Type;

import java.util.List;

public interface TypeService {
    //新增商品种类
    void add(Type type);

    //分类列表查询
    List<Type> list();

    //更新分类信息
    void update(Type t);

    //删除分类
    void delete(Integer id);
}
