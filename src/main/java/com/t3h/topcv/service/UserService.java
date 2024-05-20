package com.t3h.topcv.service;


import com.t3h.topcv.entity.User;
import com.t3h.topcv.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);

}
