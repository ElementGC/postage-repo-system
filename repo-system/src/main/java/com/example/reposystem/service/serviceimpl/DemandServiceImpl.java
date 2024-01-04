package com.example.reposystem.service.serviceimpl;

import com.example.reposystem.basicentity.Demand;
import com.example.reposystem.mapper.DemandMapper;
import com.example.reposystem.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandServiceImpl implements DemandService {
    @Autowired
    private DemandMapper demandMapper;
    @Override
    public Demand findByGoodName(String goodName) {
        return demandMapper.findByGoodName(goodName);
    }

    @Override
    public void add(Demand demand) {
        demandMapper.add(demand);
    }

    @Override
    public List<Demand> list() {
        return demandMapper.list();
    }

    @Override
    public void update(Demand demand) {
        demandMapper.update(demand);
    }

    @Override
    public void delete(Integer id) {
        demandMapper.delete(id);
    }

    @Override
    public Demand findById(Integer id) {
        return demandMapper.findById(id);
    }
}
