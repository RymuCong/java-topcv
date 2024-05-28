package com.t3h.topcv.dao;

import com.t3h.topcv.entity.Account;

public interface AccountDao {

    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

    Account findByUserName(String userName);

    void save(Account theAccount);
}
