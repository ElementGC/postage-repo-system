package com.example.reposystem.mapper;

import com.example.reposystem.basicentity.Good;
import com.example.reposystem.basicentity.Records;
import com.example.reposystem.basicentity.Rule;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GoodMapper {

    @Insert("insert into good(good_name,good_code,description,unit,first_in_time,recent_alter_time,type_id)" +
            " value(#{goodName},#{goodCode},#{description},#{unit},now(),now(),#{typeId})")
    void add(Good good);//增加新的商品信息

    @Select("select * from good where good_name = #{goodName}")
    Good findByGoodName(String goodName);//通过商品名查找商品

    @Select("select * from good where good_code = #{goodCode}")
    Good findByGoodCode(String goodCode);//通过商品条码查找商品

    @Insert("insert into records" +
            "(good_id,type,alter_time,alter_num)" +
            " value(#{goodId},#{state},#{time},#{num})")
    void addRecord(Integer goodId,Integer state,LocalDateTime time,Integer num);//添加商品变动记录

    @Update("update good set number=number+#{alterNum},recent_alter_time=#{time} where good_name = #{goodName}")
    void alter(String goodName,Integer alterNum,LocalDateTime time);//入库、出库时更改商品数量
    @Select("select * from good where id = #{id}")
    Good findByGoodId(Integer id);//通过商品 id 查找商品

    @Update("update good set good_name=#{goodName},good_code=#{goodCode}," +
            "description=#{description},unit=#{unit},type_id=#{typeId},recent_alter_time=now() " +
            "where id = #{id}")
    void update(Good good);//更改商品基本信息



    List<Good> list(String typeId,String goodName);//条件查询，因为可能会没有一些必要条件，所以从映射配置文件中写更加方便


    List<Records> recordList(String goodName);

    @Delete("delete from good where id = #{id}")
    void delete(Integer id);//通过商品 id 删除商品


    @Insert("insert into rule(create_time,update_time, state) value(now(),now(),#{tempState})")
    void initRule(Integer tempState);//为商品添加规则，其它属性可能会有不存在的，所以尝试映射配置文件。同时先添加限制规则以获取id，随后再根据id添加上对应的商品名或分类名


    void addRelation(Integer goodId, Integer typeId, Integer maxNum, Integer minNum, Integer state,Integer temptState);//补充添加剩余的信息


    List<Rule> listRules();//获取规则列表

    @Update("update rule set max_num=#{maxNum},min_num=#{minNum},related_good_id=#{relatedGoodId}," +
            "related_type_id=#{relatedTypeId},update_time=now() where id=#{id}")
    void updateRule(Rule rule);//更新指定规则

    @Delete("delete from rule where id = #{id}")
    void deleteRule(Integer id);//删除指定规则
}
