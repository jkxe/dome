package cn.fintecher.pangolin.common.service;

import cn.fintecher.pangolin.common.exception.GeneralException;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service("udeskCallService")
public class UdeskCallService {
    private final Logger logger = LoggerFactory.getLogger(UdeskCallService.class);
    @Value("${pangolin.UDesk.secret}")
    private String secret;
    @Value("${pangolin.UDesk.callUrl}")
    private String callUrl;

    public String callbyPhoneNum(String phoneNum,String callerEmail) throws GeneralException {
        String queryString = "agent_email=".concat(callerEmail).concat("&").concat("number=").concat(phoneNum).concat("&").concat("timestamp=").concat(ZWDateUtil.fomratterDate(ZWDateUtil.getNowDateTime(), "yyyyMMddHHmmss"));
        String md5Hex = DigestUtils.md5Hex(queryString.concat("&").concat(secret));
        try {
            logger.info("呼叫请求url: "+callUrl + "open_api/callcenter/agent_callout?" + queryString + "&sign=" + md5Hex);
            ResponseEntity<JSONObject> jsonObjectResponseEntity = new RestTemplate().postForEntity(callUrl + "open_api/callcenter/agent_callout?" + queryString + "&sign=" + md5Hex, null, JSONObject.class);
            logger.info("呼叫请求完成, 响应: "+ jsonObjectResponseEntity.toString());
            logger.info("呼叫请求完成, 响应code: "+ jsonObjectResponseEntity.getBody().get("code"));
            if (jsonObjectResponseEntity.hasBody() && Objects.equals(jsonObjectResponseEntity.getBody().get("code"), 1000)) {
                logger.info("呼叫请求完成, 响应call_id: "+jsonObjectResponseEntity.getBody().get("call_id"));
                return jsonObjectResponseEntity.getBody().get("call_id").toString();
            }
        } catch (Exception e) {
            logger.info("呼叫异常: "+e.getMessage(), e);
            logger.error("呼叫异常: "+e.getMessage(), e);
            throw new GeneralException("调用第三方呼叫接口失败");
        }
        return null;
    }

    public String getCallLogByCallId(String callId) throws GeneralException {
        String timeStamp = ZWDateUtil.fomratterDate(ZWDateUtil.getNowDateTime(), "yyyyMMddHHmmss");
        String queryString = "call_id=".concat(callId).concat("&timestamp=").concat(timeStamp);
        String sign = DigestUtils.md5Hex(queryString + "&" + secret);
        try {
            JSONObject jsonObject = new RestTemplate().getForObject(callUrl + "open_api/callcenter/call_log?" + queryString + "&sign=" + sign, JSONObject.class);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new GeneralException("调用第三方通话记录接口失败");
        }
    }
}
