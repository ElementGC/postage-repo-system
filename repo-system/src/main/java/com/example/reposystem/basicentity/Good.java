package com.example.reposystem.basicentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Good {

    private Integer id;//主键ID


    @NotEmpty
    private String goodName;//商品id

    @NotEmpty
    private String goodCode;//商品条码

    private String description;//商品描述

    private Integer number;//商品库存

    private String unit;//库存单位

    @NotNull
    private Integer typeId;//商品类型主键ID

    private String typeName;//商品类型名


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime firstInTime;//商品首次入库时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recentAlterTime;//商品最近发生变动的时间
}
