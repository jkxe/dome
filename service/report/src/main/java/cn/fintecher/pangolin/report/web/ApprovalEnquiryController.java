/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.mapper.ApprovalEnquiryMapper;
import cn.fintecher.pangolin.report.model.ApprovalEnquiryModel;
import cn.fintecher.pangolin.report.model.ApprovalEnquiryParams;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author : duanwenwu
 * @Description : 多条件查询审批管理
 * @Date : 18/1/16
 */
@RestController
@RequestMapping("/api/approvalEnquiryController")
@Api(description = "多条件查询审批管理")
public class ApprovalEnquiryController extends BaseController{
    private final Logger log = LoggerFactory.getLogger(ApprovalEnquiryController.class);
    @Inject
    ApprovalEnquiryMapper approvalEnquiryMapper;

    /**
     * @Description 多条件查询费用减免审批
     */
    @GetMapping("/getFeeWaiverApproval")
    @ApiOperation(value = "多条件查询费用减免审批", notes = "多条件查询费用减免审批")
    public ResponseEntity<Page<ApprovalEnquiryModel>> getFeeWaiverApproval(ApprovalEnquiryParams approvalEnquiryParams,
                                                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get Fee Waiver Approval");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(approvalEnquiryParams.getPage() + 1, approvalEnquiryParams.getSize());
            List<ApprovalEnquiryModel> approvalEnquiryModels = approvalEnquiryMapper.getFeeWaiverApproval(
                    StringUtils.trim(approvalEnquiryParams.getPersonalName()),
                    StringUtils.trim(approvalEnquiryParams.getMobileNo()),
                    StringUtils.trim(approvalEnquiryParams.getBatchNumber()),
                    approvalEnquiryParams.getApplyDerateMaxAmt(),
                    approvalEnquiryParams.getApplyDerateMinAmt(),
                    StringUtils.trim(approvalEnquiryParams.getPrincipalName()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<ApprovalEnquiryModel> pageInfo = new PageInfo<>(approvalEnquiryModels);
            Pageable pageable = new PageRequest(approvalEnquiryParams.getPage(), approvalEnquiryParams.getSize());
            Page<ApprovalEnquiryModel> page = new PageImpl<>(approvalEnquiryModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "ApprovalEnquiryModel")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalEnquiryModel", "ApprovalEnquiryModel", "查询失败")).body(null);
        }
    }
    /**
     * @Description 多条件查询核销案件审批
     */
    @GetMapping("/getCaseInfoVerificationApproval")
    @ApiOperation(value = "多条件查询核销案件审批", notes = "多条件查询核销案件审批")
    public ResponseEntity<Page<ApprovalEnquiryModel>> getCaseInfoVerificationApproval(ApprovalEnquiryParams approvalEnquiryParams,
                                                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get Fee Waiver Approval");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(approvalEnquiryParams.getPage() + 1, approvalEnquiryParams.getSize());
            List<ApprovalEnquiryModel> approvalEnquiryModels = approvalEnquiryMapper.getCaseInfoVerificationApproval(
                    StringUtils.trim(approvalEnquiryParams.getPersonalName()),
                    StringUtils.trim(approvalEnquiryParams.getMobileNo()),
                    StringUtils.trim(approvalEnquiryParams.getIdCard()),
                    StringUtils.trim(approvalEnquiryParams.getBatchNumber()),
                    approvalEnquiryParams.getOverdueMaxAmount(),
                    approvalEnquiryParams.getOverdueMinAmount(),
                    approvalEnquiryParams.getOverdueMaxDays(),
                    approvalEnquiryParams.getOverdueMinDays(),
                    StringUtils.trim(approvalEnquiryParams.getApplicationDate()),
                    StringUtils.trim(approvalEnquiryParams.getPrincipalName()),
                    approvalEnquiryParams.getApprovalStatus(),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<ApprovalEnquiryModel> pageInfo = new PageInfo<>(approvalEnquiryModels);
            Pageable pageable = new PageRequest(approvalEnquiryParams.getPage(), approvalEnquiryParams.getSize());
            Page<ApprovalEnquiryModel> page = new PageImpl<>(approvalEnquiryModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "ApprovalEnquiryModel")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalEnquiryModel", "ApprovalEnquiryModel", "查询失败")).body(null);
        }
    }
    /**
     * @Description 多条件查询电催协催申请审批
     */
    @GetMapping("/getElecAssistApplyCase")
    @ApiOperation(value = "多条件查询电催协催申请审批", notes = "多条件查询电催协催申请审批")
    public ResponseEntity<Page<ApprovalEnquiryModel>> getElecAssistApplyCase(ApprovalEnquiryParams approvalEnquiryParams,
                                                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get get Assist Apply Case");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(approvalEnquiryParams.getPage() + 1, approvalEnquiryParams.getSize());
            List<ApprovalEnquiryModel> approvalEnquiryModels = approvalEnquiryMapper.getElecAssistApplyCase(
                    StringUtils.trim(approvalEnquiryParams.getPersonalName()),
                    StringUtils.trim(approvalEnquiryParams.getMobileNo()),
                    approvalEnquiryParams.getOverdueMaxAmount(),
                    approvalEnquiryParams.getOverdueMinAmount(),
                    StringUtils.trim(approvalEnquiryParams.getPrincipalName()),
                    StringUtils.trim(approvalEnquiryParams.getApplyRealName()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<ApprovalEnquiryModel> pageInfo = new PageInfo<>(approvalEnquiryModels);
            Pageable pageable = new PageRequest(approvalEnquiryParams.getPage(), approvalEnquiryParams.getSize());
            Page<ApprovalEnquiryModel> page = new PageImpl<>(approvalEnquiryModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "ApprovalEnquiryModel")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalEnquiryModel", "ApprovalEnquiryModel", "查询失败")).body(null);
        }
    }
    /**
     * @Description 多条件查询外访协催申请审批
     */
    @GetMapping("/getOutAssistApplyCase")
    @ApiOperation(value = "多条件查询外访协催申请审批", notes = "多条件查询外访协催申请审批")
    public ResponseEntity<Page<ApprovalEnquiryModel>> getOutAssistApplyCase(ApprovalEnquiryParams approvalEnquiryParams,
                                                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get get Assist Apply Case");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(approvalEnquiryParams.getPage() + 1, approvalEnquiryParams.getSize());
            List<ApprovalEnquiryModel> approvalEnquiryModels = approvalEnquiryMapper.getOutAssistApplyCase(
                    StringUtils.trim(approvalEnquiryParams.getPersonalName()),
                    StringUtils.trim(approvalEnquiryParams.getMobileNo()),
                    approvalEnquiryParams.getOverdueMaxAmount(),
                    approvalEnquiryParams.getOverdueMinAmount(),
                    StringUtils.trim(approvalEnquiryParams.getPrincipalName()),
                    StringUtils.trim(approvalEnquiryParams.getApplyRealName()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<ApprovalEnquiryModel> pageInfo = new PageInfo<>(approvalEnquiryModels);
            Pageable pageable = new PageRequest(approvalEnquiryParams.getPage(), approvalEnquiryParams.getSize());
            Page<ApprovalEnquiryModel> page = new PageImpl<>(approvalEnquiryModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "ApprovalEnquiryModel")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalEnquiryModel", "ApprovalEnquiryModel", "查询失败")).body(null);
        }
    }
}