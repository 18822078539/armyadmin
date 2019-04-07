package com.army.armyadmin.web.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoreCompareController extends BaseController {

    //日志工具类
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Log("获取统计信息")
    @RequestMapping("scorecompare")
    public String index(){
        return "web/scorecompare/scorecompare";
    }
}
