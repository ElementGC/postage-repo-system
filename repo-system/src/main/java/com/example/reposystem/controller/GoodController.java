package com.example.reposystem.controller;

import com.example.reposystem.basicentity.*;
import com.example.reposystem.service.GoodService;
import com.example.reposystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    //添加商品
    @PostMapping
    public Result add(@RequestBody Good good){
        /*商品不想分类那样容易一眼看出来，数量也是有点大的，因此我们在新增加商品时也要对传入的参数进行校验
        * 1. 查看商品名称是否已经存在，若存在则返回“同名商品已存在，请核实”
        * 2. 查看商品条码是否存在，若存在则返回“同一条码的商品已存在，请核实”
        * 3. 添加完good数据库还要记得添加record数据库的商品记录*/
        //查询是否存在同名商品
        Good g1 = goodService.findByGoodName(good.getGoodName());
        if(g1 != null){
            return Result.error("商品名存在重复，请核实");
        }
        //查询是否存在条码重复商品
        g1 = goodService.findByGoodCode(good.getGoodCode());
        if(g1 != null){
            return Result.error("同一条码的商品已存在，请核实");
        }
        goodService.add(good);
        return Result.success("商品添加成功");

    }

    //商品出、入库
    @PutMapping("/alter")
    public Result alter(@RequestBody Records records){
        //检查商品是否在库中
        Good g1 = goodService.findByGoodName(records.getGoodName());
        if(g1 == null){
            return Result.error("商品名不存在，请核实");
        } else if (records.getType() == -1 && g1.getNumber()- records.getAlterNum()<0) {
            return Result.error("库存数量不足");
        }
        goodService.alter(g1,records.getType(),records.getAlterNum());
        return Result.success("出入库完成");
    }

    //更改商品基本信息
    @PutMapping("/update")
    public Result update(@RequestBody@Validated Good good){
        goodService.update(good);
        return Result.success("商品信息更改完成");
    }

    //获取商品列表
    @GetMapping
    public Result<PageBean<Good>> list(
            Integer pageNum, Integer pageSize,
            @RequestParam(required = false) String typeId,/*该注释用于表示此项可不传入*/
            @RequestParam(required = false) String goodName/*该注释用于表示此项可不传入*/
    ){
        /*要进行条件查询以及将查询结果分页显示，需要导入PageBean实体，使用其中方法进行分页
        * 具体实施过程放在了ServiceImpl中*/
        PageBean<Good> goods = goodService.list(pageNum,pageSize,typeId,goodName);
        return Result.success("已获取商品列表",goods);
    }

    //获取商品记录列表
    @GetMapping("/record")
    public Result<PageBean<Records>> recordList(
            Integer pageNum, Integer pageSize,
            @RequestParam(required = false) String goodName/*该注释用于表示此项可不传入*/
    ){
        /*要进行条件查询以及将查询结果分页显示，需要导入PageBean实体，使用其中方法进行分页
         * 具体实施过程放在了ServiceImpl中*/
        PageBean<Records> goods = goodService.recordList(pageNum,pageSize,goodName);
        return Result.success("已获取商品记录列表",goods);
    }

    //删除商品
    @DeleteMapping
    public Result delete(Integer id){
        //删除之前先检测该商品是否库存为空，若不为空不能删除
        Good good =  goodService.findByGoodId(id);
        if (good.getNumber() != 0)
            return Result.error("商品数量不为空，无法删除");
        goodService.delete(id);
        return Result.success("删除成功");
    }

    //新增规则
    @PostMapping("/rule")
    public Result addRule(@RequestBody Rule rule){
        if (rule.getMaxNum() == null && rule.getMinNum() == null){
            return Result.error("最大、最小预警数量不能同时为空");
        }
        if (rule.getMaxNum()==null)
            rule.setMaxNum(99999);
        if (rule.getMinNum()==null)
            rule.setMinNum(0);
        if (rule.getMaxNum()<=rule.getMinNum()) {
            return Result.error("最大预警数量必须大于最小预警数量");
        }
        Good g = new Good();
        if (rule.getState() == 0){//不等于0就一定是1,1即为分类，直接传入的就是分类id
                 g = goodService.findByGoodName(rule.getRelatedGoodName());
                 if (g == null)
                     return Result.error("商品不存在");
        }
        goodService.addRule(g.getId(), rule.getRelatedTypeId(), rule.getMaxNum(), rule.getMinNum(), rule.getState());

        return Result.success("添加规则成功");
    }

    //获取所有已有规则
    @GetMapping("/rule")
    public Result<List<Rule>> listRules(){
        List<Rule> rules = goodService.listRules();
        return Result.success("规则列表获取成功",rules);
    }

    //更新指定规则内容
    @PutMapping("/rule")
    public Result updateRule(@RequestBody Rule rule){
        goodService.updateRule(rule);
        return Result.success("规则更新成功");
    }

    //删除指定规则
    @DeleteMapping("/rule")
    public Result deleteRule(Integer id){
        goodService.deleteRule(id);
        return Result.success("规则删除成功");
    }
}
