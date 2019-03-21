package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.domain.ResponseBo;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestScore;
import com.army.armyadmin.web.domain.TestType;
import com.army.armyadmin.web.service.TestProjectService;
import com.army.armyadmin.web.service.TestScoreService;
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
 * @Date:Created in 9:23 2019/3/19
 * @Modified By:
 */
@Controller
public class TestScoreController extends BaseController {
    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestScoreService testScoreService;
    @Autowired
    private TestProjectService testProjectService;
    @Log("测试成绩维护")
    @RequestMapping("testscore")
    @RequiresPermissions("testscore:list")
    public String index(){
        return "web/testscore/testscore";
    }

    @Log("修改测试成绩")
    @RequestMapping("testscore/update")
    @ResponseBody
    public ResponseBo updateTestScore(Integer userId,Integer projectId,
                                      String score,String score1,String evalute) {
        try {
            List<TestScore> list = testScoreService.getDataByUP(userId,projectId);
            if(list!=null&&list.size()>0){
                //说明此时数据存在，需进行更新操作
                TestScore testScore = list.get(0);
                testScore.setScore(score);
                testScore.setScore1(score1);
                testScore.setEvalute(evalute);
                testScore.setProjectid(projectId);
                testScore.setUserid(userId);
                testScoreService.updateNotNull(testScore);
            }else{
                TestScore testScore = new TestScore();
                testScore.setScore(score);
                testScore.setScore1(score1);
                testScore.setEvalute(evalute);
                testScore.setProjectid(projectId);
                testScore.setUserid(userId);
                testScoreService.save(testScore);
            }
            return ResponseBo.ok("修改测试成绩成功！");
        } catch (Exception e) {
            log.error("修改测试成绩失败", e);
            return ResponseBo.error("修改测试成绩失败，请联系网站管理员！");
        }
    }


    @RequestMapping("testscore/list")
    @ResponseBody
    public Map<String, Object> testscoreList(QueryRequest request, TestScore testScore) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<TestScore> list = this.testScoreService.selectAll();
        for(TestScore ts:list){
           TestProject tp =  testProjectService.selectByKey(ts.getProjectid());
           ts.setProjectName(tp.getName());
        }
        PageInfo<TestScore> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }
}
