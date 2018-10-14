package com.acidz.daoimpl;

import com.acidz.dao.CategoryDAO;
import com.acidz.entity.Category;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/16.
 */
public class CategoryDAOimpl implements CategoryDAO {
    @Override
    public Category queryById(String id) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category where cid=?";
        Category category = null;
        try {
            category = queryRunner.query(sql, new BeanHandler<>(Category.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-查询表category");
        }
        return category;
    }

    @Override
    public void add(Category category) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update category set cname=? where cid=?";
        try {
            queryRunner.update(sql, category.getCname(), category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-查询表category");
        }
    }

    @Override
    public List<Category> queryAll() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category where fid = 0";
        List<Category> categoryList = null;
        try {
            categoryList = queryRunner.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表category");
        }
        return categoryList;
    }
}
