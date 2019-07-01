package cn.fintecher.pangolin.common.web;

import cn.fintecher.pangolin.common.exception.GeneralException;
import cn.fintecher.pangolin.common.service.UdeskCallService;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWStringUtils;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wanglu
 */
@RestController
@RequestMapping(value = "/api/udeskCallController")
@Api(value = "呼叫相关接口", description = "呼叫相关接口")
public class UdeskCallController {
    private static final Logger logger = LoggerFactory.getLogger(UdeskCallController.class);
    @Autowired
    UdeskCallService udeskCallService;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/callByPhoneNum")
    @ApiOperation(value = "电话呼叫", notes = "电话呼叫")
    public ResponseEntity<String> callByPhoneNum(@RequestParam String phoneNum,
                                                 @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        ResponseEntity<User> userResponseEntity = null;
        try {
            userResponseEntity = restTemplate.getForEntity(Constants.USERTOKEN_SERVICE_URL.concat(token), User.class);
            logger.info("呼叫请求开始: 01 ");
        } catch (Exception e) {
            logger.info("呼叫请求开始: 02 ");
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(e.getMessage(), "user", "获取不到登录人信息")).body(null);
        }
        logger.info("呼叫请求开始: 03 ");
        User user = userResponseEntity.getBody();
        if (ZWStringUtils.isEmpty(user.getChannelNo())) {
            logger.info("呼叫请求开始: 04 ");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请先配置客服邮箱")).body(null);
        }
        String callerEmail = user.getChannelNo();
        logger.info("呼叫请求开始: 05, 坐席号: "+callerEmail);
        try {
            logger.info("呼叫请求开始: 06, 坐席号: "+callerEmail);
            String callId = udeskCallService.callbyPhoneNum(phoneNum, callerEmail);
            logger.info("呼叫请求开始: 05, callId: "+callId);
            return ResponseEntity.ok(callId);
        } catch (GeneralException ge) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", ge.getMessage())).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "call failure", "呼叫失败")).body(null);
        }
    }

    @GetMapping("/getCallLog")
    @ApiOperation(value = "获取通话记录", notes = "通话记录")
    public ResponseEntity<String> getCallLog(@ApiParam(value = "通话id", required = true) @RequestParam String callId) {
        try {
            String temp = udeskCallService.getCallLogByCallId(callId);
           // String temp = udeskCallService.getCallLogByCallId("74037460-4118-4b46-790f-de858409faad");
            return ResponseEntity.ok().body(temp);
        } catch (Exception ex) {
            logger.error("失败");
            return ResponseEntity.badRequest().body(null);
        }
    }

}
