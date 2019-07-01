package cn.fintecher.pangolin.business.repository;


import cn.fintecher.pangolin.entity.CaseInfoDistributed;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import com.hsjry.lang.common.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class CaseInfoDistributedBatchRepository{
    @PersistenceContext
    private EntityManager entityManager;

    private String getValue(String str){
        if(str!=null){
            return "'"+str+"'";
        }else {
            return "null";
        }
    }
    private String getValue(Integer str){
        if(str!=null){
            return "'"+str+"'";
        }else {
            return "null";
        }
    }
    private String getValue(Date date){
        if(date!=null){
            return "'"+DateUtil.getDate(date,"yyyy-MM-dd HH:mm:ss")+"'";
        }else {
            return "null";
        }
    }
    private String getValue(BigDecimal decimal){
        if(decimal!=null){
            return "'"+decimal.doubleValue()+"'";
        }else {
            return "null";
        }
    }
    private String getValue(Object obj){
        if(obj!=null){
            return "'"+obj.toString()+"'";
        }else {
            return "null";
        }
    }

    @Modifying
    @Transactional
    public int insert(CaseInfoDistributed value){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into `case_info_distributed` ( " +
                "`unpaid_lpc`, `overdue_periods`, `left_capital`, " +
                "`hold_days`, `loan_period`, `batch_number`, `derate_amt`, `case_pool_type`, " +
                "`max_overdue_days`, `operator_time`, `source_channel`, `contract_amount`, " +
                "`recover_memo`, `collection_status`, `credit_period`, `store_name`, `case_number`, " +
                "`curr_pnlt_interest`, `lately_pay_date`, `credit_amount`, `repay_bank`, " +
                "`overdue_delay_fine_current`, `overdue_capital`, `loan_date`, `overdue_fine`, " +
                "`principal_id`, `assist_way`, `pre_repay_principal`, `operator`, `commission_rate`, " +
                "`settle_date`, `loan_amount`, `unpaid_principal`, `loan_invoice_number`, " +
                "`verfication_status`, `overdue_interest_current`, `leave_date`, `case_mark`, " +
                "`overdue_manage_fee_current`, `case_follow_in_time`, `unpaid_other_fee`, " +
                "`is_processed`, `has_pay_periods`, `per_pay_amount`, `risk_type`, " +
                "`overdue_delay_fine_before`, `update_time`, `hand_up_flag`, `apply_amount`, " +
                "`exception_check_time`, `advances_flag`, `recover_way`, `pay_status`, `left_days`, " +
                "`has_leave_days`, `overdue_interest`, `product_type`, `periods`, `early_derate_amt`, " +
                "`exception_flag`, `real_pay_amount`, `assist_flag`, `executed_periods`, `first_pay_date`, " +
                "`over_due_date`, `depart_id`, `early_settle_amt`, `account_age`, `memo`, `other_amt`, " +
                "`overdue_account_number`, `score`, `product_name`, `has_pay_amount`, `follow_up_num`, " +
                "`current_debt_amount`, `left_interest`, `case_type`, `end_remark`, `contract_number`, " +
                "`overdue_days`, `overdue_manage_fee_before`, `bus_date`, `clean_date`, `pnlt_interest`, " +
                "`collection_method`, `create_time`, `merchant_name`, `close_date`, `account_balance`, " +
                "`early_real_settle_amt`, `into_apply_id`, `overdue_manage_fee`, `left_periods`, `per_due_date`, " +
                "`remain_fee`, `pnlt_fine`, `unpaid_interest`, `repay_account_no`, `leave_case_flag`, " +
                "`collection_type`, `repay_date`, `settle_amount`, `assist_status`, `exception_type`, " +
                "`five_level`, `overdue_delay_fine`, `order_id`, `out_colcnt`, `overdue_interest_before`, " +
                "`overdue_count`, `apply_period`, `into_time`, `in_colcnt`, `unpaid_fine`, `area_id`, " +
                "`lately_pay_amount`, `personal_id`, `overdue_capital_interest`, `delegation_date`, `stop_time`, " +
                "`loan_purpose`, `company_code`, `lates_date_return`, `recover_remark`, `product_id`, " +
                "`moving_back_flag`, `loan_pay_time`, `id`, `overdue_amount`, `hand_number`, `unpaid_mth_fee`, " +
                "`unpaid_other_interest`, `customer_id`) values ");

        value.setId(ShortUUID.uuid());
        sb.append("(");
        sb.append(getValue(value.getUnpaidLpc())+",");sb.append(getValue(value.getOverduePeriods())+",");sb.append(getValue(value.getLeftCapital())+",");sb.append(getValue(value.getHoldDays())+",");sb.append(getValue(value.getLoanPeriod())+",");sb.append(getValue(value.getBatchNumber())+",");sb.append(getValue(value.getDerateAmt())+",");sb.append(getValue(value.getCasePoolType())+",");sb.append(getValue(value.getMaxOverdueDays())+",");sb.append(getValue(value.getOperatorTime())+",");sb.append(getValue(value.getSourceChannel())+",");
        sb.append(getValue(value.getContractAmount())+",");sb.append(getValue(value.getRecoverMemo())+",");sb.append(getValue(value.getCollectionStatus())+",");sb.append(getValue(value.getCreditPeriod())+",");sb.append(getValue(value.getStoreName())+",");sb.append(getValue(value.getCaseNumber())+",");sb.append(getValue(value.getCurrPnltInterest())+",");sb.append(getValue(value.getLatelyPayDate())+",");sb.append(getValue(value.getCreditAmount())+",");
        sb.append(getValue(value.getRepayBank())+",");sb.append(getValue(value.getOverdueDelayFineCurrent())+",");sb.append(getValue(value.getOverdueCapital())+",");sb.append(getValue(value.getLoanDate())+",");sb.append(getValue(value.getOverdueFine())+",");sb.append(getValue(value.getPrincipalId())+",");sb.append(getValue(value.getAssistWay())+",");sb.append(getValue(value.getPreRepayPrincipal())+",");sb.append(getValue(value.getOperator())+",");
        sb.append(getValue(value.getCommissionRate())+",");sb.append(getValue(value.getSettleDate())+",");sb.append(getValue(value.getLoanAmount())+",");sb.append(getValue(value.getUnpaidPrincipal())+",");sb.append(getValue(value.getLoanInvoiceNumber())+",");sb.append(getValue(value.getVerficationStatus())+",");sb.append(getValue(value.getOverdueInterestCurrent())+",");
        sb.append(getValue(value.getLeaveDate())+",");sb.append(getValue(value.getCaseMark())+",");sb.append(getValue(value.getOverdueManageFeeCurrent())+",");sb.append(getValue(value.getCaseFollowInTime())+",");sb.append(getValue(value.getUnpaidOtherFee())+",");sb.append(getValue(value.getIsProcessed())+",");sb.append(getValue(value.getHasPayPeriods())+",");sb.append(getValue(value.getPerPayAmount())+",");sb.append(getValue(value.getRiskType())+",");sb.append(getValue(value.getOverdueDelayFineBefore())+",");
        sb.append(getValue(value.getUpdateTime())+",");sb.append(getValue(value.getHandUpFlag())+",");sb.append(getValue(value.getApplyAmount())+",");sb.append(getValue(value.getExceptionCheckTime())+",");sb.append(getValue(value.getAdvancesFlag())+",");sb.append(getValue(value.getRecoverWay())+",");sb.append(getValue(value.getPayStatus())+",");sb.append(getValue(value.getLeftDays())+",");sb.append(getValue(value.getHasLeaveDays())+",");sb.append(getValue(value.getOverdueInterest())+",");sb.append(getValue(value.getProductType())+",");sb.append(getValue(value.getPeriods())+",");sb.append(getValue(value.getEarlyDerateAmt())+",");sb.append(getValue(value.getExceptionFlag())+",");sb.append(getValue(value.getRealPayAmount())+",");sb.append(getValue(value.getAssistFlag())+",");
        sb.append(getValue(value.getExecutedPeriods())+",");
        sb.append(getValue(value.getFirstPayDate())+",");
        sb.append(getValue(value.getOverDueDate())+",");
        sb.append(getValue(value.getDepartment())+",");
        sb.append(getValue(value.getEarlySettleAmt())+",");
        sb.append(getValue(value.getAccountAge())+",");
        sb.append(getValue(value.getMemo())+",");
        sb.append(getValue(value.getOtherAmt())+",");
        sb.append(getValue(value.getOverdueAccountNumber())+",");
        sb.append(getValue(value.getScore())+",");
        sb.append(getValue(value.getProduct().getProductName())+",");
        sb.append(getValue(value.getHasPayAmount())+",");
        sb.append(getValue(value.getFollowUpNum())+",");
        sb.append(getValue(value.getCurrentDebtAmount())+",");
        sb.append(getValue(value.getLeftInterest())+",");
        sb.append(getValue(value.getCaseType())+",");
        sb.append(getValue(value.getEndRemark())+",");
        sb.append(getValue(value.getContractNumber())+",");
        sb.append(getValue(value.getOverdueDays())+",");
        sb.append(getValue(value.getOverdueManageFeeBefore())+",");
        sb.append(getValue(value.getBusDate())+",");
        sb.append(getValue(value.getCleanDate())+",");
        sb.append(getValue(value.getPnltInterest())+",");
        sb.append(getValue(value.getCollectionMethod())+",");
        sb.append(getValue(value.getCreateTime())+",");
        sb.append(getValue(value.getMerchantName())+",");
        sb.append(getValue(value.getCloseDate())+",");
        sb.append(getValue(value.getAccountBalance())+",");
        sb.append(getValue(value.getEarlyRealSettleAmt())+",");
        sb.append(getValue(value.getIntoApplyId())+",");
        sb.append(getValue(value.getOverdueManageFee())+",");
        sb.append(getValue(value.getLeftPeriods())+",");
        sb.append(getValue(value.getPerDueDate())+",");
        sb.append(getValue(value.getRemainFee())+",");
        sb.append(getValue(value.getPnltFine())+",");
        sb.append(getValue(value.getUnpaidInterest())+",");
        sb.append(getValue(value.getRepayAccountNo())+",");
        sb.append(getValue(value.getLeaveCaseFlag())+",");
        sb.append(getValue(value.getCollectionType())+",");
        sb.append(getValue(value.getRepayDate())+",");
        sb.append(getValue(value.getSettleAmount())+",");
        sb.append(getValue(value.getAssistStatus())+",");
        sb.append(getValue(value.getExceptionType())+",");
        sb.append(getValue(value.getFiveLevel())+",");
        sb.append(getValue(value.getOverdueDelayFine())+",");
        sb.append(getValue(value.getOrderId())+",");
        sb.append(getValue(value.getOutColcnt())+",");
        sb.append(getValue(value.getOverdueInterestBefore())+",");
        sb.append(getValue(value.getOverdueCount())+",");
        sb.append(getValue(value.getApplyPeriod())+",");
        sb.append(getValue(value.getIntoTime())+",");
        sb.append(getValue(value.getInColcnt())+",");
        sb.append(getValue(value.getUnpaidFine())+",");
        sb.append(getValue(value.getArea())+",");
        sb.append(getValue(value.getLatelyPayAmount())+",");
        sb.append(getValue(value.getPersonalInfo().getId())+",");
        sb.append(getValue(value.getOverdueCapitalInterest())+",");
        sb.append(getValue(value.getDelegationDate())+",");
        sb.append(getValue(value.getStopTime())+",");
        sb.append(getValue(value.getLoanPurpose())+",");
        sb.append(getValue(value.getCompanyCode())+",");
        sb.append(getValue(value.getLatesDateReturn())+",");
        sb.append(getValue(value.getRecoverRemark())+",");
        sb.append(getValue(value.getProduct().getId())+",");
        sb.append(getValue(value.getMovingBackFlag())+",");
        sb.append(getValue(value.getLoanPayTime())+",");
        sb.append(getValue(value.getId())+",");
        sb.append(getValue(value.getOverdueAmount())+",");
        sb.append(getValue(value.getHandNumber())+",");
        sb.append(getValue(value.getUnpaidMthFee())+",");
        sb.append(getValue(value.getUnpaidOtherInterest())+",");
        sb.append(getValue(value.getCustomerId())+")");
        sb.append(";");
        Query query = entityManager.createNativeQuery(sb.toString());
        int result = query.executeUpdate();
        return result;
    }

    @Modifying
    @Transactional
    public int batchInsert(List<CaseInfoDistributed> values){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into `case_info_distributed` ( " +
                "`unpaid_lpc`, `overdue_periods`, `left_capital`, " +
                "`hold_days`, `loan_period`, `batch_number`, `derate_amt`, `case_pool_type`, " +
                "`max_overdue_days`, `operator_time`, `source_channel`, `contract_amount`, " +
                "`recover_memo`, `collection_status`, `credit_period`, `store_name`, `case_number`, " +
                "`curr_pnlt_interest`, `lately_pay_date`, `credit_amount`, `repay_bank`, " +
                "`overdue_delay_fine_current`, `overdue_capital`, `loan_date`, `overdue_fine`, " +
                "`principal_id`, `assist_way`, `pre_repay_principal`, `operator`, `commission_rate`, " +
                "`settle_date`, `loan_amount`, `unpaid_principal`, `loan_invoice_number`, " +
                "`verfication_status`, `overdue_interest_current`, `leave_date`, `case_mark`, " +
                "`overdue_manage_fee_current`, `case_follow_in_time`, `unpaid_other_fee`, " +
                "`is_processed`, `has_pay_periods`, `per_pay_amount`, `risk_type`, " +
                "`overdue_delay_fine_before`, `update_time`, `hand_up_flag`, `apply_amount`, " +
                "`exception_check_time`, `advances_flag`, `recover_way`, `pay_status`, `left_days`, " +
                "`has_leave_days`, `overdue_interest`, `product_type`, `periods`, `early_derate_amt`, " +
                "`exception_flag`, `real_pay_amount`, `assist_flag`, `executed_periods`, `first_pay_date`, " +
                "`over_due_date`, `depart_id`, `early_settle_amt`, `account_age`, `memo`, `other_amt`, " +
                "`overdue_account_number`, `score`, `product_name`, `has_pay_amount`, `follow_up_num`, " +
                "`current_debt_amount`, `left_interest`, `case_type`, `end_remark`, `contract_number`, " +
                "`overdue_days`, `overdue_manage_fee_before`, `bus_date`, `clean_date`, `pnlt_interest`, " +
                "`collection_method`, `create_time`, `merchant_name`, `close_date`, `account_balance`, " +
                "`early_real_settle_amt`, `into_apply_id`, `overdue_manage_fee`, `left_periods`, `per_due_date`, " +
                "`remain_fee`, `pnlt_fine`, `unpaid_interest`, `repay_account_no`, `leave_case_flag`, " +
                "`collection_type`, `repay_date`, `settle_amount`, `assist_status`, `exception_type`, " +
                "`five_level`, `overdue_delay_fine`, `order_id`, `out_colcnt`, `overdue_interest_before`, " +
                "`overdue_count`, `apply_period`, `into_time`, `in_colcnt`, `unpaid_fine`, `area_id`, " +
                "`lately_pay_amount`, `personal_id`, `overdue_capital_interest`, `delegation_date`, `stop_time`, " +
                "`loan_purpose`, `company_code`, `lates_date_return`, `recover_remark`, `product_id`, " +
                "`moving_back_flag`, `loan_pay_time`, `id`, `overdue_amount`, `hand_number`, `unpaid_mth_fee`, " +
                "`unpaid_other_interest`, `customer_id`) values ");

        for(int i=0;i<values.size();i++){
            CaseInfoDistributed value = values.get(i);
            value.setId(ShortUUID.uuid());
            sb.append("(");
            sb.append(getValue(value.getUnpaidLpc())+",");sb.append(getValue(value.getOverduePeriods())+",");sb.append(getValue(value.getLeftCapital())+",");sb.append(getValue(value.getHoldDays())+",");sb.append(getValue(value.getLoanPeriod())+",");sb.append(getValue(value.getBatchNumber())+",");sb.append(getValue(value.getDerateAmt())+",");sb.append(getValue(value.getCasePoolType())+",");sb.append(getValue(value.getMaxOverdueDays())+",");sb.append(getValue(value.getOperatorTime())+",");sb.append(getValue(value.getSourceChannel())+",");
            sb.append(getValue(value.getContractAmount())+",");sb.append(getValue(value.getRecoverMemo())+",");sb.append(getValue(value.getCollectionStatus())+",");sb.append(getValue(value.getCreditPeriod())+",");sb.append(getValue(value.getStoreName())+",");sb.append(getValue(value.getCaseNumber())+",");sb.append(getValue(value.getCurrPnltInterest())+",");sb.append(getValue(value.getLatelyPayDate())+",");sb.append(getValue(value.getCreditAmount())+",");
            sb.append(getValue(value.getRepayBank())+",");sb.append(getValue(value.getOverdueDelayFineCurrent())+",");sb.append(getValue(value.getOverdueCapital())+",");sb.append(getValue(value.getLoanDate())+",");sb.append(getValue(value.getOverdueFine())+",");sb.append(getValue(value.getPrincipalId())+",");sb.append(getValue(value.getAssistWay())+",");sb.append(getValue(value.getPreRepayPrincipal())+",");sb.append(getValue(value.getOperator())+",");
            sb.append(getValue(value.getCommissionRate())+",");sb.append(getValue(value.getSettleDate())+",");sb.append(getValue(value.getLoanAmount())+",");sb.append(getValue(value.getUnpaidPrincipal())+",");sb.append(getValue(value.getLoanInvoiceNumber())+",");sb.append(getValue(value.getVerficationStatus())+",");sb.append(getValue(value.getOverdueInterestCurrent())+",");
            sb.append(getValue(value.getLeaveDate())+",");sb.append(getValue(value.getCaseMark())+",");sb.append(getValue(value.getOverdueManageFeeCurrent())+",");sb.append(getValue(value.getCaseFollowInTime())+",");sb.append(getValue(value.getUnpaidOtherFee())+",");sb.append(getValue(value.getIsProcessed())+",");sb.append(getValue(value.getHasPayPeriods())+",");sb.append(getValue(value.getPerPayAmount())+",");sb.append(getValue(value.getRiskType())+",");sb.append(getValue(value.getOverdueDelayFineBefore())+",");
            sb.append(getValue(value.getUpdateTime())+",");sb.append(getValue(value.getHandUpFlag())+",");sb.append(getValue(value.getApplyAmount())+",");sb.append(getValue(value.getExceptionCheckTime())+",");sb.append(getValue(value.getAdvancesFlag())+",");sb.append(getValue(value.getRecoverWay())+",");sb.append(getValue(value.getPayStatus())+",");sb.append(getValue(value.getLeftDays())+",");sb.append(getValue(value.getHasLeaveDays())+",");sb.append(getValue(value.getOverdueInterest())+",");sb.append(getValue(value.getProductType())+",");sb.append(getValue(value.getPeriods())+",");sb.append(getValue(value.getEarlyDerateAmt())+",");sb.append(getValue(value.getExceptionFlag())+",");sb.append(getValue(value.getRealPayAmount())+",");sb.append(getValue(value.getAssistFlag())+",");
            sb.append(getValue(value.getExecutedPeriods())+",");
            sb.append(getValue(value.getFirstPayDate())+",");
            sb.append(getValue(value.getOverDueDate())+",");
            sb.append(getValue(value.getDepartment())+",");
            sb.append(getValue(value.getEarlySettleAmt())+",");
            sb.append(getValue(value.getAccountAge())+",");
            sb.append(getValue(value.getMemo())+",");
            sb.append(getValue(value.getOtherAmt())+",");
            sb.append(getValue(value.getOverdueAccountNumber())+",");
            sb.append(getValue(value.getScore())+",");
            sb.append(getValue(value.getProduct().getProductName())+",");
            sb.append(getValue(value.getHasPayAmount())+",");
            sb.append(getValue(value.getFollowUpNum())+",");
            sb.append(getValue(value.getCurrentDebtAmount())+",");
            sb.append(getValue(value.getLeftInterest())+",");
            sb.append(getValue(value.getCaseType())+",");
            sb.append(getValue(value.getEndRemark())+",");
            sb.append(getValue(value.getContractNumber())+",");
            sb.append(getValue(value.getOverdueDays())+",");
            sb.append(getValue(value.getOverdueManageFeeBefore())+",");
            sb.append(getValue(value.getBusDate())+",");
            sb.append(getValue(value.getCleanDate())+",");
            sb.append(getValue(value.getPnltInterest())+",");
            sb.append(getValue(value.getCollectionMethod())+",");
            sb.append(getValue(value.getCreateTime())+",");
            sb.append(getValue(value.getMerchantName())+",");
            sb.append(getValue(value.getCloseDate())+",");
            sb.append(getValue(value.getAccountBalance())+",");
            sb.append(getValue(value.getEarlyRealSettleAmt())+",");
            sb.append(getValue(value.getIntoApplyId())+",");
            sb.append(getValue(value.getOverdueManageFee())+",");
            sb.append(getValue(value.getLeftPeriods())+",");
            sb.append(getValue(value.getPerDueDate())+",");
            sb.append(getValue(value.getRemainFee())+",");
            sb.append(getValue(value.getPnltFine())+",");
            sb.append(getValue(value.getUnpaidInterest())+",");
            sb.append(getValue(value.getRepayAccountNo())+",");
            sb.append(getValue(value.getLeaveCaseFlag())+",");
            sb.append(getValue(value.getCollectionType())+",");
            sb.append(getValue(value.getRepayDate())+",");
            sb.append(getValue(value.getSettleAmount())+",");
            sb.append(getValue(value.getAssistStatus())+",");
            sb.append(getValue(value.getExceptionType())+",");
            sb.append(getValue(value.getFiveLevel())+",");
            sb.append(getValue(value.getOverdueDelayFine())+",");
            sb.append(getValue(value.getOrderId())+",");
            sb.append(getValue(value.getOutColcnt())+",");
            sb.append(getValue(value.getOverdueInterestBefore())+",");
            sb.append(getValue(value.getOverdueCount())+",");
            sb.append(getValue(value.getApplyPeriod())+",");
            sb.append(getValue(value.getIntoTime())+",");
            sb.append(getValue(value.getInColcnt())+",");
            sb.append(getValue(value.getUnpaidFine())+",");
            sb.append(getValue(value.getArea())+",");
            sb.append(getValue(value.getLatelyPayAmount())+",");
            sb.append(getValue(value.getPersonalInfo().getId())+",");
            sb.append(getValue(value.getOverdueCapitalInterest())+",");
            sb.append(getValue(value.getDelegationDate())+",");
            sb.append(getValue(value.getStopTime())+",");
            sb.append(getValue(value.getLoanPurpose())+",");
            sb.append(getValue(value.getCompanyCode())+",");
            sb.append(getValue(value.getLatesDateReturn())+",");
            sb.append(getValue(value.getRecoverRemark())+",");
            sb.append(getValue(value.getProduct().getId())+",");
            sb.append(getValue(value.getMovingBackFlag())+",");
            sb.append(getValue(value.getLoanPayTime())+",");
            sb.append(getValue(value.getId())+",");
            sb.append(getValue(value.getOverdueAmount())+",");
            sb.append(getValue(value.getHandNumber())+",");
            sb.append(getValue(value.getUnpaidMthFee())+",");
            sb.append(getValue(value.getUnpaidOtherInterest())+",");
            sb.append(getValue(value.getCustomerId())+")");
            if(i<values.size()-1) {
                sb.append(",");
            }else{
                sb.append(";");
            }
        }
        int result=0;
//        try{
//            this.entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery(sb.toString());
        result = query.executeUpdate();
//            this.entityManager.getTransaction().commit();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }finally{
//            this.entityManager.close();
        return result;
//        }
    }

    @Modifying
    @Transactional
    public int update(CaseInfoDistributed caseInfoDistributed){
        StringBuffer sb = new StringBuffer();
        sb.append("update `case_info_distributed` set ");
        if(caseInfoDistributed.getOverdueDays()!=null){
            sb.append("overdue_days="+getValue(caseInfoDistributed.getOverdueDays())+",");
        }
        if(caseInfoDistributed.getOverduePeriods()!=null){
            sb.append("overdue_periods="+getValue(caseInfoDistributed.getOverduePeriods())+",");
        }
        if(StringUtils.isNotEmpty(caseInfoDistributed.getPayStatus())){
            sb.append("pay_status="+getValue(caseInfoDistributed.getPayStatus())+",");
        }
        if(StringUtils.isNotEmpty(caseInfoDistributed.getIntoApplyId())){
            sb.append("into_apply_id="+getValue(caseInfoDistributed.getIntoApplyId())+",");
        }
        if(caseInfoDistributed.getAccountBalance()!=null){
            sb.append("account_balance="+getValue(caseInfoDistributed.getAccountBalance())+",");
        }
        if(caseInfoDistributed.getIntoTime()!=null){
            sb.append("into_time="+getValue(caseInfoDistributed.getIntoTime())+",");
        }
        if(caseInfoDistributed.getLoanPayTime()!=null){
            sb.append("loan_pay_time="+getValue(caseInfoDistributed.getLoanPayTime())+",");
        }
        if(StringUtils.isNotEmpty(caseInfoDistributed.getFiveLevel())){
            sb.append("five_level="+getValue(caseInfoDistributed.getFiveLevel())+",");
        }
        if(caseInfoDistributed.getApplyPeriod()!=null){
            sb.append("apply_period="+getValue(caseInfoDistributed.getApplyPeriod())+",");
        }
        if(caseInfoDistributed.getCreditPeriod()!=null){
            sb.append("credit_period="+getValue(caseInfoDistributed.getCreditPeriod())+",");
        }
        if(caseInfoDistributed.getLoanPeriod()!=null){
            sb.append("loan_period="+getValue(caseInfoDistributed.getLoanPeriod())+",");
        }
        if(caseInfoDistributed.getApplyAmount()!=null){
            sb.append("apply_amount="+getValue(caseInfoDistributed.getApplyAmount())+",");
        }
        if(caseInfoDistributed.getCreditAmount()!=null){
            sb.append("credit_amount="+getValue(caseInfoDistributed.getCreditAmount())+",");
        }
        if(caseInfoDistributed.getLoanAmount()!=null){
            sb.append("loan_amount="+getValue(caseInfoDistributed.getLoanAmount())+",");
        }
        if(caseInfoDistributed.getOverdueAmount()!=null){
            sb.append("overdue_amount="+getValue(caseInfoDistributed.getOverdueAmount())+",");
        }
        if(caseInfoDistributed.getOverdueCapitalInterest()!=null){
            sb.append("overdue_capital_interest="+getValue(caseInfoDistributed.getOverdueCapitalInterest())+",");
        }
        if(caseInfoDistributed.getOverdueCapital()!=null){
            sb.append("overdue_capital="+getValue(caseInfoDistributed.getOverdueCapital())+",");
        }
        if(caseInfoDistributed.getOverdueInterestBefore()!=null){
            sb.append("overdue_interest_before="+getValue(caseInfoDistributed.getOverdueInterestBefore())+",");
        }
        if(caseInfoDistributed.getOverdueInterestCurrent()!=null){
            sb.append("overdue_interest_current="+getValue(caseInfoDistributed.getOverdueInterestCurrent())+",");
        }
        if(caseInfoDistributed.getOverdueInterest()!=null){
            sb.append("overdue_interest="+getValue(caseInfoDistributed.getOverdueInterest())+",");
        }
        if(caseInfoDistributed.getOverdueDelayFineBefore()!=null){
            sb.append("overdue_delay_fine_before="+getValue(caseInfoDistributed.getOverdueDelayFineBefore())+",");
        }
        if(caseInfoDistributed.getOverdueDelayFineCurrent()!=null){
            sb.append("overdue_delay_fine_current="+getValue(caseInfoDistributed.getOverdueDelayFineCurrent())+",");
        }
        if(caseInfoDistributed.getOverdueDelayFine()!=null){
            sb.append("overdue_delay_fine="+getValue(caseInfoDistributed.getOverdueDelayFine())+",");
        }
        if(caseInfoDistributed.getCurrentDebtAmount()!=null){
            sb.append("current_debt_amount="+getValue(caseInfoDistributed.getCurrentDebtAmount())+",");
        }
        if(caseInfoDistributed.getLoanPurpose()!=null){
            sb.append("loan_purpose="+getValue(caseInfoDistributed.getLoanPurpose())+",");
        }
        if(caseInfoDistributed.getLeftPeriods()!=null){
            sb.append("left_periods="+getValue(caseInfoDistributed.getLeftPeriods())+",");
        }
        if(caseInfoDistributed.getContractNumber()!=null){
            sb.append("contract_number="+getValue(caseInfoDistributed.getContractNumber())+",");
        }
        if(caseInfoDistributed.getLoanInvoiceNumber()!=null){
            sb.append("loan_invoice_number="+getValue(caseInfoDistributed.getLoanInvoiceNumber())+",");
        }
        if(caseInfoDistributed.getPreRepayPrincipal()!=null){
            sb.append("pre_repay_principal="+getValue(caseInfoDistributed.getPreRepayPrincipal())+",");
        }
        if(caseInfoDistributed.getAdvancesFlag()!=null){
            sb.append("advances_flag="+getValue(caseInfoDistributed.getAdvancesFlag())+",");
        }
        if(caseInfoDistributed.getOverdueManageFeeCurrent()!=null){
            sb.append("overdue_manage_fee_current="+getValue(caseInfoDistributed.getOverdueManageFeeCurrent())+",");
        }
        if(caseInfoDistributed.getOverdueManageFeeBefore()!=null){
            sb.append("overdue_manage_fee_before="+getValue(caseInfoDistributed.getOverdueManageFeeBefore())+",");
        }
        if(caseInfoDistributed.getOverdueManageFee()!=null){
            sb.append("overdue_manage_fee="+getValue(caseInfoDistributed.getOverdueManageFee())+",");
        }
        if(caseInfoDistributed.getRepayDate()!=null){
            sb.append("repay_date="+getValue(caseInfoDistributed.getRepayDate())+",");
        }
        if(caseInfoDistributed.getMovingBackFlag()!=null){
            sb.append("moving_back_flag="+getValue(caseInfoDistributed.getMovingBackFlag())+",");
        }
        if(caseInfoDistributed.getVerficationStatus()!=null){
            sb.append("verfication_status="+getValue(caseInfoDistributed.getVerficationStatus())+",");
        }
        if(caseInfoDistributed.getCreateTime()!=null){
            sb.append("create_time="+getValue(caseInfoDistributed.getCreateTime())+",");
        }
        if(caseInfoDistributed.getUpdateTime()!=null){
            sb.append("update_time="+getValue(caseInfoDistributed.getUpdateTime())+",");
        }
        if(caseInfoDistributed.getPnltFine()!=null){
            sb.append("pnlt_fine="+getValue(caseInfoDistributed.getPnltFine())+",");
        }
        if(caseInfoDistributed.getUnpaidFine()!=null){
            sb.append("unpaid_fine="+getValue(caseInfoDistributed.getUnpaidFine())+",");
        }
        if(caseInfoDistributed.getOverdueFine()!=null){
            sb.append("overdue_fine="+getValue(caseInfoDistributed.getOverdueFine())+",");
        }
        if(caseInfoDistributed.getBusDate()!=null){
            sb.append("bus_date="+getValue(caseInfoDistributed.getBusDate())+",");
        }
        if(caseInfoDistributed.getOverdueCount()!=null){
            sb.append("overdue_count="+getValue(caseInfoDistributed.getOverdueCount())+",");
        }
        if(caseInfoDistributed.getMerchantName()!=null){
            sb.append("merchant_name="+getValue(caseInfoDistributed.getMerchantName())+",");
        }
        if(caseInfoDistributed.getStoreName()!=null){
            sb.append("store_name="+getValue(caseInfoDistributed.getStoreName())+",");
        }
        if(caseInfoDistributed.getRepayAccountNo()!=null){
            sb.append("repay_account_no="+getValue(caseInfoDistributed.getRepayAccountNo())+",");
        }
        if(caseInfoDistributed.getRepayBank()!=null){
            sb.append("repay_bank="+getValue(caseInfoDistributed.getRepayBank())+",");
        }
        if(caseInfoDistributed.getOverdueCount()!=null){
            sb.append("overdue_count="+getValue(caseInfoDistributed.getOverdueCount())+",");
        }

        String sql=sb.toString().substring(0, sb.toString().length()-1);
        sql=sql+" where id='"+caseInfoDistributed.getId()+"'";
        Query query = entityManager.createNativeQuery(sql);
        int result = query.executeUpdate();
        return result;
    }

}
