package com.army.armyadmin.web.service.impl;

import com.army.armyadmin.common.service.impl.BaseService;
import com.army.armyadmin.web.domain.TestScore;
import com.army.armyadmin.web.service.TestScoreService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:21 2019/3/19
 * @Modified By:
 */
@Service
public class TestScoreServiceImpl extends BaseService<TestScore> implements TestScoreService {
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
}
