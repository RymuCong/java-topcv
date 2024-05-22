package com.t3h.topcv.service;

import com.t3h.topcv.dao.RoleDao;
import com.t3h.topcv.dao.AccountDao;
import com.t3h.topcv.entity.Role;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

	private final AccountDao accountDao;

	private final RoleDao roleDao;

	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(AccountDao accountDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
		this.accountDao = accountDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Account findByUserName(String userName) {
		// check the database if the account already exists
		return accountDao.findByUserName(userName);
	}

	@Override
	public void save(WebUser webAccount) {
		Account account = new Account();

		// assign account details to the account object
		account.setUserName(webAccount.getUserName());
		account.setPassword(passwordEncoder.encode(webAccount.getPassword()));
		account.setFullName(webAccount.getFullName());
		account.setEmail(webAccount.getEmail());
		account.setStatus(1);

		// give account default role of "employee"
		account.setRoles(Collections.singletonList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		// save account in the database
		accountDao.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Account user = accountDao.findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

	private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role tempRole : roles) {
			SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
			authorities.add(tempAuthority);
		}

		return authorities;
	}
}
