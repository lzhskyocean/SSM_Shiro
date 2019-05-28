package com.lzh.dao;

import com.lzh.bean.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lizhenhao
 */
public interface ItemDao {

    //模糊查询商品数量
    Integer findCountByLike(@Param("name") String name);

    //分页查询
    List<Item> findItemByLikePage(@Param("name") String name,
                                  @Param("offset") Integer offset,
                                  @Param("size") Integer size);

    //添加商品
    Integer saveItem(Item item);

    //通过id查询商品
    Item findItemById(@Param("id") Long id);


    //通过id进行修改商品信息
    Integer updateItemById(Item item);

    //通过id删除商品
    Integer deleteItemById(@Param("id") Long id);
}
