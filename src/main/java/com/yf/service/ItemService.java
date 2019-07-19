package com.yf.service;

import com.yf.pojo.Item;
import com.yf.util.PageInfo;

public interface ItemService {


    //分页查询商品信息
    PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size);

    //添加商品
    Integer save(Item item);

    //根据id删除商品
    Integer del(Long id);

    Item up(Long id);

    Integer update(Item item);
}
