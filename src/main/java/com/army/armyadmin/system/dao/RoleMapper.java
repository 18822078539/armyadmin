package com.army.armyadmin.system.dao;

import com.army.armyadmin.common.config.MyMapper;
import com.army.armyadmin.system.domain.Role;
import com.army.armyadmin.system.domain.RoleWithMenu;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
	
	List<Role> findUserRole(String userName);
	
	List<RoleWithMenu> findById(Long roleId);
}