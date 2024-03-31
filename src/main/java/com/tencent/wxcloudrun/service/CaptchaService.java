package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Captcha;

import java.util.Optional;

public interface CaptchaService  {
    Captcha getCaptcha(String user_id);

    void saveCaptcha(Captcha captcha);
}
