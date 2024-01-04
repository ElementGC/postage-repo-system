package com.example.reposystem.mapper;

import com.example.reposystem.basicentity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeMapper {
    @Insert("insert into type(type_name,type_alias,create_time,update_time)" +
            " value(#{typeName},#{typeAlias},now(),now())")
    void add(Type type);//创建新种类

    @Select("select * from type")
    List<Type> list();//获取到所有的分类

    @Update("update type set type_name=#{typeName}, type_alias=#{typeAlias}, update_time=now()" +
            " where id=#{id}")
    void update(Type t);//更新商品分类

    @Delete("delete from type where id = #{id}")
    void delete(Integer id);//删除指定分类
}
