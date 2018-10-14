package com.acidz.dao;

import com.acidz.entity.User;

/**
 * Created by Acidz on 2018/9/12.
 */
public interface UserDAO extends BaseDAO<User>{
    public User login(String name, String password);

    boolean checkUsername(String name);
}
