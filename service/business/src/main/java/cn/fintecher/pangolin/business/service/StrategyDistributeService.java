package cn.fintecher.pangolin.business.service;


import cn.fintecher.pangolin.business.model.CaseInfoDistributeParams;
import cn.fintecher.pangolin.business.model.CaseInfoDistributedModel;
import cn.fintecher.pangolin.business.repository.CaseInfoDistributedRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by  huyanmin.
 * Description:
 * Date: 2018-06-07
 */
@Service("StrategyDistributeService")
public class StrategyDistributeService {

    Logger logger = LoggerFactory.getLogger(StrategyDistributeService.class);

    @Autowired
    CaseInfoDistributedService caseInfoDistributedService;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    SysParamRepository sysParamRepository;

    @Inject
    EntityManageService entityManageService;

    @Inject
    CaseInfoService caseInfoService;


    @Async
    public void accessCountStrategyAllocation(CaseInfoDistributeParams caseInfoDistributeParams, User user) {

        SysParam system = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.SYSPARAM_STRATEGY_STATUS_CODE)
                .and(QSysParam.sysParam.type.eq(Constants.ADDRESS_STRATEGY_STATUS_TYPE))
                .and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode())));

        if (Objects.nonNull(system)) {
            if (Constants.BatchStatus.STOP.getValue().equals(system.getValue())) {
                //策略开始执行，不允许重复点击
                system.setValue(Constants.BatchStatus.RUNING.getValue());
                sysParamRepository.saveAndFlush(system);
                List<CaseInfoDistributedModel> all = new ArrayList<>();
                List<Object[]> objects;
                try {
                    logger.info("策略分配开始, 查询案件信息。。。");
                    caseInfoDistributeParams.setCompanyCode(user.getCompanyCode());
                    objects = entityManageService.getCaseInfoDistribute(caseInfoDistributeParams, "case_info_distributed");
                    all = caseInfoService.findModelList(objects, all);
                    if (all.size() > 0) {
                        countStrategyAllocation(all, user);
                        caseInfoDistributedService.reminderMessage(user.getId(), "策略分配成功", "策略分配成功提醒");
                    } else {
                        logger.info("无待分配的案件！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("获取案件遇到问题");
                }
            } else {
                logger.info("策略正在执行。。。");
            }
        }else {
            logger.info("系统参数异常");
        }
    }

    /**
     * 统计策略分配结果，每1000条分配一次
     *
     * @param all  所选择的案件数或查出所有的待分配案件数
     * @param user 用户
     * @return
     */
    public void countStrategyAllocation(List<CaseInfoDistributedModel> all, User user) {

        try {
            logger.info("策略统计结果开始的总案件数{}", all.size());
            if (all.size() <= 1000) {
                caseInfoDistributedService.countStrategyAllocation(all, user);
            } else {
                List<CaseInfoDistributedModel> caseInfoDistributeds = new ArrayList<>();
                caseInfoDistributeds.addAll(all.subList(0, 1000));
                //每1000条进行策略分配
                if (caseInfoDistributeds.size() > 0) {
                    caseInfoDistributedService.countStrategyAllocation(caseInfoDistributeds, user);
                    all.removeAll(caseInfoDistributeds);
                }
                countStrategyAllocation(all, user);
            }
        } catch (Exception e) {
            caseInfoDistributedService.reminderMessage(user.getId(), "策略分配失败", "策略分配失败提醒");
            logger.error(e.getMessage(), e);
        } finally {
            logger.info("策略分配执行结束。。。");
            SysParam system = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.SYSPARAM_STRATEGY_STATUS_CODE)
                .and(QSysParam.sysParam.type.eq(Constants.ADDRESS_STRATEGY_STATUS_TYPE))
                .and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode())));
        system.setValue(Constants.BatchStatus.STOP.getValue());
        sysParamRepository.save(system);
    }
    }

    @Async
    public void innerCountStrategyAllocation(List<CaseStrategy> caseStrategies, List<CaseInfoDistributedModel> all, User user) {

        SysParam system = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.SYSPARAM_STRATEGY_STATUS_CODE)
                .and(QSysParam.sysParam.type.eq(Constants.ADDRESS_STRATEGY_STATUS_TYPE))
                .and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode())));
        if (Objects.nonNull(system)) {
            if (Constants.BatchStatus.STOP.getValue().equals(system.getValue())) {
                try {
                    logger.info("策略统计结果开始的总案件数{}", all.size());
                    if (all.size() <= 1000) {
                        caseInfoService.innerStrategyDistribute(caseStrategies,all, user);
                    } else {
                        List<CaseInfoDistributedModel> caseInfoDistributeds = new ArrayList<>();
                        caseInfoDistributeds.addAll(all.subList(0, 1000));
                        //每1000条进行策略分配
                        if (caseInfoDistributeds.size() > 0) {
                            caseInfoService.innerStrategyDistribute(caseStrategies, caseInfoDistributeds, user);
                            all.removeAll(caseInfoDistributeds);
                        }
                        innerCountStrategyAllocation(caseStrategies, all, user);
                    }
                } catch (Exception e) {
                    caseInfoDistributedService.reminderMessage(user.getId(), "策略分配失败", "策略分配失败提醒");
                    logger.error(e.getMessage(), e);
                } finally {
                    logger.info("策略分配执行结束。。。");
                    SysParam system1 = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.SYSPARAM_STRATEGY_STATUS_CODE)
                            .and(QSysParam.sysParam.type.eq(Constants.ADDRESS_STRATEGY_STATUS_TYPE))
                            .and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode())));
                    system1.setValue(Constants.BatchStatus.STOP.getValue());
                    sysParamRepository.save(system1);
                }
            } else {
                logger.info("策略正在执行。。。");
            }
        }else {
            logger.info("系统参数异常");
        }
    }
}
