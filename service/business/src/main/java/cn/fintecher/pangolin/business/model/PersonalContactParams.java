package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gonghebin
 * @date 2019/2/21 0021上午 9:59
 */
@Data
public class PersonalContactParams {

    private String id;

    @ApiModelProperty(notes = "关系：145-本人，146-配偶，147-父母，148-子女，149-亲属，150-同事，151-朋友，152-其他，153-亲兄弟姐妹，154-单位")
    private Integer relation;

    @ApiModelProperty(notes = "联系电话状态")
    private Integer phoneStatus;

    @ApiModelProperty(notes = "地址状态")
    private Integer addressStatus;

}
