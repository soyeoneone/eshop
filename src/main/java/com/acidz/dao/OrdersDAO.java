package com.acidz.dao;

import com.acidz.entity.MyOrders;
import com.acidz.entity.Orders;

import java.util.List;

/**
 * Created by Acidz on 2018/9/18.
 */
public interface OrdersDAO extends BaseDAO<Orders> {
    public void updateTotalPrice(Orders order);

    public void updateByOid(String oid, String address, String name, String telephone);

    public void updateByOid(String oid);

    public List<MyOrders> queryAllByUid(String uid);
}
