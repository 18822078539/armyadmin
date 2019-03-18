package com.army.armyadmin.system.service;

import com.army.armyadmin.system.domain.UserOnline;

import java.util.List;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
