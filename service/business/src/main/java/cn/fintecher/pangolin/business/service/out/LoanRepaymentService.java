package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.out.LoanRepaymentModel;
import cn.fintecher.pangolin.business.model.out.Receipt;
import cn.fintecher.pangolin.business.repository.CaseRepaymentRecordRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Base64;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.MD5;
import cn.fintecher.pangolin.util.RandomUtil;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author: chenjing
 * @Description
 * @Date 2018/7/12
 **/
@Service("LoanRepaymentService")
public class LoanRepaymentService {

    final Logger log = LoggerFactory.getLogger(LoanRepaymentService.class);


    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CaseRepaymentRecordRepository caseRepaymentRecordRepository;


    /**
     * @param user
     * @param caseNumber
     * @description 调取借据还款记录
     */
    @Transactional
    public void getLoanRepayment(User user, String caseNumber) {
        Receipt receipt = setReceipt(caseNumber);
        RestTemplate restTemplate = new RestTemplate();
        SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.LOAN_REPAYMENT_URL).and(QSysParam.sysParam.companyCode.eq(Constants.APPLY_PD_TYPE)));
        if (Objects.nonNull(param)) {
            String jsonString = restTemplate.postForObject(param.getValue(), receipt, String.class);
            Map maps = (Map) JSON.parse(jsonString);
            String code = (String) maps.get("code");
            if (code.equals("E0001")) {
                List<LoanRepaymentModel> loanRepaymentModels = getLoanRepaymentModelFromJsonArray((String) maps.get("receiptList"));
                if (!loanRepaymentModels.isEmpty()) {
                    try {
                        saveCaseRepaymentRecord(loanRepaymentModels, caseNumber, user);
                    } catch (Exception e) {
                        log.info("还款记录保存失败");
                    }
                }
            } else if (code.equals("E1001")) {
                throw new RuntimeException((String) maps.get("msg"));
            } else if (code.equals("E0098")) {
                throw new RuntimeException((String) maps.get("msg"));
            } else {
                throw new RuntimeException("未知错误");
            }

        } else {
            throw new RuntimeException("贷前接口系统配置地址获取失败");
        }

    }

    /**
     * @param caseNumber
     * @return
     * @Description 请求参数模型
     */
    private Receipt setReceipt(String caseNumber) {
        Receipt receipt = new Receipt();
        SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.LOAN_REPAYMENT_PLATFORMID).and(QSysParam.sysParam.companyCode.eq(Constants.APPLY_PD_TYPE)));
        String app_id = null;
        if(Objects.nonNull(param)){
            app_id = param.getValue();//接入平台id
        }else {
            throw new RuntimeException("缺少参数");
        }

        String channelSN;//渠道流水号
        String timestamp = ZWDateUtil.getDateTimes();//请求时间-14位时间
        RandomUtil.getRandomNumber(3);//三位随机数
        Base64.encode("");
        MD5.MD5Encode("");
        String msg_digest;//消息摘要
        receipt.setApp_id(app_id);

        return receipt;
    }

    /**
     * @description 保存借据还款记录
     * @param loanRepaymentModels
     * @param caseNumber
     * @param user
     */
    private void saveCaseRepaymentRecord(List<LoanRepaymentModel> loanRepaymentModels, String caseNumber, User user) {
        Set<String> applNoAndLoanNumberSet = new HashSet<>();
        Iterator<CaseRepaymentRecord> caseRepaymentRecords = caseRepaymentRecordRepository.findAll(QCaseRepaymentRecord.caseRepaymentRecord.applNo.eq(caseNumber)).iterator();
        while (caseRepaymentRecords.hasNext()) {
            CaseRepaymentRecord caseRepaymentRecord = caseRepaymentRecords.next();
            applNoAndLoanNumberSet.add(caseRepaymentRecord.getApplNo().concat("_").concat(caseRepaymentRecord.getLoanNumber().toString()));
        }
        for (LoanRepaymentModel loanRepaymentModel : loanRepaymentModels) {
            String caps = loanRepaymentModel.getApplNo().concat("_").concat(loanRepaymentModel.getLoanNumber().toString());
            if (!applNoAndLoanNumberSet.contains(caps)){
                CaseRepaymentRecord caseRepaymentRecord = new CaseRepaymentRecord();
                caseRepaymentRecord.setApplNo(loanRepaymentModel.getApplNo());
                caseRepaymentRecord.setLoanAmt(loanRepaymentModel.getLoanAmt());
                caseRepaymentRecord.setLoanNumber(loanRepaymentModel.getLoanNumber());
                caseRepaymentRecord.setCurrentAmt(loanRepaymentModel.getCurrentAmt());
                caseRepaymentRecord.setTerminationFlag(loanRepaymentModel.getTerminationFlag());
                caseRepaymentRecord.setDelqFlag(loanRepaymentModel.getDelqFlag());
                caseRepaymentRecord.setTransferUser(user.getRealName());
                caseRepaymentRecord.setTransferTime(new Date());
                caseRepaymentRecordRepository.save(caseRepaymentRecord);
            }
        }
    }


    /**
     * 解析json字符串
     *
     * @param str
     * @return preLendingData
     */
    //JSONObject
    private static LoanRepaymentModel getLoanRepaymentModelFromJsonObject(String str) {
        JSONObject jsonObject = JSONObject.fromObject(str);
        LoanRepaymentModel loanRepaymentModel = (LoanRepaymentModel) JSONObject.toBean(jsonObject, LoanRepaymentModel.class);
        return loanRepaymentModel;
    }

    //jsonArray
    private List<LoanRepaymentModel> getLoanRepaymentModelFromJsonArray(String str) {
        List<LoanRepaymentModel> loanRepaymentModels = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(str);
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            LoanRepaymentModel loanRepaymentModel = (LoanRepaymentModel) JSONObject.toBean(jsonObject, LoanRepaymentModel.class);
            loanRepaymentModels.add(loanRepaymentModel);
        }
        return loanRepaymentModels;
    }


}
