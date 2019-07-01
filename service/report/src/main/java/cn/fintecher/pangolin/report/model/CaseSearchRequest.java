package cn.fintecher.pangolin.report.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by sunyanping on 2019/5/15
 */
@Data
public class CaseSearchRequest {

    private String caseNumber;
    private String personalName;
    private String mobileNo;
    private String merchantName;
    private Date overdueDateMin;
    private Date overdueDateMax;
    private String currentCollectorName;
    private BigDecimal overdueAmountMin;
    private BigDecimal overdueAmoutnMax;
    private Integer overduePeriods;
    private Integer overdueDaysMin;
    private Integer overdueDaysMax;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settleDateMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settleDateMax;
    private String sourceChannel;
    private String collectionMethod;
    private String payStatus;


    public void setOverdueDateMin(String overdueDateMin) {
        this.overdueDateMin = plus1(overdueDateMin);
    }

    public void setOverdueDateMax(String overdueDateMax) {
        this.overdueDateMax = plus1(overdueDateMax);
    }

    /**
     * 由于页面展示的时候时间加了一天，所以在做查询时，查询的时间减去一天，才能查出展示正确的数据
     * 我也不知道为什么要这么做原始数据，这个就是按照业务正确性作了个处理
     * @param oldDate
     * @return
     */
    private Date plus1(String oldDate) {
        if (StringUtils.isNotBlank(oldDate)) {
            LocalDate localDate = LocalDate.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minus(1, ChronoUnit.DAYS);
            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }
        return null;
    }
}
