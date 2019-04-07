package com.army.armyadmin.web.dao;

import com.army.armyadmin.common.config.MyMapper;
import com.army.armyadmin.web.domain.TestScore;

import java.util.List;

public interface TestScoreMapper extends MyMapper<TestScore> {

    List<TestScore> getSumScore(TestScore testscore);
}