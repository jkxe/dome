package cn.fintecher.pangolin.report.model;

import lombok.Data;

/**
 * @author : xiaqun
 * @Description : 通用报表参数
 * @Date : 9:00 2017/8/2
 */

@Data
public class GeneralParams {
    private String startDate; //起始时间
    private String endDate; //终止时间
    private String userName; //用户名
    private String id; //部门id
    private Integer type; //报表类型 0-实时 1历史
    private String companyCode; //公司code码
}