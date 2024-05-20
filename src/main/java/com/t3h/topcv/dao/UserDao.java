package com.t3h.topcv.dao;

import com.t3h.topcv.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
}
