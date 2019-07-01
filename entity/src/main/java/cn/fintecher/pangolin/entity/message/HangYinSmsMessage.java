package cn.fintecher.pangolin.entity.message;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 杭银短信发送实体
 * Created by suyuchao on 2019/01/11.
 */
@Document
@Data
public class HangYinSmsMessage {
    //消息主题
    private String messageTopic;
    //手机号列表
    private List<String> phoneNumList;
    //短信内容
    private String content;
}
