package com.army.armyadmin.system.controller;

import com.army.armyadmin.common.annotation.Log;
import com.army.armyadmin.common.config.FebsProperies;
import com.army.armyadmin.common.controller.BaseController;
import com.army.armyadmin.common.domain.ResponseBo;
import com.army.armyadmin.common.service.RedisService;
import com.army.armyadmin.common.util.MD5Utils;
import com.army.armyadmin.common.util.vcode.Captcha;
import com.army.armyadmin.common.util.vcode.GifCaptcha;
import com.army.armyadmin.system.domain.User;
import com.army.armyadmin.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String CODE_KEY = "_code";

    @Autowired
    private FebsProperies febsProperies;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/login")
    public String login() {
        super.getSession();
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(String username, String password, String code, Boolean rememberMe, HttpServletRequest request) {
        if (!StringUtils.isNotBlank(code)) {
            return ResponseBo.warn("验证码不能为空！");
        }
        String codeKey = request.getRequestedSessionId() + CODE_KEY;
        String codeInRedis = redisService.get(codeKey);

        if (!StringUtils.equals(MD5Utils.encrypt(code.toLowerCase()), codeInRedis)) {
            return ResponseBo.warn("验证码错误！");
        }
        redisService.del(codeKey);
        // 密码 MD5 加密
        password = MD5Utils.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            Subject subject = getSubject();
            if (subject != null)
                subject.logout();
            super.login(token);
            this.userService.updateLoginTime(username);
            return ResponseBo.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseBo.error("认证失败！");
        }
    }

    @GetMapping(value = "gifCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            System.out.println("验证码" + request.getRequestedSessionId());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");

            Captcha captcha = new GifCaptcha(
                    febsProperies.getValidateCode().getWidth(),
                    febsProperies.getValidateCode().getHeight(),
                    febsProperies.getValidateCode().getLength());
            captcha.out(response.getOutputStream());
            String sessionId = request.getRequestedSessionId();
            if (StringUtils.isNotBlank(sessionId)) {
                redisService.set(sessionId + CODE_KEY, MD5Utils.encrypt(captcha.text().toLowerCase()));
            }
        } catch (Exception e) {
            log.error("图形验证码生成失败", e);
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }

    @Log("访问系统")
    @RequestMapping("/index")
    public String index(Model model) {
        // 登录成后，即可通过 Subject 获取登录的用户信息
        User user = super.getCurrentUser();
        model.addAttribute("user", user);
        return "index";
    }
}
