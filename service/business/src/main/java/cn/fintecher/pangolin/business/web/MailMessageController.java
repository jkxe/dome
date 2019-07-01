package cn.fintecher.pangolin.business.web;


import cn.fintecher.pangolin.business.model.EmailBatchSendParams;
import cn.fintecher.pangolin.business.model.EmailSendParams;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.PersonalRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.repository.TemplateRepository;
import cn.fintecher.pangolin.business.service.MessageService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.EmailMessage;
import cn.fintecher.pangolin.entity.message.HangYinEMailMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.HttpUtil;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;


/**
 * Created by Administrator on 2017/10/31.
 */

@RestController
@RequestMapping("/api/mailMessageController")
@Api(value = "邮件发送", description = "邮件发送")
public class MailMessageController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(MailMessageController.class);
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SysParamRepository sysParamRepository;
    @Autowired
    PersonalRepository personalRepository;

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    @ApiOperation(value = "发送邮件催收", notes = "发送邮件催收")
    public ResponseEntity sendMailMessage(@RequestBody EmailBatchSendParams emailBatchSendParams,
                                          @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "User is not login", "用户未登录")).body(null);
        }
        try {
            if (emailBatchSendParams != null && !CollectionUtils.isEmpty(emailBatchSendParams.getEmailSendParamList())) {

                //获取邮件发送接口地址
                SysParam smsAddressParam = getSysParamByCondition(user, Constants.EMAIL_SEND_ADDRESS);
                if (Objects.isNull(smsAddressParam)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("发送邮件", "", "未找到发送邮件接口地址的配置信息")).body(null);
                }
                String urlAddress = smsAddressParam.getValue();

                //邮件发件人
                SysParam fromMailParam = getSysParamByCondition(user, Constants.EMAIL_FROM_MAIL);
                if (Objects.isNull(smsAddressParam)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("发送邮件", "", "未找到邮件发件人地址的配置信息")).body(null);
                }
                String fromMail = fromMailParam.getValue();


                //根据邮件模板编号获取邮件模板
                Template template = templateRepository.findOne(emailBatchSendParams.getTesmId());
                Template tmp = new Template();
                BeanUtils.copyProperties(template, tmp);
                //邮件发送人员集合
                List<EmailSendParams> emailSendParamList = emailBatchSendParams.getEmailSendParamList();
                EmailMessage emailMessage = null;
                //邮箱地址
                Date currentDate = ZWDateUtil.getNowDateTime();
                for (EmailSendParams emailSendParam : emailSendParamList) {
                    //获取案件集合
                    if (emailSendParam != null && StringUtils.hasText(emailSendParam.getEmail())) {
                        //对内容进行处理
                        String templateContent="";
                        CaseInfo caseInfo = caseInfoRepository.findOne(emailSendParam.getCupoId());
                        templateContent = initEmailMessage(template, caseInfo);
                        templateContent = templateContent.replace("${receiver}", emailSendParam.getCustName());
                        templateContent = templateContent.replace("{{", "").replace("}}", "");

                        //发送邮件
//                        emailMessage = new EmailMessage();
//                        emailMessage.setTemplateContent(template.getMessageContent());
//                        emailMessage.setSendTo(emailSendParam.getEmail());
//                        emailMessage.setSendTime(currentDate);
//                        emailMessage.setTitle("邮件催收");
//                        rabbitTemplate.convertAndSend("mr.cui.mail.send", emailMessage);
                        HangYinEMailMessage hangYinEMailMessage = new HangYinEMailMessage();
                        hangYinEMailMessage.setSubject("邮件催收");
                        ArrayList<String> emailList = new ArrayList<>();
                        emailList.add(emailSendParam.getEmail());
                        hangYinEMailMessage.setEmailList(emailList);
                        hangYinEMailMessage.setContent(templateContent);
                        hangYinEMailMessage.setFromMail(fromMail);
                        log.info("开始调用邮件发送接口,请求参数{}",hangYinEMailMessage);
                        String httpStr=null;
                        try {
                            httpStr = HttpUtil.doPost(urlAddress, JSONObject.toJSON(hangYinEMailMessage));
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        log.info("邮件发送接口调用结束,返回结果:{}",httpStr);
                        JSONObject resultJson = JSONObject.parseObject(httpStr);
                        if (resultJson.get("code").equals("0")){
                            log.info("发送邮件成功!");
                            messageService.saveMessage(caseInfo, caseInfo.getPersonalInfo(), template, emailSendParam.getCustId(), user, emailBatchSendParams.getMereStyle(), SendMessageRecord.Flag.AUTOMATIC.getValue(), null);

                        }else {
                            log.info("发送邮件失败!");
                            messageService.saveMessage(caseInfo, caseInfo.getPersonalInfo(), template, emailSendParam.getCustId(), user, emailBatchSendParams.getMereStyle(), SendMessageRecord.Flag.MANUAL.getValue(), null);
                        }
                    }
                }
                templateRepository.save(tmp);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "邮箱为空")).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("发送成功", "")).body(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "发送失败")).body(null);
        }
    }


    private String initEmailMessage(Template template,CaseInfo caseInfo){
        Personal personal=personalRepository.findOne(caseInfo.getCustomerId());
        String messageContent = template.getMessageContent().replace("${cust_name}", personal.getName())
                .replace("${date}", caseInfo.getRepayDate() == null ? "" :ZWDateUtil.fomratterDate(caseInfo.getRepayDate(),"yyyy-MM-dd") .toString())
                .replace("${day}", caseInfo.getOverdueDays() == null ? "" : caseInfo.getOverdueDays().toString())
                .replace("${money}", caseInfo.getOverdueAmount() == null ? "" : caseInfo.getOverdueAmount().setScale(2).toString())
                .replace("${loanInvoiceNumber}", caseInfo.getLoanInvoiceNumber())
                .replace("${ID}", personal.getCertificatesNumber())
                .replace("${sex}", personal.getSex() == null ? "" : Personal.SexEnum.getEnumByCode(personal.getSex()).getRemark());
        return messageContent;
    }


    private SysParam getSysParamByCondition(User user, String code) {
        BooleanBuilder exp = new BooleanBuilder();
        exp.and(QSysParam.sysParam.code.eq(code));
        exp.and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode()));
        exp.and(QSysParam.sysParam.status.eq(SysParam.StatusEnum.Start.getValue()));
        return sysParamRepository.findOne(exp);
    }
}
