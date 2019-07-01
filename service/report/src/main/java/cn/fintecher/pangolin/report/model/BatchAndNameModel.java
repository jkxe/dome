package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author gonghebin
 * @date 2019/3/3 0003下午 9:30
 */
@Data
public class BatchAndNameModel {

    /*已催回案件数*/
    private BigInteger outcaseClosedCount;

    /*已催回案件金额*/
    private BigDecimal outcaseClosedAmt;

}
