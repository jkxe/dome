package cn.fintecher.pangolin.entity.message;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 杭银邮件发送实体
 * Created by suyuchao on 2019/01/29.
 */
@Document
@Data
public class HangYinEMailMessage {
    //邮件主题
    private String subject;
    //收件⼈邮箱地址列表
    private List<String> emailList;
    //邮件内容
    private String content;
    //发件⼈地址
    private String fromMail;
}
