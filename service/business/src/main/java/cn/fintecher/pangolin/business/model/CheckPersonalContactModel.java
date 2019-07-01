package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ZhangYaJun
 * @Title: CheckPersonalContactModel
 * @ProjectName pangolin-server-hzyh
 * @Description:
 * @date 2019/2/19 0019下午 15:47
 */
@Data
public class CheckPersonalContactModel {

   @ApiModelProperty("客户id")
   private  String personalId; //客户id
   @ApiModelProperty("联系人姓名")
   private  String personalName; //姓名
   @ApiModelProperty("联系人手机号")
   private  String personalPhone; //手机号
   @ApiModelProperty("关系")
   private  Integer relation; //关系
   @ApiModelProperty("地址")
   private  String address; //地址
}
