package com.tencent.wxcloudrun.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
@JacksonXmlRootElement(localName = "xml")
@Data
public class GetXmlMessageVo {
    @JacksonXmlProperty(localName = "toUserName")
    String toUserName;
    @JacksonXmlProperty(localName = "fromUserName")
    String fromUserName;
    @JacksonXmlProperty(localName = "createTime")
    Integer createTime;
    @JacksonXmlProperty(localName = "msgType")
    String msgType;
    @JacksonXmlProperty(localName = "content")
    String content;
    @JacksonXmlProperty(localName = "msgId")
    Integer msgId;
    @JacksonXmlProperty(localName = "msgDataId")
    String msgDataId;
    @JacksonXmlProperty(localName = "idx")
    Integer idx;
}
