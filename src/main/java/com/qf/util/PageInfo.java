package com.qf.util;

import lombok.Data;

import java.util.List;
@Data
public class PageInfo<T> {

    private Integer page;

    private Integer size;

    private long total;

    private Integer pages;

    private Integer offset;

    private List<T> list;

    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 5 : size;
        this.total = total < 0 ? 0 : total;
        // 计算出pages和offset;
        this.pages = (int) (this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1);
        this.offset = (this.page - 1) * this.size;
    }
}
