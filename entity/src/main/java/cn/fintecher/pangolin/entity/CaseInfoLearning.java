package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-09-26-11:44
 */
@Entity
@Table(name = "case_info_learning")
@Data
public class CaseInfoLearning extends BaseEntity {
    private String caseNumber;
    private String machineLearningResult;
    private String modelType;
    private String operator;
    @ApiModelProperty("分析日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;
    private String companyCode;
}
