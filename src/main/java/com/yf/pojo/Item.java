package com.yf.pojo;/*
 *
 *商品表对应的实体表
 */

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Item {

    //id
    private Long id;

    //产品名称
    @NotBlank(message = "商品名称不能为空")
    private String name;

    //商品价格
    /*涉及到价格问题不用double，使用BigDecimal或long*/
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;

    //商品的生产日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;

    //商品的描述
    @NotBlank(message = "商品描述不能为空")
    private String description;

    //商品的图片
    private String pic;

    //商品创建时间
    private Date created;
}
