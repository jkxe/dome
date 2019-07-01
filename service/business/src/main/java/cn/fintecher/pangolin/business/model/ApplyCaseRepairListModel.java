package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ZhangYaJun
 * @Title: ApplyCaseRepairListModel
 * @ProjectName pangolin-server-hzyh
 * @Description:
 * @date 2019/1/28 0028下午 13:54
 */
@Data
@ApiModel("审批列表查询")
public class ApplyCaseRepairListModel {

   private  String  personalName;//  客户姓名
   private String mobileNo; // 客户电话
   private String  caseNumber;// 案件编号
   private String loanInvoiceNumber; // 借据号
   private  String personalMobileNo; //客户手机号
   private  String idCard; // 客户身份证
   private  Integer approvalStatus; //审核状态

}
