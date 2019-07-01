package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.CaseInfoIntelligentModel;
import cn.fintecher.pangolin.business.model.CaseInfoIntelligentParams;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.IdcardUtils;
import cn.fintecher.pangolin.entity.util.MD5;
import cn.fintecher.pangolin.util.ZWDateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.fintecher.pangolin.entity.util.Base64.encode;

/**
 * @author yuanyanting
 * @version Id:CaseInfoIntelligentService.java,v 0.1 2017/12/20 11:21 yuanyanting Exp $$
 */
@Service
public class CaseInfoIntelligentService {
    private final Logger log = LoggerFactory.getLogger(CaseInfoIntelligentService.class);

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaseTurnRecordRepository caseTurnRecordRepository;

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    private CaseInfoDistributedRepository caseInfoDistributedRepository;

    /**
     * 智能分案预览
     *
     * @param user
     * @param caseInfoToDistribute
     * @return
     */
    public List<CaseInfoIntelligentModel> getCaseInfoDistribute(User user, Object[] caseInfoToDistribute) {
        List<CaseInfoIntelligentModel> list = new ArrayList<>();
        if (!Objects.equals(caseInfoToDistribute.length, 0)) {
            StringBuilder sb = findCaseInfoDistribute(caseInfoToDistribute);
            StringBuilder sb1 = new StringBuilder();
            RestTemplate restTemplate = new RestTemplate();
            sb1.append(MD5.MD5Encode(Constants.BEGIN_CODE).substring(0, 31)).append(encode(sb.toString())).append(MD5.MD5Encode(Constants.END_CODE).substring(0, 31));
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity1 = new HttpEntity<>(sb1.toString(), headers);
            SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.TODISTRIBUTE_CASE_CODE).and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode())));
            if (Objects.nonNull(sysParam)) {
                ResponseEntity<String> entity = restTemplate.exchange(sysParam.getValue(), HttpMethod.POST, entity1, String.class);
                JSONArray jsonArray = JSONArray.fromObject(entity.getBody());
                CaseInfoIntelligentModel model;
                for (int i = 0; i < jsonArray.size(); i++) {
                    List<String> caseNumberList = new ArrayList<>();
                    Boolean flag = false;
                    JSONObject jsonObjectReturn = jsonArray.getJSONObject(i);
                    String collector1 = jsonObjectReturn.get("collector").toString();
                    String caseNumber1 = jsonObjectReturn.get("caseNumber").toString();
                    for (int j = 0; j < list.size(); j++) {
                        if (Objects.equals(collector1, list.get(j).getCollectorId())) {
                            model = list.get(j);
                            caseNumberList = list.get(j).getCaseNumbers();
                            CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(QCaseInfoDistributed.caseInfoDistributed.caseNumber.eq(caseNumber1));
                            caseNumberList.add(caseNumber1);
                            model.setCount(caseNumberList.size());
                            model.setCaseNumbers(caseNumberList);
                            model.setSum(model.getSum().add(caseInfoDistributed.getOverdueAmount()));
                            flag = true;
                        }
                    }
                    if (!flag) {
                        model = new CaseInfoIntelligentModel();
                        User user1 = userRepository.findOne(collector1);
                        model.setCollector(user1.getRealName());//催收员姓名
                        model.setCollectorId(collector1);//催收员Id
                        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(QCaseInfoDistributed.caseInfoDistributed.caseNumber.eq(caseNumber1));
                        model.setSum(caseInfoDistributed.getOverdueAmount());
                        caseNumberList.add(caseNumber1);//案件编号
                        model.setCount(caseNumberList.size());
                        model.setCaseNumbers(caseNumberList);
                        list.add(model);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 智能分案
     *
     * @param params
     * @return
     */
    public void setCaseInfoDistribute(CaseInfoIntelligentParams params) {
        List<CaseInfo> caseInfoList = new ArrayList<>();//案件的集合
        List<CaseInfoDistributed> caseInfoDistributedList = new ArrayList<>();//案件分配集合
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();//流转记录集合
        List<CaseInfoIntelligentModel> intelligentModels = params.getIntelligentModels();
        for (CaseInfoIntelligentModel model : intelligentModels) {
            List<String> caseNumbers = model.getCaseNumbers();
            for (String caseNumber : caseNumbers) {
                CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(QCaseInfoDistributed.caseInfoDistributed.caseNumber.eq(caseNumber));//案件信息
                CaseInfo caseInfo = new CaseInfo();
                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                BeanUtils.copyProperties(caseInfoDistributed,caseInfo);
                User user = userRepository.findOne(QUser.user.id.eq(model.getCollectorId()));//催收员信息
                caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue());//催收类型：待催收
                caseInfo.setDepartment(user.getDepartment());//部门信息
                //caseInfo.setReturnFlag(0);
                if (Objects.equals(user.getType(),1)) {
                    caseInfo.setCollectionType(CaseInfo.CollectionType.TEL.getValue());
                } else if (Objects.equals(user.getType(),2)) {
                    caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
                } else {
                    caseInfo.setCollectionType(CaseInfo.CollectionType.COMPLEX.getValue());
                }
                caseInfo.setCurrentCollector(user);//当前催收员
                BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                caseTurnRecord.setId(null); //主键置空
                caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
                if (Objects.nonNull(user)) { //催收员不为空则是分给催收员
                    caseTurnRecord.setReceiveDeptName(user.getDepartment().getName()); //接收部门名称
                    caseTurnRecord.setReceiveUserId(user.getId()); //接收人ID
                    caseTurnRecord.setReceiveUserRealName(user.getRealName()); //接受人名称
                    caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                } else {
                    caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
                }
                caseTurnRecord.setCirculationType(3); //流转类型 3-正常流转
                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                caseInfoList.add(caseInfo);
                caseTurnRecordList.add(caseTurnRecord);
                caseInfoDistributedList.add(caseInfoDistributed);
            }
        }
        caseInfoDistributedRepository.delete(caseInfoDistributedList);
        caseInfoRepository.save(caseInfoList);
        caseTurnRecordRepository.save(caseTurnRecordList);
    }

    public StringBuilder findCaseInfoDistribute(Object[] caseInfoDistributes) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < caseInfoDistributes.length; i++) {
                Object[] objects = (Object[]) caseInfoDistributes[i];
                if (Objects.nonNull(objects[0])) {
                    sb.append(objects[0].toString()).append(Constants.ENCRYPT_CODE);
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[1])) {
                    sb.append(objects[1].toString()).append(Constants.ENCRYPT_CODE);
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[2])) {
                    Integer age = IdcardUtils.getAge(objects[2].toString());
                    String sex = IdcardUtils.getSex(objects[2].toString());
                    sb.append(age).append(Constants.ENCRYPT_CODE).append(sex).append(Constants.ENCRYPT_CODE);
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE).append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[3])) {
                    String mobileNo = objects[3].toString(); // 电话号码(例：1840927****)
                    String num = StringUtils.left(mobileNo, 7);
                    sb.append(num).append(Constants.ENCRYPT_CODE);
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[4])) {
                    sb.append(objects[4].toString()).append(Constants.ENCRYPT_CODE); // 公司名称
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[5])) {
                    sb.append(objects[5].toString()).append(Constants.ENCRYPT_CODE); // 产品名称
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[6])) {
                    sb.append(objects[6].toString()).append(Constants.ENCRYPT_CODE); // 产品系列名称
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[7])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[7].toString())).append(Constants.ENCRYPT_CODE); // 贷款日期
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[8])) {
                    sb.append(objects[8].toString()).append(Constants.ENCRYPT_CODE); // 合同金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[9])) {
                    sb.append(objects[9].toString()).append(Constants.ENCRYPT_CODE); // 剩余本金
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[10])) {
                    sb.append(objects[10].toString()).append(Constants.ENCRYPT_CODE); // 剩余利息
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[11])) {
                    sb.append(objects[11].toString()).append(Constants.ENCRYPT_CODE); // 逾期总金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[12])) {
                    sb.append(objects[12].toString()).append(Constants.ENCRYPT_CODE); // 逾期本金
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[13])) {
                    sb.append(objects[13].toString()).append(Constants.ENCRYPT_CODE); // 逾期利息
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[14])) {
                    sb.append(objects[14].toString()).append(Constants.ENCRYPT_CODE); // 逾期罚息
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[15])) {
                    sb.append(objects[15].toString()).append(Constants.ENCRYPT_CODE); // 逾期滞纳金
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[16])) {
                    sb.append(objects[16].toString()).append(Constants.ENCRYPT_CODE); // 还款期数
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[17])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[17].toString())).append(Constants.ENCRYPT_CODE); // 每期还款日
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[18])) {
                    sb.append(objects[18].toString()).append(Constants.ENCRYPT_CODE); // 每期还款金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[19])) {
                    sb.append(objects[19].toString()).append(Constants.ENCRYPT_CODE); // 其它费用
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[20])) {
                    sb.append(objects[20].toString()).append(Constants.ENCRYPT_CODE); // 逾期期数
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[21])) {
                    sb.append(objects[21].toString()).append(Constants.ENCRYPT_CODE); // 逾期天数
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[22])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[22].toString())).append(Constants.ENCRYPT_CODE); // 逾期日期
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[23])) {
                    sb.append(objects[23].toString()).append(Constants.ENCRYPT_CODE); // 已还款金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[24])) {
                    sb.append(objects[24].toString()).append(Constants.ENCRYPT_CODE); // 已还款期数
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[25])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[25].toString())).append(Constants.ENCRYPT_CODE); // 最近还款日期
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[26])) {
                    sb.append(objects[26].toString()).append(Constants.ENCRYPT_CODE); // 最近还款金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[27])) {
                    sb.append(objects[27].toString()).append(Constants.ENCRYPT_CODE); // 佣金比例
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[28])) {
                    sb.append(objects[28].toString()).append(Constants.ENCRYPT_CODE); // 开户银行
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[29])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[29].toString())).append(Constants.ENCRYPT_CODE); // 委案日期
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[30])) {
                    sb.append(objects[30].toString()).append(Constants.ENCRYPT_CODE); // 委托方姓名
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[31])) {
                    sb.append(objects[31].toString()).append(Constants.ENCRYPT_CODE); // 催收方式
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[32])) {
                    sb.append(objects[32].toString()).append(Constants.ENCRYPT_CODE); // 逾期减免金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[33])) {
                    sb.append(objects[33].toString()).append(Constants.ENCRYPT_CODE); // 逾期实际还款金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[34])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[34].toString())).append(Constants.ENCRYPT_CODE); // 操作时间
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[35])) {
                    sb.append(objects[35].toString()).append(Constants.ENCRYPT_CODE); // 地区名称
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                sb.append(Constants.ENCRYPT_ENDCODE);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("操作失败");
        }
        return sb;
    }
}
