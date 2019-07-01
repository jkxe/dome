package cn.fintecher.pangolin.report.model;

import lombok.Data;

/**
 * @author : chenjing
 * @Description : 案件流转记录多条件查询对象
 * @Date : 10:19 2018/6/20
 */

@Data
public class CaseTurnParams {
    private String caseNumber;//案件编号（借据号）
    private String batchNumber;//批次号
    private String principalName;//委托方
    private String personName;//客户名
    private String mobileNo;//手机号
    private String idCard;//身份证号
    private Integer turnFromPool;//流转去向
    private Integer turnToPool;//流转来源
    private Integer casePoolType;// 当前案件类型
    private String operatorStartTime;//开始流转日期
    private String operatorEndTime;//截止流转日期
    private String applyTime;//申请日期
    private Integer turnApprovalStatus;//流转审核状态（待审批-213，通过-214，拒绝-215）
    private String circulationType;//流转类型
    private Integer triggerAction;// 触发动作
    private Integer page;//当前页数
    private Integer size;//每页行数
}
