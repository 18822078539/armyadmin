package com.army.armyadmin.system.dao;

import com.army.armyadmin.common.config.MyMapper;
import com.army.armyadmin.system.domain.User;
import com.army.armyadmin.system.domain.UserWithRole;

import java.util.List;

public interface UserMapper extends MyMapper<User> {

	List<User> findUserWithDept(User user);
	
	List<UserWithRole> findUserWithRole(Long userId);
	
	User findUserProfile(User user);

	List<User> findUserScore(User user);
}