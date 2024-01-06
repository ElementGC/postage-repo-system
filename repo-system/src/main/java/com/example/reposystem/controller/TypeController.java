package com.example.reposystem.controller;

import com.example.reposystem.basicentity.Result;
import com.example.reposystem.basicentity.Type;
import com.example.reposystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping
    public Result add(@RequestBody @Validated Type type){
        typeService.add(type);
        return Result.success("新种类添加成功");
    }
    @GetMapping
    public Result<List<Type>> list(){
        List<Type> types = typeService.list();
        return Result.success("列表查询（无条件）完成",types);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Type t){
        typeService.update(t);
        return Result.success("分类更新成功");
    }

    @DeleteMapping
    public Result delete(Integer id){
        //未分类（id=1）不能删除
        if(id == 1){
            return Result.error("初始分类，不能删除");
        }
        //删除这个id对应的分类
        typeService.delete(id);
        return Result.success("删除成功");
    }

}
