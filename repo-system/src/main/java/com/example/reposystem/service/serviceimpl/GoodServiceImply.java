package com.example.reposystem.service.serviceimpl;

import com.example.reposystem.basicentity.Good;
import com.example.reposystem.basicentity.PageBean;
import com.example.reposystem.basicentity.Records;
import com.example.reposystem.basicentity.Rule;
import com.example.reposystem.mapper.GoodMapper;
import com.example.reposystem.service.GoodService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodServiceImply implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
    //增加新商品（仅增加信息，不涉及数量变动）
    @Override
    public void add(Good good) {
        goodMapper.add(good);
    }

    @Override
    public Good findByGoodName(String goodName) {

        return goodMapper.findByGoodName(goodName);
    }

    @Override
    public Good findByGoodCode(String goodCode) {
        return goodMapper.findByGoodCode(goodCode);
    }

    //通过商品id获取商品信息
    @Override
    public Good findByGoodId(Integer id) {
        return goodMapper.findByGoodId(id);
    }


    @Override
    public void alter(Good good,Integer state,Integer num) {
        LocalDateTime time = LocalDateTime.now();
        goodMapper.alter(good.getGoodName(),state*num,time);
        goodMapper.addRecord(good.getId(),state,time, num);
    }

    @Override
    public void update(Good good) {
        goodMapper.update(good);
    }

    @Override
    public PageBean<Good> list(Integer pageNum, Integer pageSize, String typeId,String goodName) {
        //1. 创建PageBean对象
        PageBean<Good> pb = new PageBean<>();
        //2. 开启分页查询调用PageHelper插件
        PageHelper.startPage(pageNum,pageSize);
        //3. 直接调用条件查询Mapper即可
        List<Good> goods= goodMapper.list(typeId,goodName);
        //4. 将所的结果进行强制类型转换，让PageHelper 能够调用Page方法
        Page<Good> p = (Page<Good>) goods;
        //5. 将最后数据填充到 PageBean 中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<Records> recordList(Integer pageNum, Integer pageSize, String goodName) {
        //1. 创建PageBean对象
        PageBean<Records> pb = new PageBean<>();
        //2. 开启分页查询调用PageHelper插件
        PageHelper.startPage(pageNum,pageSize);
        //3. 直接调用条件查询Mapper即可
        List<Records> records= goodMapper.recordList(goodName);
        //4. 将所的结果进行强制类型转换，让PageHelper 能够调用Page方法
        Page<Records> p = (Page<Records>) records;
        //5. 将最后数据填充到 PageBean 中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void delete(Integer id) {
        goodMapper.delete(id);
    }

    //为商品添加规则
    @Override
    public void addRule(Integer goodId, Integer typeId, Integer maxNum, Integer minNum, Integer state) {
        Integer temptState = -1;
        goodMapper.initRule(temptState);
        goodMapper.addRelation(goodId,typeId,maxNum,minNum, state, temptState);//将查找对应数据的方式变更为通过时间查找
    }

    //获取规则列表
    @Override
    public List<Rule> listRules() {
        return goodMapper.listRules();
    }

    //更新指定规则
    @Override
    public void updateRule(Rule rule) {
        if(rule.getState() == 0){
            Good g = goodMapper.findByGoodName(rule.getRelatedGoodName());
            rule.setRelatedGoodId(g.getId());
        }
        goodMapper.updateRule( rule );
    }

    //删除指定规则
    @Override
    public void deleteRule(Integer id) {
        goodMapper.deleteRule(id);
    }
}
