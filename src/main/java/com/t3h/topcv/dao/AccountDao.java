package com.t3h.topcv.dao;

import com.t3h.topcv.entity.Account;

public interface AccountDao {

    Account findByUserName(String userName);

    void save(Account theAccount);
}
