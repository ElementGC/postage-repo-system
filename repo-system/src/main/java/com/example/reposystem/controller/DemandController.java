package com.example.reposystem.controller;


import com.example.reposystem.basicentity.*;
import com.example.reposystem.service.DemandService;
import com.example.reposystem.service.GoodService;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class DemandController {
    @Autowired
    private DemandService demandService;
    @Autowired
    private GoodService goodService;

    //添加需求数据
    @PostMapping
    public Result add(@RequestBody Demand demand){
        //检查商品是否存在
        Good g = goodService.findByGoodName(demand.getGoodName());
        if (g == null){
            //无法通过商品名进行搜索
            return Result.error("商品不存在，请检查后重试");
        }
        //检查该商品需求数据是否已经存在
        Demand d = demandService.findByGoodName(demand.getGoodName());
        if (d != null){
            //能够在已有的需求数据中找到该商品
            return Result.error("商品的历史需求已存在，请勿重复添加");
        }
        demandService.add(demand);
        return Result.success("商品历史需求添加成功");
    }

    //获取历史需求数据
    @GetMapping
    public Result<List<Demand>> list(){
        List<Demand> demands = demandService.list();
        return Result.success("历史需求数据列表获取成功",demands);
    }

    //更新商品历史需求数据
    @PutMapping
    public Result update(@RequestBody Demand demand){
        demandService.update(demand);
        return Result.success("商品历史需求数据更新成功");
    }

    //删除指定商品历史需求数据
    @DeleteMapping
    public Result delete(Integer id){
        demandService.delete(id);
        return Result.success("商品历史需求数据已删除");
    }

    // 获取指定id的需求预测数据
    @GetMapping("/predict")
    public Result<Demand> predict(Integer id){
        Demand d = demandService.findById(id);
        //准备数据
        SimpleRegression simpleRegression = new SimpleRegression();
        double[]x = {1,2,3,4,5,6};
        double[]y = {d.getJulN(),d.getAugN(),d.getSepN(),d.getOctN(),d.getNovN(),d.getDecN()};
        //添加数据
        for(Integer i = 0; i<6 ;i++){
            simpleRegression.addData(x[i],y[i]);
        }
        //得到线性回归结果
        double res =simpleRegression.predict(7);
        res = Double.parseDouble(String.format("%.2f",res).toString());
        d.setPreN(res);
        return Result.success("预测数据获取成功",d);
    }

}
