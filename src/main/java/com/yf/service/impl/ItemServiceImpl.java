package com.yf.service.impl;/*
 *
 *
 */

import com.yf.mapper.ItemMapper;
import com.yf.pojo.Item;
import com.yf.service.ItemService;
import com.yf.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size) {
        //查询数据总条数
        Long total = itemMapper.findpages(name);
        //创建PageInfo
        PageInfo<Item> pageInfo = new PageInfo<>(page,size ,total );

        List<Item> lists = itemMapper.findByPageAndSize(name, pageInfo.getOffset(), pageInfo.getSize());
        pageInfo.setList(lists);
        return pageInfo;
    }

    @Override
    public Integer save(Item item) {
        return itemMapper.save(item);
    }

    @Override
    public Integer del(Long id) {
        return itemMapper.delById(id);
    }

    @Override
    public Item up(Long id) {
        return itemMapper.up(id);
    }

    @Override
    public Integer update(Item item) {
        return itemMapper.update(item);
    }

}
