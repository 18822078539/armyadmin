package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.QueryRequest;
import com.army.armyadmin.common.domain.ResponseBo;
import com.army.armyadmin.common.util.FileUtils;
import com.army.armyadmin.web.domain.TestType;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:wangjianlin
 * @Description:
 * @Date:Created in 9:22 2019/3/19
 * @Modified By:
 */
@Controller
public class TestTypeController extends BaseController{
    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestTypeService testTypeService;

    @Log("获取测试类别信息")
    @RequestMapping("testtype")
    @RequiresPermissions("testtype:list")
    public String index(){
        return "web/testtype/testtype";
    }

    @RequestMapping("testtype/list")
    @ResponseBody
    public Map<String, Object> testtypeList(QueryRequest request, TestType testtype) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<TestType> list = this.testTypeService.findAlltesttypes(testtype,request);
        PageInfo<TestType> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    @RequestMapping("testtype/excel")
    @ResponseBody
    public ResponseBo testtypeExcel(TestType testtype) {
        try {
            List<TestType> list = this.testTypeService.findAlltesttypes(testtype,null);
            return FileUtils.createExcelByPOIKit("测试类别表", list, TestType.class);
        } catch (Exception e) {
            log.error("导出测试类别信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("testtype/csv")
    @ResponseBody
    public ResponseBo testtypeCsv(TestType testtype){
        try {
            List<TestType> list = this.testTypeService.findAlltesttypes(testtype,null);
            return FileUtils.createCsv("测试类别表", list, TestType.class);
        } catch (Exception e) {
            log.error("导出测试类别信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("testtype/gettesttype")
    @ResponseBody
    public ResponseBo gettesttype(Integer testtypeId) {
        try {
            TestType testtype = this.testTypeService.selectByKey(testtypeId);
            return ResponseBo.ok(testtype);
        } catch (Exception e) {
            log.error("获取测试类别信息失败", e);
            return ResponseBo.error("获取测试类别信息失败，请联系网站管理员！");
        }
    }

    @Log("新增测试类别 ")
    @RequiresPermissions("testtype:add")
    @RequestMapping("testtype/add")
    @ResponseBody
    public ResponseBo addtesttype(TestType testtype) {
        try {
            testtype.setCreatetime(new Date());
            this.testTypeService.save(testtype);
            return ResponseBo.ok("新增测试类别成功！");
        } catch (Exception e) {
            log.error("新增测试类别失败", e);
            return ResponseBo.error("新增测试类别失败，请联系网站管理员！");
        }
    }
    @Log("获取所有测试类别 ")
    @RequestMapping("testtype/getAll")
    @ResponseBody
    public ResponseBo getAll() {
        try {
            List<TestType> list = testTypeService.selectAll();
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("新增测试类别失败", e);
            return ResponseBo.error("新增测试类别失败，请联系网站管理员！");
        }
    }

    @Log("删除测试类别")
    @RequiresPermissions("testtype:delete")
    @RequestMapping("testtype/delete")
    @ResponseBody
    public ResponseBo deletetesttypes(String ids) {
        try {
            this.testTypeService.deletetesttypes(ids);
            return ResponseBo.ok("删除测试类别成功！");
        } catch (Exception e) {
            log.error("删除测试类别失败", e);
            return ResponseBo.error("删除测试类别失败，请联系网站管理员！");
        }
    }

    @Log("修改测试类别")
    @RequiresPermissions("testtype:update")
    @RequestMapping("testtype/update")
    @ResponseBody
    public ResponseBo updatetesttype(TestType testtype) {
        try {
            this.testTypeService.updateNotNull(testtype);
            return ResponseBo.ok("修改测试类别成功！");
        } catch (Exception e) {
            log.error("修改测试类别失败", e);
            return ResponseBo.error("修改测试类别失败，请联系网站管理员！");
        }
    }
}
