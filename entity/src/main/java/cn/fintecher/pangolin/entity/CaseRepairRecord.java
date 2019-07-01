package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @author yuanyanting
 * @version Id:CaseRepairRecord.java,v 0.1 2017/8/8 16:26 yuanyanting Exp $$
 */

@Entity
@Data
@Table(name = "case_repair_record")
public class CaseRepairRecord extends BaseEntity {


    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "文件ID")
    private String fileId;

    @ApiModelProperty(notes = "文件类型")
    private String fileType;

    @ApiModelProperty(notes = "文件地址")
    private String fileUrl;

    @ApiModelProperty(notes = "修复说明")
    private String repairMemo;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "案件ID")
    private String caseId;

     @ApiModelProperty("案件信修主键")
    private String repairFileId;

}
