package com.lzh.service.impl;

import com.lzh.SsmTest;
import com.lzh.bean.Item;
import com.lzh.service.ItemService;
import com.lzh.util.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lizhenhao
 */
public class ItemServiceImplTest extends SsmTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void findItemByCondition() {

        PageInfo<Item> pageInfo = itemService.findItemByCondition("联想", 0, 5);

        System.out.println(pageInfo.getList().size()+"条数据");

        System.out.println(pageInfo);
    }

    @Test
    @Transactional
    public void addItem() {

        Item item = new Item();

        item.setName("华为P30");
        item.setPrice(3000);
        item.setProductionDate(new Date());
        item.setDescription("性价比极高!!!");
        item.setPic("http://localhost/static/images/5caf4444-63b0-4048-a32a-86eba135998a.jpg");

        boolean b = itemService.addItem(item);

        //断言判断
        assertEquals(true,b);


    }

    @Test
    public void findItemById() {

        Item item = itemService.findItemById(1L);

        System.out.println(item);

    }

    @Test
    @Transactional
    public void updateItemById() {

        Item item = new Item();

        item.setId(1L);
        item.setName("华为P30");
        item.setPrice(3000);
        item.setProductionDate(new Date());
        item.setDescription("性价比极高!!!");
        item.setPic("http://localhost/static/images/5caf4444-63b0-4048-a32a-86eba135998a.jpg");

        boolean b = itemService.updateItemById(item);

        assertEquals(true,b);
    }

    @Test
    @Transactional
    public void deleteItemById() {

        Integer count = itemService.deleteItemById(1L);

        assertEquals(new Integer(1),count);

    }
}