package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : gaobeibei
 * @Description : 案件文件
 * @Date : 2017/8/15
 */
@Entity
@Table(name = "case_file")
@Data
public class CaseFile implements Serializable {
    @Id
    private String id;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "文件ID")
    private String fileId;

    @ApiModelProperty(notes = "文件类型")
    private String fileType;

    @ApiModelProperty(notes = "文件来源")
    private Integer fileSource;

    @ApiModelProperty(notes = "文件代码")
    private String fileCode;

    @ApiModelProperty(notes = "文件地址")
    private String fileUrl;

    @ApiModelProperty("操作员")
    private String operator;

    @ApiModelProperty("操作员")
    private String operatorName;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("公司Code")
    private String companyCode;

    @ApiModelProperty("案件ID")
    private String caseId;

    @ApiModelProperty("合同id")
    private String contractId;

    @ApiModelProperty("客户id")
    private String customerId;

    @ApiModelProperty("合同类型")
    private String contractType;

    @ApiModelProperty("合同状态")
    private String contractStatus;

    @ApiModelProperty("合同名称")
    private String contractName;

    @ApiModelProperty("授权到期日")
    private Date creditEnddate;

    @ApiModelProperty("授权有效期")
    private String authorizedValid;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品编号")
    private String productNo;

    @ApiModelProperty("法大大获取文件uid")
    private String fileUid;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public enum FileSource{
        Input_data(1000,"进件资料"),
        Supplementary(1001,"补充资料"),
        Online_Contract(1002,"线上合同"),
        Under_Line_Contract(1003,"线下合同"),
        Other_Information(1004,"其他资料");

        private Integer values;
        private String remark;

        FileSource(Integer values,String remark){
            this.values = values;
            this.remark = remark;
        }
        public Integer getValues() {
            return values;
        }
        public String getRemark() {
            return remark;
        }
    }
}
