package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by qijigui on 2018/01/05
 * <p>
 * 用户策略分配的时候传参数
 */
@Data
@ApiModel("待分配案件多条件查询参数")
public class CaseInfoDistributeParams {
    @ApiModelProperty("客户姓名")
    private String personalName;
    @ApiModelProperty("手机号码")
    private String mobileNo;
    @ApiModelProperty("申请省份ID")
    private String provinceId;
    @ApiModelProperty("申请城市ID")
    private String cityId;
    @ApiModelProperty("批次号")
    private String batchNumber;
    @ApiModelProperty("逾期天数(最小)")
    private Integer overdueDaysStart;
    @ApiModelProperty("逾期天数(最大)")
    private Integer overdueDaysEnd;
    @ApiModelProperty(notes = "案件金额(最小)")
    private BigDecimal overDueAmountStart;
    @ApiModelProperty(notes = "案件金额(最大)")
    private BigDecimal overDueAmountEnd;
    @ApiModelProperty("案件手数(最小)")
    private Integer handNumberStart;
    @ApiModelProperty("案件手数(最大)")
    private Integer handNumberEnd;
    @ApiModelProperty("委托方ID")
    private String principalId;
    @ApiModelProperty("佣金比例(最小)")
    private BigDecimal commissionRateStart;
    @ApiModelProperty("佣金比例(最大)")
    private BigDecimal commissionRateEnd;
    @ApiModelProperty(value = "公司Code", required = true)
    private String companyCode;
    @ApiModelProperty("案件ID集合")
    private List<String> caseIds;
    @ApiModelProperty("身份证号码")
    private String idCard;
    @ApiModelProperty("案件Id集合")
    private String caseId;

    public String setCaseId(List<String> caseIds) {
        String result = "('";
        if (Objects.isNull(caseIds) || caseIds.isEmpty()) {
            return "";
        } else {

            for (String temp : caseIds) {
                result += temp + "','";
            }
        }
        return result.substring(0, result.length() - 2) + ")";
    }

}
