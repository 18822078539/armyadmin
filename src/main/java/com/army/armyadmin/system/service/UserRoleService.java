package com.army.armyadmin.system.service;

import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.system.domain.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String roleIds);

	void deleteUserRolesByUserId(String userIds);
}
