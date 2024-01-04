package com.example.reposystem.service;

import com.example.reposystem.basicentity.Good;
import com.example.reposystem.basicentity.PageBean;
import com.example.reposystem.basicentity.Records;
import com.example.reposystem.basicentity.Rule;

import java.util.List;

public interface GoodService {
    //添加商品
    void add(Good good);

    //通过商品名查看是否存在同名商品
    Good findByGoodName(String goodName);

    //通过商品条码查看是否存在同名商品
    Good findByGoodCode(String goodCode);

    //通过商品id获取商品信息
    Good findByGoodId(Integer id);

    //实现更改库存操作
    void alter(Good good,Integer state,Integer num);

    //实现更改商品信息操作
    void update(Good good);

    //条件分页列表查询
    PageBean<Good> list(Integer pageNum, Integer pageSize, String typeId,String goodName);

    PageBean<Records> recordList(Integer pageNum, Integer pageSize, String goodName);

    //删除商品
    void delete(Integer id);

    //为商品添加规则
    void addRule(Integer goodId, Integer typeId, Integer maxNum, Integer minNum, Integer state);

    //获取规则列表
    List<Rule> listRules();

    //更新指定规则
    void updateRule(Rule rule);

    //删除指定规则
    void deleteRule(Integer id);
}
