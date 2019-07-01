package cn.fintecher.pangolin.common.service;

import cn.fintecher.pangolin.common.model.AddTaskRecorderRequest;
import cn.fintecher.pangolin.entity.util.Base64;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.Map;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-06-21-17:31
 */
@Service
public class CallService {
    private final Logger log = LoggerFactory.getLogger(CallService.class);
    //erpv3系统参数
    @Value("${pangolin.call-server.two-way-interface}")
    private String twoWayInterface;
    @Value("${pangolin.call-server.secret}")
    private String secret;
    //中通天鸿系统参数  联系人  师秋艳 QQ 2853152686
    private static final String timeout = "50";
    @Value("${pangolin.zhongtong-server.enterprise-code}")
    private String enterpriseCode;
    @Value("${pangolin.zhongtong-server.proceedSign}")
    private String proceedSign;
    @Value("${pangolin.zhongtong-server.cti}")
    private String cti;
    @Value("${pangolin.zhongtong-server.webCall1800}")
    private String webCall1800;
    @Value("${pangolin.zhongtong-server.secret}")
    private String ztSecret;
    //中通天鸿下载录音
    @Value("${pangolin.zhongtong-server.downloadrRecord}")
    private String downloadrRecord;
    @Value("${pangolin.zhongtong-server.recordlist}")
    private String recordlist;

    //中通天鸿的电话呼叫
    public String encode(String nonce, String created, String secret) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        String decode = decryptBASE64(nonce) + created + secret;
        return Base64.encode(md.digest(decode.getBytes()));
    }

    public String decryptBASE64(String key) {
        try {
            return new String(Base64.decode(key));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * @Description : 中通天鸿 164 中通天鸿的打电话接口
     */

    public HttpMethod getPostMethod(AddTaskRecorderRequest request) {
        PostMethod post = new PostMethod(cti);
        String Nonce = Base64.encode("123456abc".getBytes()); //随机字符串
        String Created = String.valueOf(System.currentTimeMillis());
        String PDDigest = "";
        try {
            PDDigest = encode(Nonce, Created, ztSecret);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String headerValue = "UsernameToken Username=\"" + enterpriseCode + "\",PasswordDigest=\"" + PDDigest + "\",Nonce=\"" + Nonce + "\",Created=\"" + Created + "\"";
        post.setRequestHeader("X-WSSE", headerValue);   //http请求头的键名（大小写无关）固定字符串，后面添加一个空格
//        String test = "{\"filter\":{\"start_time\":\"2014-05-06 00:00:00\"}}";
        NameValuePair model1 = new NameValuePair("caller", request.getCaller());
        NameValuePair model2 = new NameValuePair("called", request.getCallee());
        NameValuePair model3 = new NameValuePair("timeout", timeout);
        NameValuePair model4 = new NameValuePair("display_caller", proceedSign);
        NameValuePair model5 = new NameValuePair("display_called", proceedSign);
        post.setRequestBody(new NameValuePair[]{model1, model2, model3, model4, model5});
        return post;
    }

    /**
     * @Description : 中通天鸿 164 下载保存录音
     */
    public HttpMethod downloadRecord(String callId) {
        String agId = null;
        String params = "vcc_code=" + enterpriseCode + "&call_id=" + callId + "&result_type=1" + "&ag_id=" + agId;

        GetMethod get = new GetMethod(downloadrRecord + "?" + params);
        String Nonce = Base64.encode("123456abc".getBytes()); //随机字符串
        String Created = String.valueOf(System.currentTimeMillis());
        String PDDigest = "";
        try {
            PDDigest = encode(Nonce, Created, ztSecret);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String headerValue = "UsernameToken Username=\"" + enterpriseCode + "\",PasswordDigest=\"" + PDDigest + "\",Nonce=\"" + Nonce + "\",Created=\"" + Created + "\"";
        get.setRequestHeader("X-WSSE", headerValue); //http请求头的键名（大小写无关）固定字符串，后面添加一个空格
        return get;
    }

    /**
     * @Description : 中通天鸿 164 通话记录
     */
    public HttpMethod getAllVoice() {
        String startTime = ZWDateUtil.fomratterDate(ZWDateUtil.getNowDate(), "yyyy-MM-dd HH:mm:ss");
        PostMethod post = new PostMethod(recordlist);
        String Nonce = new BASE64Encoder().encode("123456abc".getBytes()); //随机字符串
        String Created = String.valueOf(System.currentTimeMillis());
        String PDDigest = "";
        try {
            PDDigest = encode(Nonce, Created, ztSecret);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String headerValue = "UsernameToken Username=\"" + enterpriseCode + "\",PasswordDigest=\"" + PDDigest + "\",Nonce=\"" + Nonce + "\",Created=\"" + Created + "\"";
        post.setRequestHeader("X-WSSE", headerValue);   //http请求头的键名（大小写无关）固定字符串，后面添加一个空格
        NameValuePair model1 = new NameValuePair("vcc_code", enterpriseCode);
        String jsonStr = "{\"filter\": {\"ag_num\": \"" + null + "\",\"start_time\":\"" + startTime + "\"}}";
        NameValuePair model2 = new NameValuePair("info", jsonStr);
        post.setRequestBody(new NameValuePair[]{model1, model2});
        return post;
    }

    /**
     * @Description : 云羿 165 打电话的签入动作
     */
    public Map<String, String> signIn(String key, String value) {
        Map<String, String> map = Constants.map;
        map.put(key, value);
        return map;
    }

    /**
     * @Description : 云羿 165 打电话的签出动作
     */
    public Map<String, String> signOut(String key, String value) {
        Map<String, String> map = Constants.map;
        map.remove(key);
        return map;
    }


}
