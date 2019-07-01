package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.CompanyRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.entity.Company;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.IdcardUtils;
import cn.fintecher.pangolin.entity.util.MD5;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Objects;
import static cn.fintecher.pangolin.entity.util.Base64.encode;

/**
 * @author yuanyanting
 * @version Id:CaseAnalysisScheduled.java,v 0.1 2017/11/8 17:45 yuanyanting Exp $$
 */
@Component
//@EnableScheduling
public class CaseAnalysisScheduled {

    private final Logger log = LoggerFactory.getLogger(CaseAnalysisScheduled.class);
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private CaseInfoRepository caseInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SysParamRepository sysParamRepository;

//    @Scheduled(cron = "0 51 07 * * ?")
    void caseClosed() {
        try {
            log.info("开始查询已结案案件{}", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            //获取公司码
            List<Company> companyList = companyRepository.findAll();
            for (Company company : companyList) {
                if (Objects.nonNull(company.getCode())) {
                    SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.ANALYSIS_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                    if (Objects.nonNull(param)) {
                        if (Objects.equals(Integer.parseInt(param.getValue()), 1)) {
                            Object[] caseInfoClosed = caseInfoRepository.findCaseInfoClosed(company.getCode());
                            if (!Objects.equals(caseInfoClosed.length,0)) {
                                StringBuilder sb = findCaseInfo(caseInfoClosed);
                                StringBuilder sb1 = new StringBuilder();
                                sb1.append(MD5.MD5Encode(Constants.BEGIN_CODE).substring(0, 31)).append(encode(sb.toString())).append(MD5.MD5Encode(Constants.END_CODE).substring(0, 31));
                                RestTemplate restTemplate = new RestTemplate();
                                HttpHeaders headers = new HttpHeaders();
                                //System.out.println(sb.toString());
                                //System.out.println(sb1.toString());
                                HttpEntity<String> entity1 = new HttpEntity<>(sb1.toString(), headers);
                                SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.CLOSE_CASE_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                                if (Objects.nonNull(sysParam)) {
                                    String value = sysParam.getValue();
                                    restTemplate.exchange(value, HttpMethod.POST, entity1, String.class);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("发送数据失败");
        }
    }

//    @Scheduled(cron = "0 0 23 * * ?")
    void caseIn() {
        try {
            log.info("开始查询处理中案件{}" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            List<Company> companyList = companyRepository.findAll();
            for (Company company : companyList) {
                if (Objects.nonNull(company.getCode())) {
                    SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.ANALYSIS_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                    if (Objects.nonNull(param)) {
                        if (Objects.equals(Integer.parseInt(param.getValue()), 1)) {
                            Object[] caseInfoIn = caseInfoRepository.findCaseInfoIn(company.getCode());
                            if (!Objects.equals(caseInfoIn.length,0)) {
                                StringBuilder sb = findCaseInfo(caseInfoIn);
                                StringBuilder sb1 = new StringBuilder();
                                sb1.append(MD5.MD5Encode(Constants.BEGIN_CODE).substring(0, 31)).append(encode(sb.toString())).append(MD5.MD5Encode(Constants.END_CODE).substring(0, 31));
                                RestTemplate restTemplate = new RestTemplate();
                                HttpHeaders headers = new HttpHeaders();
                                HttpEntity<String> entity1 = new HttpEntity<>(sb1.toString(), headers);
                                SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.HANGLING_CASE_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                                if (Objects.nonNull(sysParam)) {
                                    String value = sysParam.getValue();
                                    restTemplate.exchange(value, HttpMethod.POST, entity1, String.class);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("发送数据失败");
        }
    }

//    @Scheduled(cron = "0 0 23 * * ?")
    void findAllUser() {
        try {
            log.info("开始查询所有用户信息{}", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            List<Company> companyList = companyRepository.findAll();
            for (Company company : companyList) {
                if (Objects.nonNull(company.getCode())) {
                    SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.ANALYSIS_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                    if (Objects.nonNull(param)) {
                        if (Objects.equals(Integer.parseInt(param.getValue()), 1)) {
                            StringBuilder sb = new StringBuilder();
                            StringBuilder sb1 = new StringBuilder();
                            Object[] allUser = userRepository.findAllUser(company.getCode());
                            // 用户信息
                            for (int i = 0; i < allUser.length; i++) {
                                Object[] objects = (Object[]) allUser[i];
                                if (Objects.nonNull(objects[0].toString())) {
                                    sb.append(objects[0].toString()).append(Constants.ENCRYPT_CODE); // 催员id
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[1])) {
                                    sb.append(objects[1].toString()).append(Constants.ENCRYPT_CODE); // 公司code
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[2])) {
                                    sb.append(objects[2].toString()).append(Constants.ENCRYPT_CODE); // 类型
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[3])) {
                                    sb.append(objects[3].toString()).append(Constants.ENCRYPT_CODE); // 性别
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[4])) {
                                    sb.append(objects[4].toString()).append(Constants.ENCRYPT_CODE); // 状态
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[5])) {
                                    sb.append(objects[5].toString()).append(Constants.ENCRYPT_CODE); // 年龄
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[6])) {
                                    sb.append(objects[6].toString()).append(Constants.ENCRYPT_CODE); // 工龄
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[7])) {
                                    sb.append(objects[7].toString()).append(Constants.ENCRYPT_CODE); // 民族
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[8])) {
                                    sb.append(objects[8].toString()).append(Constants.ENCRYPT_CODE); // 催员参与催收的案件总数
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[9])) {
                                    sb.append(objects[9].toString()).append(Constants.ENCRYPT_CODE); // 催员参与催收的案件总金额
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[10])) {
                                    sb.append(objects[10].toString()).append(Constants.ENCRYPT_CODE); // 催回的案件总数
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[11])) {
                                    sb.append(objects[11].toString()).append(Constants.ENCRYPT_CODE); // 催回的案件总金额
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[12])) {
                                    sb.append(objects[12].toString()).append(Constants.ENCRYPT_CODE); // 承诺还款案件总数
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[13])) {
                                    sb.append(objects[13].toString()).append(Constants.ENCRYPT_CODE); // 承诺还款总金额
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[14])) {
                                    sb.append(objects[14].toString()).append(Constants.ENCRYPT_CODE); // 通话记录总数
                                } else {
                                    sb.append("").append(Constants.ENCRYPT_CODE);
                                }
                                if (Objects.nonNull(objects[15])) {
                                    sb.append(objects[15].toString()); // 外访记录总数
                                } else {
                                    sb.append("");
                                }
                                sb.append(Constants.ENCRYPT_ENDCODE);
                            }
                            //System.out.println(sb.toString());
                            sb1.append(MD5.MD5Encode(Constants.BEGIN_CODE).substring(0, 31)).append(encode(sb.toString())).append(MD5.MD5Encode(Constants.END_CODE).substring(0, 31));
                            //System.out.println(sb1.toString());
                            RestTemplate restTemplate = new RestTemplate();
                            HttpHeaders headers = new HttpHeaders();
                            HttpEntity<String> entity1 = new HttpEntity<>(sb1.toString(), headers);
                            SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.USER_CODE).and(QSysParam.sysParam.companyCode.eq(company.getCode())));
                            if (Objects.nonNull(sysParam)) {
                                restTemplate.exchange(sysParam.getValue(), HttpMethod.POST, entity1, String.class);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("发送数据失败");
        }
    }

    public StringBuilder findCaseInfo(Object[] caseInfos) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < caseInfos.length; i++) {
                Object[] objects = (Object[]) caseInfos[i];
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
                    sb.append(objects[32].toString()).append(Constants.ENCRYPT_CODE); // 当前催收员
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[33])) {
                    sb.append(objects[33].toString()).append(Constants.ENCRYPT_CODE); // 逾期减免金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[34])) {
                    sb.append(objects[34].toString()).append(Constants.ENCRYPT_CODE); // 逾期实际还款金额
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[35])) {
                    sb.append(ZWDateUtil.getFormatDate(objects[35].toString())).append(Constants.ENCRYPT_CODE); // 操作时间
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[36])) {
                    sb.append(objects[36].toString()).append(Constants.ENCRYPT_CODE); // 地区名称
                } else {
                    sb.append("").append(Constants.ENCRYPT_CODE);
                }
                if (Objects.nonNull(objects[37])) {
                    sb.append(objects[37].toString()); // 催计数
                } else {
                    sb.append("");
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
