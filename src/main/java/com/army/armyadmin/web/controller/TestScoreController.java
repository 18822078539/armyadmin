package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.domain.ResponseBo;
import com.army.armyadmin.system.domain.User;
import com.army.armyadmin.system.service.UserService;
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

import java.util.HashMap;
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
    @Autowired
    private TestTypeService testTypeService;
    @Autowired
    private UserService userService;

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
//            List<TestScore> list = testScoreService.getDataByUP(userId,projectId);
//            if(list!=null&&list.size()>0){
//                //说明此时数据存在，需进行更新操作
//                TestScore testScore = list.get(0);
//                testScore.setScore(score);
//                testScore.setScore1(score1);
//                testScore.setEvalute(evalute);
//                testScore.setProjectid(projectId);
//                testScore.setUserid(userId);
//                testScoreService.updateNotNull(testScore);
//            }else{
                TestScore testScore = new TestScore();
                testScore.setScore(score);
                testScore.setScore1(score1);
                testScore.setEvalute(evalute);
                testScore.setProjectid(projectId);
                testScore.setUserid(userId);
                testScoreService.save(testScore);
//            }
            return ResponseBo.ok("新增测试成绩成功！");
        } catch (Exception e) {
            log.error("新增测试成绩失败", e);
            return ResponseBo.error("新增测试成绩失败，请联系网站管理员！");
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
           TestType tt = testTypeService.selectByKey(tp.getTypeid());
           ts.setTypeName(tt.getName());
        }
        PageInfo<TestScore> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    /**
     * 查询当前用户的所有测试程序
     * @param request
     * @param testScore
     * @return
     */
    @RequestMapping("testscore/mylist")
    @ResponseBody
    public Map<String, Object> myTestscoreList(QueryRequest request, TestScore testScore) {
        User user = super.getCurrentUser();
        List<TestScore> list = this.testScoreService.selectData(Integer.parseInt(user.getUserId()+""));
        for(TestScore ts:list){
            TestProject tp =  testProjectService.selectByKey(ts.getProjectid());
            ts.setProjectName(tp.getName());
            TestType tt = testTypeService.selectByKey(tp.getTypeid());
            ts.setTypeName(tt.getName());
        }
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        return rspData;
    }

    /**
     * 查询当前用户的所有测试程序
     * @param request
     * @return
     */
    @RequestMapping("testscore/otherlist")
    @ResponseBody
    public Map<String, Object> otherTestscoreList(QueryRequest request, String otherUsername) {
        User user = userService.findByName(otherUsername);
        List<TestScore> list = this.testScoreService.selectData(Integer.parseInt(user.getUserId()+""));
        for(TestScore ts:list){
            TestProject tp =  testProjectService.selectByKey(ts.getProjectid());
            ts.setProjectName(tp.getName());
            TestType tt = testTypeService.selectByKey(tp.getTypeid());
            ts.setTypeName(tt.getName());
        }
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        return rspData;
    }


    /**
     * 查询当前用户的成绩统计信息
     * @return
     */
    @RequestMapping("testscore/getstatics")
    @ResponseBody
    public ResponseBo getStatics(){
        try {
            TestScore ts = new TestScore();

            User user = super.getCurrentUser();
            TestScore testScore = new TestScore();
            testScore.setUserid(Integer.parseInt(user.getUserId()+""));
            List<TestScore> testScores = testScoreService.getSumScore(testScore);
            for(TestScore item:testScores){
                if(item.getUserid()==Integer.parseInt(user.getUserId()+"")){
                    ts = item;
                }
            }
            return ResponseBo.ok(ts);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("查询失败");
        }

    }

    /**
     * 查询当前用户的成绩统计信息
     * @return
     */
    @RequestMapping("testscore/getotherstatics")
    @ResponseBody
    public ResponseBo getOtherStatics(String username){

        try {
            TestScore ts = new TestScore();
            User user = userService.findByName(username);
            TestScore testScore = new TestScore();
            testScore.setUserid(Integer.parseInt(user.getUserId()+""));
            List<TestScore> testScores = testScoreService.getSumScore(testScore);
            for(TestScore item:testScores){
                if(item.getUserid()==Integer.parseInt(user.getUserId()+"")){
                    ts = item;
                }
            }
            return ResponseBo.ok(ts);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("查询失败");
        }

    }

    /**
     * 查询当前用户的指定项目的考试成绩
     * @return
     */
    @RequestMapping("testscore/getMyDataByProId")
    @ResponseBody
    public ResponseBo getMyDataByProId(Integer proId){

        try {
            User user = super.getCurrentUser();
            List<TestScore> testScores = testScoreService.getDataByUP(Integer.parseInt(user.getUserId()+""),proId);
            return ResponseBo.ok(testScores);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("查询失败");
        }

    }

    /**
     * 查询所有用户的指定项目的考试成绩
     * @return
     */
    @RequestMapping("testscore/getAllDataByProId")
    @ResponseBody
    public ResponseBo getAllDataByProId(Integer proId){
        int hgnum = 0;
        int nhgnum = 0;
        int lhnum = 0 ;
        int yxnum = 0;
        List<TestScore> testScores = testScoreService.selectProId(proId);
        for(TestScore ts:testScores){
            Integer s = Integer.parseInt(ts.getScore1());
            if(s<60){
                nhgnum++;
            }else if(s>=60&& s<75){
                hgnum++;
            }else if(s>=75&&s<90){
                lhnum++;
            }else{
                yxnum++;
            }
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("hg",hgnum);
        map.put("nhg",nhgnum);
        map.put("lh",lhnum);
        map.put("yx",yxnum);
        return ResponseBo.ok(map);
    }

}
