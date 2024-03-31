package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Captcha;
import com.tencent.wxcloudrun.service.CaptchaService;
import com.tencent.wxcloudrun.vo.GetXmlMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Random;

@RestController
public class CaptchaController {
    final CaptchaService captchaService;
    final Logger logger;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @GetMapping(value = "/captcha/get")
    public ApiResponse getCaptcha(@PathParam("user_id") String user_id){
        String captcha = captchaService.getCaptcha(user_id);
        if(captcha.isEmpty()){
            return ApiResponse.error("没有验证码");
        }
        return ApiResponse.ok(captcha);
    }

    @PostMapping(value = "/captcha/post'")
    public ApiResponse saveCaptcha(@RequestBody() GetXmlMessageVo vo){
        Captcha captcha = new Captcha();
        Random random = new Random();
        random.ints(6);
        captcha.setCaptcha(random.ints(6).toString());
        captcha.setUser_id(vo.getFromUserName());
        captcha.setMess_type(vo.getMsgType());
        captchaService.saveCaptcha(captcha);
        return ApiResponse.ok(captcha);
    }

}
