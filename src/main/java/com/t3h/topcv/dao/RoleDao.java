package com.t3h.topcv.dao;

import com.t3h.topcv.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
