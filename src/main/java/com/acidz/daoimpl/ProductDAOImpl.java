package com.acidz.daoimpl;

import com.acidz.dao.ProductDAO;
import com.acidz.entity.PageBean;
import com.acidz.entity.Product;
import com.acidz.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Acidz on 2018/9/16.
 */
public class ProductDAOImpl implements ProductDAO {
    @Override
    public Product queryById(String id) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ?";
        Product product = null;
        try {
            product = queryRunner.query(sql, new BeanHandler<>(Product.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product");
        }
        return product;
    }

    @Override
    public void add(Product product) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
                    product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(), product.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String uuid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from product where pid=?";
        try {
            queryRunner.update(sql,uuid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-删除表product记录");
        }
    }

    @Override
    public void update(Product product) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,cid=? where pid=?";
        try {
            queryRunner.update(sql, product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(),
                    product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getCid(), product.getPid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作数据库失败-跟新表product");
        }
    }

    @Override
    public List<Product> queryAll() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product";
        List<Product> products = null;
        try {
            products = queryRunner.query(sql, new BeanListHandler<>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product");
        }
        return products;
    }

    @Override
    public List<Product> queryAllByCid(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid = ?";
        List<Product> products = null;
        try {
            products = queryRunner.query(sql,new BeanListHandler<>(Product.class),cid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product");
        }
        return products;
    }

    @Override
    public Map queryInfo(int id) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product p,category c where p.cid = c.cid and p.pid = ?";
        Map productInfo = null;
        try {
            productInfo = queryRunner.query(sql, new MapHandler(), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product&category");
        }
        return productInfo;
    }

    @Override
    public int getTotalRecord() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product";
        Number number = null;
        try {
            number = (Number)queryRunner.query(sql, new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int cnt = number.intValue();
        return cnt;
    }

    @Override
    public List<Product> queryAll(int startIndex, int pageSize) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product order by pdate desc limit ?,?";
        List<Product> products = null;
        try {
            products = queryRunner.query(sql, new BeanListHandler<>(Product.class), startIndex, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product");
        }
        return products;
    }

    @Override
    public PageBean queryAll(PageBean pageBean) {
        String keyword = pageBean.getKeyword();
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where "+pageBean.getSearchType()+" like ? order by pdate desc";
        List<Product> products = null;
        try {
            products = queryRunner.query(sql, new BeanListHandler<>(Product.class),
                    "%"+keyword+"%");
            pageBean.setTotalRecord(products.size());
            List<Product> subproducts = null;
            int startIndex = pageBean.getStartIndex();
            int endIndex = pageBean.getStartIndex()+pageBean.getPageSize();
            if (endIndex > products.size())
                endIndex = products.size();
            subproducts = products.subList(startIndex, endIndex);
            pageBean.setProducts(subproducts);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库失败-表product");
        }
        return pageBean;
    }
}
