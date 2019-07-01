package cn.fintecher.pangolin.report.service;


import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ExcelUtil;
import cn.fintecher.pangolin.report.entity.*;
import cn.fintecher.pangolin.report.mapper.*;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.util.ExcelExportHelper;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.hsjry.lang.common.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author : xiaqun
 * @Description : 报表服务业务
 * @Date : 13:54 2017/8/2
 */

@Service("reportService")
public class ReportService {
    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Inject
    BackMoneyReportMapper backMoneyReportMapper;

    @Inject
    PerformanceReportMapper performanceReportMapper;

    @Inject
    DailyProcessReportMapper dailyProcessReportMapper;

    @Inject
    DailyResultReportMapper dailyResultReportMapper;

    @Inject
    PerformanceRankingReportMapper performanceRankingReportMapper;

    @Inject
    SmsReportMapper smsReportMapper;

    @Inject
    RestTemplate restTemplate;
    /**
     * @Description 查询催收员回款报表
     */
    public List<BackMoneyModel> getBackMoneyReport(GeneralParams generalParams, User tokenUser) throws ParseException {
        List<BackMoneyModel> backMoneyModels = new ArrayList<>();
        List<BackMoneyReport> backMoneyReports = makeBackMoneyReport(generalParams, tokenUser);
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName()); //获取登录人的部门信息
        //构建报表展示模型
        if (backMoneyReports.isEmpty()) {
            return null;
        }
        BackMoneyModel backMoneyModel = new BackMoneyModel();
        BackMoneySecModel backMoneySecModel = new BackMoneySecModel();
        BackMoneyThiModel backMoneyThiModel = new BackMoneyThiModel();
        for (BackMoneyReport backMoneyReport : backMoneyReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(backMoneyReport.getDeptCode(), deptModel.getCode())
                        && !Objects.equals(backMoneyReport.getUserName(), tokenUser.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (backMoneyReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(backMoneyModel.getDeptCode(), backMoneyReport.getParentDeptCode())) { //一级模型中有该部门code码
                List<BackMoneySecModel> backMoneySecModelList = backMoneyModel.getBackMoneySecModels(); //获得二级模型集合
                if (Objects.isNull(backMoneySecModelList)) { //如果二级模型集合为空则new
                    backMoneySecModelList = new ArrayList<>();
                    backMoneySecModel = new BackMoneySecModel();
                    backMoneySecModel.setGroupCode(backMoneyReport.getDeptCode());
                    backMoneySecModel.setGroupName(backMoneyReport.getDeptName());

                    //回款报表三级模型
                    List<BackMoneyThiModel> backMoneyThiModelList = new ArrayList<>();
                    backMoneyThiModel = new BackMoneyThiModel();
                    backMoneyThiModel.setUserName(backMoneyReport.getUserName());
                    backMoneyThiModel.setCode(backMoneyReport.getDeptCode());

                    //回款报表集合
                    List<BackMoneyReport> backMoneyReportList = new ArrayList<>();
                    backMoneyReportList.add(backMoneyReport);

                    backMoneyThiModel.setBackMoneyReports(backMoneyReportList); //三级模型中加入报表集合
                    backMoneyThiModelList.add(backMoneyThiModel);
                    backMoneySecModel.setBackMoneyThiModels(backMoneyThiModelList); //二级模型中加入三级模型
                    backMoneySecModelList.add(backMoneySecModel);
                    backMoneyModel.setBackMoneySecModels(backMoneySecModelList); //一级模型中加入二级模型
                } else { //二级模型不为空
                    int flag1 = 0; //判断二级模型中是否包含该对象组别code
                    for (BackMoneySecModel backMoneySecModel1 : backMoneySecModelList) {
                        if (Objects.equals(backMoneySecModel1.getGroupCode(), backMoneyReport.getDeptCode())) {
                            flag1 = 1;
                            backMoneySecModel = backMoneySecModel1;
                            break;
                        }
                    }
                    if (0 == flag1) { //不包含
                        backMoneySecModel = new BackMoneySecModel();
                        backMoneySecModel.setGroupCode(backMoneyReport.getDeptCode());
                        backMoneySecModel.setGroupName(backMoneyReport.getDeptName());

                        //回款报表三级模型
                        List<BackMoneyThiModel> backMoneyThiModelList = new ArrayList<>();
                        backMoneyThiModel = new BackMoneyThiModel();
                        backMoneyThiModel.setUserName(backMoneyReport.getUserName());
                        backMoneyThiModel.setCode(backMoneyReport.getDeptCode());

                        //回款报表集合
                        List<BackMoneyReport> backMoneyReportList = new ArrayList<>();
                        backMoneyReportList.add(backMoneyReport);

                        backMoneyThiModel.setBackMoneyReports(backMoneyReportList); //三级模型中加入报表集合
                        backMoneyThiModelList.add(backMoneyThiModel);
                        backMoneySecModel.setBackMoneyThiModels(backMoneyThiModelList); //二级模型中加入三级模型
                        backMoneySecModelList.add(backMoneySecModel);
                        backMoneyModel.setBackMoneySecModels(backMoneySecModelList); //一级模型中加入二级模型
                    } else { //包含
                        List<BackMoneyThiModel> backMoneyThiModelList = backMoneySecModel.getBackMoneyThiModels(); //获取三级模型
                        int flag2 = 0; //判断三级模型中是否包含该对象的部门code
                        for (BackMoneyThiModel backMoneyThiModel1 : backMoneyThiModelList) {
                            if (Objects.equals(backMoneyThiModel1.getCode(), backMoneyReport.getDeptCode())) {
                                flag2 = 1;
                                backMoneyThiModel = backMoneyThiModel1;
                                break;
                            }
                        }
                        if (0 == flag2) { //不包含
                            backMoneyThiModel = new BackMoneyThiModel();
                            backMoneyThiModel.setUserName(backMoneyReport.getUserName());
                            backMoneyThiModel.setCode(backMoneyReport.getDeptCode());

                            //回款报表集合
                            List<BackMoneyReport> backMoneyReportList = new ArrayList<>();
                            backMoneyReportList.add(backMoneyReport);
                            backMoneyThiModel.setBackMoneyReports(backMoneyReportList); //三级模型中加入报表集合
                            backMoneyThiModelList.add(backMoneyThiModel);
                        } else { //包含
                            List<BackMoneyReport> backMoneyReportList = backMoneyThiModel.getBackMoneyReports();
                            backMoneyReportList.add(backMoneyReport);
                            backMoneyThiModel.setBackMoneyReports(backMoneyReportList); //三级模型中加入报表集合
                        }
                    }
                }
            } else { //一级模型中没有该部门code码
                //回款报表一级模型
                backMoneyModel = new BackMoneyModel();
                backMoneyModel.setDeptCode(backMoneyReport.getParentDeptCode());
                backMoneyModel.setDeptName(backMoneyReport.getParentDeptName());

                //回款报表二级模型
                List<BackMoneySecModel> backMoneySecModelList = new ArrayList<>();
                backMoneySecModel = new BackMoneySecModel();
                backMoneySecModel.setGroupCode(backMoneyReport.getDeptCode());
                backMoneySecModel.setGroupName(backMoneyReport.getDeptName());

                //回款报表三级模型
                List<BackMoneyThiModel> backMoneyThiModelList = new ArrayList<>();
                backMoneyThiModel = new BackMoneyThiModel();
                backMoneyThiModel.setUserName(backMoneyReport.getUserName());
                backMoneyThiModel.setCode(backMoneyReport.getDeptCode());

                //回款报表集合
                List<BackMoneyReport> backMoneyReportList = new ArrayList<>();
                backMoneyReportList.add(backMoneyReport);
                backMoneyThiModelList.add(backMoneyThiModel);

                backMoneyThiModel.setBackMoneyReports(backMoneyReportList); //三级模型中加入报表集合
                backMoneySecModel.setBackMoneyThiModels(backMoneyThiModelList); //二级模型中加入三级模型
                backMoneySecModelList.add(backMoneySecModel);
                backMoneyModel.setBackMoneySecModels(backMoneySecModelList); //一级模型中加入二级模型

                backMoneyModels.add(backMoneyModel);
            }
        }
        return backMoneyModels;
    }

