package com.army.armyadmin.system.service;

import com.army.armyadmin.common.domain.Tree;
import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.system.domain.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {

	Tree<Dept> getDeptTree();

	List<Dept> findAllDepts(Dept dept);

	Dept findByName(String deptName);

	Dept findById(Long deptId);
	
	void addDept(Dept dept);
	
	void updateDept(Dept dept);

	void deleteDepts(String deptIds);
}
