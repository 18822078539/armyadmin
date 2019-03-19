package com.army.armyadmin.web.service.impl;

import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.service.impl.BaseService;
import com.army.armyadmin.web.domain.TestType;
import com.army.armyadmin.web.service.TestTypeService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
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
 * @Date:Created in 9:18 2019/3/19
 * @Modified By:
 */
@Service
public class TestTypeServiceImpl extends BaseService<TestType> implements TestTypeService {

    public Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<TestType> findAlltesttypes(TestType testtype, QueryRequest request) {
        try {
            Example example = new Example(TestType.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(testtype.getName())) {
                criteria.andCondition("name like", "%"+testtype.getName()+"%");
            }
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取测试类型失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void deletetesttypes(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        this.batchDelete(list,"id",TestType.class);
    }
}
