package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.CapaMessageParams;
import cn.fintecher.pangolin.business.model.CapaPersonals;
import cn.fintecher.pangolin.business.model.PersonalParams;
import cn.fintecher.pangolin.business.model.SMSMessageParams;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.MessageService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.HangYinSmsMessage;
import cn.fintecher.pangolin.entity.message.PaaSMessage;
import cn.fintecher.pangolin.entity.message.SMSMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.HttpUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 11:24 2017/9/1
 */
@RestController
@RequestMapping("/api/sMSMessageController")
@Api(value = "角色资源管理", description = "角色资源管理")
public class SMSMessageController extends BaseController implements Runnable {
    private static final String ENTITY_NAME = "SMSMessage";
    private final Logger logger = LoggerFactory.getLogger(SMSMessageController.class);

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    PersonalRepository personalRepository;
    @Autowired
    PersonalContactRepository personalContactRepository;
    @Autowired
    SysParamRepository sysParamRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;
    @Autowired
    SendMessageRecordRepository sendMessageRecordRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MessageService messageService;

    /**
     * 电催 电催执行页 短信Icon和一键发送短息
     */
    @PostMapping("/SendMessageSingle")
    @ApiOperation(value = "添加短信记录", notes = "添加短信记录")
    public ResponseEntity<String> addAccSMSMessageByHand(@RequestBody @ApiParam("短息信息") SMSMessageParams smsMessageParams,
                                                                       @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        Template template = templateRepository.findOne(smsMessageParams.getTesmId());
        if (Objects.isNull(template)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "没有找到模板")).body(null);
        }
        Personal personal = personalRepository.findOne(smsMessageParams.getPersonalId());
        if (Objects.isNull(personal)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "没有客户信息")).body(null);
        }
        Template temp = new Template();
        BeanUtils.copyProperties(template, temp);
        //获取短信发送系统参数
        SysParam sysParam = getSysParamByCondition(user, Constants.SMS_PUSH_CODE);
        if (Objects.isNull(sysParam)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "未找到发送短息的配置信息")).body(null);
        }
        String value = sysParam.getValue();
        //获取短信发送接口地址
        SysParam smsAddressParam = getSysParamByCondition(user, Constants.SMS_PUSH_ADDRESS);
        if (Objects.isNull(smsAddressParam)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "未找到发送短息接口地址的配置信息")).body(null);
        }
        String smsAddress = smsAddressParam.getValue();
        //查询短信发送时间间隔
        SysParam sysParamInterval = getSysParamByCondition(user, Constants.SMS_PUSH_Interval);
        Integer interval = Objects.isNull(sysParamInterval) ? 0 : Integer.parseInt(sysParamInterval.getValue());

        //联系人列表
        List<PersonalParams> personalParams = smsMessageParams.getPersonalParamsList();
        List<PersonalParams> sendFails = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        if (Objects.isNull(personalParams) || personalParams.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "没有客户信息")).body(null);
        }
        //相关案件
        BooleanBuilder caseBuilder = new BooleanBuilder();
        caseBuilder.and(QCaseInfo.caseInfo.loanInvoiceNumber.eq(smsMessageParams.getLoanInvoiceNumber())).and(QCaseInfo.caseInfo.collectionStatus.notIn(24));
        CaseInfo caseInfo = caseInfoRepository.findOne(caseBuilder);

        Integer overduePeriods = caseInfo.getOverduePeriods();
        String paramCode = "";
        if (overduePeriods < 12){
            paramCode = Constants.SMS_MAX_COUNT + overduePeriods;
        }else {
            paramCode = Constants.SMS_MAX_COUNT + 12;
        }
        SysParam sysParamSms = sysParamRepository.findOne(QSysParam.sysParam.code.eq(paramCode));
        Integer maxCount = Integer.valueOf(sysParamSms.getValue());

        BooleanBuilder sysParamBuilder = new BooleanBuilder();
        sysParamBuilder.and(QSendMessageRecord.sendMessageRecord.caseId.eq(caseInfo.getId()));
        long count = sendMessageRecordRepository.count(sysParamBuilder);
        logger.info("当前案件历史发送短信次数:{}",count);
        //短信发送数量超过当配置的最大值
        if (maxCount < count){
            PersonalParams faildParam = new PersonalParams();
            faildParam.setReason("超过M"+overduePeriods+"阶段最大短信发送量");
            sendFails.add(faildParam);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("已超过"+smsMessageParams.getLoanInvoiceNumber()+"借据M"+overduePeriods+"阶段最大短信发送量："+maxCount+"，发送失败。", "")).body(null);
        }
        //初始化消息
        PaaSMessage message = new PaaSMessage();
        String messageContent = initSmsMessage(template,caseInfo,personal);

        message.setTemplate(template.getTemplateCode());
        message.setCompanyCode(user.getCompanyCode());
        message.setContent(messageContent);
        message.setUserId(user.getId());
