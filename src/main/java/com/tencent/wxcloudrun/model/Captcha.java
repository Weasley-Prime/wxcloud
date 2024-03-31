package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Captcha implements Serializable {
    private Integer id;

    private String user_id;

    private String mess_type;

    private String captcha;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
