package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Captcha;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaptchaMapper {
    Captcha getCaptcha(@Param("user_id") String user_id);

    void saveCaptcha(Captcha captcha);
}