//        params.put("userName", personal.getName());
//        params.put("business", caseInfo.getPrincipalId().getName());
//        params.put("money", caseInfo.getOverdueAmount().toString());
        message.setParams(params);
        Thread thread = new Thread(() -> {
            //遍历所有联系人
            for (PersonalParams personalParams1 : personalParams) {
                message.setPhoneNumber(personalParams1.getPersonalPhone());
                //空号发送失败
                if (ZWStringUtils.isEmpty(message.getPhoneNumber())) {
                    sendFails.add(personalParams1);
                    //空号的话不用往数据库插入数据
                    continue;
                }
                run(interval);
                //0 ERPV3 1 极光 2 创蓝 3 数据宝 4 aliyun 5 沃动 6杭银
                try {
                    Integer flag = SendMessageRecord.Flag.AUTOMATIC.getValue();
                    switch (value) {
                        case "0":
                            SMSMessage smsMessage = new SMSMessage();
                            BeanUtils.copyProperties(message, smsMessage);
                            restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendSmsMessage"), smsMessage, String.class);
                            break;
                        case "1":
                            SMSMessage JGMessage = new SMSMessage();
                            BeanUtils.copyProperties(message, JGMessage);
                            restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendJGSmsMessage"), JGMessage, String.class);
                            break;
                        case "2":
                            restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendPaaSMessage"), message, String.class);
                            break;
                        case "3":
                            restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendLookMessage"), message, String.class);
                            break;
                        case "4":
                            restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendAliyunMessage"), message, String.class);
                            break;
                        case "6":
                            HangYinSmsMessage hangYinSmsMessage = new HangYinSmsMessage();
                            hangYinSmsMessage.setMessageTopic("催收短信");
                            hangYinSmsMessage.setContent(messageContent);
                            ArrayList<String> phoneNumList = new ArrayList<>();
                            phoneNumList.add(message.getPhoneNumber());
//                            phoneNumList.add("18621001286");
                            hangYinSmsMessage.setPhoneNumList(phoneNumList);
                            logger.info("开始调用发送短信接口,请求参数:{}",hangYinSmsMessage);
                            String httpStr=null;
                            try {
                                httpStr = HttpUtil.doPost(smsAddress, JSONObject.toJSON(hangYinSmsMessage));
                            }catch (Exception ex){
                                logger.error(ex.getMessage(), ex);
                            }
                            logger.info("调用发送短信接口结束,返回结果:{}",httpStr);
                            JSONObject resultJson = JSONObject.parseObject(httpStr);
                            if (resultJson.get("code").equals("0")){
                                logger.info("发送短信成功!");

                            }else {
                                logger.info("发送短信失败!");
                                flag = SendMessageRecord.Flag.MANUAL.getValue();
                            }
                            break;
                    }
                    //发送成功
                    messageService.saveMessage(caseInfo, personal, template, personalParams1.getContId(), user, smsMessageParams.getSendType(), flag, messageContent);
                    templateRepository.saveAndFlush(temp);
                } catch (Exception ex) {
                    //发送失败
                    personalParams1.setReason(ex.getMessage());
                    sendFails.add(personalParams1);
                    messageService.saveMessage(caseInfo, personal, template, personalParams1.getContId(), user, smsMessageParams.getSendType(), SendMessageRecord.Flag.MANUAL.getValue(), messageContent);
                    templateRepository.saveAndFlush(temp);
                }
            }
        });
        thread.start();
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("提交成功,后台已开始发送短信", "")).body("提交成功,后台已开始发送短信");
    }

    @PostMapping("/SendCapaMessageSingle")
    @ApiOperation(value = "智能短信记录", notes = "智能短信记录")
    public ResponseEntity sendCapaMessageSingle(@RequestBody @ApiParam("短息信息") CapaMessageParams capaMessageParams,
                                                                      @RequestHeader(value = "X-UserToken") String token) {
        User user;
        Integer maxCount = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }

        String alertInfo = "";
        List<CapaPersonals> capaPersonals1 = capaMessageParams.getCapaPersonals();

        Iterator<CapaPersonals> it = capaPersonals1.iterator();
        while(it.hasNext()){
            CapaPersonals capaPersonals = it.next();

            CaseInfo caseInfo = caseInfoRepository.findOne(capaPersonals.getCaseId());

            Integer overduePeriods = caseInfo.getOverduePeriods();
            String paramCode = "";
            if (overduePeriods < 12){
                paramCode = Constants.SMS_MAX_COUNT + overduePeriods;
            }else {
                paramCode = Constants.SMS_MAX_COUNT + 12;
            }
            SysParam sysParamSms = sysParamRepository.findOne(QSysParam.sysParam.code.eq(paramCode));
            maxCount = Integer.valueOf(sysParamSms.getValue());

            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(QSendMessageRecord.sendMessageRecord.caseId.eq(caseInfo.getId()));
            long count = sendMessageRecordRepository.count(booleanBuilder);
            logger.info("当前案件历史发送短信次数:{}",count);
            //短信发送数量超过当配置的最大值
            if (maxCount < count){
                it.remove();
                alertInfo = alertInfo + capaPersonals.getLoanInvoiceNumber() + ",";
            }
        }
        Template template = templateRepository.findOne(capaMessageParams.getTesmId());
        if (Objects.isNull(template)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "没有找到模板")).body(null);
        }
        //获取短信发送系统参数
        SysParam sysParam = getSysParamByCondition(user, Constants.SMS_PUSH_CODE);
        if (Objects.isNull(sysParam)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请先配置系统参数")).body(null);
        }
        String type = sysParam.getValue();
        //获取短信发送接口地址
        SysParam smsAddressParam = getSysParamByCondition(user, Constants.SMS_PUSH_ADDRESS);
        if (Objects.isNull(smsAddressParam)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "未找到发送短息接口地址的配置信息")).body(null);
        }
        String smsAddress = smsAddressParam.getValue();
        //查询短信发送时间间隔
        SysParam sysParamInterval = getSysParamByCondition(user, Constants.SMS_PUSH_Interval);
        Integer interval = Objects.isNull(sysParamInterval) ? 0 : Integer.parseInt(sysParamInterval.getValue());
        Map<String, String> params = new HashMap<>();
        List<PersonalParams> sendFails = new ArrayList<>();
        Thread thread = new Thread(() -> {
            //遍历每个客户
            for (CapaPersonals capaPersonals : capaPersonals1) {
                CaseInfo caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.id.eq(capaPersonals.getCaseId()));

                Personal personal = personalRepository.findOne(capaPersonals.getPersonalId());
                if (Objects.isNull(personal)) {
                    logger.error("没有查询到客户信息");
                    continue;
                }
                //初始化消息
                PaaSMessage message = new PaaSMessage();
                //客户姓名：${custName} 还款日期：${repayDate} 逾期天数：${overdueDays} 逾期总金额：${overdueAmount} 借据号：${loanInvoiceNumber} 性别：${sex}
                String messageContent = initSmsMessage(template,caseInfo,personal);


                messageContent = messageContent.replace("{{", "").replace("}}", "");
                message.setTemplate(template.getTemplateCode());
                message.setCompanyCode(user.getCompanyCode());
                message.setContent(messageContent);
                message.setUserId(user.getId());
//                params.put("userName", personal.getName());
//                params.put("business", caseInfo.getPrincipalId().getName());
//                params.put("money", caseInfo.getOverdueAmount().toString());
                message.setParams(params);
                //遍历每个联系人
                for (int i = 0; i < capaPersonals.getConcatIds().size(); i++) {
                    String entity = null;
                    message.setPhoneNumber(capaPersonals.getConcatPhones().get(i));
                    //空号发送失败
                    if (ZWStringUtils.isEmpty(message.getPhoneNumber())) {
                        PersonalParams personalParams = new PersonalParams();
                        personalParams.setPersonalName(capaPersonals.getConcatNames().get(i));
                        personalParams.setPersonalPhone(message.getPhoneNumber());
                        personalParams.setReason("电话号码为空");
                        sendFails.add(personalParams);
                        continue;
                    }
                    run(interval);
                    try {
                        Integer flag = SendMessageRecord.Flag.AUTOMATIC.getValue();
                        //0 ERPV3 1 极光 2 创蓝 3 数据宝 4 aliyun 5 沃动 6杭银
                        switch (type) {
                            case "0":
                                SMSMessage smsMessage = new SMSMessage();
                                BeanUtils.copyProperties(message, smsMessage);
                                smsMessage.setParams(params);
                                restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendSmsMessage"), smsMessage, String.class);
                                break;
                            case "1":
                                SMSMessage JGMessage = new SMSMessage();
                                BeanUtils.copyProperties(message, JGMessage);
                                JGMessage.setParams(params);
                                restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendJGSmsMessage"), JGMessage, String.class);
                                break;
                            case "2":
                                restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendPaaSMessage"), message, String.class);
                                break;
                            case "3":
                                restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendLookMessage"), message, String.class);
                                break;
                            case "4":
                                message.setParams(params);
                                restTemplate.postForObject(Constants.COMMON_SERVICE_SMS.concat("sendAliyunMessage"), message, String.class);
                                break;
                            case "6":
                                HangYinSmsMessage hangYinSmsMessage = new HangYinSmsMessage();
                                hangYinSmsMessage.setMessageTopic("催收短信");
                                hangYinSmsMessage.setContent(messageContent);
                                ArrayList<String> phoneNumList = new ArrayList<>();
                                phoneNumList.add(message.getPhoneNumber());
//                            phoneNumList.add("18621001286");
                                hangYinSmsMessage.setPhoneNumList(phoneNumList);
                                logger.info("开始调用发送短信接口,请求参数:{}",hangYinSmsMessage);
                                String httpStr = HttpUtil.doPost(smsAddress, JSONObject.toJSON(hangYinSmsMessage));
                                logger.info("调用发送短信接口结束,返回结果:{}",httpStr);
                                JSONObject resultJson = JSONObject.parseObject(httpStr);
                                if (resultJson.get("code").equals("0")){
                                    logger.info("发送短信成功!");

                                }else {
                                    logger.info("发送短信失败!");
                                    flag = SendMessageRecord.Flag.MANUAL.getValue();
                                }
                                break;
                        }
                        messageService.saveMessage(caseInfo, personal, template, capaPersonals.getConcatIds().get(i), user, Integer.valueOf(capaMessageParams.getSendType()), flag, messageContent);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                        PersonalParams personalParams = new PersonalParams();
                        personalParams.setPersonalName(capaPersonals.getConcatNames().get(i));
                        personalParams.setPersonalPhone(message.getPhoneNumber());
                        personalParams.setReason(ex.getMessage());
                        sendFails.add(personalParams);
                        messageService.saveMessage(caseInfo, personal, template, capaPersonals.getConcatIds().get(i), user, Integer.valueOf(capaMessageParams.getSendType()), SendMessageRecord.Flag.MANUAL.getValue(), messageContent);
                    }
                }
            }
        });
        thread.start();
        String retInfo = "提交成功,后台已开始发送短信";
        if (StringUtils.isNotBlank(alertInfo)){
            String substring = alertInfo.substring(0, alertInfo.length() - 1);
            retInfo = retInfo + ":其中借据:" + substring + "超过最大短信发送量:" + maxCount;
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(retInfo);

    }

    private String initSmsMessage(Template template,CaseInfo caseInfo, Personal personal){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String repayDate = formatter.format(caseInfo.getRepayDate());
        String messageContent = template.getMessageContent().replace("${custName}", personal.getName() == null ? "" : personal.getName())
                .replace("${repayDate}", caseInfo.getRepayDate() == null ? "" : repayDate)
                .replace("${overdueDays}", caseInfo.getOverdueDays() == null ? "" : caseInfo.getOverdueDays().toString())
                .replace("${overdueAmount}", caseInfo.getOverdueAmount() == null ? "" : caseInfo.getOverdueAmount().setScale(2).toString())
                .replace("${loanInvoiceNumber}", caseInfo.getLoanInvoiceNumber())
                .replace("${sex}", personal.getSex() == null ? "" : Personal.SexEnum.getEnumByCode(personal.getSex()).getRemark());
//客户姓名：${custName} 还款日期：${repayDate} 逾期天数：${overdueDays} 还款金额：${overdueAmount} 借据号：${loanInvoiceNumber} 性别：${sex}
        return messageContent;
    }


    private SysParam getSysParamByCondition(User user, String code) {
        BooleanBuilder exp = new BooleanBuilder();
        exp.and(QSysParam.sysParam.code.eq(code));
        exp.and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode()));
        exp.and(QSysParam.sysParam.status.eq(SysParam.StatusEnum.Start.getValue()));
        return sysParamRepository.findOne(exp);
    }

    @Override
    public void run() {
    }

    public void run(Integer time) {
        try {
            new Thread().sleep(time);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
