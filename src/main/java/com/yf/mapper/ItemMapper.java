package com.yf.mapper;

import com.yf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {

    Long findpages(@Param("name") String name);

    List<Item> findByPageAndSize(@Param("name") String name,
                                 @Param("offset") Integer offset,
                                 @Param("size") Integer size);

    //添加商品
    Integer save(Item item);

    //删除商品
    Integer delById(@Param("id")Long id);

    //查找修改商品失败
    Item up(Long id);

    //修改商品
    Integer update(Item item);

}


















