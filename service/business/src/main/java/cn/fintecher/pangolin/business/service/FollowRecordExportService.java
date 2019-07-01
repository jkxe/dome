package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.entity.CaseFollowupRecord;
import cn.fintecher.pangolin.enums.EInvalidCollection;
import cn.fintecher.pangolin.enums.EffectiveCollection;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * @Author : sunyanping
 * @Description :
 * @Date : 2017/8/15.
 */
@Service
public class FollowRecordExportService {

    private static final String[] TITLE_LIST = {"案件编号", "批次号", "委托方", "跟进时间", "跟进方式", "客户姓名", "客户身份证", "催收对象", "姓名", "电话/地址", "定位地址", "催收反馈", "跟进内容", "客户号", "账户号", "手数"};
    private static final String[] PRO_NAMES = {"caseNumber", "batchNumber", "principalName", "follTime", "follType", "personalName", "idcard", "follTarget", "follTargetName", "follPhoneNum", "location", "follFeedback", "follContent", "personalNum", "accountNum", "handNum"};

    @Inject
    private CaseInfoRepository caseInfoRepository;

    public Map<String, String> createHead() {
        Map<String, String> headMap = new LinkedHashMap<>();
        for (int i = 0; i < PRO_NAMES.length; i++) {
            headMap.put(PRO_NAMES[i], TITLE_LIST[i]);
        }
        return headMap;
    }

    public List<Map<String, Object>> createData(List<Object[]> records) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Object[] record : records) {
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("caseNumber", record[0]); //案件编号
            dataMap.put("batchNumber", record[1]); //批次号
            dataMap.put("principalName", record[2]); //委托方
            dataMap.put("follTime", ZWDateUtil.fomratterDate((Date) record[3], null)); //跟进时间
            CaseFollowupRecord.Type[] values = CaseFollowupRecord.Type.values(); //跟进方式
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(record[4], values[i].getValue())) {
                    dataMap.put("follType", values[i].getRemark());
                    break;
                }
            }
            dataMap.put("personalName", record[5]); //客户姓名
            dataMap.put("idcard", record[6]); //身份证号
            CaseFollowupRecord.Target[] values1 = CaseFollowupRecord.Target.values(); //催收对象
            for (int i = 0; i < values1.length; i++) {
                if (Objects.equals(record[7], values1[i].getValue())) {
                    dataMap.put("follTarget", values1[i].getRemark());
                    break;
                }
            }
            dataMap.put("follTargetName", record[8]); //催收对象姓名

            // 电话催收
            if (Objects.equals(record[4], CaseFollowupRecord.Type.TEL.getValue())) {
//                CaseFollowupRecord.ContactState[] values2 = CaseFollowupRecord.ContactState.values();
//                for (int i = 0; i < values2.length; i++) {
//                    if (Objects.equals(record.getContactState(), values2[i].getValue())) {
//                        dataMap.put("follContype", values2[i].getRemark());  //电话/地址状态
//                        break;
//                    }
//                }
                dataMap.put("follPhoneNum", record[9]); //电话/地址
            }
            // 外访/协催
            if (!Objects.equals(record[4], CaseFollowupRecord.Type.TEL.getValue())) {
//                Personal.AddrStatus[] values2 = Personal.AddrStatus.values();
//                for (int i = 0; i < values2.length; i++) {
//                    if (Objects.equals(record.getAddrStatus(), values2[i].getValue())) {
//                        dataMap.put("follContype", values2[i].getRemark()); //电话/地址状态
//                        break;
//                    }
//                }
                dataMap.put("follPhoneNum", record[10]); //电话/地址


            }

            EffectiveCollection[] values2 = EffectiveCollection.values(); //催收反馈
            for (int i = 0; i < values2.length; i++) {
                if (Objects.equals(record[12], values2[i].getValue())) {
                    dataMap.put("follFeedback", values2[i].getRemark());
                    break;
                }
            }
            EInvalidCollection[] values3 = EInvalidCollection.values();
            for (int i = 0; i < values3.length; i++) {
                if (Objects.equals(record[12], values3[i].getValue())) {
                    dataMap.put("follFeedback", values3[i].getRemark());
                    break;
                }
            }

            dataMap.put("location", record[11]);//定位地址
            dataMap.put("follContent", record[13]); //跟进内容
            dataMap.put("personalNum", record[14]); //客户号
            dataMap.put("accountNum", record[15]); //账户号
            dataMap.put("handNum", record[16]); //手数
            dataList.add(dataMap);
        }
        return dataList;
    }
}
