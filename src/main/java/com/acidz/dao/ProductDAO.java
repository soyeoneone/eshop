package com.acidz.dao;

import com.acidz.entity.PageBean;
import com.acidz.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by Acidz on 2018/9/16.
 */
public interface ProductDAO extends BaseDAO<Product> {
    public List<Product> queryAllByCid(String categoryid);

    Map queryInfo(int id);

    int getTotalRecord();

    List<Product> queryAll(int startIndex, int pageSize);

    PageBean queryAll(PageBean pageBean);
}
