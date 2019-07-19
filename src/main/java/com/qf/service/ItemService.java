package com.qf.service;

import com.qf.pojo.Item;
import com.qf.util.PageInfo;

public interface ItemService {
    PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size);

    Integer save(Item item);

    Integer del(long id);
}
