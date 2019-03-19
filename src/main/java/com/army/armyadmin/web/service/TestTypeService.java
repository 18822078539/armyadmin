package com.army.armyadmin.web.service;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.web.domain.TestType;

import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:17 2019/3/19
 * @Modified By:
 */
public interface TestTypeService extends IService<TestType>{
    //查询所有的测试类别
    List<TestType> findAlltesttypes(TestType testtype, QueryRequest request);
    //批量删除测试类别
    void deletetesttypes(String ids);
}
