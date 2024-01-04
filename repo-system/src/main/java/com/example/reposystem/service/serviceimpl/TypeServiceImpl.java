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
        typeMapper.delete(id);
    }
}
