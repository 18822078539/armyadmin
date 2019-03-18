package com.army.armyadmin.system.service;

import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.system.domain.RoleMenu;

public interface RoleMenuServie extends IService<RoleMenu> {

	void deleteRoleMenusByRoleId(String roleIds);

	void deleteRoleMenusByMenuId(String menuIds);
}
