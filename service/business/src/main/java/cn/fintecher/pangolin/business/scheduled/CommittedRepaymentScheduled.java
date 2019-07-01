package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.CaseFollowupRecordRepository;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: gonghebin
 * @Date Created in 2019/1/21
 */
@Component
@EnableScheduling
public class CommittedRepaymentScheduled {
    private static final Logger logger = LoggerFactory.getLogger(CommittedRepaymentScheduled.class);

    @Autowired
    CaseFollowupRecordRepository caseFollowupRecordRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;
    @Autowired
    ReminderService reminderService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void tempService(){
        logger.info("承诺还款提醒任务开始");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(new Date());
        List<CaseFollowupRecord> all = caseFollowupRecordRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getPromiseDate() == null){
                continue;
            }
            if (df.format(all.get(i).getPromiseDate()).equals(format)){
                // 需要发送站内信息
//                List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(all.get(i).getCaseNumber());
                Iterator<CaseInfo> byCaseNumber = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(all.get(i).getCaseNumber()).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))).iterator();
//                if (byCaseNumber.size() == 0){
//                    continue;
//                }
                if (!byCaseNumber.hasNext()){
                    continue;
                }
                CaseInfo currentCaseInfo=byCaseNumber.next();
                String id = currentCaseInfo.getCurrentCollector().getId();
                String title = "承诺还款提醒";
                String content = "客户[".concat(currentCaseInfo.getPersonalInfo().getName()).concat("]的案件").concat(all.get(i).getCaseNumber()).concat("承诺今天(").concat(new SimpleDateFormat("yyyy-MM-dd").format(all.get(i).getPromiseDate())).concat(")还款，请及时跟进。");
                SendReminderMessage sendReminderMessage = new SendReminderMessage();
                sendReminderMessage.setTitle(title);
                sendReminderMessage.setContent(content);
                sendReminderMessage.setType(ReminderType.COMMITTED_REPAYMENT);
                sendReminderMessage.setMode(ReminderMode.POPUP);
                sendReminderMessage.setCreateTime(new Date());
                sendReminderMessage.setUserId(id);
                sendReminderMessage.setCaseNumber(all.get(i).getCaseNumber());
                sendReminderMessage.setCaseId(all.get(i).getCaseId());
                reminderService.sendReminder(sendReminderMessage);
            }
        }

        logger.info("承诺还款提醒任务结束");
    }
}
