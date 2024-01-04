package com.example.reposystem.mapper;

import com.example.reposystem.basicentity.Demand;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DemandMapper {

    @Select("select * from demand where good_name = #{goodName}")
    Demand findByGoodName(String goodName);//通过商品名查找历史需求

    @Insert("insert into demand(good_name,jul_n,aug_n,sep_n,oct_n,nov_n,dec_n)" +
            " value(#{goodName},#{julN},#{augN},#{sepN},#{octN},#{novN},#{decN})")
    void add(Demand demand);//添加商品历史需求

    @Select("select * from demand")
    List<Demand> list();//获取历史需求数据列表

    @Update("update demand set jul_n=#{julN},aug_n=#{augN},sep_n=#{sepN},oct_n=#{octN}," +
            "nov_n=#{novN},dec_n=#{decN} where id=#{id}")
    void update(Demand demand);//给指定商品更新历史需求数据

    @Delete("delete from demand where id = #{id}")
    void delete(Integer id);//删除指定商品历史需求数据

    @Select("select * from demand where id = #{id}")
    Demand findById(Integer id);
}
