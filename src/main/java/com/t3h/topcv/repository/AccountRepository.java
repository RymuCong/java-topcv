package com.t3h.topcv.repository;

import com.t3h.topcv.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
