package com.t3h.topcv.service;

import com.t3h.topcv.dto.AuthRequest;
import com.t3h.topcv.dto.AuthResponse;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.user.CompanyUser;
import com.t3h.topcv.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Account findByUserName(String userName);

    void saveCandidate(WebUser webAccount);

    void saveCompany(CompanyUser webAccount);

    AuthResponse login(AuthRequest loginDto);

    Account getUser();
}
