package com.acidz.daoimpl;

import com.acidz.dao.AdminuserDAO;
import com.acidz.entity.Adminuser;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Acidz on 2018/10/9.
 */
public class AdminuserDAOImpl implements AdminuserDAO {
    @Override
    public Adminuser queryById(String id) {
        return null;
    }

    @Override
    public void add(Adminuser adminuser) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into adminuser values(?,?,?,?,?)";
        try {
            queryRunner.update(sql, adminuser.getId(), adminuser.getName(),
                    adminuser.getPassword(), adminuser.getLastlogintime(), adminuser.getLevel());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-插入表adminuser");
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(Adminuser adminuser) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update adminuser set password=? where id=?";
        try {
            queryRunner.update(sql, adminuser.getPassword(), adminuser.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-跟新表adminuser");
        }
    }

    @Override
    public List<Adminuser> queryAll() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from adminuser";
        List<Adminuser> adminusers = null;
        try {
            adminusers = queryRunner.query(sql, new BeanListHandler<>(Adminuser.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-查询表adminuser");
        }
        return adminusers;
    }

    @Override
    public Adminuser login(String name, String password) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from adminuser where name=? and password=?";
        Adminuser adminuser = null;
        try {
            adminuser = queryRunner.query(sql, new BeanHandler<>(Adminuser.class), name, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-查询表adminuser");
        }
        return adminuser;
    }

}
