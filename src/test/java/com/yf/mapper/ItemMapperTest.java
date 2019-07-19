package com.yf.mapper;

import com.yf.AcTests;
import com.yf.pojo.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class ItemMapperTest extends AcTests {

    @Autowired
    private ItemMapper itemMapper;
    @Test
    public void findpages() {
        Long count = itemMapper.findpages("联想");
        System.out.println(count);
    }

    @Test
    public void findByPageAndSize() {
        List<Item> lists = itemMapper.findByPageAndSize(null, 1, 5);
        for (Item list : lists) {
            System.out.println(list);
        }
    }

    @Test
    @Transactional
    public void save() {
        Item item = new Item();
        item.setName("xxx");
        item.setPrice(new BigDecimal(1.1));
        item.setProductionDate(new Date());
        item.setDescription("aaaaaaaaaaaaaa");
        item.setPic("zzzzzzzzzz");
        Integer count = itemMapper.save(item);
        System.out.println(count);
    }

    @Test
    @Transactional
    public void delById() {
        Integer count = itemMapper.delById(10L);
        System.out.println(count);
    }


    @Test
    public void up() {
        Item up = itemMapper.up(12L);
        System.out.println(up.toString());
    }

    @Test
    @Transactional
    public void update() {
        Item item = new Item();
        item.setId(10L);
        item.setName("xxx");
        item.setPrice(new BigDecimal(88888));
        item.setProductionDate(new Date());
        item.setDescription("aaaaaaaaaaaaaa");
        item.setPic("zzzzzzzzzz");
        Integer update = itemMapper.update(item);
        System.out.println(update);
    }
}