package com.acidz.daoimpl;

import com.acidz.dao.OrderitemDAO;
import com.acidz.entity.Orderitem;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/18.
 */
public class OrderitemDAOImpl implements OrderitemDAO {
    @Override
    public Orderitem queryById(String id) {
        return null;
    }

    @Override
    public void add(Orderitem orderitem) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into orderitem values (?,?,?,?,?)";
        try {
            queryRunner.update(sql,orderitem.getItemid(),orderitem.getCount(),orderitem.getSubtotal(),orderitem.getPid(),orderitem.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-插入表orderitem");
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(Orderitem orderitem) {

    }

    @Override
    public List<Orderitem> queryAll() {
        return null;
    }
}
