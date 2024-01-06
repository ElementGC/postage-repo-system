package com.example.reposystem.service.serviceimpl;

import com.example.reposystem.basicentity.Type;
import com.example.reposystem.mapper.TypeMapper;
import com.example.reposystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;


    //新增商品种类
    @Override
    public void add(Type type) {
        typeMapper.add(type);
    }

    //分类列表查询
    @Override
    public List<Type> list() {
        return typeMapper.list();
    }

    //更新分类信息
    @Override
    public void update(Type t) {
        typeMapper.update(t);

    }

    @Override
    public void delete(Integer id) {
        //转移属于这个分类商品到未分类（typeId=1）中
        typeMapper.transfer(id);
        //删除分类
        typeMapper.delete(id);

    }
}
