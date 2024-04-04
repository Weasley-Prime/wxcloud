package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Captcha;
import com.tencent.wxcloudrun.service.CaptchaService;
import com.tencent.wxcloudrun.tools.MessageTools;
import com.tencent.wxcloudrun.vo.GetXmlMessageVo;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tencent.wxcloudrun.tools.SHA1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
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
    public String checkSignature(@PathParam("signature") String signature,@PathParam("timestamp") String timestamp,@PathParam("nonce") String nonce,@PathParam("echostr") String echostr) {
        logger.info("/checkSignature get request");
        String sha1 = SHA1.getSHA1(token, timestamp, nonce, signature);
        logger.info("sha1 is" + sha1);
//        if(sha1 == echostr){
        return echostr;
//        }
//        return "不一样";
    }

    @PostMapping(value = "/checkSignature",consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public String checkSignaturePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("/checkSignaturePost get request");
        logger.info("================================================================");
        request.setCharacterEncoding("UTF-8");

        String str = null;

        try {
//将request请求，传到Message工具类的转换方法中，返回接收到的Map对象

            Map<String,String> map = MessageTools.xmlToMap(request);

//从集合中，获取XML各个节点的内容

            String ToUserName = map.get("ToUserName");

            String FromUserName = map.get("FromUserName");

            String CreateTime = map.get("CreateTime");

            String MsgType = map.get("MsgType");

            String Content = map.get("Content");

            String MsgId = map.get("MsgId");

            if(MsgType.equals("text")) {//判断消息类型是否是文本消息(text)

                GetXmlMessageVo message = new GetXmlMessageVo();

                message.setFromUserName(ToUserName);//原来【接收消息用户】变为回复时【发送消息用户】

                message.setToUserName(FromUserName);

                message.setMsgType("text");

                message.setCreateTime(new Date().getTime());//创建当前时间为消息时间

                message.setContent("您好，" + FromUserName + "\n我是：" + ToUserName

                        + "\n您发送的消息类型为：" + MsgType + "\n您发送的时间为" + CreateTime

                        + "\n我回复的时间为：" + message.getCreateTime() + "您发送的内容是" + Content);

                str = MessageTools.objectToXml(message); //调用Message工具类，将对象转为XML字符串
            }

        } catch (DocumentException e){
            e.printStackTrace();
        }
        return str;
    }
}
