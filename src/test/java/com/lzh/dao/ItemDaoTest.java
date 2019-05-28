package com.lzh.dao;

import com.lzh.SsmTest;
import com.lzh.bean.Item;
import com.lzh.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lizhenhao
 */
public class ItemDaoTest extends SsmTest {

    @Autowired
    private ItemDao itemDao;

    @Test
    public void findCountByLike() {

        Integer count = itemDao.findCountByLike("车");

        System.out.println(count);
    }

    @Test
    public void findItemByLikePage() {

        List<Item> itemList = itemDao.findItemByLikePage("联想", 0, 5);

        for (Item item : itemList) {
            System.out.println(item);
        }

    }


    @Test
    @Transactional//测试中添加一个事务,为了不污染我们的数据(增删改测试)
    public void saveItem(){

        Item item = new Item();

        item.setName("华为P30");
        item.setPrice(3000);
        item.setProductionDate(new Date());
        item.setDescription("性价比极高!!!");
        item.setPic("http://localhost/static/images/5caf4444-63b0-4048-a32a-86eba135998a.jpg");

        Integer count = itemDao.saveItem(item);

        //断言测试(预测结果是1,如果不是就是出现异常)
        assertEquals(new Integer(1),count);


    }

    @Test
    public void findItemById() {

        Item item = itemDao.findItemById(1L);

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

        Integer count = itemDao.updateItemById(item);

       assertEquals(new Integer(1),count);
    }

    @Test
    @Transactional
    public void deleteItemById() {

        Integer count = itemDao.deleteItemById(1L);

        assertEquals(new Integer(1),count);

    }


}