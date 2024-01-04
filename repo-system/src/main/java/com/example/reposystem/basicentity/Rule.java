package com.example.reposystem.basicentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rule {
    private Integer id;
    private Integer minNum;
    private Integer maxNum;
    private Integer relatedGoodId;
    private Integer relatedTypeId;
    private String relatedGoodName;
    private String relatedTypeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer state;

}
