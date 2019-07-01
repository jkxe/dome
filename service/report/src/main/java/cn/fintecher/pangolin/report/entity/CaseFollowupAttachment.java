package cn.fintecher.pangolin.report.entity;

import cn.fintecher.pangolin.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  APP端案件协催记录 附件model，包含音频文件与附件
 * @Package cn.fintecher.pangolin.report.entity
 * @ClassName: cn.fintecher.pangolin.report.entity.CaseFollowupAttachment
 * @date 2018年10月09日 16:47
 */
@Data
//@Entity
//@Table(name = "case_followup_attachment")
public class CaseFollowupAttachment extends BaseEntity {


    @ApiModelProperty("案件协催记录id")
    private String caseFollowupRecordId;

    @ApiModelProperty("附件名称")
    private String attachmentName;

    @ApiModelProperty("附件url")
    private String attachmentUrl;

    @ApiModelProperty("附件类型")
    private String type;

    @ApiModelProperty("附件类型名称")
    private String typeName;

    @ApiModelProperty("操作员")
    private String operator;

    @ApiModelProperty("操作时间")
    private Date operatorTime;


}
