package cn.fintecher.pangolin.business.model;

import lombok.Data;

/**
 * @author : xiaqun
 * @Description : 共债案件数量模型
 * @Date : 19:09 2017/10/11
 */

@Data
public class CommonCaseCountModel {
    private Integer count; //共债案件个数
    private Boolean flag;  //案件到期是否提醒
}