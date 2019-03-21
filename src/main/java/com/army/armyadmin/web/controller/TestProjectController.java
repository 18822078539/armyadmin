package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.domain.ResponseBo;
import com.army.armyadmin.common.util.DateUtil;
import com.army.armyadmin.common.util.FileUtils;
import com.army.armyadmin.web.domain.TestProject;
import com.army.armyadmin.web.domain.TestType;
import com.army.armyadmin.web.service.TestProjectService;
import com.army.armyadmin.web.service.TestTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:23 2019/3/19
 * @Modified By:
 */
@Controller
public class TestProjectController extends BaseController{
    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TestProjectService testProjectService;
    @Autowired
    private TestTypeService testTypeService;
    
    @Log("获取测试项目信息")
    @RequestMapping("testproject")
    @RequiresPermissions("testproject:list")
    public String index(){
        return "web/testproject/testproject";
    }

    @RequestMapping("testproject/list")
    @ResponseBody
    public Map<String, Object> testprojectList(QueryRequest request, TestProject testproject) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<TestProject> list = this.testProjectService.findAlltestprojects(testproject,request);
        for(TestProject project:list){
            TestType testType = testTypeService.selectByKey(project.getTypeid());
            project.setTypename(testType.getName());
        }
        PageInfo<TestProject> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    @RequestMapping("testproject/excel")
    @ResponseBody
    public ResponseBo testprojectExcel(TestProject testproject) {
        try {
            List<TestProject> list = this.testProjectService.findAlltestprojects(testproject,null);
            return FileUtils.createExcelByPOIKit("测试项目表", list, TestProject.class);
        } catch (Exception e) {
            log.error("导出测试项目信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("testproject/csv")
    @ResponseBody
    public ResponseBo testprojectCsv(TestProject testproject){
        try {
            List<TestProject> list = this.testProjectService.findAlltestprojects(testproject,null);
            return FileUtils.createCsv("测试项目表", list, TestProject.class);
        } catch (Exception e) {
            log.error("导出测试项目信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("testproject/gettestproject")
    @ResponseBody
    public ResponseBo gettestproject(Integer testprojectId) {
        try {
            TestProject testproject = this.testProjectService.selectByKey(testprojectId);
            return ResponseBo.ok(testproject);
        } catch (Exception e) {
            log.error("获取测试项目信息失败", e);
            return ResponseBo.error("获取测试项目信息失败，请联系网站管理员！");
        }
    }

    @Log("新增测试项目 ")
    @RequestMapping("testproject/add")
    @ResponseBody
    public ResponseBo addtestproject(TestProject testproject) {

        try {
            Date testDate = DateUtil.strToDate("yyyy-MM-dd hh:mm:ss",testproject.getTesttimestr());
            testproject.setTesttime(testDate);
            this.testProjectService.save(testproject);
            return ResponseBo.ok("新增测试项目成功！");
        } catch (Exception e) {
            log.error("新增测试项目失败", e);
            return ResponseBo.error("新增测试项目失败，请联系网站管理员！");
        }
    }

    @Log("删除测试项目")
    @RequiresPermissions("testproject:delete")
    @RequestMapping("testproject/delete")
    @ResponseBody
    public ResponseBo deletetestprojects(String ids) {
        try {
            this.testProjectService.deletetestprojects(ids);
            return ResponseBo.ok("删除测试项目成功！");
        } catch (Exception e) {
            log.error("删除测试项目失败", e);
            return ResponseBo.error("删除测试项目失败，请联系网站管理员！");
        }
    }

    @Log("修改测试项目")
    @RequiresPermissions("testproject:update")
    @RequestMapping("testproject/update")
    @ResponseBody
    public ResponseBo updatetestproject(TestProject testproject) {
        try {
            this.testProjectService.updateNotNull(testproject);
            return ResponseBo.ok("修改测试项目成功！");
        } catch (Exception e) {
            log.error("修改测试项目失败", e);
            return ResponseBo.error("修改测试项目失败，请联系网站管理员！");
        }
    }

    @Log("根据测试类别获取测试项目")
    @RequestMapping("testproject/getByType")
    @ResponseBody
    public ResponseBo getByType(Integer typeId) {
        try {
            List<TestProject> list = testProjectService.selectAllByType(typeId);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("获取测试项目失败", e);
            return ResponseBo.error("获取测试项目失败，请联系网站管理员！");
        }
    }
}
