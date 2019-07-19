package com.yf.service.impl;

import com.yf.AcTests;
import com.yf.pojo.Item;
import com.yf.service.ItemService;
import com.yf.util.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImplTest extends AcTests {

    @Autowired
    private ItemService itemService;

    @Test
    public void findItemAndLimit() {
        PageInfo<Item> pa = itemService.findItemAndLimit(null, 1, 5);
        System.out.println(pa.toString());
    }
}