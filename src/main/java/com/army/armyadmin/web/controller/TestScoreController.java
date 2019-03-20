package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Log("测试成绩维护")
    @RequestMapping("testscore")
    @RequiresPermissions("testscore:list")
    public String index(){
        return "web/testscore/testscore";
    }
}
