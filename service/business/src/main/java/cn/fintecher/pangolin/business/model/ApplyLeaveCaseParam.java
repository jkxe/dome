package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.util.List;

@Data
public class ApplyLeaveCaseParam {

    private String taskId;//任务id
    private List<String> caseIdList;//案件id集合
    private String leaveReason;//留案按说明
    private String leftDate;//结案时间
}
