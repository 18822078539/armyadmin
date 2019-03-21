package com.army.armyadmin.web.service;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.web.domain.TestProject;

import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:19 2019/3/19
 * @Modified By:
 */
public interface TestProjectService extends IService<TestProject>{
    List<TestProject> findAlltestprojects(TestProject testproject, QueryRequest request);

    void deletetestprojects(String ids);

    List<TestProject> selectAllByType(Integer typeId);
}
