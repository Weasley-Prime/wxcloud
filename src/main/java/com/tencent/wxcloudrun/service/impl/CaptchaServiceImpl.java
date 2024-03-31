package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CaptchaMapper;
import com.tencent.wxcloudrun.model.Captcha;
import com.tencent.wxcloudrun.service.CaptchaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    private final CaptchaMapper captchaMapper;

    public CaptchaServiceImpl(CaptchaMapper captchaMapper) {
        this.captchaMapper = captchaMapper;
    }

    @Override
    public String getCaptcha(String user_id) {
        return captchaMapper.getCaptcha(user_id);
    }

    @Override
    public void saveCaptcha(Captcha captcha) {
        captchaMapper.saveCaptcha(captcha);
    }
}
