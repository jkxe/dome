package cn.fintecher.pangolin.business.model.out;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("借据还款调取对象")
public class Receipt {
    private String app_id;//接入平台id D18FINTELL
    private Date timestamp;//请求时间 yyyyMMddHHmmss
    private String channelSN;//渠道流水号 ,渠道码后7位+14位时间+3位数字
    private String msg_digest;//消息摘要 ,格式为：Base64(SHA1(appid+md5(timestamp)+SHA1(appsecret)))
    private String jcLtNo;//额度号
}
