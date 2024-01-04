package com.example.reposystem.service;

import com.example.reposystem.basicentity.Demand;

import java.util.List;

public interface DemandService {
    Demand findByGoodName(String goodName);//通过商品名查找历史需求

    void add(Demand demand);//添加商品历史需求

    List<Demand> list();//获取历史需求数据列表

    void update(Demand demand);//更新指定商品历史需求数据

    void delete(Integer id);//删除指定商品历史需求数据

    Demand findById(Integer id);//通过id查找指定商品历史需求
}
