package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.LoadCaseModel;
import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.PersonalContact;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : sunyanping
 * @Description : 分案表导出
 * @Date : 2017/7/27.
 */
@Service("personalInfoExportService")
public class PersonalInfoExportService {

//    private static final String[] COLLECT_DATA = {"机构名称", "客户姓名", "身份证号", "联系电话", "归属城市", "总期数", "逾期天数", "逾期金额", "贷款日期", "还款状态", "催收员", "申请号", "产品名称", "产品类型", "案件本金", "逾期期数"};
//    private static final String[] COLLECT_PRO = {"deptName", "custName", "idCard", "phone", "city", "periods", "overDays", "overAmt", "loanDate", "payStatus", "collector", "caseNum", "productName", "productSeries", "leftCapital", "overduePeriods"};
//
//    private static final String[] BASE_INFO_DATA = {"客户姓名", "身份证号", "归属城市", "手机号", "身份证户籍地址", "家庭地址", "家庭电话", "产品名称", "产品系列"};
//    private static final String[] BASE_INFO_PRO = {"custName", "idCard", "city", "phone", "idCardAddress", "homeAddress", "homePhone", "productName", "productSeries"};
//
//    private static final String[] WORK_INFO_DATA = {"工作单位名称", "工作单位地址", "工作单位电话"};
//    private static final String[] WORK_INFO_PRO = {"workName", "workAddress", "workPhone"};
//
//    private static final String[] CONTACT_INFO_DATA = {"关系", "姓名", "手机号码", "住宅电话", "现居住地址", "工作单位", "单位电话"};
//    private static final String[] CONTACT_INFO_PRO = {"relation", "contactName", "contactPhone", "contactHomePhone", "contactAddress", "contactWorkCompany", "contactWorkPhone"};
//
//    private static final String[] BANK_INFO_DATA = {"还款卡银行", "还款卡号"};
//    private static final String[] BANK_INFO_PRO = {"bankName", "bankCard"};
//
//    private static final String[] BATCH_NUM_DATA = {"机构名称", "客户姓名", "身份证号", "联系电话", "归属城市", "总期数", "逾期天数", "逾期金额", "贷款日期", "还款状态", "批次号"};
//    private static final String[] BATCH_NUM_PRO = {"deptName", "custName", "idCard", "phone", "city", "periods", "overDays", "overAmt", "loanDate", "payStatus", "batchNum"};

    private static final String[] CASE_STATUS_DATA = {  "案件编号","借据号","客户姓名", "身份证号", "联系电话",  "总期数", "逾期期数", "逾期天数", "逾期金额","贷款余额","逾期次数", "贷款日期", "产品类型", "产品名称","归属城市", "案件状态", "案件类型","机构名称","催收员","归C日期","已结清日期"};
    private static final String[] CASE_STATUS_PRO = { "caseNumber", "loanInvoiceNumber","custName", "idCard", "phone", "periods", "overduePeriods",  "overDays","overdueCapital","accountBalance","overdueCount","loanDate", "seriesName",  "productName", "city", "caseStatus", "caseType", "deptName","currentCollector","cleanDate","settleDate"};

    /**
     * 创建表头
     * @param list       选择的配置项
     * @return
     */
    public Map<String, String> createHeadMap(List<List<String>> list) {
        Map<String, String> headMap = new LinkedHashMap<>(); //存储头信息
            List<String> caseStatus = list.get(0);
            // 遍历caseStatus
            for (int i = 0; i < caseStatus.size(); i++) {
                for (int k = 0; k < CASE_STATUS_DATA.length; k++) {
                    if (Objects.equals(caseStatus.get(i), CASE_STATUS_DATA[k])) {
                        headMap.put(CASE_STATUS_PRO[k], caseStatus.get(i));
                        break;
                    }
                }
            }

        return headMap;
    }

    /**
     * 生成数据
     *
     * @param loadCaseModels 根据条件筛选的数据
     * @return
     */
    public List<Map<String, Object>> createDataList(List<LoadCaseModel> loadCaseModels) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (LoadCaseModel loadCaseModel : loadCaseModels) {
            Map<String, Object> map = new HashMap<>();
            map.put("caseNumber", loadCaseModel.getCaseNumber());//案件编号
            map.put("loanInvoiceNumber", loadCaseModel.getLoanInvoiceNumber());//借据号
            map.put("custName", loadCaseModel.getCustName()); //客户姓名
            map.put("idCard", loadCaseModel.getIdCard()); //身份证号
            map.put("phone", loadCaseModel.getPhone()); //联系电话
            map.put("periods", loadCaseModel.getPeriods()); //总期数
            map.put("overduePeriods", loadCaseModel.getOverduePeriods()); //逾期期数
            map.put("overDays", loadCaseModel.getOverDays()); //逾期天数
            map.put("overdueCapital", loadCaseModel.getOverdueCapital());//逾期总金额
            map.put("overAmt", loadCaseModel.getOverAmt()); //逾期本金
            map.put("accountBalance",loadCaseModel.getAccountBalance());//账户余额
            map.put("overdueCount",loadCaseModel.getOverdueCount());//逾期次数
            map.put("approvedLoanAmt",loadCaseModel.getApprovedLoanAmt());//贷款金额
            map.put("loanDate", loadCaseModel.getLoanDate()); //贷款日期
            map.put("payStatus", loadCaseModel.getPayStatus()); //还款状态
            map.put("seriesName", loadCaseModel.getSeriesName());//产品系列名称
            map.put("productName", loadCaseModel.getProductName());//产品名称
            map.put("city", loadCaseModel.getCity()); //归属城市
            CaseInfo.CasePoolType[] values2 = CaseInfo.CasePoolType.values();
            for (int i = 0; i < values2.length; i++) {
                if (Objects.equals(values2[i].getValue(), loadCaseModel.getCaseType())) { //案件类型
                    map.put("caseType", values2[i].getRemark());
                    break;
                }
            }
            CaseInfo.CollectionStatus[] values1 = CaseInfo.CollectionStatus.values();
            for (int i = 0; i < values1.length; i++) {
                if (Objects.equals(values1[i].getValue(), loadCaseModel.getCaseStatus())){ //案件状态
                    map.put("caseStatus", values1[i].getRemark());
                    break;
                }
            }
            map.put("deptName", loadCaseModel.getDeptName());//机构名称
            map.put("currentCollector", loadCaseModel.getCurrentCollector());//当前催员
            map.put("cleanDate",loadCaseModel.getCleanDate());//归C日期
            map.put("settleDate",loadCaseModel.getSettleDate());//已结清日期
            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 获取多个案件中联系人最多的数
     *
     * @param caseInfoList
     * @return
     */
    public int getMaxNum(List<CaseInfo> caseInfoList) {
        // 遍历获取到联系人信息做多的数目
        int maxNum = 0;
        for (CaseInfo caseInfo : caseInfoList) {
            List<PersonalContact> personalContacts = caseInfo.getPersonalInfo().getPersonalContacts();
            if (personalContacts.size() > maxNum) {
                maxNum = personalContacts.size();
            }
        }
        return maxNum > 4 ? 4 : maxNum;
    }
}
