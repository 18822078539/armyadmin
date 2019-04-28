package com.army.armyadmin.web.service;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.IService;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestScore;

import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:20 2019/3/19
 * @Modified By:
 */
public interface TestScoreService extends IService<TestScore>{
    /**
     * 根据用户id和测试项目id进行数据查询
     * @param userId
     * @param projectId
     * @return
     */
    List<TestScore> getDataByUP(Integer userId,Integer projectId);

    List<TestScore> getDataByUT(Integer userId,Integer typeId);

    /**
     * 根据用户id查询成绩
     * @param userId 用户id
     * @return
     */
    List<TestScore> selectData(Integer userId);

    List<TestScore> getSumScore(TestScore testscore);

    List<TestScore> selectProId(Integer proId);

    List<TestScore> findAllTestScore(TestScore testScore, QueryRequest request);
}
