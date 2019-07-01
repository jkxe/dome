package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.CaseInfoDistributedRepository;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.enums.EExceptionType;
import com.querydsl.core.BooleanBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定期检查分案异常案件
 */
@Configuration
@EnableScheduling
public class CaseDivisionExceptionCheckScheduled {

    private static final Logger log = LoggerFactory.getLogger(CaseDivisionExceptionCheckScheduled.class);

    @Inject
    private CaseInfoDistributedRepository caseInfoDistributedRepository;
    @Autowired
    private SysParamRepository sysParamRepository;
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Scheduled(cron = "0 00 02 * * ?")
    private void caseAutoRecoverTask() {
        log.info("定时任务开始,自动检查分案异常案件{}", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
/*            逻辑说明
            显示新入催案件X小时未分配，策略案件分配失败，催收中案件在一个队列停滞超过X小时的案件，在分案异常日志中显示异常原因。
            入催X小时的X支持配置，停滞最大时间X支持配置。
            每天定期检查，检查时间可配置。已经分配的异常案件不在列表中显示。*/
        SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.MAX_UNDISTRIBUTED_TIME));
        long maxHour = Long.parseLong(sysParam.getValue());
        SysParam sysParam1 = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.QUEUE_MAX_TIME));
        long maxQueueHour = Long.parseLong(sysParam1.getValue());
        //1.显示新入催案件X小时未分配
        //查询所有待分配案件 当前时间-流入时间 > X天
        List<CaseInfoDistributed> caseInfoDistributeds = caseInfoDistributedRepository.findAll();
        for (CaseInfoDistributed caseInfoDistributed : caseInfoDistributeds) {
            Date caseFollowInTime = caseInfoDistributed.getCaseFollowInTime();
            if (caseFollowInTime != null) {
                Date nowDate = new Date();
                long diff = nowDate.getTime() - caseFollowInTime.getTime();
                long hour = diff / (60 * 60 * 1000);
                if (hour < maxHour) {
                    continue;
                }
                caseInfoDistributed.setExceptionFlag(1);
                caseInfoDistributed.setExceptionCheckTime(nowDate);
                caseInfoDistributed.setExceptionType(EExceptionType.UNDISTRIBUTED.getValue());
            }

        }
        caseInfoDistributedRepository.save(caseInfoDistributeds);

        //3.催收中案件在一个队列停滞超过X小时的案件
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfo.caseInfo.allocateTime.isNotNull());
        builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
        Iterable<CaseInfo> iterable = caseInfoRepository.findAll(builder);
        ArrayList<CaseInfo> caseInfos = new ArrayList<>();
        for (CaseInfo caseInfo : iterable) {
            Date allocateTime = caseInfo.getAllocateTime();
            if (allocateTime != null) {
                Date nowDate = new Date();
                long diff = nowDate.getTime() - allocateTime.getTime();
                long hour = diff / (60 * 60 * 1000);
                if (hour < maxQueueHour) {
                    continue;
                }
                caseInfo.setExceptionFlag(1);
                caseInfo.setExceptionCheckTime(nowDate);
                caseInfo.setExceptionType(EExceptionType.STAGNATION_TOO_LONG.getValue());
                caseInfos.add(caseInfo);
            }
        }
        caseInfoRepository.save(caseInfos);
    }
}
