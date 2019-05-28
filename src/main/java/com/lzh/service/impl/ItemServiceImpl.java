package com.lzh.service.impl;

import com.lzh.bean.Item;
import com.lzh.dao.ItemDao;
import com.lzh.service.ItemService;
import com.lzh.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizhenhao
 */


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;


    @Override
    public PageInfo<Item> findItemByCondition(String name, Integer page, Integer size) {

        //1.查询商品总数
        Integer count = itemDao.findCountByLike(name);

        //2.创建分页信息PageInfo
        PageInfo<Item> pageInfo = new PageInfo<>(page, size,count);
        //2.分页查询商品
        List<Item> list= itemDao.findItemByLikePage(name,pageInfo.getOffset(),pageInfo.getSize());

        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    public boolean addItem(Item item) {

        Integer count = itemDao.saveItem(item);

        if(count==1){
            return true;
        }
        return false;
    }

    @Override
    public Item findItemById(Long id) {

        return itemDao.findItemById(id);

    }

    @Override
    public boolean updateItemById(Item item) {

        Integer count = itemDao.updateItemById(item);

        if (count==1){
            return true;
        }

        return false;
    }

    @Override
    public Integer deleteItemById(Long id) {

        return itemDao.deleteItemById(id);

    }
}
