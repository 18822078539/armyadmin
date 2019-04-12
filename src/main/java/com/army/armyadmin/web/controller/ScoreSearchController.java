package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestScore;
import com.army.armyadmin.web.domain.TestType;
import com.army.armyadmin.web.service.TestProjectService;
import com.army.armyadmin.web.service.TestScoreService;
import com.army.armyadmin.web.service.TestTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 10:26 2019/4/12
 * @Modified By:
 */
@Controller
public class ScoreSearchController extends BaseController{

    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 测试成绩服务
     */
    @Autowired
    private TestScoreService testScoreService;
    @Autowired
    private TestTypeService testTypeService;
    @Autowired
    private TestProjectService testProjectService;
    @Log("测试成绩查询")
    @RequestMapping("scoresearch")
    @RequiresPermissions("scoresearch:list")
    public String index(){
        return "web/scoresearch/scoresearch";
    }


    /**
     * 查询所有的测试人员成绩
     * @param request
     * @param testScore
     * @return
     */
    @RequestMapping("scoresearch/list")
    @ResponseBody
    public Map<String, Object> scoresearchList(QueryRequest request, TestScore testScore) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<TestScore> list = this.testScoreService.findAllTestScore(testScore,request);
        for(TestScore score:list){
            TestProject testProject = testProjectService.selectByKey(score.getProjectid());
            score.setProjectName(testProject.getName());
            TestType testType = testTypeService.selectByKey(testProject.getTypeid());
            score.setTypeName(testType.getName());
        }
        PageInfo<TestScore> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }
}
