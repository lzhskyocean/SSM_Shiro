package com.lzh.service;

import com.lzh.bean.Item;
import com.lzh.util.PageInfo;

/**
 * @author lizhenhao
 */
public interface ItemService {
    PageInfo<Item> findItemByCondition(String name, Integer page, Integer size);

    boolean addItem(Item item);

    Item findItemById(Long id);

    boolean updateItemById(Item item);

    Integer deleteItemById(Long id);
}
