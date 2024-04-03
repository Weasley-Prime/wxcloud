package com.tencent.wxcloudrun.vo;

import lombok.Data;

@Data
public class SignatureVo {

    private String signature;

    private String timestamp;

    private String nonce;

    private String echostr;
}
