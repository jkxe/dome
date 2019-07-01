package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.model.OutDistributeInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by baizhangyu on 2017/7/26
 */
@Data
public class OutsourceInfo {
    private List<String> outId; //委外方的Id
    private List<Integer> distributionCount;//分配数量
    private List<String> outCaseIds;//委外案件id集合
    private Integer isDebt; //("是否共债优先 0 停用 1 启用")
    private Integer isNumAvg;//("是否数量平均 0 停用 1 按数量 2 按金额")
    private List<OutDistributeInfo> OutDistributeInfos;//委外案件按数量平均分配结果集
}
