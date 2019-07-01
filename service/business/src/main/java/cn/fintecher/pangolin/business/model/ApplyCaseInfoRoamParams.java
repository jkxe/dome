package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("流转申请对象")
public class ApplyCaseInfoRoamParams {

    private List<String> caseId;//案件id
    private String taskId;//任务id
    private Integer pooltype;//流转去向类型
    private String turnDescribe;//流转说明
}
