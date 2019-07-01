package cn.fintecher.pangolin.business.repository;


import cn.fintecher.pangolin.entity.OverdueDetail;
import com.hsjry.lang.common.util.DateUtil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class OverdueDetailBatchRepository{
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

    @Modifying
    @Transactional
    public int insert(OverdueDetail value){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into `hy_overdue_details` ( " +
                "`branch_name`, `credit_period`, `verification_status`, " +
                "`update_time`, `certificate_no`, `merchant_name`, " +
                "`loan_amt`, `stat_time`, `loan_pay_time`, `left_overdue_fee`, " +
                "`loan_purpose_name`, `current_debt_amount`, `product_name`, " +
                "`before_current_left_repay_fee`, `loan_amount`, `flag`, " +
                "`loan_invoice_id`, `overdue_days`, `client_name`, `collection_method`, " +
                "`overdue_periods`, `create_time`, `left_repay_fee`, " +
                "`before_current_left_repay_interest`, `certificate_type`, " +
                "`store_name`, `pre_repay_principal`, `contract_id`, " +
                "`before_current_left_overdue_fee`, `five_level`, `into_apply_id`, " +
                "`into_time`, `user_id`, `left_repay_management_fee`, `before_current_left_repay_management_fee`, " +
                "`left_num`, `source_channel`, `repay_num`, `user_account`, `clear_overdue_amount`, " +
                "`case_number`, `bus_date`, `apply_period`, `credit_amt`, `current_pre_repay_principal`, " +
                "`current_month_debt_amount`, `left_repay_interest`, `repay_date`, `apply_amount`, " +
                "`moving_back_flag`) values ");

            OverdueDetail overdueDetail = value;
            sb.append("(");
            sb.append(getValue(overdueDetail.getBranchName())+",");
            sb.append(getValue(overdueDetail.getCreditPeriod())+",");
            sb.append(getValue(overdueDetail.getVerificationStatus())+",");
            sb.append(getValue(overdueDetail.getUpdateTime())+",");
            sb.append(getValue(overdueDetail.getCertificateNo())+",");sb.append(getValue(overdueDetail.getMerchantName())+",");sb.append(getValue(overdueDetail.getLoanAmt())+",");sb.append(getValue(overdueDetail.getStatTime())+",");
            sb.append(getValue(overdueDetail.getLoanPayTime())+",");sb.append(getValue(overdueDetail.getLeftOverdueFee())+",");sb.append(getValue(overdueDetail.getLoanPurposeName())+",");sb.append(getValue(overdueDetail.getCurrentDebtAmount())+",");sb.append(getValue(overdueDetail.getProductName())+",");
            sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayFee())+",");sb.append(getValue(overdueDetail.getLoanAmount())+",");sb.append(getValue(overdueDetail.getFlag())+",");sb.append(getValue(overdueDetail.getLoanInvoiceId())+",");
            sb.append(getValue(overdueDetail.getOverdueDays())+",");
            sb.append(getValue(overdueDetail.getClientName())+",");sb.append(getValue(overdueDetail.getCollectionMethod())+",");sb.append(getValue(overdueDetail.getOverduePeriods())+",");
            sb.append(getValue(overdueDetail.getCreateTime())+",");sb.append(getValue(overdueDetail.getLeftRepayFee())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayInterest())+",");sb.append(getValue(overdueDetail.getCertificateType())+",");
            sb.append(getValue(overdueDetail.getStoreName())+",");sb.append(getValue(overdueDetail.getPreRepayPrincipal())+",");sb.append(getValue(overdueDetail.getContractId())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftOverdueFee())+",");
            sb.append(getValue(overdueDetail.getFiveLevel())+",");sb.append(getValue(overdueDetail.getIntoApplyId())+",");sb.append(getValue(overdueDetail.getIntoTime())+",");sb.append(getValue(overdueDetail.getPersonalInfo().getId())+",");
            sb.append(getValue(overdueDetail.getLeftRepayManagementFee())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayManagementFee())+",");sb.append(getValue(overdueDetail.getLeftNum())+",");sb.append(getValue(overdueDetail.getSourceChannel())+",");
            sb.append(getValue(overdueDetail.getRepayNum())+",");sb.append(getValue(overdueDetail.getUserAccount())+",");sb.append(getValue(overdueDetail.getClearOverdueAmount())+",");sb.append(getValue(overdueDetail.getCaseNumber())+",");
            sb.append(getValue(overdueDetail.getBusDate())+",");sb.append(getValue(overdueDetail.getApplyPeriod())+",");
            sb.append(getValue(overdueDetail.getCreditAmt())+",");sb.append(getValue(overdueDetail.getCurrentPreRepayPrincipal())+",");
            sb.append(getValue(overdueDetail.getCurrentMonthDebtAmount())+",");sb.append(getValue(overdueDetail.getLeftRepayInterest())+",");
            sb.append(getValue(overdueDetail.getRepayDate())+",");sb.append(getValue(overdueDetail.getApplyAmount())+",");
            sb.append(getValue(overdueDetail.getMovingBackFlag())+")");
            sb.append(";");
        Query query = entityManager.createNativeQuery(sb.toString());
        int result = query.executeUpdate();
        return result;
    }

    @Modifying
    @Transactional
    public int batchInsert(List<OverdueDetail> values){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into `hy_overdue_details` ( " +
                "`branch_name`, `credit_period`, `verification_status`, " +
                "`update_time`, `certificate_no`, `merchant_name`, " +
                "`loan_amt`, `stat_time`, `loan_pay_time`, `left_overdue_fee`, " +
                "`loan_purpose_name`, `current_debt_amount`, `product_name`, " +
                "`before_current_left_repay_fee`, `loan_amount`, `flag`, " +
                "`loan_invoice_id`, `overdue_days`, `client_name`, `collection_method`, " +
                "`overdue_periods`, `create_time`, `left_repay_fee`, " +
                "`before_current_left_repay_interest`, `certificate_type`, " +
                "`store_name`, `pre_repay_principal`, `contract_id`, " +
                "`before_current_left_overdue_fee`, `five_level`, `into_apply_id`, " +
                "`into_time`, `user_id`, `left_repay_management_fee`, `before_current_left_repay_management_fee`, " +
                "`left_num`, `source_channel`, `repay_num`, `user_account`, `clear_overdue_amount`, " +
                "`case_number`, `bus_date`, `apply_period`, `credit_amt`, `current_pre_repay_principal`, " +
                "`current_month_debt_amount`, `left_repay_interest`, `repay_date`, `apply_amount`, " +
                "`moving_back_flag`) values ");

        for(int i=0;i<values.size();i++){
            OverdueDetail overdueDetail = values.get(i);
            sb.append("(");
            sb.append(getValue(overdueDetail.getBranchName())+",");
            sb.append(getValue(overdueDetail.getCreditPeriod())+",");
            sb.append(getValue(overdueDetail.getVerificationStatus())+",");
            sb.append(getValue(overdueDetail.getUpdateTime())+",");
            sb.append(getValue(overdueDetail.getCertificateNo())+",");sb.append(getValue(overdueDetail.getMerchantName())+",");sb.append(getValue(overdueDetail.getLoanAmt())+",");sb.append(getValue(overdueDetail.getStatTime())+",");
            sb.append(getValue(overdueDetail.getLoanPayTime())+",");sb.append(getValue(overdueDetail.getLeftOverdueFee())+",");sb.append(getValue(overdueDetail.getLoanPurposeName())+",");sb.append(getValue(overdueDetail.getCurrentDebtAmount())+",");sb.append(getValue(overdueDetail.getProductName())+",");
            sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayFee())+",");sb.append(getValue(overdueDetail.getLoanAmount())+",");sb.append(getValue(overdueDetail.getFlag())+",");sb.append(getValue(overdueDetail.getLoanInvoiceId())+",");
            sb.append(getValue(overdueDetail.getOverdueDays())+",");
            sb.append(getValue(overdueDetail.getClientName())+",");sb.append(getValue(overdueDetail.getCollectionMethod())+",");sb.append(getValue(overdueDetail.getOverduePeriods())+",");
            sb.append(getValue(overdueDetail.getCreateTime())+",");sb.append(getValue(overdueDetail.getLeftRepayFee())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayInterest())+",");sb.append(getValue(overdueDetail.getCertificateType())+",");
            sb.append(getValue(overdueDetail.getStoreName())+",");sb.append(getValue(overdueDetail.getPreRepayPrincipal())+",");sb.append(getValue(overdueDetail.getContractId())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftOverdueFee())+",");
            sb.append(getValue(overdueDetail.getFiveLevel())+",");sb.append(getValue(overdueDetail.getIntoApplyId())+",");sb.append(getValue(overdueDetail.getIntoTime())+",");sb.append(getValue(overdueDetail.getPersonalInfo().getId())+",");
            sb.append(getValue(overdueDetail.getLeftRepayManagementFee())+",");sb.append(getValue(overdueDetail.getBeforeCurrentLeftRepayManagementFee())+",");sb.append(getValue(overdueDetail.getLeftNum())+",");sb.append(getValue(overdueDetail.getSourceChannel())+",");
            sb.append(getValue(overdueDetail.getRepayNum())+",");sb.append(getValue(overdueDetail.getUserAccount())+",");sb.append(getValue(overdueDetail.getClearOverdueAmount())+",");sb.append(getValue(overdueDetail.getCaseNumber())+",");
            sb.append(getValue(overdueDetail.getBusDate())+",");sb.append(getValue(overdueDetail.getApplyPeriod())+",");
            sb.append(getValue(overdueDetail.getCreditAmt())+",");sb.append(getValue(overdueDetail.getCurrentPreRepayPrincipal())+",");
            sb.append(getValue(overdueDetail.getCurrentMonthDebtAmount())+",");sb.append(getValue(overdueDetail.getLeftRepayInterest())+",");
            sb.append(getValue(overdueDetail.getRepayDate())+",");sb.append(getValue(overdueDetail.getApplyAmount())+",");
            sb.append(getValue(overdueDetail.getMovingBackFlag())+")");
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
}
