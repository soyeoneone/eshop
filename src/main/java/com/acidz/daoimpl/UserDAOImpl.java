package com.acidz.daoimpl;

import com.acidz.dao.UserDAO;
import com.acidz.entity.User;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/12.
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public User login(String name, String password) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username=? and password = ?";
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class), name, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库查询失败");
        }
        return user;
    }

    @Override
    public boolean checkUsername(String name) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ?";
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class),name);
            if (user==null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-校验用户名是否存在");
        }
        return false;
    }

    @Override
    public User queryById(String id) {
        return null;
    }

    @Override
    public void add(User user) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库添加失败");
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> queryAll() {
        return null;
    }
}
