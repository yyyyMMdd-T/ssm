package com.qf.mapper;

import com.qf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {

    //查询商品总数
    long findCountByName(@Param("name")String name);

    //分页查询商品信息

    List<Item> findItemByNameLikeAndLimit(@Param("name")String name,
                                          @Param("offset")Integer offset,
                                          @Param("size")Integer size);

    //添加商品
    Integer save(Item item);

    //删除商品

    Integer delById(@Param("id")long id);
}
