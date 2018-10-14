package com.acidz.dao;

import java.util.List;

/**
 * Created by Acidz on 2018/9/12.
 */
public interface BaseDAO<T> {
    public T queryById(String id);
    //
    public void add(T t);

    public void delete(String uuid);

    public void update(T t);

    public List<T> queryAll();
}
