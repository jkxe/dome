package cn.fintecher.pangolin.dataimp.util;

import cn.fintecher.pangolin.dataimp.web.ObtainTaticsStrategyController;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.util
 * @ClassName: cn.fintecher.pangolin.dataimp.util.UserUtils
 * @date 2018年06月20日 14:07
 */
public final class UserUtils {

    private final static Logger logger = LoggerFactory.getLogger(ObtainTaticsStrategyController.class);


    public static ResponseEntity<User> checkUser(String token,RestTemplate restTemplate){
        ResponseEntity<User> userResponseEntity=null;
        try {
            userResponseEntity = restTemplate.getForEntity(Constants.USERTOKEN_SERVICE_URL.concat(token), User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("无法获取用户", "")).body(null);
        }
        return userResponseEntity;
    }



}
