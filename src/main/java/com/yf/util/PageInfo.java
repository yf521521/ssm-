package com.yf.util;/*
 *
 *
 */

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {

    //当前页码
    private Integer page;

    //每页条数
    private Integer size;

    //总条数
    private Long total;

    //计算总页数
    private Integer pages;

    //商品信息
    private List<T> list;

    //    开始
    private Integer offset;

    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 5 : size;
        this.total = total < 0 ? 0 : total;
        //
        this.pages = (int)(this.total % this.size == 0 ? this.total / this.size : (this.total / this.size + 1 ));
        this.offset = (page - 1) * size;
    }
}
