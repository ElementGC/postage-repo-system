package com.example.reposystem.basicentity;


import lombok.Data;

@Data
public class Demand {
    private Integer id;
    private String goodName;
    //7-12月的需求数据
    private Integer julN;
    private Integer augN;
    private Integer sepN;
    private Integer octN;
    private Integer novN;
    private Integer decN;
    //预测结果
    private double preN;
}
