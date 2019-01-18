package top.nanli.weixin.entity.message;

import top.nanli.weixin.util.MessageType;

/**
 * @Description: 文本消息
 * @Auther: LiNan
 * @Date: 2019/1/4 21:58
 */

public class TextMessage extends BaseMessage {
    //文本消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String getMsgType() {
        return MessageType.TEXT_MESSAGE.toString();
    }

}
