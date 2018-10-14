package com.acidz.daoimpl;

import com.acidz.dao.OrdersDAO;
import com.acidz.entity.MyOrders;
import com.acidz.entity.Orders;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/18.
 */
public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public Orders queryById(String id) {
        return null;
    }

    @Override
    public void add(Orders orders) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(sql,orders.getOid(),orders.getOrdertime(),orders.getTotal(),orders.getState(),orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-插入表orders");
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(Orders orders) {

    }

    @Override
    public List<Orders> queryAll() {
        return null;
    }

    @Override
    public void updateTotalPrice(Orders order) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set total = ? where oid = ?";
        try {
            queryRunner.update(sql, order.getTotal(), order.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-更新表orders列total");
        }
    }

    @Override
    public void updateByOid(String oid, String address, String name, String telephone) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set address = ?, name = ?, telephone = ? where oid = ?";
        try {
            queryRunner.update(sql, address, name, telephone, oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-更新表orders-列address,name,telephone");
        }
    }

    @Override
    public void updateByOid(String oid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orders set state = 1 where oid = ?";
        try {
            queryRunner.update(sql, oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-表orders列state");
        }
    }

    @Override
    public List<MyOrders> queryAllByUid(String uid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select o.uid,o.oid,o.ordertime,o.total,o.state,oi.count,oi.subtotal,p.pimage,p.pname,p.shop_price from orders o,orderitem oi,product p where o.oid=oi.oid and oi.pid=p.pid and o.uid=?";
        List<MyOrders> myOrders = null;
        try {
            myOrders =queryRunner.query(sql, new BeanListHandler<>(MyOrders.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myOrders;

    }

}