    /**
     * @Description 催收员业绩进展报表
     */
    public List<PerformanceModel> getPerformanceReport(PerformanceParams performanceParams, User tokenUser) {
        List<PerformanceModel> performanceModels = new ArrayList<>();
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName()); //获取登录人的部门信息
        List<PerformanceBasisModel> performanceBasisModels;
        if (Objects.isNull(tokenUser.getCompanyCode())) {
            performanceBasisModels = performanceReportMapper.getPerformanceReport(deptModel.getCode(), performanceParams.getCode(), StringUtils.trim(performanceParams.getUserName()), performanceParams.getCompanyCode());
        } else {
            performanceBasisModels = performanceReportMapper.getPerformanceReport(deptModel.getCode(), performanceParams.getCode(), StringUtils.trim(performanceParams.getUserName()), tokenUser.getCompanyCode());
        }
        if (performanceBasisModels.isEmpty()) {
            return null;
        }
        //构建报表展示模型
        PerformanceModel performanceModel = new PerformanceModel();
        PerformanceSecModel performanceSecModel = new PerformanceSecModel();
        for (PerformanceBasisModel performanceBasisModel : performanceBasisModels) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(deptModel.getCode(), performanceBasisModel.getDeptCode())
                        && !Objects.equals(tokenUser.getUserName(), performanceBasisModel.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (performanceBasisModel.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(performanceModel.getDeptCode(), performanceBasisModel.getParentDeptCode())) { //一级模型中有该部门code码
                List<PerformanceSecModel> performanceSecModels = performanceModel.getPerformanceSecModels(); //获得二级模型集合
                if (Objects.isNull(performanceSecModels)) { //如果二级模型集合为空则new
                    performanceSecModels = new ArrayList<>();

                    //二级模型
                    performanceSecModel = new PerformanceSecModel();
                    performanceSecModel.setGroupName(performanceBasisModel.getDeptCode());
                    performanceSecModel.setGroupName(performanceBasisModel.getDeptName());

                    //基础模型
                    List<PerformanceBasisModel> performanceBasisModelList = new ArrayList<>();
                    performanceBasisModelList.add(performanceBasisModel);

                    performanceSecModel.setPerformanceBasisModels(performanceBasisModelList); //二级模型中加入基础模型
                    performanceSecModels.add(performanceSecModel);
                    performanceModel.setPerformanceSecModels(performanceSecModels); //一级模型中加入二级模型
                } else { //二级模型不为空
                    int flag = 0; //判断二级模型中是否包含该code码
                    for (PerformanceSecModel performanceSecModel1 : performanceSecModels) {
                        if (Objects.equals(performanceSecModel1.getGroupCode(), performanceBasisModel.getDeptCode())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (0 == flag) { //不包含
                        performanceSecModel = new PerformanceSecModel();
                        performanceSecModel.setGroupCode(performanceBasisModel.getDeptCode());
                        performanceSecModel.setGroupName(performanceBasisModel.getDeptName());

                        //基础模型
                        List<PerformanceBasisModel> performanceBasisModelList = new ArrayList<>();
                        performanceBasisModelList.add(performanceBasisModel);
                        performanceSecModel.setPerformanceBasisModels(performanceBasisModelList); //二级模型中加入基础模型
                        performanceSecModels.add(performanceSecModel);
                    } else { //包含
                        List<PerformanceBasisModel> performanceBasisModelList = performanceSecModel.getPerformanceBasisModels();
                        performanceBasisModelList.add(performanceBasisModel);
                        performanceSecModel.setPerformanceBasisModels(performanceBasisModelList); //二级模型中加入基础模型
                    }
                }
            } else { //一级模型中没有该部门code码
                //一级模型
                performanceModel = new PerformanceModel();
                performanceModel.setDeptCode(performanceBasisModel.getParentDeptCode());
                performanceModel.setDeptName(performanceBasisModel.getParentDeptName());

                //二级模型
                List<PerformanceSecModel> performanceSecModels = new ArrayList<>();
                performanceSecModel = new PerformanceSecModel();
                performanceSecModel.setGroupCode(performanceBasisModel.getDeptCode());
                performanceSecModel.setGroupName(performanceBasisModel.getDeptName());

                //基础模型
                List<PerformanceBasisModel> performanceBasisModelList = new ArrayList<>();
                performanceBasisModelList.add(performanceBasisModel);

                performanceSecModel.setPerformanceBasisModels(performanceBasisModelList); //二级模型中加入基础模型
                performanceSecModels.add(performanceSecModel);
                performanceModel.setPerformanceSecModels(performanceSecModels); //一级模型中加入二级模型

                performanceModels.add(performanceModel);
            }
        }
        return performanceModels;
    }

    /**
     * @Description 催收员每日催收过程报表
     */
    public List<DailyProcessModel> getDailyProcessReport(GeneralParams generalParams, User tokenUser) throws ParseException {
        List<DailyProcessModel> dailyProcessModels = new ArrayList<>();
        String deptCode = backMoneyReportMapper.getDeptCode(generalParams.getId());
        List<DailyProcessReport> dailyProcessReports;
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName()); //获取登录人的部门信息
        if (Objects.equals(generalParams.getType(), 0)) { //实时报表
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                dailyProcessReports = dailyProcessReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode()); //获得实时报表
            } else {
                dailyProcessReports = dailyProcessReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode()); //获得实时报表
            }
        } else { //历史报表
            Date date1 = ZWDateUtil.getFormatDate(generalParams.getStartDate());
            Date date2 = ZWDateUtil.getFormatDate(generalParams.getEndDate());
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                dailyProcessReports = dailyProcessReportMapper.getHistoryReport(date1, date2,
                        deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else {
                dailyProcessReports = dailyProcessReportMapper.getHistoryReport(date1, date2,
                        deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        }
        //构建报表展示模型
        if (dailyProcessReports.isEmpty()) { //如果报表为空则返回null
            return null;
        }
        DailyProcessModel dailyProcessModel = new DailyProcessModel();
        DailyProcessSecModel dailyProcessSecModel = new DailyProcessSecModel();
        DailyProcessThiModel dailyProcessThiModel = new DailyProcessThiModel();
        for (DailyProcessReport dailyProcessReport : dailyProcessReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(deptModel.getCode(), dailyProcessReport.getDeptCode())
                        && !Objects.equals(tokenUser.getUserName(), dailyProcessReport.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (dailyProcessReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(dailyProcessModel.getDeptCode(), dailyProcessReport.getParentDeptCode())) { //一级模型中有该部门code码
                List<DailyProcessSecModel> dailyProcessSecModels = dailyProcessModel.getDailyProcessSecModels();
                if (Objects.isNull(dailyProcessSecModels)) { //如果二级模型为空则new
                    dailyProcessSecModels = new ArrayList<>();
                    //二级模型
                    dailyProcessSecModel = new DailyProcessSecModel();
                    dailyProcessSecModel.setGroupCode(dailyProcessReport.getDeptCode());
                    dailyProcessSecModel.setGroupName(dailyProcessReport.getDeptName());

                    //三级模型
                    List<DailyProcessThiModel> dailyProcessThiModels = new ArrayList<>();
                    dailyProcessThiModel = new DailyProcessThiModel();
                    dailyProcessThiModel.setDeptCode(dailyProcessReport.getDeptCode());
                    dailyProcessThiModel.setUserName(dailyProcessReport.getUserName());

                    //基础报表模型
                    List<DailyProcessReport> dailyProcessReportList = new ArrayList<>();
                    dailyProcessReportList.add(dailyProcessReport);

                    dailyProcessThiModel.setDailyProcessReports(dailyProcessReportList); //三级模型中加入基础模型
                    dailyProcessThiModels.add(dailyProcessThiModel);
                    dailyProcessSecModel.setDailyProcessThiModels(dailyProcessThiModels); //二级模型中加入三级模型
                    dailyProcessSecModels.add(dailyProcessSecModel);
                    dailyProcessModel.setDailyProcessSecModels(dailyProcessSecModels); //一级模型中加入二级模型
                } else { //不为空
                    int flag1 = 0; //判断二级模型中是否有该code码
                    for (DailyProcessSecModel dailyProcessSecModel1 : dailyProcessSecModels) {
                        if (Objects.equals(dailyProcessSecModel1.getGroupCode(), dailyProcessReport.getDeptCode())) {
                            flag1 = 1;
                            break;
                        }
                    }
                    if (0 == flag1) { //如果不包含
                        //二级模型
                        dailyProcessSecModel = new DailyProcessSecModel();
                        dailyProcessSecModel.setGroupCode(dailyProcessReport.getDeptCode());
                        dailyProcessSecModel.setGroupName(dailyProcessReport.getDeptName());

                        //三级模型
                        List<DailyProcessThiModel> dailyProcessThiModels = new ArrayList<>();
                        dailyProcessThiModel = new DailyProcessThiModel();
                        dailyProcessThiModel.setDeptCode(dailyProcessReport.getDeptCode());
                        dailyProcessThiModel.setUserName(dailyProcessReport.getUserName());

                        //基础模型
                        List<DailyProcessReport> dailyProcessReportList = new ArrayList<>();
                        dailyProcessReportList.add(dailyProcessReport);

                        dailyProcessThiModel.setDailyProcessReports(dailyProcessReportList); //三级模型中加入基础模型
                        dailyProcessThiModels.add(dailyProcessThiModel);
                        dailyProcessSecModel.setDailyProcessThiModels(dailyProcessThiModels); //二级模型中加入三级模型
                        dailyProcessSecModels.add(dailyProcessSecModel);
                        dailyProcessModel.setDailyProcessSecModels(dailyProcessSecModels); //一级模型中加入二级模型
                    } else { //如果包含
                        List<DailyProcessThiModel> dailyProcessThiModels = dailyProcessSecModel.getDailyProcessThiModels(); //获得三级模型集合
                        int flag2 = 0; //判断三级模型中是否包含该code码
                        for (DailyProcessThiModel dailyProcessThiModel1 : dailyProcessThiModels) {
                            if (Objects.equals(dailyProcessThiModel1.getDeptCode(), dailyProcessReport.getDeptCode())) {
                                flag2 = 1;
                                break;
                            }
                        }
                        if (0 == flag2) { //不包含
                            //三级模型
                            dailyProcessThiModel = new DailyProcessThiModel();
                            dailyProcessThiModel.setDeptCode(dailyProcessReport.getDeptCode());
                            dailyProcessThiModel.setUserName(dailyProcessReport.getUserName());

                            //基础模型
                            List<DailyProcessReport> dailyProcessReportList = new ArrayList<>();
                            dailyProcessReportList.add(dailyProcessReport);
                            dailyProcessThiModel.setDailyProcessReports(dailyProcessReportList); //三级模型中加入基础模型
                            dailyProcessThiModels.add(dailyProcessThiModel);
                        } else { //包含
                            List<DailyProcessReport> dailyProcessReportList = dailyProcessThiModel.getDailyProcessReports();
                            dailyProcessReportList.add(dailyProcessReport);
                            dailyProcessThiModel.setDailyProcessReports(dailyProcessReportList);
                        }
                    }
                }
            } else { //一级模型中没有该部门code码
                //一级模型
                dailyProcessModel = new DailyProcessModel();
                dailyProcessModel.setDeptCode(dailyProcessReport.getParentDeptCode());
                dailyProcessModel.setDeptName(dailyProcessReport.getParentDeptName());

                //二级模型
                List<DailyProcessSecModel> dailyProcessSecModels = new ArrayList<>();
                dailyProcessSecModel = new DailyProcessSecModel();
                dailyProcessSecModel.setGroupCode(dailyProcessReport.getDeptCode());
                dailyProcessSecModel.setGroupName(dailyProcessReport.getDeptName());

                //三级模型
                List<DailyProcessThiModel> dailyProcessThiModels = new ArrayList<>();
                dailyProcessThiModel = new DailyProcessThiModel();
                dailyProcessThiModel.setDeptCode(dailyProcessReport.getDeptCode());
                dailyProcessThiModel.setUserName(dailyProcessReport.getUserName());

                //基础报表模型
                List<DailyProcessReport> dailyProcessReportList = new ArrayList<>();
                dailyProcessReportList.add(dailyProcessReport);

                dailyProcessThiModel.setDailyProcessReports(dailyProcessReportList); //三级模型中加入基础模型
                dailyProcessThiModels.add(dailyProcessThiModel);
                dailyProcessSecModel.setDailyProcessThiModels(dailyProcessThiModels); //二级模型中就加入三级模型
                dailyProcessSecModels.add(dailyProcessSecModel);
                dailyProcessModel.setDailyProcessSecModels(dailyProcessSecModels); //一级模型中加入二级模型

                dailyProcessModels.add(dailyProcessModel);
            }
        }
        return dailyProcessModels;
    }

    /**
     * @Description 催收员每日催收结果报表
     */
    public List<DailyResultModel> getDailyResultReport(GeneralParams generalParams, User tokenUser) throws ParseException {
        List<DailyResultModel> dailyResultModels = new ArrayList<>();
        String deptCode = backMoneyReportMapper.getDeptCode(generalParams.getId());
        List<DailyResultReport> dailyResultReports;
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName()); //获取登录人的部门信息
        if (Objects.equals(generalParams.getType(), 0)) { //实时报表
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                dailyResultReports = dailyResultReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else {
                dailyResultReports = dailyResultReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        } else { //历史报表
            Date date1 = ZWDateUtil.getFormatDate(generalParams.getStartDate());
            Date date2 = ZWDateUtil.getFormatDate(generalParams.getEndDate());
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                dailyResultReports = dailyResultReportMapper.getHistoryReport(date1, date2,
                        deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else {
                dailyResultReports = dailyResultReportMapper.getHistoryReport(date1, date2,
                        deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        }
        //构建报表展示模型
        if (Objects.isNull(dailyResultReports)) {
            return null;
        }
        DailyResultModel dailyResultModel = new DailyResultModel();
        DailyResultSecModel dailyResultSecModel = new DailyResultSecModel();
        DailyResultThiModel dailyResultThiModel = new DailyResultThiModel();
        for (DailyResultReport dailyResultReport : dailyResultReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(deptModel.getCode(), dailyResultReport.getDeptCode())
                        && !Objects.equals(tokenUser.getUserName(), dailyResultReport.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (dailyResultReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(dailyResultModel.getDeptCode(), dailyResultReport.getParentDeptCode())) { //一级模型中有该部门code码
                List<DailyResultSecModel> dailyResultSecModels = dailyResultModel.getDailyResultSecModels();
                if (Objects.isNull(dailyResultSecModels)) { //如果二级模型集合为空则new
                    dailyResultSecModels = new ArrayList<>();
                    //二级模型
                    dailyResultSecModel = new DailyResultSecModel();
                    dailyResultSecModel.setGroupCode(dailyResultReport.getDeptCode());
                    dailyResultSecModel.setGroupName(dailyResultReport.getDeptName());

                    //三级模型
                    List<DailyResultThiModel> dailyResultThiModels = new ArrayList<>();
                    dailyResultThiModel = new DailyResultThiModel();
                    dailyResultThiModel.setDeptCode(dailyResultReport.getDeptCode());
                    dailyResultThiModel.setUserName(dailyResultReport.getUserName());

                    //基础报表模型
                    List<DailyResultReport> dailyResultReportList = new ArrayList<>();
                    dailyResultReportList.add(dailyResultReport);

                    dailyResultThiModel.setDailyResultReports(dailyResultReportList); //三级模型中加入基础模型
                    dailyResultThiModels.add(dailyResultThiModel);
                    dailyResultSecModel.setDailyResultThiModels(dailyResultThiModels); //二级模型中加入三级模型
                    dailyResultSecModels.add(dailyResultSecModel);
                    dailyResultModel.setDailyResultSecModels(dailyResultSecModels); //一级模型中加入二级模型
                } else { //不为空
                    int flag1 = 0; //判断二级模型中是否有该code码
                    for (DailyResultSecModel dailyResultSecModel1 : dailyResultSecModels) {
                        if (Objects.equals(dailyResultSecModel1.getGroupCode(), dailyResultReport.getDeptCode())) {
                            flag1 = 1;
                            break;
                        }
                    }
                    if (0 == flag1) { //如果不包含
                        //二级模型
                        dailyResultSecModel = new DailyResultSecModel();
                        dailyResultSecModel.setGroupCode(dailyResultReport.getDeptCode());
                        dailyResultSecModel.setGroupName(dailyResultReport.getDeptName());

                        //三级模型
                        List<DailyResultThiModel> dailyResultThiModels = new ArrayList<>();
                        dailyResultThiModel = new DailyResultThiModel();
                        dailyResultThiModel.setDeptCode(dailyResultReport.getDeptCode());
                        dailyResultThiModel.setUserName(dailyResultReport.getUserName());

                        //基础模型
                        List<DailyResultReport> dailyResultReportList = new ArrayList<>();
                        dailyResultReportList.add(dailyResultReport);

                        dailyResultThiModel.setDailyResultReports(dailyResultReportList); //三级模型中加入基础模型
                        dailyResultThiModels.add(dailyResultThiModel);
                        dailyResultSecModel.setDailyResultThiModels(dailyResultThiModels); //二级模型中加入三级模型
                        dailyResultSecModels.add(dailyResultSecModel);
                        dailyResultModel.setDailyResultSecModels(dailyResultSecModels); //一级模型中加入二级模型
                    } else { //如果包含
                        List<DailyResultThiModel> dailyResultThiModels = dailyResultSecModel.getDailyResultThiModels(); //获得三级模型集合
                        int flag2 = 0; //判断三级模型中是否包含该code码
                        for (DailyResultThiModel dailyResultThiModel1 : dailyResultThiModels) {
                            if (Objects.equals(dailyResultThiModel1.getDeptCode(), dailyResultReport.getDeptCode())) {
                                flag2 = 1;
                                break;
                            }
                        }
                        if (0 == flag2) { //不包含
                            //三级模型
                            dailyResultThiModel = new DailyResultThiModel();
                            dailyResultThiModel.setDeptCode(dailyResultReport.getDeptCode());
                            dailyResultThiModel.setUserName(dailyResultReport.getUserName());

                            //基础模型
                            List<DailyResultReport> dailyResultReportList = new ArrayList<>();
                            dailyResultReportList.add(dailyResultReport);
                            dailyResultThiModel.setDailyResultReports(dailyResultReportList); //三级模型中加入基础模型
                            dailyResultThiModels.add(dailyResultThiModel);
                        } else { //包含
                            List<DailyResultReport> dailyResultReportList = dailyResultThiModel.getDailyResultReports();
                            dailyResultReportList.add(dailyResultReport);
                            dailyResultThiModel.setDailyResultReports(dailyResultReportList);
                        }
                    }
                }
            } else { //一级模型中没有该部门code码
                //一级模型
                dailyResultModel = new DailyResultModel();
                dailyResultModel.setDeptCode(dailyResultReport.getParentDeptCode());
                dailyResultModel.setDeptName(dailyResultReport.getParentDeptName());

                //二级模型
                List<DailyResultSecModel> dailyResultSecModels = new ArrayList<>();
                dailyResultSecModel = new DailyResultSecModel();
                dailyResultSecModel.setGroupCode(dailyResultReport.getDeptCode());
                dailyResultSecModel.setGroupName(dailyResultReport.getDeptName());

                //三级模型
                List<DailyResultThiModel> dailyResultThiModels = new ArrayList<>();
                dailyResultThiModel = new DailyResultThiModel();
                dailyResultThiModel.setDeptCode(dailyResultReport.getDeptCode());
                dailyResultThiModel.setUserName(dailyResultReport.getUserName());

                //基础报表模型
                List<DailyResultReport> dailyResultReportList = new ArrayList<>();
                dailyResultReportList.add(dailyResultReport);

                dailyResultThiModel.setDailyResultReports(dailyResultReportList); //三级模型中加入基础模型
                dailyResultThiModels.add(dailyResultThiModel);
                dailyResultSecModel.setDailyResultThiModels(dailyResultThiModels); //二级模型中就加入三级模型
                dailyResultSecModels.add(dailyResultSecModel);
                dailyResultModel.setDailyResultSecModels(dailyResultSecModels); //一级模型中加入二级模型

                dailyResultModels.add(dailyResultModel);
            }
        }
        return dailyResultModels;
    }

    /**
     * @Description 催收员业绩排名报表
     */
    public List<CollectorPerformanceModel> getPerformanceRankingReport(PerformanceRankingParams performanceRankingParams, User tokenUser) {
        //获得催收员业绩排名报表
        List<PerformanceRankingReport> performanceRankingReports = getRankingReport(performanceRankingParams, tokenUser, 0);
        if (Objects.isNull(performanceRankingReports)) {
            return null;
        }

        //构建报表展示模型
        List<CollectorPerformanceModel> collectorPerformanceModels = new ArrayList<>();
        CollectorPerformanceModel collectorPerformanceModel = new CollectorPerformanceModel();
        for (PerformanceRankingReport performanceRankingReport : performanceRankingReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(tokenUser.getDepartment().getCode(), performanceRankingReport.getDeptCode())
                        && !Objects.equals(tokenUser.getUserName(), performanceRankingReport.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (performanceRankingReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(collectorPerformanceModel.getDeptCode(), performanceRankingReport.getParentDeptCode())) { //如果部门code码不为空
                List<PerformanceRankingReport> performanceRankingReports1 = collectorPerformanceModel.getPerformanceRankingReports();
                if (Objects.isNull(performanceRankingReports1)) {
                    performanceRankingReports1 = new ArrayList<>();
                }
                performanceRankingReports1.add(performanceRankingReport);
            } else { //部门code码为空
                collectorPerformanceModel = new CollectorPerformanceModel();
                collectorPerformanceModel.setDeptCode(performanceRankingReport.getParentDeptCode()); //部门code码
                collectorPerformanceModel.setDeptName(performanceRankingReport.getParentDeptName()); //部门名称

                List<PerformanceRankingReport> performanceRankingReportList = new ArrayList<>();
                performanceRankingReportList.add(performanceRankingReport); //催收员业绩排名报表集合
                collectorPerformanceModel.setPerformanceRankingReports(performanceRankingReportList);
                collectorPerformanceModels.add(collectorPerformanceModel);
            }
        }

        //添加组长姓名
        for (CollectorPerformanceModel collectorPerformanceModel2 : collectorPerformanceModels) {
            List<PerformanceRankingReport> performanceRankingReportList = collectorPerformanceModel2.getPerformanceRankingReports();
            saveGroupLeader(performanceRankingReportList);
        }

        //添加累计,排名
        for (CollectorPerformanceModel collectorPerformanceModel1 : collectorPerformanceModels) {
            List<PerformanceRankingReport> performanceRankingReports1 = collectorPerformanceModel1.getPerformanceRankingReports();
            performanceRankingReports1.sort((o1, o2) -> o2.getMonthBackMoney().compareTo(o1.getMonthBackMoney()));
            doCalculate(performanceRankingReports1);
        }
        return collectorPerformanceModels;
    }

    /**
     * @Description 催收员业绩报名小组汇总报表
     */
    public List<PerformanceSummaryModel> getSummaryReport(PerformanceRankingParams performanceRankingParams, User tokenUser) {
        //获得催收员业绩排名报表
        List<PerformanceRankingReport> performanceRankingReports;
        if (Objects.equals(performanceRankingParams.getType(), 0)) { //实时报表
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DATE, 1);
            Date startDate = cal.getTime();
            Date endDate = ZWDateUtil.getNowDate();
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                performanceRankingReports = performanceRankingReportMapper.getRealtimeSummaryReport(startDate, endDate, performanceRankingParams.getCompanyCode(),
                        tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
            } else {
                performanceRankingReports = performanceRankingReportMapper.getRealtimeSummaryReport(startDate, endDate, tokenUser.getCompanyCode(),
                        tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
            }
        } else { //历史报表
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                performanceRankingReports = performanceRankingReportMapper.getHistorySummayReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), performanceRankingParams.getCompanyCode(),
                        tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
            } else {
                performanceRankingReports = performanceRankingReportMapper.getHistorySummayReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), tokenUser.getCompanyCode(),
                        tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
            }
        }
        if (performanceRankingReports.isEmpty()) {
            return null;
        }

        //构建展示模型
        List<PerformanceSummaryModel> performanceSummaryModels = new ArrayList<>();
        PerformanceSummaryModel performanceSummaryModel = new PerformanceSummaryModel();
        PerformanceSummarySecModel performanceSummarySecModel = new PerformanceSummarySecModel();
        for (PerformanceRankingReport performanceRankingReport : performanceRankingReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(tokenUser.getDepartment().getCode(), performanceRankingReport.getDeptCode())
                        && !Objects.equals(tokenUser.getUserName(), performanceRankingReport.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (performanceRankingReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(performanceSummaryModel.getDeptCode(), performanceRankingReport.getParentDeptCode())) { //一级模型中有该部门code码
                List<PerformanceSummarySecModel> performanceSummarySecModels = performanceSummaryModel.getPerformanceSummarySecModels();
                if (Objects.isNull(performanceSummarySecModels)) { //如果二级模型集合为null则new
                    //二级模型
                    List<PerformanceSummarySecModel> performanceSummarySecModelList = new ArrayList<>();
                    performanceSummarySecModel = new PerformanceSummarySecModel();
                    performanceSummarySecModel.setGroupCode(performanceRankingReport.getDeptCode()); //组别code码
                    performanceSummarySecModel.setGroupName(performanceRankingReport.getDeptName()); //组别名称

                    //基础模型
                    List<PerformanceRankingReport> performanceRankingReportList = new ArrayList<>();
                    performanceRankingReportList.add(performanceRankingReport);
                    performanceSummarySecModel.setPerformanceRankingReports(performanceRankingReportList); //二级模型中加入基础报表模型
                    performanceSummarySecModelList.add(performanceSummarySecModel);

                    performanceSummaryModel.setPerformanceSummarySecModels(performanceSummarySecModelList); //一级模型中加入二级模型
                } else { //有二级模型集合
                    int flag = 0; //判断二级模型中是否有该code码
                    for (PerformanceSummarySecModel performanceSummarySecModel1 : performanceSummarySecModels) {
                        if (Objects.equals(performanceSummarySecModel1.getGroupCode(), performanceRankingReport.getDeptCode())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (0 == flag) { //不包含
                        //二级模型
                        performanceSummarySecModel = new PerformanceSummarySecModel();
                        performanceSummarySecModel.setGroupCode(performanceRankingReport.getDeptCode()); //组别code码
                        performanceSummarySecModel.setGroupName(performanceRankingReport.getDeptName()); //组别名称

                        List<PerformanceRankingReport> performanceRankingReportList = new ArrayList<>();
                        performanceRankingReportList.add(performanceRankingReport);
                        performanceSummarySecModel.setPerformanceRankingReports(performanceRankingReportList); //二级模型中加入基础报表集合
                        performanceSummarySecModels.add(performanceSummarySecModel);
                    } else { //包含
                        List<PerformanceRankingReport> performanceRankingReportList = performanceSummarySecModel.getPerformanceRankingReports();
                        performanceRankingReportList.add(performanceRankingReport);
                        performanceSummarySecModel.setPerformanceRankingReports(performanceRankingReportList);
                    }
                }
            } else { //一级模型中没有该部门code码
                //一级模型
                performanceSummaryModel = new PerformanceSummaryModel();
                performanceSummaryModel.setDeptCode(performanceRankingReport.getParentDeptCode()); //部门code码
                performanceSummaryModel.setDeptName(performanceRankingReport.getParentDeptName()); //部门名称

                //二级模型
                List<PerformanceSummarySecModel> performanceSummarySecModels = new ArrayList<>();
                performanceSummarySecModel = new PerformanceSummarySecModel();
                performanceSummarySecModel.setGroupCode(performanceRankingReport.getDeptCode()); //组别code码
                performanceSummarySecModel.setGroupName(performanceRankingReport.getDeptName()); //组别名称

                //基础报表
                List<PerformanceRankingReport> performanceRankingReportList = new ArrayList<>();
                performanceRankingReportList.add(performanceRankingReport);

                performanceSummarySecModel.setPerformanceRankingReports(performanceRankingReportList); //二级模型中加入基础报表集合
                performanceSummarySecModels.add(performanceSummarySecModel);
                performanceSummaryModel.setPerformanceSummarySecModels(performanceSummarySecModels); //一级模型中加入二级模型集合

                performanceSummaryModels.add(performanceSummaryModel);
            }
        }

        //添加组长名称
        for (PerformanceSummaryModel performanceSummaryModel1 : performanceSummaryModels) {
            List<PerformanceSummarySecModel> performanceSummarySecModels = performanceSummaryModel1.getPerformanceSummarySecModels();
            for (PerformanceSummarySecModel performanceSummarySecModel1 : performanceSummarySecModels) {
                List<PerformanceRankingReport> performanceRankingReportList = performanceSummarySecModel1.getPerformanceRankingReports();
                saveGroupLeader(performanceRankingReportList);
            }
        }

        //添加累计,排名
        for (PerformanceSummaryModel performanceSummaryModel1 : performanceSummaryModels) {
            List<PerformanceSummarySecModel> performanceSummarySecModels = performanceSummaryModel1.getPerformanceSummarySecModels();
            for (PerformanceSummarySecModel performanceSummarySecModel1 : performanceSummarySecModels) {
                List<PerformanceRankingReport> performanceRankingReportList = performanceSummarySecModel1.getPerformanceRankingReports();
                doCalculate(performanceRankingReportList);
            }
        }
        return performanceSummaryModels;
    }

    /**
     * @Description 查询催收员业绩排名报表
     */
    private List<PerformanceRankingReport> getRankingReport(PerformanceRankingParams performanceRankingParams, User tokenUser, Integer flag) {
        List<PerformanceRankingReport> performanceRankingReports;
        if (Objects.equals(performanceRankingParams.getType(), 0)) { //实时报表
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DATE, 1);
            Date startDate = cal.getTime();
            Date endDate = ZWDateUtil.getNowDate();
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                if (0 == flag) {
                    performanceRankingReports = performanceRankingReportMapper.getRealtimeReport(startDate, endDate, performanceRankingParams.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                } else {
                    performanceRankingReports = performanceRankingReportMapper.getRealtimeSummaryReport(startDate, endDate, performanceRankingParams.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                }
            } else {
                if (0 == flag) {
                    performanceRankingReports = performanceRankingReportMapper.getRealtimeReport(startDate, endDate, tokenUser.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                } else {
                    performanceRankingReports = performanceRankingReportMapper.getRealtimeSummaryReport(startDate, endDate, tokenUser.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                }
            }
        } else { //历史报表
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                if (0 == flag) {
                    performanceRankingReports = performanceRankingReportMapper.getHistoryReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), performanceRankingParams.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                } else {
                    performanceRankingReports = performanceRankingReportMapper.getHistorySummayReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), performanceRankingParams.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                }
            } else {
                if (0 == flag) {
                    performanceRankingReports = performanceRankingReportMapper.getHistoryReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), tokenUser.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                } else {
                    performanceRankingReports = performanceRankingReportMapper.getHistorySummayReport(performanceRankingParams.getStartDate(),performanceRankingParams.getEndDate(), tokenUser.getCompanyCode(),
                            tokenUser.getDepartment().getCode(), StringUtils.trim(performanceRankingParams.getRealName()), performanceRankingParams.getCode());
                }
            }
        }
        if (performanceRankingReports.isEmpty()) {
            return null;
        } else {
            return performanceRankingReports;
        }
    }

    /**
     * @Description 计算累计，排名
     */
    private List<PerformanceRankingReport> doCalculate(List<PerformanceRankingReport> performanceRankingReports) {
        Integer caseNumSum = 0; //案件总数
        BigDecimal dayAmtSum = new BigDecimal(0); //当日回款总金额
        BigDecimal monthAmtSum = new BigDecimal(0); //月累计回款金额
        BigDecimal targetSum = new BigDecimal(0); //月度回款金额总目标
        BigDecimal targetDisparitySum = new BigDecimal(0); //月度目标金额总差距

        BigDecimal month = new BigDecimal(0); //月累计金额，做排名用
        int rank = 0; //排名
        for (PerformanceRankingReport report : performanceRankingReports) {
            //计算累计
            caseNumSum = caseNumSum + report.getCaseNum();
            dayAmtSum = dayAmtSum.add(report.getDayBackMoney());
            monthAmtSum = monthAmtSum.add(report.getMonthBackMoney());
            targetDisparitySum = targetDisparitySum.add(report.getTargetDisparity());
            targetSum = targetSum.add(report.getTarget());

            //排名
            if (!Objects.equals(report.getMonthBackMoney(), month)) {
                rank++;
                report.setRank(rank);
            } else {
                report.setRank(rank);
            }
            month = report.getMonthBackMoney();
        }
        PerformanceRankingReport performanceRankingReport = new PerformanceRankingReport();
        performanceRankingReport.setRealName("累计");
        performanceRankingReport.setCaseNum(caseNumSum);
        performanceRankingReport.setDayBackMoney(dayAmtSum);
        performanceRankingReport.setMonthBackMoney(monthAmtSum);
        performanceRankingReport.setTarget(targetSum);
        performanceRankingReport.setTargetDisparity(targetDisparitySum);

        performanceRankingReports.add(performanceRankingReport);
        return performanceRankingReports;
    }

    /**
     * @Description 添加组长名称
     */
    private List<PerformanceRankingReport> saveGroupLeader(List<PerformanceRankingReport> performanceRankingReports) {
        performanceRankingReports.sort((o1, o2) -> o2.getDeptCode().compareTo(o1.getDeptCode()));

        Map<String, List<PerformanceRankingReport>> map = new LinkedHashMap<>();
        for (PerformanceRankingReport performanceRankingReport1 : performanceRankingReports) {
            if (map.containsKey(performanceRankingReport1.getDeptCode())) {
                List<PerformanceRankingReport> performanceRankingReportList = map.get(performanceRankingReport1.getDeptCode());
                performanceRankingReportList.add(performanceRankingReport1);
                map.put(performanceRankingReport1.getDeptCode(), performanceRankingReportList);
            } else {
                List<PerformanceRankingReport> performanceRankingReportList = new ArrayList<>();
                performanceRankingReportList.add(performanceRankingReport1);
                map.put(performanceRankingReport1.getDeptCode(), performanceRankingReportList);
            }
        }

        List<PerformanceRankingReport> list = new ArrayList<>();
        for (Map.Entry<String, List<PerformanceRankingReport>> entry : map.entrySet()) {
            PerformanceRankingReport report = null;
            for (PerformanceRankingReport performanceRankingReport : entry.getValue()) {
                Integer manage = performanceRankingReportMapper.getManage(performanceRankingReport.getUserName(), performanceRankingReport.getCompanyCode());
                if (1 == manage) { //是管理者
                    report = performanceRankingReport;
                    break;
                }
            }
            if (report != null) {
                for (PerformanceRankingReport performanceRankingReport2 : entry.getValue()) {
                    performanceRankingReport2.setManageName(report.getRealName());
                }
            }
            //过滤组长
            for (PerformanceRankingReport performanceRankingReport : entry.getValue()) {
                if (Objects.equals(performanceRankingReport.getRealName(), performanceRankingReport.getManageName())) {
                    performanceRankingReports.remove(performanceRankingReport);
                    break;
                }
            }
            list.addAll(performanceRankingReports);
        }
        return list;
    }

    /**
     * @Description 催收员业绩报名汇总报表
     */
    public List<GroupLeaderModel> getGroupLeaderReport(PerformanceRankingParams performanceRankingParams, User tokenUser) {
        //获得催收员业绩排名报表
        List<PerformanceRankingReport> performanceRankingReportList = getRankingReport(performanceRankingParams, tokenUser, 1);
        if (Objects.isNull(performanceRankingReportList)) {
            return null;
        }

        //添加组长名称
        Map<String, List<PerformanceRankingReport>> map1 = new LinkedHashMap<>();
        for (PerformanceRankingReport performanceRankingReport : performanceRankingReportList) {
            if (map1.containsKey(performanceRankingReport.getParentDeptCode())) {
                List<PerformanceRankingReport> performanceRankingReports = map1.get(performanceRankingReport.getParentDeptCode());
                performanceRankingReports.add(performanceRankingReport);
                map1.put(performanceRankingReport.getParentDeptCode(), performanceRankingReports);
            } else {
                List<PerformanceRankingReport> performanceRankingReports = new ArrayList<>();
                performanceRankingReports.add(performanceRankingReport);
                map1.put(performanceRankingReport.getParentDeptCode(), performanceRankingReports);
            }
        }

        for (Map.Entry<String, List<PerformanceRankingReport>> entry : map1.entrySet()) {
            List<PerformanceRankingReport> performanceRankingReports = entry.getValue();
            saveGroupLeader(performanceRankingReports);
        }

        //报表分组
        Map<String, List<PerformanceRankingReport>> map = new LinkedHashMap<>();
        for (PerformanceRankingReport performanceRankingReport : performanceRankingReportList) {
            //过滤管理人员
            Integer manage = performanceRankingReportMapper.getManage(performanceRankingReport.getUserName(), performanceRankingReport.getCompanyCode());
            if (1 == manage) { //是管理者
                continue;
            }
            if (map.containsKey(performanceRankingReport.getDeptCode())) { //map的key包含所循环的报表的组别code码
                List<PerformanceRankingReport> performanceRankingReports = map.get(performanceRankingReport.getDeptCode());
                performanceRankingReports.add(performanceRankingReport);
                map.put(performanceRankingReport.getDeptCode(), performanceRankingReports);
            } else { //不包含
                List<PerformanceRankingReport> performanceRankingReports = new ArrayList<>();
                performanceRankingReports.add(performanceRankingReport);
                map.put(performanceRankingReport.getDeptCode(), performanceRankingReports);
            }
        }

        //构建展示模型
        List<GroupLeaderModel> groupLeaderModels = new ArrayList<>();
        for (Map.Entry<String, List<PerformanceRankingReport>> entry : map.entrySet()) {
            List<PerformanceRankingReport> performanceRankingReports = entry.getValue();
            Integer caseNum = 0; //案件数量
            Integer manageNum = 0; //管理人数
            BigDecimal dayBackMoney = new BigDecimal(0); //当日回款金额
            BigDecimal monthBackMoney = new BigDecimal(0); //月累计回款金额
            BigDecimal target = new BigDecimal(0); //月度回款金额目标
            BigDecimal targetDisparity = new BigDecimal(0); //月度目标差距
            String groupCode = null; //组别code码
            String groupName = null; //组别名称
            List<String> manageName = new ArrayList<>(); //组长
            Date nowDate = null; //报表日期
            for (PerformanceRankingReport performanceRankingReport : performanceRankingReports) {
                caseNum = caseNum + performanceRankingReport.getCaseNum();
                manageNum = manageNum + 1;
                dayBackMoney = dayBackMoney.add(performanceRankingReport.getDayBackMoney());
                monthBackMoney = monthBackMoney.add(performanceRankingReport.getMonthBackMoney());
                target = target.add(performanceRankingReport.getTarget());
                targetDisparity = targetDisparity.add(performanceRankingReport.getTargetDisparity());
                groupCode = performanceRankingReport.getDeptCode();
                groupName = performanceRankingReport.getDeptName();
                manageName.add(performanceRankingReport.getManageName());
                nowDate = performanceRankingReport.getNowDate();
            }
            BigDecimal average = monthBackMoney.divide(new BigDecimal(manageNum), 4, BigDecimal.ROUND_HALF_DOWN); //人均回款金额
            GroupLeaderModel groupLeaderModel = new GroupLeaderModel();
            groupLeaderModel.setGroupCode(groupCode);
            groupLeaderModel.setGroupName(groupName);
            groupLeaderModel.setCaseNum(caseNum);
            groupLeaderModel.setManageNum(manageNum);
            groupLeaderModel.setDayBackMoney(dayBackMoney);
            groupLeaderModel.setMonthBackMoney(monthBackMoney);
            groupLeaderModel.setAverageMoney(average);
            groupLeaderModel.setTarget(target);
            groupLeaderModel.setTargetDisparity(targetDisparity);
            groupLeaderModel.setManageName(manageName.get(0));
            groupLeaderModel.setNowDate(nowDate);
            groupLeaderModels.add(groupLeaderModel);
        }

        //计算累计，排名
        Integer caseSum = 0; //案件数量
        Integer manageSum = 0; //管理人数
        BigDecimal daySum = new BigDecimal(0); //当日回款金额
        BigDecimal monthSum = new BigDecimal(0); //月累计回款金额
        BigDecimal targetSum = new BigDecimal(0); //月度回款金额目标
        BigDecimal targetDisparitySum = new BigDecimal(0); //月度目标差距

        Integer rank = 0; //排名
        for (GroupLeaderModel groupLeaderModel : groupLeaderModels) {
            caseSum = caseSum + groupLeaderModel.getCaseNum();
            manageSum = manageSum + groupLeaderModel.getManageNum();
            daySum = daySum.add(groupLeaderModel.getDayBackMoney());
            monthSum = monthSum.add(groupLeaderModel.getMonthBackMoney());
            targetSum = targetSum.add(groupLeaderModel.getTarget());
            targetDisparitySum = targetDisparitySum.add(groupLeaderModel.getTargetDisparity());
        }

        //排序
        groupLeaderModels.sort((o1, o2) -> o2.getAverageMoney().compareTo(o1.getAverageMoney()));
        BigDecimal temp = new BigDecimal(0);
        for (GroupLeaderModel groupLeaderModel1 : groupLeaderModels) {
            if (!Objects.equals(temp, groupLeaderModel1.getAverageMoney())) {
                rank++;
                groupLeaderModel1.setRank(rank);
            } else {
                groupLeaderModel1.setRank(rank);
            }
            temp = groupLeaderModel1.getAverageMoney();
        }

        GroupLeaderModel groupLeaderModel1 = new GroupLeaderModel();
        groupLeaderModel1.setGroupName("累计");
        groupLeaderModel1.setCaseNum(caseSum);
        groupLeaderModel1.setManageNum(manageSum);
        groupLeaderModel1.setDayBackMoney(daySum);
        groupLeaderModel1.setMonthBackMoney(monthSum);
        groupLeaderModel1.setTarget(targetSum);
        groupLeaderModel1.setTargetDisparity(targetDisparitySum);
        groupLeaderModels.add(groupLeaderModel1);

        return groupLeaderModels;
    }

    /**
     * @Description 获取催收员回款报表
     */
    private List<BackMoneyReport> makeBackMoneyReport(GeneralParams generalParams, User tokenUser) throws ParseException {
        List<BackMoneyReport> backMoneyReports;
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName()); //获取登录人的部门信息
        String deptCode = backMoneyReportMapper.getDeptCode(generalParams.getId());
        if (Objects.equals(generalParams.getType(), 0)) { //实时报表
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                backMoneyReports = backMoneyReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else {
                //获取当日有回款的记录
                backMoneyReports = backMoneyReportMapper.getRealTimeReport(deptModel.getCode(), deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        } else { //历史报表
            Date date1 = ZWDateUtil.getFormatDate(generalParams.getStartDate());
            Date date2 = ZWDateUtil.getFormatDate(generalParams.getEndDate());
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                backMoneyReports = backMoneyReportMapper.getHistoryReport(deptModel.getCode(), date1,
                        date2, deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else {
                backMoneyReports = backMoneyReportMapper.getHistoryReport(deptModel.getCode(), date1,
                        date2, deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        }
        return backMoneyReports;
    }

    /**
     * @Description 短信发送统计报表
     */
    public List<SmsModel> getSmsReport(GeneralParams generalParams, User tokenUser) throws ParseException {
        DeptModel deptModel = backMoneyReportMapper.getDept(tokenUser.getUserName());
        String deptCode = backMoneyReportMapper.getDeptCode(generalParams.getId());
        //获取报表
        List<SmsReport> smsReports;
        if (Objects.equals(generalParams.getType(), 0)) { //实时报表
            if (Objects.isNull(tokenUser.getCompanyCode())) { //是超级管理员
                smsReports = smsReportMapper.getRealtimeReport(deptModel.getCode(), deptCode,
                        StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else { //不是超级管理员
                smsReports = smsReportMapper.getRealtimeReport(deptModel.getCode(), deptCode,
                        StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        } else { //历史报表
            Date date1 = ZWDateUtil.getFormatDate(generalParams.getStartDate());
            Date date2 = ZWDateUtil.getFormatDate(generalParams.getEndDate());
            if (Objects.isNull(tokenUser.getCompanyCode())) { //是超级管理员
                smsReports = smsReportMapper.getHistoryReport(deptModel.getCode(), date1, date2,
                        deptCode, StringUtils.trim(generalParams.getUserName()), generalParams.getCompanyCode());
            } else { //不是超级管理员
                smsReports = smsReportMapper.getHistoryReport(deptModel.getCode(), date1, date2,
                        deptCode, StringUtils.trim(generalParams.getUserName()), tokenUser.getCompanyCode());
            }
        }

        //构建报表展示模型
        if (smsReports.isEmpty()) {
            return null;
        }
        List<SmsModel> smsModels = new ArrayList<>();
        List<SmsSecModel> smsSecModels;
        SmsModel smsModel = new SmsModel();
        SmsSecModel smsSecModel = new SmsSecModel();
        for (SmsReport smsReport : smsReports) {
            if (Objects.equals(tokenUser.getManager(), 0)) {
                if (Objects.equals(smsReport.getDeptCode(), deptModel.getCode())
                        && !Objects.equals(smsReport.getUserName(), tokenUser.getUserName())) { //过滤同部门
                    continue;
                }
            }
            if (smsReport.getUserName().contains("administrator")) {
                continue;
            }
            if (Objects.equals(smsModel.getDeptCode(), smsReport.getParentDeptCode())) { //一级模型中有该部门code码
                smsSecModels = smsModel.getSmsSecModels();
                int flag = 0; //判断二级模型是否有该组别code码
                for (SmsSecModel smsSecModel1 : smsSecModels) {
                    if (Objects.equals(smsSecModel1.getGroupCode(), smsReport.getDeptCode())) {
                        flag = 1;
                        break;
                    }
                }
                if (0 == flag) { //不包含
                    smsSecModel = new SmsSecModel();
                    smsSecModel.setGroupCode(smsReport.getDeptCode()); //组别code码
                    smsSecModel.setGroupName(smsReport.getDeptName()); //组别名称

                    List<SmsReport> smsReportList = new ArrayList<>();
                    smsReportList.add(smsReport);
                    smsSecModel.setSmsReports(smsReportList); //二级模型中加入基础模型集合
                    smsSecModels.add(smsSecModel);
                } else { //包含
                    List<SmsReport> smsReportList = smsSecModel.getSmsReports();
                    smsReportList.add(smsReport);
                    smsSecModel.setSmsReports(smsReportList);
                }
            } else { //一级模型没有该部门code码
                smsModel = new SmsModel();
                smsModel.setDeptCode(smsReport.getParentDeptCode()); //部门code码
                smsModel.setDeptName(smsReport.getParentDeptName()); //部门名称

                smsSecModels = new ArrayList<>();
                smsSecModel = new SmsSecModel();
                smsSecModel.setGroupCode(smsReport.getDeptCode()); //组别code码
                smsSecModel.setGroupName(smsReport.getDeptName()); //组别名称

                List<SmsReport> smsReportList = new ArrayList<>();
                smsReportList.add(smsReport);
                smsSecModel.setSmsReports(smsReportList); //二级模型中加入基础模型
                smsSecModels.add(smsSecModel);
                smsModel.setSmsSecModels(smsSecModels); //一级模型中加入二级模型

                smsModels.add(smsModel);
            }
        }

        //添加排名和累计
        for (SmsModel smsModel1 : smsModels) {
            List<SmsSecModel> smsSecModelList = smsModel1.getSmsSecModels();
            for (SmsSecModel smsSecModel1 : smsSecModelList) {
                Integer smsSum = 0; //累计发短信数量
                Integer successSum = 0; //累计发送成功数量
                Integer failureSum = 0; //累计发送失败数量
                Integer rank = 0; //排名
                Integer susNum = 0; //当前发短信数量，用作排名对比
                List<SmsReport> smsReportList = smsSecModel1.getSmsReports();
                for (int i = 0; i < smsReportList.size(); i++) {
                    smsSum = smsSum + smsReportList.get(i).getSmsNum(); //计算发送短信总数量
                    successSum = successSum + smsReportList.get(i).getSuccessNum(); //计算发送成功总数量
                    failureSum = failureSum + smsReportList.get(i).getFailureNum(); //计算发送失败总数量
                    if (0 == i) { //第一次计算排名时
                        rank++;
                        smsReportList.get(i).setRank(rank);
                    } else {
                        if (Objects.equals(smsReportList.get(i).getSmsNum(), susNum)) { //发送短信数量和上一个人一样的话
                            smsReportList.get(i).setRank(rank);
                        } else { //不一样
                            rank++;
                            smsReportList.get(i).setRank(rank);
                        }
                    }
                    susNum = smsReportList.get(i).getSmsNum();
                }
                SmsReport smsReport = new SmsReport();
                smsReport.setRealName("累计");
                smsReport.setSmsNum(smsSum);
                smsReport.setSuccessNum(successSum);
                smsReport.setFailureNum(failureSum);
                smsReportList.add(smsReport);
            }
        }
        return smsModels;
    }

    /**
     * @Description 导出催收员回款报表
     */
    public String exportBackMoneyReport(GeneralParams generalParams, User tokenUser) throws IOException, ParseException {
        //获取导出数据模型
        List<BackMoneyModel> backMoneyModels = getBackMoneyReport(generalParams, tokenUser);
        if (Objects.isNull(backMoneyModels)) {
            backMoneyModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.BACK_MONEY_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.BACK_MONEY_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        XSSFWorkbook hssfWorkbook = downloadXSSTemplate(requestUrl);
        //设置excel表格样式
        XSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //组合并开始行
        int groupIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //回款累计，只在实时报表中使用
        BigDecimal total;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "backDate", "backMoney"};

        //给excel中填值
        for (BackMoneyModel backMoneyModel : backMoneyModels) { //循环一级模型集合，获取二级模型集合
            List<BackMoneySecModel> backMoneySecModels = backMoneyModel.getBackMoneySecModels();
            for (BackMoneySecModel backMoneySecModel : backMoneySecModels) { //循环二级模型集合，获取三级模型集合
                List<BackMoneyThiModel> backMoneyThiModels = backMoneySecModel.getBackMoneyThiModels();

                //对累计数据初始化
                total = new BigDecimal(0);

                for (BackMoneyThiModel backMoneyThiModel : backMoneyThiModels) { //循环三级模型集合，获取回款报表基础模型集合
                    List<BackMoneyReport> backMoneyReports = backMoneyThiModel.getBackMoneyReports();
                    for (BackMoneyReport backMoneyReport : backMoneyReports) { //遍历每一个回款报表模型
                        //在excel创建一行
                        XSSFRow row = hssfSheet.createRow((short) userIndex);

                        //创建单元格
                        XSSFCell cell = null;
                        int paramIndex = 0; //excel数据展示顺序数组角标

                        for (int i = 0; i < 2; i++) {
                            if (0 == i) {
                                Object obj = ExcelUtil.getProValue("parentDeptName", backMoneyReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 0); //给单元格set值
                            } else if (1 == i) {
                                Object obj = ExcelUtil.getProValue("deptName", backMoneyReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 1); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        }

                        //给该行每一列设置数据
                        for (int i = 2; i < 5; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                            Object obj = ExcelUtil.getProValue(paramArray[paramIndex], backMoneyReport); //通过字段映射获取相应的数据
                            cell = setCellValue(obj, row, i); //给单元格set值
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式

                            //如果为实时报表的话则累计还款金额
                            if (4 == i && Objects.equals(generalParams.getType(), 0)) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = new BigDecimal(0);
                                }
                                total = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(total); //计算累计数据
                            }
                            paramIndex++;
                        }
                        //设置完毕后行数+1,进行下一行设置
                        userIndex++;
                    }
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex + 1;

                //如果导出实时报表
                if (Objects.equals(generalParams.getType(), 0)) { //如果导出实时报表，则多加一行累计
                    //在excel创建一行
                    XSSFRow row = hssfSheet.createRow((short) (userIndex));

                    //创建单元格
                    XSSFCell cell;

                    //给该行每一列设置数据
                    for (int i = 0; i < 5; i++) { //i为报表模版数据列数,从第[1]列组别开始
                        if (0 == i || 2 == i || 3 == i) { //如果为第[2]列姓名或者第[3]列日期,则设置为空
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else if (1 == i) { //如果为第[1]列组别,则设置为累计
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("累计");
                        } else { //如果为第[4]列还款金额,则设置为total
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(total.doubleValue());
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
            }
            //设置组合并起始行
            groupIndex = userIndex;

            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }

        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出核销查询列表
     */
    public String exportChargeOffList(List<QueryChargeOffResponse> dataLists) throws IOException, ParseException {

        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;
        Map<String, String> headMap = new LinkedHashMap<>(); // Excel头
        headMap.put("caseNumber", "案件编号");
        headMap.put("loanInvoiceNumber", "借据号");
        headMap.put("personalName", "客户姓名");
        headMap.put("personalMobileNo", "手机号");
        headMap.put("idCard", "身份证号");
        headMap.put("seriesName", "产品类型");
        headMap.put("productName", "产品名称");
        headMap.put("payStatus", "逾期期数");
        headMap.put("overdueDays", "逾期天数");
     //   headMap.put("overdueCount", "逾期次数");
        headMap.put("state", "核销说明");
        headMap.put("accountBalance", "账户余额");
        headMap.put("overdueAmount", "逾期金额");

        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            Map<String, Object> map;
            DecimalFormat format = new DecimalFormat("0.00");
            for (QueryChargeOffResponse chargeOff : dataLists) {
                map = new LinkedHashMap<>();
                map.put("caseNumber", chargeOff.getCaseNumber()); // 案件编号
                map.put("loanInvoiceNumber", chargeOff.getLoanInvoiceNumber()); // 借据号
                map.put("personalName", chargeOff.getPersonalName()); // 客户姓名
                map.put("personalMobileNo", chargeOff.getMobileNo()); // 手机号
                map.put("idCard", chargeOff.getCertificatesNumber()); // 身份证号
                map.put("seriesName", chargeOff.getSeriesName()); // 产品类型
                map.put("productName", chargeOff.getProductName()); // 产品名称
                map.put("payStatus", chargeOff.getPayStatus()); // 逾期期数
                map.put("overdueDays", chargeOff.getOverdueDays()); // 逾期天数
               // map.put("overdueCount", caseInfo.getOverdueCount()); // 逾期次数
                map.put("state", ""); // 核销说明
                map.put("accountBalance",format.format(chargeOff.getAccountBalance())); // 账户余额
                map.put("overdueAmount", format.format(chargeOff.getOverdueAmount())); // 逾期金额
                dataList.add(map);
            }
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("核销管理");
            ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "核销管理表.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            return restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class).getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            // 删除文件
            if (file != null) {
                file.deleteOnExit();
            }
        }
    }

    /**
     * @Description 导出催收员业绩进展报表
     */
    public String exportPerformanceReport(PerformanceParams performanceParams, User tokenUser) throws IOException {
        //获取导出数据模型
        List<PerformanceModel> performanceModels = getPerformanceReport(performanceParams, tokenUser);
        if (Objects.isNull(performanceModels)) {
            performanceModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.PERFORMANCE_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.PERFORMANCE_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //组合并开始行
        int groupIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //待催收案件总数
        Integer waitCollectNumTotal;
        //待催收案件总金额
        BigDecimal waitCollectAmtTotal;
        //催收中的案件总数
        Integer collectingNumTotal;
        //催收中的案件总金额
        BigDecimal collectingAmtTotal;
        //逾期还款中案件总数
        Integer overdueNumTotal;
        //逾期还款中案件总金额
        BigDecimal overdueAmtTotal;
        //提前结清中案件总数
        Integer advanceNumTotal;
        //提前结清中案件总金额
        BigDecimal advanceAmtTotal;
        //承诺还款案件总数
        Integer promiseNumTotal;
        //承诺还款案件总金额
        BigDecimal promiseAmtTotal;
        //结案案件总数
        Integer endNumTotal;
        //结案案件总金额
        BigDecimal endAmtTotal;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "waitCollectNum", "waitCollectAmt", "collectingNum", "collectingAmt", "overdueNum", "overdueAmt", "advanceNum", "advanceAmt", "promiseNum", "promiseAmt", "endNum", "endAmt"};

        //给excel中填值
        for (PerformanceModel performanceModel : performanceModels) { //循环一级模型集合，获取二级模型集合
            List<PerformanceSecModel> performanceSecModels = performanceModel.getPerformanceSecModels();
            for (PerformanceSecModel performanceSecModel : performanceSecModels) { //循环二级模型集合，获取报表基础模型集合
                List<PerformanceBasisModel> performanceBasisModels = performanceSecModel.getPerformanceBasisModels();

                //给累计的数据初始化
                waitCollectNumTotal = 0;
                waitCollectAmtTotal = new BigDecimal(0);
                collectingNumTotal = 0;
                collectingAmtTotal = new BigDecimal(0);
                overdueNumTotal = 0;
                overdueAmtTotal = new BigDecimal(0);
                advanceNumTotal = 0;
                advanceAmtTotal = new BigDecimal(0);
                promiseNumTotal = 0;
                promiseAmtTotal = new BigDecimal(0);
                endNumTotal = 0;
                endAmtTotal = new BigDecimal(0);

                for (PerformanceBasisModel performanceBasisModel : performanceBasisModels) { //遍历每一个业绩进展报表模型
                    //在excel创建一行
                    HSSFRow row = hssfSheet.createRow((short) userIndex);

                    //创建单元格
                    HSSFCell cell = null;
                    int paramIndex = 0; //excel数据展示顺序数组角标

                    for (int i = 0; i < 2; i++) {
                        if (0 == i) {
                            Object obj = ExcelUtil.getProValue("parentDeptName", performanceBasisModel); //通过字段映射获取相应的数据
                            cell = setCellValue(obj, row, 0); //给单元格set值
                        } else if (1 == i) {
                            Object obj = ExcelUtil.getProValue("deptName", performanceBasisModel); //通过字段映射获取相应的数据
                            cell = setCellValue(obj, row, 1); //给单元格set值
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }

                    //给该行每一列设置数据
                    for (int i = 2; i < 15; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                        Object obj = ExcelUtil.getProValue(paramArray[paramIndex], performanceBasisModel); //通过字段映射获取相应的数据
                        cell = setCellValue(obj, row, i); //给单元格set值
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式

                        //计算累计数据
                        if (3 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            waitCollectNumTotal = (Integer) obj + waitCollectNumTotal; //待催收案件总数
                        } else if (4 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            waitCollectAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(waitCollectAmtTotal); //待催收案件总金额
                        } else if (5 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            collectingNumTotal = (Integer) obj + collectingNumTotal; //催收中案件总数
                        } else if (6 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            collectingAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(collectingAmtTotal); //催收中案件总金额
                        } else if (7 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            overdueNumTotal = (Integer) obj + overdueNumTotal; //逾期还款中案件总数
                        } else if (8 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            overdueAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(overdueAmtTotal); //逾期还款中案件总金额
                        } else if (9 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            advanceNumTotal = (Integer) obj + advanceNumTotal; //提前结清中案件总数
                        } else if (10 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            advanceAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(advanceAmtTotal); //提前结清中案件总金额
                        } else if (11 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            promiseNumTotal = (Integer) obj + promiseNumTotal; //承诺还款案件总数
                        } else if (12 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            promiseAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(promiseAmtTotal); //承诺还款案件总金额
                        } else if (13 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = 0;
                            }
                            endNumTotal = (Integer) obj + endNumTotal; //结案案件总数
                        } else if (14 == i) {
                            if (Objects.isNull(obj)) { //如果为空的话则设为0
                                obj = new BigDecimal(0);
                            }
                            endAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(endAmtTotal); //结案案件总金额
                        }

                        paramIndex++;
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex + 1;

                //在excel创建一行
                HSSFRow row = hssfSheet.createRow((short) (userIndex));

                //创建单元格
                HSSFCell cell = null;

                //给该行每一列设置数据
                for (int i = 0; i < 15; i++) { //i为报表模版数据列数,从第[1]列组别开始
                    if (0 == i || 2 == i) { //如果为第[2]列姓名,则设置为空
                        cell = row.createCell(i, CellType.STRING);
                        cell.setCellValue("");
                    } else if (1 == i) { //如果为第[1]列组别,则设置为累计
                        cell = row.createCell(i, CellType.STRING);
                        cell.setCellValue("累计");
                    } else if (3 == i) { //如果为第[3]列待催收案件数,则设置为waitCollectNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(waitCollectNumTotal);
                    } else if (4 == i) { //如果为第[4]列待催收案件金额,则设置为waitCollectAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(waitCollectAmtTotal.doubleValue());
                    } else if (5 == i) { //如果为第[5]列催收中案件数,则设置为collectingNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(collectingNumTotal);
                    } else if (6 == i) { //如果为第[6]列催收中案件金额,则设置为collectingAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(collectingAmtTotal.doubleValue());
                    } else if (7 == i) { //如果为第[7]列逾期还款中案件数,则设置为overdueNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(overdueNumTotal);
                    } else if (8 == i) { //如果为第[8]列逾期还款中案件金额,则设置为overdueAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(overdueAmtTotal.doubleValue());
                    } else if (9 == i) { //如果为第[9]列提前结清中案件数,则设置为advanceNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(advanceNumTotal);
                    } else if (10 == i) { //如果为第[10]列提前结清中案件金额,则设置为advanceAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(advanceAmtTotal.doubleValue());
                    } else if (11 == i) { //如果为第[11]列承诺还款案件数,则设置为promiseNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(promiseNumTotal);
                    } else if (12 == i) { //如果为第12]列承诺还款案件金额,则设置为promiseAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(promiseAmtTotal.doubleValue());
                    } else if (13 == i) { //如果为第[13]列结案案件数,则设置为endNumTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(endNumTotal);
                    } else if (14 == i) { //如果为第[14]列结案案件金额,则设置为endAmtTotal
                        cell = row.createCell(i, CellType.NUMERIC);
                        cell.setCellValue(endAmtTotal.doubleValue());
                    }
                    cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                }
                //设置完毕后行数+1,进行下一行设置
                userIndex++;
            }
            //设置组合并起始行
            groupIndex = userIndex;

            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }
        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出催收员每日催收过程报表
     */
    public String exportDailyProcessReport(GeneralParams generalParams, User tokenUser) throws IOException, ParseException {
        //获取导出数据模型
        List<DailyProcessModel> dailyProcessModels = getDailyProcessReport(generalParams, tokenUser);
        if (Objects.isNull(dailyProcessModels)) {
            dailyProcessModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.DAILY_PROCESS_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.DAILY_PROCESS_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 3;

        //组合并开始行
        int groupIndex = 3;

        //部门合并开始行
        int deptIndex = 3;

        //案件总数
        Integer caseNumTotal;
        //案件总金额
        BigDecimal caseAmtTotal;
        //当日处理案件总数
        Integer handleNumTotal;
        //承诺还款案件总数
        Integer promiseNumTotal;
        //协商跟进案件总数
        Integer consultNumTotal;
        //他人转告案件总数
        Integer otherTellNumTotal;
        //查找案件总数
        Integer findNumTotal;
        //无人应答案件总数
        Integer noAnswerNumTotal;
        //每日呼叫总数
        Integer callNumTotal;
        //每日有效呼叫总数
        Integer effectiveCallNumTotal;
        //每日通话总时长
        Integer callDurationTotal;
        //沟通记录总条数
        Integer communicateNumTotal;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "nowDate", "caseNum", "caseAmt", "handleNum", "promiseNum", "consultNum", "otherTellNum", "findNum", "noAnswerNum", "callNum", "effectiveCallNum", "callDuration", "communicateNum"};

        //给excel中填值
        for (DailyProcessModel dailyProcessModel : dailyProcessModels) { //循环一级模型集合，获取二级模型集合
            List<DailyProcessSecModel> dailyProcessSecModels = dailyProcessModel.getDailyProcessSecModels();
            for (DailyProcessSecModel dailyProcessSecModel : dailyProcessSecModels) { //循环二级模型集合，获取三级模型集合
                List<DailyProcessThiModel> dailyProcessThiModels = dailyProcessSecModel.getDailyProcessThiModels();

                //给累计的数据初始化
                caseNumTotal = 0;
                caseAmtTotal = new BigDecimal(0);
                handleNumTotal = 0;
                promiseNumTotal = 0;
                consultNumTotal = 0;
                otherTellNumTotal = 0;
                findNumTotal = 0;
                noAnswerNumTotal = 0;
                callNumTotal = 0;
                effectiveCallNumTotal = 0;
                callDurationTotal = 0;
                communicateNumTotal = 0;
                for (DailyProcessThiModel dailyProcessThiModel : dailyProcessThiModels) { //循环三级模型集合，获取每日催收过程报表集合
                    List<DailyProcessReport> dailyProcessReports = dailyProcessThiModel.getDailyProcessReports();
                    for (DailyProcessReport dailyProcessReport : dailyProcessReports) { //遍历每一个每日催收过程报表
                        //在excel创建一行
                        HSSFRow row = hssfSheet.createRow((short) userIndex);

                        //创建单元格
                        HSSFCell cell = null;
                        int paramIndex = 0; //excel数据展示顺序数组角标

                        for (int i = 0; i < 2; i++) {
                            if (0 == i) {
                                Object obj = ExcelUtil.getProValue("parentDeptName", dailyProcessReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 0); //给单元格set值
                            } else if (1 == i) {
                                Object obj = ExcelUtil.getProValue("deptName", dailyProcessReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 1); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        }

                        //给该行每一列设置数据
                        for (int i = 2; i < 16; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                            Object obj = ExcelUtil.getProValue(paramArray[paramIndex], dailyProcessReport); //通过字段映射获取相应的数据
                            cell = setCellValue(obj, row, i); //给单元格set值
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式

                            //计算累计数据
                            if (4 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                caseNumTotal = (Integer) obj + caseNumTotal; //案件总数
                            } else if (5 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                caseAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(caseAmtTotal); //案件总金额
                            } else if (6 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                handleNumTotal = (Integer) obj + handleNumTotal; //当日处理案件总数
                            } else if (7 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                promiseNumTotal = (Integer) obj + promiseNumTotal; //承诺还款案件总数
                            } else if (8 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                consultNumTotal = (Integer) obj + consultNumTotal; //协商跟进收案件总数
                            } else if (9 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                otherTellNumTotal = (Integer) obj + otherTellNumTotal; //他人转告案件总数
                            } else if (10 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                findNumTotal = (Integer) obj + findNumTotal; //查找收案件总数
                            } else if (11 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                noAnswerNumTotal = (Integer) obj + noAnswerNumTotal; //无人应答案件总数
                            } else if (12 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                callNumTotal = (Integer) obj + callNumTotal; //每日呼叫总数
                            } else if (13 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                effectiveCallNumTotal = (Integer) obj + effectiveCallNumTotal; //每日有效呼叫总数
                            } else if (14 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                callDurationTotal = (Integer) obj + callDurationTotal; //每日通话总时长
                            } else if (15 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                communicateNumTotal = (Integer) obj + communicateNumTotal; //沟通记录总条数
                            }
                            paramIndex++;
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        }
                        //设置完毕后行数+1,进行下一行设置
                        userIndex++;
                    }
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex + 1;

                //如果导出实时报表
                if (Objects.equals(generalParams.getType(), 0)) { //如果导出实时报表，则多加一行累计
                    //在excel创建一行
                    HSSFRow row = hssfSheet.createRow((short) (userIndex));

                    //创建单元格
                    HSSFCell cell = null;

                    //给该行每一列设置数据
                    for (int i = 0; i < 16; i++) { //i为报表模版数据列数,从第[1]列组别开始
                        if (0 == i || 2 == i || 3 == i) { //如果为第[2]列姓名或者第[3]列日期,则设置为空
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else if (1 == i) { //如果为第[1]列组别,则设置为累计
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("累计");
                        } else if (i == 4) { //如果为第[4]列案件数量,则设置为caseNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(caseNumTotal);
                        } else if (i == 5) { //如果为第[5]列案件金额,则设置为caseAmtTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(caseAmtTotal.doubleValue());
                        } else if (i == 6) { //如果为第[6]列当日处理案件数量,则设置为handleNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(handleNumTotal);
                        } else if (i == 7) { //如果为第[7]列承诺还款案件数,则设置为promiseNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(promiseNumTotal);
                        } else if (i == 8) { //如果为第[8]列协商跟进案件数,则设置为consultNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(consultNumTotal);
                        } else if (i == 9) { //如果为第[9]列他人转告案件数,则设置为otherTellNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(otherTellNumTotal);
                        } else if (i == 10) { //如果为第[10]列查找案件数,则设置为findNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(findNumTotal);
                        } else if (i == 11) { //如果为第[11]列无人应答案件数,则设置为noAnswerNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(noAnswerNumTotal);
                        } else if (i == 12) { //如果为第[12]列每日呼叫数,则设置为callNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(callNumTotal);
                        } else if (i == 13) { //如果为第[13]列每日有效呼叫数,则设置为effectiveCallNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(effectiveCallNumTotal);
                        } else if (i == 14) { //如果为第[14]列每日通话时长,则设置为callDurationTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(callDurationTotal);
                        } else if (i == 15) { //如果为第[15]列沟通记录条数,则设置为communicateNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(communicateNumTotal);
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
            }
            //设置组合并起始行
            groupIndex = userIndex;

            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }
        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出催收员每日催收结果报表
     */
    public String exportDailyResultReport(GeneralParams generalParams, User tokenUser) throws IOException, ParseException {
        //获取导出数据模型
        List<DailyResultModel> dailyResultModels = getDailyResultReport(generalParams, tokenUser);
        if (Objects.isNull(dailyResultModels)) {
            dailyResultModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.DAILY_RESULT_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.DAILY_RESULT_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //组合并开始行
        int groupIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //案件总数
        Integer caseNumTotal;
        //案件总金额
        BigDecimal caseAmtTotal;
        //当日回款总户数
        Integer dayNumTotal;
        //当日回款总金额
        BigDecimal dayAmtTotal;
        //月度回款总户数
        Integer monthNumTotal;
        //月度回款总金额
        BigDecimal monthAmtTotal;
        //当日回款总户数比
        BigDecimal dayNumRateTotal;
        //当日回款总金额比
        BigDecimal dayAmtRateTotal;
        //总回款比
        BigDecimal backMoneyRateTotal;
        //月度回款金额总目标
        BigDecimal targetTotal;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "nowDate", "caseNum", "caseAmt", "dayNum", "dayAmt", "monthNum", "monthAmt", "dayNumRate", "dayAmtRate", "backMoneyRate", "target"};

        //给excel中填值
        for (DailyResultModel dailyResultModel : dailyResultModels) { //循环一级模型集合，获取二级模型集合
            List<DailyResultSecModel> dailyResultSecModels = dailyResultModel.getDailyResultSecModels();
            for (DailyResultSecModel dailyResultSecModel : dailyResultSecModels) { //循环二级模型集合，获取三级模型集合
                List<DailyResultThiModel> dailyResultThiModels = dailyResultSecModel.getDailyResultThiModels();

                //给累计的数据初始化
                caseNumTotal = 0;
                caseAmtTotal = new BigDecimal(0);
                dayNumTotal = 0;
                dayAmtTotal = new BigDecimal(0);
                monthNumTotal = 0;
                monthAmtTotal = new BigDecimal(0);
                dayNumRateTotal = new BigDecimal(0);
                dayAmtRateTotal = new BigDecimal(0);
                backMoneyRateTotal = new BigDecimal(0);
                targetTotal = new BigDecimal(0);
                for (DailyResultThiModel dailyResultThiModel : dailyResultThiModels) { //循环三级模型集合，获取每日催收过程报表集合
                    List<DailyResultReport> dailyResultReports = dailyResultThiModel.getDailyResultReports();
                    for (DailyResultReport dailyResultReport : dailyResultReports) { //遍历每一个每日催收过程报表
                        //在excel创建一行
                        HSSFRow row = hssfSheet.createRow((short) userIndex);

                        //创建单元格
                        HSSFCell cell = null;
                        int paramIndex = 0; //excel数据展示顺序数组角标

                        for (int i = 0; i < 2; i++) {
                            if (0 == i) {
                                Object obj = ExcelUtil.getProValue("parentDeptName", dailyResultReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 0); //给单元格set值
                            } else if (1 == i) {
                                Object obj = ExcelUtil.getProValue("deptName", dailyResultReport); //通过字段映射获取相应的数据
                                cell = setCellValue(obj, row, 1); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        }

                        //给该行每一列设置数据
                        for (int i = 2; i < 14; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                            Object obj = ExcelUtil.getProValue(paramArray[paramIndex], dailyResultReport); //通过字段映射获取相应的数据
                            if (10 == i || 11 == i || 12 == i) {
                                cell = row.createCell(i, CellType.STRING);
                                BigDecimal temp = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                                cell.setCellValue(temp.toString() + "%");
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式

                            //计算累计数据
                            if (4 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                caseNumTotal = (Integer) obj + caseNumTotal; //案件总数
                            } else if (5 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                caseAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(caseAmtTotal); //案件总金额
                            } else if (6 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                dayNumTotal = (Integer) obj + dayNumTotal; //当日回款总户数
                            } else if (7 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                dayAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(dayAmtTotal); //当日回款总金额
                            } else if (8 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                monthNumTotal = (Integer) obj + monthNumTotal; //月度回款总户数
                            } else if (9 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                monthAmtTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(monthAmtTotal); //月度回款总金额
                            } else if (10 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                dayNumRateTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(dayNumRateTotal); //当日回款总户数比
                            } else if (11 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                dayAmtRateTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(dayAmtRateTotal); //当日回款总金额比
                            } else if (12 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                backMoneyRateTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(backMoneyRateTotal); //总回款比
                            } else if (13 == i) {
                                if (Objects.isNull(obj)) { //如果为空的话则设为0
                                    obj = 0;
                                }
                                targetTotal = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP).add(targetTotal); //月度回款金额总目标
                            }
                            paramIndex++;
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        }
                        //设置完毕后行数+1,进行下一行设置
                        userIndex++;
                    }
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex + 1;

                //如果导出实时报表
                if (Objects.equals(generalParams.getType(), 0)) { //如果导出实时报表，则多加一行累计
                    //在excel创建一行
                    HSSFRow row = hssfSheet.createRow((short) (userIndex));

                    //创建单元格
                    HSSFCell cell = null;

                    //给该行每一列设置数据
                    for (int i = 0; i < 14; i++) { //i为报表模版数据列数,从第[1]列组别开始
                        if (0 == i || 2 == i || 3 == i) { //如果为第[2]列姓名或者第[3]列日期,则设置为空
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else if (1 == i) { //如果为第[1]列组别,则设置为累计
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("累计");
                        } else if (i == 4) { //如果为第[4]列案件数量,则设置为caseNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(caseNumTotal);
                        } else if (i == 5) { //如果为第[5]列案件金额,则设置为caseAmtTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(caseAmtTotal.doubleValue());
                        } else if (i == 6) { //如果为第[6]列当日回款户数,则设置为dayNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(dayNumTotal);
                        } else if (i == 7) { //如果为第[7]列单日回款金额,则设置为dayAmtTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(dayAmtTotal.doubleValue());
                        } else if (i == 8) { //如果为第[8]列月度累计回款户数,则设置为monthNumTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(monthNumTotal);
                        } else if (i == 9) { //如果为第[9]列月度累计回款金额,则设置为monthAmtTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(monthAmtTotal.doubleValue());
                        } else if (i == 10) { //如果为第[10]列当日回款户数比,则设置为dayNumRateTotal
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(dayNumRateTotal.multiply(new BigDecimal(100)).toString() + "%");
                        } else if (i == 11) { //如果为第[11]列当日回款金额比,则设置为dayAmtRateTotal
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(dayAmtRateTotal.multiply(new BigDecimal(100)).toString() + "%");
                        } else if (i == 12) { //如果为第[12]列回款比,则设置为backMoneyRateTotal
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(backMoneyRateTotal.multiply(new BigDecimal(100)).toString() + "%");
                        } else if (i == 13) { //如果为第[13]列月度回款金额目标,则设置为targetTotal
                            cell = row.createCell(i, CellType.NUMERIC);
                            cell.setCellValue(targetTotal.doubleValue());
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
            }
            //设置组合并起始行
            groupIndex = userIndex;

            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }
        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出催收员业绩排名报表
     */
    public String exportPerformanceRankingReport(PerformanceRankingParams performanceRankingParams, User tokenUser) throws IOException {
        //获得报表展示模型
        List<CollectorPerformanceModel> collectorPerformanceModels = getPerformanceRankingReport(performanceRankingParams, tokenUser);
        if (Objects.isNull(collectorPerformanceModels)) {
            collectorPerformanceModels = new ArrayList<>();
        }

        //获得汇总报表展示模型
        List<GroupLeaderModel> groupLeaderModels = getGroupLeaderReport(performanceRankingParams, tokenUser);
        if (Objects.isNull(groupLeaderModels)) {
            groupLeaderModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.PERFORMANCE_RANKING_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.PERFORMANCE_RANKING_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //定义excel数据展示顺序
        String[] paramArray = {"deptName", "realName", "nowDate", "caseNum", "dayBackMoney", "monthBackMoney", "rank", "target", "targetDisparity"};

        //给excel中填值
        for (CollectorPerformanceModel collectorPerformanceModel : collectorPerformanceModels) { //循环展示模型，获得二级模型
            List<PerformanceRankingReport> performanceRankingReports = collectorPerformanceModel.getPerformanceRankingReports();
            for (PerformanceRankingReport performanceRankingReport : performanceRankingReports) {
                //在excel创建一行
                HSSFRow row = hssfSheet.createRow((short) userIndex);

                //创建单元格
                HSSFCell cell = null;
                int paramIndex = 0; //excel数据展示顺序数组角标

                for (int i = 0; i < 1; i++) {
                    if (0 == i) {
                        Object obj = ExcelUtil.getProValue("parentDeptName", performanceRankingReport); //通过字段映射获取相应的数据
                        if (Objects.isNull(obj)) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else {
                            cell = setCellValue(obj, row, 0); //给单元格set值
                        }
                    }
                    cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                }

                //给该行每一列设置数据
                for (int i = 1; i < 10; i++) {
                    Object obj = ExcelUtil.getProValue(paramArray[paramIndex], performanceRankingReport); //通过字段映射获取相应的数据
                    if (1 == i || 3 == i || 6 == i) {
                        if (Objects.isNull(obj)) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else {
                            if (1 == i) {
                                cell = setCellValue(obj, row, i); //给单元格set值
                                if (Objects.nonNull(performanceRankingReport.getManageName())) {
                                    cell.setCellValue(obj + "(" + performanceRankingReport.getManageName() + ")");
                                } else {
                                    cell.setCellValue(obj + "(无)");
                                }
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                        }
                    } else {
                        if (Objects.isNull(obj)) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else {
                            cell = setCellValue(obj, row, i); //给单元格set值
                        }
                    }
                    cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    paramIndex++;
                }
                //设置完毕后行数+1,进行下一行设置
                userIndex++;
            }
            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }

        //添加汇总报表
        addSummaryReport(userIndex, groupLeaderModels, hssfSheet, hssfWorkbook, hssfCellStyle);

        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出催收员业绩小组汇总排名报表
     */
    public String exportSummaryReport(PerformanceRankingParams performanceRankingParams, User tokenUser) throws IOException {
        //获得报表展示模型
        List<PerformanceSummaryModel> performanceSummaryModels = getSummaryReport(performanceRankingParams, tokenUser);
        if (Objects.isNull(performanceSummaryModels)) {
            performanceSummaryModels = new ArrayList<>();
        }

        //获得汇总报表展示模型
        List<GroupLeaderModel> groupLeaderModels = getGroupLeaderReport(performanceRankingParams, tokenUser);
        if (Objects.isNull(groupLeaderModels)) {
            groupLeaderModels = new ArrayList<>();
        }

        //下载催收员回款报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.PERFORMANCE_SUMMARY_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.PERFORMANCE_SUMMARY_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //组合并开始行
        int groupIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "nowDate", "caseNum", "dayBackMoney", "monthBackMoney", "rank", "target", "targetDisparity"};

        //给excel中填值
        for (PerformanceSummaryModel performanceSummaryModel : performanceSummaryModels) {
            List<PerformanceSummarySecModel> performanceSummarySecModels = performanceSummaryModel.getPerformanceSummarySecModels();
            for (PerformanceSummarySecModel performanceSummarySecModel : performanceSummarySecModels) {
                List<PerformanceRankingReport> performanceRankingReports = performanceSummarySecModel.getPerformanceRankingReports();
                for (PerformanceRankingReport performanceRankingReport : performanceRankingReports) {
                    //在excel创建一行
                    HSSFRow row = hssfSheet.createRow((short) userIndex);

                    //创建单元格
                    HSSFCell cell = null;
                    int paramIndex = 0; //excel数据展示顺序数组角标

                    for (int i = 0; i < 2; i++) {
                        if (0 == i) {
                            Object obj = ExcelUtil.getProValue("parentDeptName", performanceRankingReport); //通过字段映射获取相应的数据
                            if (Objects.isNull(obj)) {
                                cell = row.createCell(i, CellType.STRING);
                                cell.setCellValue("");
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                        } else if (1 == i) {
                            Object obj = ExcelUtil.getProValue("deptName", performanceRankingReport); //通过字段映射获取相应的数据
                            if (Objects.isNull(obj)) {
                                cell = row.createCell(i, CellType.STRING);
                                cell.setCellValue("");
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                                if (Objects.nonNull(performanceRankingReport.getManageName())) {
                                    cell.setCellValue(obj + "(" + performanceRankingReport.getManageName() + ")");
                                } else {
                                    cell.setCellValue(obj + "(无)");
                                }
                            }
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }

                    //给该行每一列设置数据
                    for (int i = 2; i < 10; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                        Object obj = ExcelUtil.getProValue(paramArray[paramIndex], performanceRankingReport); //通过字段映射获取相应的数据
                        if (3 == i || 7 == i) {
                            if (Objects.isNull(obj)) {
                                cell = row.createCell(i, CellType.STRING);
                                cell.setCellValue("");
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                        } else {
                            cell = setCellValue(obj, row, i); //给单元格set值
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                        paramIndex++;
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex;
            }

            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }

        //添加汇总报表
        addSummaryReport(userIndex, groupLeaderModels, hssfSheet, hssfWorkbook, hssfCellStyle);

        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }

    /**
     * @Description 导出短信发送统计报表
     */
    public String exportSmsReport(GeneralParams generalParams, User tokenUser) throws ParseException, IOException {
        //获得报表展示模型
        List<SmsModel> smsModels = getSmsReport(generalParams, tokenUser);
        if (Objects.isNull(smsModels)) {
            smsModels = new ArrayList<>();
        }

        //下载短信发送统计报表模版
        //拼接请求地址
        String requestUrl = Constants.SYSPARAM_URL.concat("?").concat("userId").concat("=").concat(tokenUser.getId()).
                concat("&").concat("code").concat("=").concat(Constants.SMS_REPORT_EXCEL_URL_CODE).
                concat("&").concat("type").concat("=").concat(Constants.SMS_REPORT_EXCEL_URL_TYPE).
                concat("&").concat("companyCode").concat("=").concat(tokenUser.getCompanyCode());

        log.debug(requestUrl);
        //下载模版
        HSSFWorkbook hssfWorkbook = downloadTemplate(requestUrl);
        //设置excel表格样式
        HSSFCellStyle hssfCellStyle = setStyle(hssfWorkbook);
        //创建excel表格
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        //催收员开始行
        int userIndex = 2;

        //组合并开始行
        int groupIndex = 2;

        //部门合并开始行
        int deptIndex = 2;

        //定义excel数据展示顺序
        String[] paramArray = {"realName", "nowDate", "smsNum", "successNum", "failureNum", "rank"};

        //给excel中填值
        for (SmsModel smsModel : smsModels) {
            List<SmsSecModel> smsSecModels = smsModel.getSmsSecModels();
            for (SmsSecModel smsSecModel : smsSecModels) {
                List<SmsReport> smsReportList = smsSecModel.getSmsReports();
                for (SmsReport smsReport : smsReportList) {
                    //在excel创建一行
                    HSSFRow row = hssfSheet.createRow((short) userIndex);

                    //创建单元格
                    HSSFCell cell = null;
                    int paramIndex = 0; //excel数据展示顺序数组角标

                    for (int i = 0; i < 2; i++) {
                        Object obj = null;
                        if (0 == i) {
                            obj = ExcelUtil.getProValue("parentDeptName", smsReport); //通过字段映射获取相应的数据
                        } else if (1 == i) {
                            obj = ExcelUtil.getProValue("deptName", smsReport); //通过字段映射获取相应的数据
                        }
                        if (Objects.isNull(obj)) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue("");
                        } else {
                            cell = setCellValue(obj, row, i); //给单元格set值
                        }
                        cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                    }

                    //给该行每一列设置数据
                    if (Objects.equals(generalParams.getType(), 1)) {
                        for (int i = 2; i < 8; i++) {
                            Object obj = ExcelUtil.getProValue(paramArray[paramIndex], smsReport); //通过字段映射获取相应的数据
                            if (3 == i) {
                                Object obj1 = ExcelUtil.getProValue(paramArray[0], smsReport);
                                if (Objects.equals(obj1, "累计")) {
                                    cell = row.createCell(i, CellType.STRING);
                                    cell.setCellValue("");
                                } else {
                                    cell = row.createCell(i, CellType.STRING);
                                    cell.setCellValue(generalParams.getStartDate() + "~" + generalParams.getEndDate());
                                }
                            } else if (i == 7) {
                                if (Objects.isNull(obj)) {
                                    cell = row.createCell(i, CellType.STRING);
                                    cell.setCellValue("");
                                } else {
                                    cell = setCellValue(obj, row, i); //给单元格set值
                                }
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                            paramIndex++;
                        }
                    } else {
                        for (int i = 2; i < 8; i++) {
                            Object obj = ExcelUtil.getProValue(paramArray[paramIndex], smsReport); //通过字段映射获取相应的数据
                            if (3 == i || 7 == i) {
                                if (Objects.isNull(obj)) {
                                    cell = row.createCell(i, CellType.STRING);
                                    cell.setCellValue("");
                                } else {
                                    cell = setCellValue(obj, row, i); //给单元格set值
                                }
                            } else {
                                cell = setCellValue(obj, row, i); //给单元格set值
                            }
                            cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                            paramIndex++;
                        }
                    }
                    //设置完毕后行数+1,进行下一行设置
                    userIndex++;
                }
                //合并组别
                if (userIndex - groupIndex > 1) {
                    CellRangeAddress cra = new CellRangeAddress(groupIndex, userIndex - 1, 1, 1); // 四个参数分别是：起始行，结束行,起始列，结束列
                    hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
                }
                groupIndex = userIndex;
            }
            //合并部门
            CellRangeAddress cra = new CellRangeAddress(deptIndex, userIndex - 1, 0, 0); // 四个参数分别是：起始行，结束行,起始列，结束列
            hssfSheet.addMergedRegion(cra); //在excel里增加合并单元格
            deptIndex = userIndex;
        }
        //上传填好数据的报表
        return uploadExcel(hssfWorkbook);
    }


    /**
     * @Description 给报表单元格set值
     */

    private HSSFCell setCellValue(Object obj, HSSFRow row, Integer j) {
        HSSFCell cell;
        if (obj instanceof String) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue((String) obj);
        } else if (obj instanceof BigDecimal) {
            cell = row.createCell(j, CellType.NUMERIC);
            BigDecimal amt = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP);
            cell.setCellValue(amt.doubleValue());
        } else if (obj instanceof Integer) {
            cell = row.createCell(j, CellType.NUMERIC);
            cell.setCellValue((Integer) obj);
        } else if (obj instanceof Double) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(BigDecimal.valueOf((Double) obj * 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
        } else if (obj instanceof Date) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(ZWDateUtil.fomratterDate((Date) obj, "yyyy-MM-dd"));
        } else {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(obj.toString());
        }
        return cell;
    }
    /**
     * @Description 给报表单元格set值
     */

    private XSSFCell setCellValue(Object obj, XSSFRow row, Integer j) {
        XSSFCell cell;
        if (obj instanceof String) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue((String) obj);
        } else if (obj instanceof BigDecimal) {
            cell = row.createCell(j, CellType.NUMERIC);
            BigDecimal amt = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_UP);
            cell.setCellValue(amt.doubleValue());
        } else if (obj instanceof Integer) {
            cell = row.createCell(j, CellType.NUMERIC);
            cell.setCellValue((Integer) obj);
        } else if (obj instanceof Double) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(BigDecimal.valueOf((Double) obj * 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
        } else if (obj instanceof Date) {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(ZWDateUtil.fomratterDate((Date) obj, "yyyy-MM-dd"));
        } else {
            cell = row.createCell(j, CellType.STRING);
            cell.setCellValue(obj.toString());
        }
        return cell;
    }

    /**
     * @Description excel样式
     */
    private HSSFCellStyle setStyle(HSSFWorkbook hssfWorkbook) throws IOException {
        //设置报表excel格式
        HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
        Font fontBody = hssfWorkbook.createFont();
        fontBody.setFontName("宋体");
        fontBody.setFontHeightInPoints((short) 11);
        hssfCellStyle.setFont(fontBody);
        hssfCellStyle.setBorderLeft(BorderStyle.THIN);
        hssfCellStyle.setBorderRight(BorderStyle.THIN);
        hssfCellStyle.setBorderBottom(BorderStyle.THIN);
        hssfCellStyle.setBorderTop(BorderStyle.THIN);
        hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return hssfCellStyle;
    }
    /**
     * @Description excel样式
     */
    private XSSFCellStyle setStyle(XSSFWorkbook hssfWorkbook) throws IOException {
        //设置报表excel格式
        XSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
        Font fontBody = hssfWorkbook.createFont();
        fontBody.setFontName("宋体");
        fontBody.setFontHeightInPoints((short) 11);
        hssfCellStyle.setFont(fontBody);
        hssfCellStyle.setBorderLeft(BorderStyle.THIN);
        hssfCellStyle.setBorderRight(BorderStyle.THIN);
        hssfCellStyle.setBorderBottom(BorderStyle.THIN);
        hssfCellStyle.setBorderTop(BorderStyle.THIN);
        hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return hssfCellStyle;
    }

    /**
     * @Description 下载报表模版
     */
    private HSSFWorkbook downloadTemplate(String requestUrl) throws IOException {
        ResponseEntity<SysParam> responseEntity = restTemplate.getForEntity(requestUrl, SysParam.class);
        if (!responseEntity.hasBody()) {
            throw new RuntimeException("报表模版系统参数未找到");
        }
        //获得催收员回款报表模型地址
        String url = responseEntity.getBody().getValue();

        //获取报表模型
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restOutTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restOutTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<byte[]>(headers),
                byte[].class);
        return new HSSFWorkbook(new ByteArrayInputStream(response.getBody()));
    }
    /**
     * @Description 下载报表模版 07及以上版本excel
     */
    private XSSFWorkbook downloadXSSTemplate(String requestUrl) throws IOException {
        ResponseEntity<SysParam> responseEntity = restTemplate.getForEntity(requestUrl, SysParam.class);
        if (!responseEntity.hasBody()) {
            throw new RuntimeException("报表模版系统参数未找到");
        }
        //获得催收员回款报表模型地址
        String url = responseEntity.getBody().getValue();

        //获取报表模型
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restOutTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restOutTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<byte[]>(headers),
                byte[].class);
        return new XSSFWorkbook(new ByteArrayInputStream(response.getBody()));
    }

    /**
     * @Description 把生成的报表上传到文件服务器
     */
    private String uploadExcel(HSSFWorkbook hssfWorkbook) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            hssfWorkbook.write(out);
            String resultFilePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "催收员报表.xls");
            File file = new File(resultFilePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            return responseEntity.getBody();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
    /**
     * @Description 把生成的报表上传到文件服务器
     */
    private String uploadExcel(XSSFWorkbook hssfWorkbook) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            hssfWorkbook.write(out);
            String resultFilePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "催收员报表.xlsx");
            File file = new File(resultFilePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            return responseEntity.getBody();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    /**
     * @Description 添加汇总报表
     */
    private void addSummaryReport(int userIndex, List<GroupLeaderModel> groupLeaderModels, HSSFSheet hssfSheet, HSSFWorkbook hssfWorkbook, HSSFCellStyle hssfCellStyle) {
        //空2行
        userIndex = userIndex + 2;

        //在excel创建一行
        HSSFRow hssfRow = hssfSheet.createRow((short) userIndex);

        //设置报表excel格式
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        Font fontBody = hssfWorkbook.createFont();
        fontBody.setFontName("宋体");
        fontBody.setFontHeightInPoints((short) 14);
        fontBody.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(fontBody);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //创建单元格
        HSSFCell cell = null;

        //创建表头
        for (int i = 0; i < 10; i++) {
            cell = hssfRow.createCell(i, CellType.STRING);
            if (0 == i) {
                cell.setCellValue("组别");
            } else if (1 == i) {
                cell.setCellValue("案件数量");
            } else if (2 == i) {
                cell.setCellValue("管理人数");
            } else if (3 == i) {
                cell.setCellValue("日期");
            } else if (4 == i) {
                cell.setCellValue("当日回款金额");
            } else if (5 == i) {
                cell.setCellValue("月累计回款金额");
            } else if (6 == i) {
                cell.setCellValue("人均回款金额");
            } else if (7 == i) {
                cell.setCellValue("排名");
            } else if (8 == i) {
                cell.setCellValue("月度回款金额目标");
            } else if (9 == i) {
                cell.setCellValue("月度目标差距");
            }
            cell.setCellStyle(style);
        }

        //设置完表头后数据行+1
        userIndex++;

        //定义excel数据展示顺序
        String[] params = {"groupName", "caseNum", "manageNum", "nowDate", "dayBackMoney", "monthBackMoney", "averageMoney", "rank", "target", "targetDisparity"};

        //循环添加数据
        for (GroupLeaderModel groupLeaderModel : groupLeaderModels) {
            //在excel创建一行
            HSSFRow row = hssfSheet.createRow((short) userIndex);

            //获取展示顺序下表
            int paramIndex = 0;

            for (int i = 0; i < 10; i++) {
                Object obj = ExcelUtil.getProValue(params[paramIndex], groupLeaderModel); //通过字段映射获取相应的数据
                if (0 == i) {
                    if (Objects.equals(obj, "累计")) {
                        cell = setCellValue(obj, row, i); //给单元格set值
                    } else {
                        cell = setCellValue(obj, row, i); //给单元格set值
                        if (Objects.nonNull(groupLeaderModel.getManageName())) {
                            cell.setCellValue(obj + "(" + groupLeaderModel.getManageName() + ")");
                        } else {
                            cell.setCellValue(obj + "(无)");
                        }
                    }
                } else {
                    if (Objects.isNull(obj)) {
                        cell = row.createCell(i, CellType.STRING);
                        cell.setCellValue("");
                    } else {
                        cell = setCellValue(obj, row, i); //给单元格set值
                    }
                }
                cell.setCellStyle(hssfCellStyle); //给单元格设置格式
                paramIndex++;
            }
            userIndex++;
        }
    }

    /**
     * @Description 导出逾期明细列表
     */
    public String exportOverdueDetailList(List<OverdueDetail> dataList) throws IOException, ParseException {
        try(XSSFWorkbook hssfWorkbook = new XSSFWorkbook()) {


            //创建excel表格
            XSSFSheet hssfSheet = hssfWorkbook.createSheet("逾期明细记录");
            //创建单元格
            XSSFCell firstCell;

            //行号
            int rowIndex = 0;

            XSSFRow titleRow = hssfSheet.createRow(rowIndex);

            //标题
            String [] titles = {"统计时间", "借据号", "案件编号", "申请编号", "客户姓名", "证件号码", "手机号码",
                    "贷款余额", "产品名称", "还款卡号", "推荐人", "进件时间", "经销商名称", "门店名称",
                    "放款时间", "逾期天数", "五级分类", "申请期数", "授信期数", "申请金额", "授信金额",
                    "公司名称", "公司电话区号", "公司电话", "公司电话分机号", "公司地址", "联系人姓名", "联系人手机",
                    "住宅地址", "配偶姓名", "配偶手机", "解除逾期状态所需金额", "月结所欠金额", "当前应扣本金", "当期以前未偿还利息合计",
                    "当期剩余应缴利息", "当期以前未偿还滞纳金", "当期剩余应缴滞纳金", "当期以前未偿还分期手续费", "当期剩余应缴分期手续费", "当期以前未偿还罚息", "当期剩余应缴罚息",
                    "当前欠款总额", "行业名称", "还款银行","未偿还期限", "合同编号", "代偿标记", "回迁标记"};
            //创建首行标题
            for(int i = 0 ; i < titles.length; i ++){
                firstCell = titleRow.createCell(i, CellType.STRING);
                firstCell.setCellValue(titles[i]);
            }

            //定义excel数据展示顺序,一行七个属性,空出来的代表是复合属性,会通过自定义方法解析
            String[] paramArray = {"statTime","loanInvoiceId","caseNumber","intoApplyId","certificateNo","loanAmt","productName","userAccount","intoTime","merchantName","storeName",
                    "loanPayTime","overdueDays","fiveLevel","applyPeriod","creditPeriod","applyAmount","creditAmt",


                                    "clearOverdueAmount","currentMonthDebtAmount","currentPreRepayPrincipal","beforeCurrentLeftRepayInterest",
                    "leftRepayInterest","beforeCurrentLeftRepayManagementFee","leftRepayManagementFee","beforeCurrentLeftRepayFee","leftRepayFee","beforeCurrentLeftOverdueFee","leftOverdueFee",
                    "currentDebtAmount","loanPurposeName","branchName","leftNum","contractId","flag","movingBackFlag"};

            int [] cellIndex = {0,1,2,3,5,7,8,9,11,12,13,
                    14,15,16,17,18,19,20,


                    31,32,33,34,
                    35,36,37,38,39,40,41,
                    42,43,44,45,46,47,48};

            for (OverdueDetail overdueDetail : dataList) {
                XSSFRow row = hssfSheet.createRow(++rowIndex);
                //给该行每一列设置数据
                for (int i = 0; i < cellIndex.length; i++) { //i为报表模版数据列数,从第[2]列姓名开始
                    Object obj = ExcelUtil.getProValue(paramArray[i], overdueDetail); //通过字段映射获取相应的数据
                    if("statTime".equals(paramArray[i]) && obj != null){
                        obj = DateUtil.getDate((Date) obj,"yyyy-MM-dd");
                    }
                    if("intoTime".equals(paramArray[i]) && obj != null){
                        obj = DateUtil.getDate(DateUtil.getDate((String)obj,"yyyy/MM/dd"),"yyyy-MM-dd");
                    }
                    if("loanPayTime".equals(paramArray[i]) && obj != null){
                        obj = DateUtil.getDate(DateUtil.getDate((String)obj,"yyyy/MM/dd"),"yyyy-MM-dd");
                    }
                    setCellValue(obj == null ? "" : obj.toString(),row,cellIndex[i]);
                }

                Personal person = overdueDetail.getPersonalInfo();//客户信息
                setCellValue(overdueDetail.getClientName() != null ? overdueDetail.getClientName() : "",row,4);//客户姓名
    //            setCellValue(person != null && person.getCertificatesNumber() != null ? person.getCertificatesNumber() : "",row,5);//证件号码
                setCellValue(person != null && person.getMobileNo() != null ? person.getMobileNo() : "",row,6);//手机号码
                setCellValue(person != null && person.getReferrer() != null ? person.getReferrer() : "",row,10);//推荐人
                List<PersonalContact> contractors;//联系人

                if(person != null && person.getPersonalJobs() != null && person.getPersonalJobs().size() > 0){
                    PersonalJob job = (PersonalJob) overdueDetail.getPersonalInfo().getPersonalJobs().toArray()[0];
                    setCellValue(job != null && job.getCompanyName() != null ? job.getCompanyName():"",row,21);//公司名称
                    setCellValue(job != null && job.getPhoneAreaCode() != null ? job.getPhoneAreaCode() : "",row,22);//公司电话区号
                    setCellValue(job != null && job.getPhone() != null ? job.getPhone() :"",row,23);//公司电话
                    setCellValue(job != null && job.getPhoneExt() != null ? job.getPhoneExt() :"",row,24);//公司电话分机号
                    setCellValue(job != null && job.getAddress() != null ? job.getAddress() :"",row,25);//公司地址
                }
                if(person != null && person.getPersonalContacts() != null && person.getPersonalContacts().size() > 0){
                    contractors = person.getPersonalContacts().subList(0,person.getPersonalContacts().size());
                    PersonalContact defaultContractor = contractors.get(0);//首个联系人作为默认联系人
                    setCellValue(defaultContractor.getName() == null ? "" :defaultContractor.getName(),row,26);//联系人姓名
                    setCellValue(defaultContractor.getPhone() == null ? "":defaultContractor.getPhone(),row,27);//联系人手机
                    for(PersonalContact spouse:  contractors){
                        //获取配偶信息
                        if(spouse.getRelation()!=null && spouse.getRelation() == 146){
                            setCellValue(spouse.getName() == null ? "":spouse.getName(),row,29);//配偶姓名
                            setCellValue(spouse.getPhone()== null ?"":spouse.getPhone(),row,30);//配偶手机
                            break;
                        }
                    }

                }
                setCellValue(person != null && person.getLocalHomeAddress() != null ?person.getLocalHomeAddress():"",row,28);//住宅地址
            }
            //上传填好数据的报表
            return uploadExcel(hssfWorkbook);
        } finally {

        }
    }
}
