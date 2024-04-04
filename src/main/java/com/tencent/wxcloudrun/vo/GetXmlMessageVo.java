package com.tencent.wxcloudrun.vo;


import lombok.Data;

@Data
public class GetXmlMessageVo {

    String toUserName;

    String fromUserName;

    Long createTime;

    String msgType;

    String content;

    String msgId;

    String msgDataId;

    String idx;
}
