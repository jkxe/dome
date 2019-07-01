package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.InvalidContactCaseParam;
import cn.fintecher.pangolin.business.repository.CaseFollowupRecordRepository;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.MissingConnectionInfoRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.enums.ECollectionType;
import cn.fintecher.pangolin.enums.EInvalidCollection;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by duchao on 2018/6/15.
 */
@Service("ContactFeedbackService")
public class ContactFeedbackService {
    private final Logger logger = LoggerFactory.getLogger(ContactFeedbackService.class);


    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private CaseFollowupRecordRepository caseFollowupRecordRepository;

    @Autowired
    private MissingConnectionInfoRepository missingConnectionInfoRepository;

    /**
     * 获取连续N天失联催记
     *
     * @param n
     * @param companyCode
     * @return
     */
    public List<MissingConnectionInfo> getInvalidContactCase(int n, String companyCode) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始处理");
        stopWatch.stop();
        if (n > 0) {
            stopWatch.start("查询基础信息");
            List<Integer> invalidCollectionValues = new ArrayList<>();
            List<MissingConnectionInfo> missingConnectionInfoList = new ArrayList<>();
            for (EInvalidCollection invalidCollection : EInvalidCollection.values()) {
                invalidCollectionValues.add(invalidCollection.getValue());
            }
            //todo 电催-无效类型催收反馈
            stopWatch.stop();
            stopWatch.start("查询案件和跟进记录");
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())
                    .and(QCaseInfo.caseInfo.companyCode.eq(companyCode)));
            List<CaseFollowupRecord> caseFollowupRecords = caseFollowupRecordRepository.findAll();
            List<CaseInfo> caseInfoList = Lists.newArrayList(caseInfoRepository.findAll(builder));
            stopWatch.stop();
            stopWatch.start("循环处理");
            for (CaseInfo caseInfo : caseInfoList) {
                MissingConnectionInfo missingConnectionInfo = new MissingConnectionInfo();
                boolean firstMissingFlag = true;
                int missingCount = 0;
                int missingTimes = 0;
                int longestMissing = 0;
                boolean currentMissingFlag;
                List<CaseFollowupRecord> followupRecords = Lists.newArrayList(Iterables.filter(caseFollowupRecords, input -> caseInfo.getId().equals(input.getCaseId())));
                followupRecords = new Ordering<CaseFollowupRecord>() {
                    @Override
                    public int compare(CaseFollowupRecord left, CaseFollowupRecord right) {

                        return left.getOperatorTime().compareTo(right.getOperatorTime());
                    }
                }.immutableSortedCopy(followupRecords);
                Date LastFollowupRecordDate = new Date();
                    //todo 电催-无效类型催收反馈
                for (CaseFollowupRecord caseFollowupRecord : followupRecords) {
                    if (caseFollowupRecord.getCollectionType().equals(ECollectionType.outside.getValue())){

                    }
                    if (invalidCollectionValues.contains(caseFollowupRecord.getCollectionFeedback())) {
                        if (!ZWDateUtil.compareDate(LastFollowupRecordDate, caseFollowupRecord.getOperatorTime()) &&
                                !ZWDateUtil.compareDate(new Date(), caseFollowupRecord.getOperatorTime())) {
                            missingCount++;
                            if (missingCount >= n) {
                                if (missingCount == n) {
                                    missingTimes++;
                                    if (firstMissingFlag) {
                                        missingConnectionInfo.setCaseNumber(caseInfo.getCaseNumber());
                                        missingConnectionInfo.setContractNumber(caseInfo.getContractNumber());
                                        missingConnectionInfo.setPersonalName(caseInfo.getPersonalInfo().getName());
                                        missingConnectionInfo.setIdCard(caseInfo.getPersonalInfo().getIdCard());
                                        missingConnectionInfo.setMobileNo(caseInfo.getPersonalInfo().getMobileNo());
                                        missingConnectionInfo.setSex(caseInfo.getPersonalInfo().getSex());
                                        missingConnectionInfo.setFirstMissingTime(caseFollowupRecord.getOperatorTime());
                                        missingConnectionInfo.setCompanyCode(companyCode);
                                        firstMissingFlag = false;
                                    }
                                    missingConnectionInfo.setMissingTimes(missingTimes);
                                }
                                if (longestMissing < missingCount) {
                                    longestMissing = missingCount;
                                    missingConnectionInfo.setLongestMissingDays(longestMissing);
                                }
                            }
                            missingConnectionInfo.setCurrentMissingDays(missingCount);
                        }
                        currentMissingFlag = true;
                        LastFollowupRecordDate = caseFollowupRecord.getOperatorTime();
                    } else {
                        currentMissingFlag = false;
                        missingCount = 0;
                        missingConnectionInfo.setCurrentMissingDays(0);
                        LastFollowupRecordDate = caseFollowupRecord.getOperatorTime();
                    }
                    missingConnectionInfo.setCurrentMissingFlag(currentMissingFlag);
                }

                if (!firstMissingFlag) {
                    missingConnectionInfoList.add(missingConnectionInfo);
                }

            }
            stopWatch.stop();
            stopWatch.start("保存记录");
            missingConnectionInfoList = missingConnectionInfoRepository.save(missingConnectionInfoList);
            stopWatch.stop();
            logger.debug(stopWatch.prettyPrint());
            return missingConnectionInfoList;
        }
        return null;
    }

    /**
     * 获取所有失联案件
     *
     * @param companyCode
     * @return
     */
    public List<MissingConnectionInfo> getAllMissingConnectionInfo(String companyCode, InvalidContactCaseParam param) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.companyCode.eq(companyCode));
        if (Objects.nonNull(param.getCaseNumber())) {
            booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.caseNumber.like("%".concat(param.getCaseNumber()).concat("%")));
        }
        if (Objects.nonNull(param.getContractNumber())) {
            booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.contractNumber.like("%".concat(param.getContractNumber()).concat("%")));
        }
        if (Objects.nonNull(param.getIdCard())) {
            booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.idCard.like("%".concat(param.getIdCard()).concat("%")));
        }
        if (Objects.nonNull(param.getPersonalName())) {
            booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.personalName.like("%".concat(param.getPersonalName()).concat("%")));
        }
        if (Objects.nonNull(param.getMobileNo())) {
            booleanBuilder.and(QMissingConnectionInfo.missingConnectionInfo.mobileNo.like("%".concat(param.getMobileNo()).concat("%")));
        }
        List<MissingConnectionInfo> missingConnectionInfoList = Lists.newArrayList(missingConnectionInfoRepository.findAll(booleanBuilder));
        // 合并需要导出的数据(共债案件合并)
        if (Objects.isNull(missingConnectionInfoList)){
            // 查询出来的数据是空的
            return missingConnectionInfoList;
        }
        List<MissingConnectionInfo> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  missingConnectionInfoList.size() ; i ++ )  {
            if (caseNums.contains(missingConnectionInfoList.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(missingConnectionInfoList.get(i).getCaseNumber());
            MissingConnectionInfo missingConnectionInfo = missingConnectionInfoList.get(i);
            list1.add(missingConnectionInfo);
        }

        return list1;
    }

    /**
     * 获取实体所有字段及描述，制作Excel表头
     *
     * @return
     */
    public HashMap<String, String> createExcelHeader() {
        LinkedHashMap<String, String> headerMap = new LinkedHashMap<>(16);
        List<Field> fields = Lists.newArrayList(MissingConnectionInfo.class.getDeclaredFields());
        for (Field field : fields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (Objects.isNull(annotation.notes()) || StringUtils.isEmpty(annotation.notes())) {
                headerMap.put(field.getName(), annotation.name());
            }
        }
        return headerMap;
    }

    /**
     * 获取并返回失联数据，导出Excel
     *
     * @param companyCode
     * @return
     */
    public List<Map<String, Object>> createExcelData(String companyCode, InvalidContactCaseParam param) {
        List<Map<String, Object>> hashMapList = new ArrayList<>();
        List<MissingConnectionInfo> missingConnectionInfoList = getAllMissingConnectionInfo(companyCode, param);
        for (MissingConnectionInfo missingConnectionInfo : missingConnectionInfoList) {
            HashMap<String, Object> hashMap = new HashMap<>(16);
            for (Field field : missingConnectionInfo.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    if (field.getName().equals("sex")) {
                        if (field.get(missingConnectionInfo).equals(Personal.SexEnum.MAN.getValue())) {
                            hashMap.put(field.getName(), Personal.SexEnum.MAN.getRemark());
                        } else if (field.get(missingConnectionInfo).equals(Personal.SexEnum.WOMEN.getValue())) {
                            hashMap.put(field.getName(), Personal.SexEnum.WOMEN.getRemark());
                        } else {
                            hashMap.put(field.getName(), Personal.SexEnum.UNKNOWN.getRemark());
                        }
                    } else if (field.getName().equals("currentMissingFlag")) {
                        if ((Boolean) field.get(missingConnectionInfo)) {
                            hashMap.put(field.getName(), "是");
                        } else {
                            hashMap.put(field.getName(), "否");
                        }
                    } else {
                        hashMap.put(field.getName(), field.get(missingConnectionInfo));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            hashMapList.add(hashMap);
        }
        return hashMapList;
    }


}
