/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 *@author duanwenwu
 *@Description 核销案件下载记录展示模型
 *@Date 18/1/11
 */
@Data
public class caseInfoVerificationPackagingModel {
    private Date packagingTime;//打包时间
    private Integer count;// 案件数量
    private BigDecimal totalAmount;//案件总金额
    private Integer downloadCount;//下载次数
    private String packagingState; //备注
}