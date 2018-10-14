package com.acidz.dao;

import com.acidz.entity.Adminuser;

/**
 * Created by Acidz on 2018/10/9.
 */
public interface AdminuserDAO extends BaseDAO<Adminuser> {
    Adminuser login(String name, String password);
}
