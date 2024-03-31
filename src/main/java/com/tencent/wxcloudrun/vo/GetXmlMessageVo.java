package com.tencent.wxcloudrun.vo;

import lombok.Data;

@Data
public class GetXmlMessageVo {
    String toUserName;

    String fromUserName;

    Integer createTime;

    String msgType;

    String content;

    Integer msgId;

    String msgDataId;

    Integer idx;
}
