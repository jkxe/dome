package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("协催申请对象")
public class AssistApplyProcessMode {

    private String caseNumber; //案件id
    private Integer assistWay; //协催方式(单次协催30，全程协催31)
    private String applyReason;//协催说明（原因）

    private String taskId; //任务id

}
