package cn.fintecher.pangolin.dataimp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description  案件获取获取策略json字符串对应的解析对象。
 * 将json转换后，最后提取里面的有用条件作为查询条件进行拼接。
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName cn.fintecher.pangolin.dataimp.model.ObtainStrategyJsonModel
 * @date 2018年06月20日 1702
 */
@Data
public class ObtainStrategyJsonModel {

    @ApiModelProperty(notes = "逻辑关系(符号表示[&&/||])")
    private String relation;

    @ApiModelProperty(notes = "是否是叶子条件(true:是,false:否)")
    private Boolean leaf;

    @ApiModelProperty(notes = "变量字段属性名")
    private String variable;

    @ApiModelProperty(notes = "变量字段属性值")
    private String value;

    @ApiModelProperty(notes = "值符号(大于，小于，大于等于等)")
    private String symbol;

    @ApiModelProperty(notes = "区域数组")
    private List<String> cityArr;

    @ApiModelProperty(notes = "逻辑关系(文字描述[并且/或])")
    private String bracket;


    @ApiModelProperty(notes = "子条件")
    private List<ObtainStrategyJsonModel> children;

    @Override
    public String toString() {
        return "ObtainStrategyJsonModel{" +
                "relation='" + relation + '\'' +
                ", leaf=" + leaf +
                ", variable='" + variable + '\'' +
                ", value='" + value + '\'' +
                ", symbol='" + symbol + '\'' +
                ", cityArr=" + cityArr +
                ", bracket='" + bracket + '\'' +
                ", children=" + children +
                '}';
    }
}
