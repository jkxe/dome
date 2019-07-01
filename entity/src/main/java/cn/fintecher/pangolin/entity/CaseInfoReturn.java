package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author : xiaqun
 * @Description : 案件退案池
 * @Date : 16:52 2017/9/19
 */
@Entity
@Table(name = "case_info_return")
@Data
public class CaseInfoReturn extends BaseEntity {
    @OneToOne
    @ApiModelProperty(notes = "案件ID")
    @JoinColumn(name = "case_id")
    private CaseInfo caseId;

    @JoinColumn(name = "case_number")
    private String caseNumber;

    @ApiModelProperty(notes = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty(notes = "操作人用户名")
    private String operator;

    @ApiModelProperty(notes = "回收说明")
    private String reason;

    @ApiModelProperty(notes = "退回来源 内催，委外，司法，核销")
    private Integer source;

    @ApiModelProperty(notes = "委外方名称")
    private String outsName;

    @ApiModelProperty(notes = "委外时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outTime;

    @ApiModelProperty(notes = "委外到期日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date overOutsourceTime;

    @ApiModelProperty(notes = "委外批次号")
    private String outBatch;

    @ApiModelProperty(notes = "公司Code")
    private String companyCode;

    @ApiModelProperty(notes = "hy-回收类型 数据字典:0401")
    private Integer returnType;
    /**
     * 案件退回来源
     */
    public enum Source {
        //内催
        INTERNALCOLLECTION(225,"内催"),
        //委外
        OUTSOURCE(226, "委外"),
        //司法
        JUDICATURE(227, "司法"),
        //核销
        VERIFICATION(228, "核销");

        private Integer value;

        private String remark;

        Source(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }
    /**
     * 案件退回来源
     */
    public enum ReturnType {
        //自动
        AUTOMATIC(1091, "自动回收"),
        //手动
        MANUAL(1092, "手动回收");

        private Integer value;

        private String remark;

        ReturnType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }

        public static ReturnType getEnumByCode(Integer code){
            for (ReturnType returnType : ReturnType.values()) {
                if(returnType.value.equals(code)){
                    return returnType;
                }
            }
            return null;
        }

    }
}
