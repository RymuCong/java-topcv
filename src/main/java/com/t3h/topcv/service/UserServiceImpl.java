package com.t3h.topcv.service;

import com.t3h.topcv.dao.RoleDao;
import com.t3h.topcv.dao.AccountDao;
import com.t3h.topcv.dto.AuthRequest;
import com.t3h.topcv.dto.AuthResponse;
import com.t3h.topcv.entity.Role;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.security.JwtTokenService;
import com.t3h.topcv.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	private final AccountDao accountDao;

	private final RoleDao roleDao;

	private final BCryptPasswordEncoder passwordEncoder;

	private final JwtTokenService jwtTokenService;

	private final AuthenticationProvider authenticationProvider;

	@Autowired
	public UserServiceImpl(AccountDao accountDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder,
						   JwtTokenService jwtTokenService, AuthenticationProvider authenticationProvider) {
		this.accountDao = accountDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenService = jwtTokenService;
		this.authenticationProvider = authenticationProvider;
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
	public AuthResponse login(AuthRequest loginDto) {
		final Authentication authentication = authenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsername(),
						loginDto.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final Account user = accountDao.findByUserName(loginDto.getUsername());

		Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		String token = jwtTokenService.generateToken(user.getUserName(), grantedAuthorities);

		AuthResponse response = new AuthResponse();
		AuthResponse.Data data = new AuthResponse.Data();
		data.setToken(token);
		data.setStatus(1); // set the status value

		// Set the role value based on the role name
		for (Role role : user.getRoles()) {
			switch (role.getName()) {
				case "ROLE_CANDIDATE":
					data.setRole(1);
					break;
				case "ROLE_COMPANY":
					data.setRole(2);
					break;
				case "ROLE_ADMIN":
					data.setRole(3);
					break;
				default:
					// Handle unknown roles if necessary
					break;
			}
		}

		data.setToken_access(token); // set the token_access value
		response.setData(data);
		response.setMessage("Đăng nhập thành công");

		return response;
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

	@Override
	public Account getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return accountDao.findByUserName(userDetails.getUsername());
	}
}
