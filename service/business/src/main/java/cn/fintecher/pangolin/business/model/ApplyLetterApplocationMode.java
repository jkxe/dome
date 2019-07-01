package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 *
 * zyj
 */
@Data
@ApiModel("信修申请对象")
public class ApplyLetterApplocationMode {

    private String caseId; //案件id
  //  private Integer assistWay; //审批流
    private String applyReason;//申请说明（原因）

    private String taskId; //任务id

}
