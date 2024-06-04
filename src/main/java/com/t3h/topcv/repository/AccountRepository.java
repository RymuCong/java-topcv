package com.t3h.topcv.repository;

import com.t3h.topcv.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Account findByUserName(String userName);
}
