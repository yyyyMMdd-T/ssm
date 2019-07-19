package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size) {
       //查询数据总数

        long total=itemMapper.findCountByName(name);

        //创建爱你pageinfo对象
        PageInfo<Item>  pageInfo=new PageInfo<>(page, size, total);
        //查询商品信息
        List<Item> list=itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffset(), pageInfo.getSize());
        //jiang  list集合set到pageinfo中
        pageInfo.setList(list);
        //        返回


        return pageInfo;
    }

    @Override
    @Transactional
    public Integer save(Item item) {
       return itemMapper.save(item);
    }


    //删除商品
    @Override
    @Transactional
    public Integer del(long id) {

        return itemMapper.delById(id);
    }
}
