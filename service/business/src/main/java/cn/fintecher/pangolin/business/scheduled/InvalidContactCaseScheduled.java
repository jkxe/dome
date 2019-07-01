package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.MissingConnectionInfoRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.service.ContactFeedbackService;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by duchao on 2018/6/24.
 */

@Component
//@EnableScheduling
public class InvalidContactCaseScheduled {

    private final Logger log = LoggerFactory.getLogger(InvalidContactCaseScheduled.class);

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    private ContactFeedbackService contactFeedbackService;

    @Autowired
    private MissingConnectionInfoRepository missingConnectionInfoRepository;

//    @Scheduled(cron = "0 0 2 * * ?")
    private void getInvalidContactCase() {
        try {
            log.info("开始处理失联案件...");
            missingConnectionInfoRepository.deleteAll();
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(QSysParam.sysParam.code.eq(Constants.INVALID_CONTACT_PARAMETER))
                    .and(QSysParam.sysParam.companyCode.isNotNull());
            List<SysParam> sysParamList = Lists.newArrayList(sysParamRepository.findAll(booleanBuilder));
            for (SysParam sysParam : sysParamList) {
                contactFeedbackService.getInvalidContactCase(Integer.valueOf(sysParam.getValue()), sysParam.getCompanyCode());
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
    }
}
