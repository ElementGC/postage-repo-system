package com.example.reposystem.basicentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Records {
    private Integer id;//主键ID
    private Integer goodId;//
    private String goodName;//记录对应的商品
    private String goodCode;//商品的条码
    private Integer type;//记录的类型: 0-入库；1-出库
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alterTime;//记录发生时间
    private Integer alterNum;//记录中商品的变动数量

}
