package com.army.armyadmin.web.service.impl;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.impl.BaseService;
import com.army.armyadmin.web.dao.TestScoreMapper;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestScore;
import com.army.armyadmin.web.service.TestScoreService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:21 2019/3/19
 * @Modified By:
 */
@Service
public class TestScoreServiceImpl extends BaseService<TestScore> implements TestScoreService {
    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestScoreMapper testScoreMapper;

    @Override
    public List<TestScore> getDataByUP(Integer userId, Integer projectId) {
        try {
            Example example = new Example(TestScore.class);
            example.createCriteria().andCondition("userid = ",userId).andCondition("projectid = ",projectId);
            return this.selectByExample(example);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<TestScore> selectData(Integer userId) {
        try {
            Example example = new Example(TestScore.class);
            example.createCriteria().andCondition("userid = ",userId);
            return this.selectByExample(example);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TestScore> getSumScore(TestScore testscore) {
        return testScoreMapper.getSumScore(testscore);
    }

    @Override
    public List<TestScore> selectProId(Integer proId) {
        try {
            Example example = new Example(TestScore.class);
            example.createCriteria().andCondition("projectid = ",proId);
            return this.selectByExample(example);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }    }

    @Override
    public List<TestScore> findAllTestScore(TestScore testScore, QueryRequest request) {
        try {
            Example example = new Example(TestScore.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(testScore.getUsername())) {
                criteria.andCondition("username like", "%"+testScore.getUsername()+"%");
            }
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取测试成绩失败", e);
            return new ArrayList<>();
        }
    }

}
