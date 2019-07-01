/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.report.model.ApprovalEnquiryModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author : duanwenwu
 * @Description : 多条件查询审批管理
 * @Date : 18/1/16
 */
public interface ApprovalEnquiryMapper{
    /**
     * @Description 多条件查询费用减免审批
     */
    List<ApprovalEnquiryModel> getFeeWaiverApproval(@Param("personalName") String personalName,
                                                    @Param("mobileNo") String mobileNo,
                                                    @Param("batchNumber") String batchNumber,
                                                    @Param("applyDerateMaxAmt")BigDecimal applyDerateMaxAmt,
                                                    @Param("applyDerateMinAmt")BigDecimal applyDerateMinAmt,
                                                    @Param("principalName") String principalName,
                                                    @Param("deptCode") String deptCode,
                                                    @Param("isManager") Integer isManager,
                                                    @Param("userId") String userId,
                                                    @Param("companyCode") String companyCode);
    /**
     * @Description 多条件查询核销案件审批
     */
    List<ApprovalEnquiryModel> getCaseInfoVerificationApproval(
                                                    @Param("personalName") String personalName,
                                                    @Param("mobileNo") String mobileNo,
                                                    @Param("idCard") String idCard,
                                                    @Param("batchNumber") String batchNumber,
                                                    @Param("overdueMaxAmount")BigDecimal overdueMaxAmount,
                                                    @Param("overdueMinAmount")BigDecimal overdueMinAmount,
                                                    @Param("overdueMaxDays")Integer overdueMaxDays,
                                                    @Param("overdueMinDays")Integer overdueMinDays,
                                                    @Param("applicationDate") String applicationDate,
                                                    @Param("principalName") String principalName,
                                                    @Param("approvalStatus") Integer approvalStatus,
                                                    @Param("deptCode") String deptCode,
                                                    @Param("isManager") Integer isManager,
                                                    @Param("userId") String userId,
                                                    @Param("companyCode") String companyCode);
    /**
     * @Description 多条件查询电催协催申请审批
     */
    List<ApprovalEnquiryModel> getElecAssistApplyCase(
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("overdueMaxAmount")BigDecimal overdueMaxAmount,
            @Param("overdueMinAmount")BigDecimal overdueMinAmount,
            @Param("principalName") String principalName,
            @Param("applyRealName") String applyRealName,
            @Param("deptCode") String deptCode,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询外访协催申请审批
     */
    List<ApprovalEnquiryModel> getOutAssistApplyCase(
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("overdueMaxAmount")BigDecimal overdueMaxAmount,
            @Param("overdueMinAmount")BigDecimal overdueMinAmount,
            @Param("principalName") String principalName,
            @Param("applyRealName") String applyRealName,
            @Param("deptCode") String deptCode,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode);
}