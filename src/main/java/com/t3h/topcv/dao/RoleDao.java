package com.t3h.topcv.dao;

import com.t3h.topcv.entity.Role;

import java.util.Optional;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

	Optional<Role> findById(Long id);
}
