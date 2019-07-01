package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "history_outsource_distribution")
@Data
public class HistoryOutSourceDistribution extends BaseEntity {

    @ApiModelProperty(notes = "客户id")
    private String personalId;

    @ApiModelProperty(notes = "委外方id")
    private String outId;

    @ApiModelProperty(notes = "分配时间")
    private Date opertorTime;
}
