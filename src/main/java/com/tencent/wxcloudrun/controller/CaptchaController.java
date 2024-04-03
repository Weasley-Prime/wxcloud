package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Captcha;
import com.tencent.wxcloudrun.service.CaptchaService;
import com.tencent.wxcloudrun.tools.AesException;
import com.tencent.wxcloudrun.vo.GetXmlMessageVo;
import com.tencent.wxcloudrun.vo.SignatureVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tencent.wxcloudrun.tools.SHA1;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class CaptchaController {
    final CaptchaService captchaService;

//    @Value("${wx.token}")
    private static String token = "wzd_token";
    final Logger logger;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @GetMapping(value = "/captcha/get")
    public ApiResponse getCaptcha(@PathParam("user_id") String user_id){
        Captcha captcha = captchaService.getCaptcha(user_id);
        if(captcha==null){
            return ApiResponse.error("没有验证码");
        }
        if(captcha.getCaptcha().isEmpty()){
            return ApiResponse.error("没有验证码");
        }
        return ApiResponse.ok(captcha);
    }

    @PostMapping(value = "/captcha/post")
    public ApiResponse saveCaptcha(@RequestBody() GetXmlMessageVo vo){
        Captcha captcha = new Captcha();
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        captcha.setCaptcha(code.toString());
        captcha.setUser_id(vo.getFromUserName());
        captcha.setMess_type(vo.getMsgType());
        captchaService.saveCaptcha(captcha);
        return ApiResponse.ok(captcha);
    }

    @GetMapping(value = "/checkSignature")
    public String checkSignature(@PathParam("signature") String signature,@PathParam("timestamp") String timestamp,@PathParam("nonce") String nonce,@PathParam("echostr") String echostr) throws AesException {
        String sha1 = SHA1.getSHA1(token, timestamp, nonce, signature);
        if(sha1 == echostr){
            return echostr;
        }
        return "不一样";
    }

}
