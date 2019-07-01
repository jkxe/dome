package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : hanwannan
 * @Description : 客户影像文件
 * @Date : 2017/8/15
 */
@Entity
@Table(name = "hy_personal_img_files")
@Data
public class PersonalImgFile implements Serializable {
    @Id
    private String id;

    @ApiModelProperty(notes = "客户id")
    private String customerId;

    @ApiModelProperty(notes = "资源项id")
    private String resourceId;

    @ApiModelProperty(notes = "影像类型")
    private String imageKind;

    @ApiModelProperty(notes = "影像类别   1-文档，2-图片，3-录音，4-录像，5-授信合同，6-借款合同，X-未知")
    private String imageType;

    @ApiModelProperty(notes = "影像名称")
    private String imageName;

    @ApiModelProperty(notes = "影像地址")
    private String imageUrl;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
