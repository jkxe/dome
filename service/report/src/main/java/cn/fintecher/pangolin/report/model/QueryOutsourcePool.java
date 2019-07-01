package cn.fintecher.pangolin.report.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author : huyanmin
 * @Description : 委外催收
 * @Date : 2017/9/25
 */

//@Entity
@Data
public class QueryOutsourcePool {

    /*受托方ID*/
    private String outsId;
    /*受托方名称*/
    private String outsName;
    /*案件流入时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date comeOutsourceTime;
    /*案件批次号*/
    private String batchNumber;
    /*委案日期*/
    private Date outTime;
    /*委外结案日期*/
    private Date overOutsourceTime;
    /*剩余委托时间(天)*/
    private BigInteger leftDay;
    /*案件总金额*/
    private BigDecimal outcaseTotalAmt;
    /*案件数量*/
    private BigInteger outcaseTotalCount;
    /*已催回案件数*/
    private String outcaseClosedCount;
    /*已催回案件数比例*/
    private String outcaseCountRate;
    /*已催回案件金额*/
    private BigDecimal outcaseClosedAmt;
    /*已催回案件金额比例*/
    private String outcaseAmtRate;
    /*公司标识码*/
    private String companyCode;
    /*公司标识码*/
    private String outsCode;
    /*催收中案件数*/
    private BigInteger currentOutsourceCount;
    /*催收中案件金额*/
    private BigDecimal currentOutsourceAmt;
    //产品类型
    private String seriesName;
    //省份
    private String areaName;
    //城市
    private String cityName;
    //结案日期
    private Date closeDate;
    //账户余额
    private BigDecimal accountBalance;
    private BigDecimal overdueAmount; // 逾期金额
}
