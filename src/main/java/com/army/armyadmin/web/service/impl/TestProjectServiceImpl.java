package com.army.armyadmin.web.service.impl;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.impl.BaseService;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestType;
import com.army.armyadmin.web.service.TestProjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:19 2019/3/19
 * @Modified By:
 */
@Service
public class TestProjectServiceImpl extends BaseService<TestProject> implements TestProjectService {
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<TestProject> findAlltestprojects(TestProject testproject, QueryRequest request) {
        try {
            Example example = new Example(TestProject.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(testproject.getName())) {
                criteria.andCondition("name like", "%"+testproject.getName()+"%");
            }
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取测试项目失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void deletetestprojects(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        this.batchDelete(list,"id",TestProject.class);
    }
}
