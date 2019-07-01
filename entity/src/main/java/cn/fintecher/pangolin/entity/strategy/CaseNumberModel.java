package cn.fintecher.pangolin.entity.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  存放正在审批案件的案件编号,这些案件是不走策略的。
 * @Package cn.fintecher.pangolin.entity.strategy
 * @ClassName: cn.fintecher.pangolin.entity.strategy.CaseNumberModel
 * @date 2018年07月20日 10:56
 */
@Data
public class CaseNumberModel {

    @ApiModelProperty(notes = "存放正在审批案件的案件编号")
    private List<String> caseNumberList;

}
