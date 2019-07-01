package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "outsource_whip")
@Data
public class OutSourceWhip extends BaseEntity{

    @ApiModelProperty(name = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "委外方id")
    private String outId;
}
