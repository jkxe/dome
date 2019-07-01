package cn.fintecher.pangolin.dataimp.model;

import lombok.Data;

import java.util.Date;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName: cn.fintecher.pangolin.dataimp.model.OutsourcePoolImpModel
 * @date 2018年07月05日 09:25
 */
@Data
public class OutsourcePoolImpModel {

    private Date closeDateTemp;
    private String outBatch;
    private Date outTime;
    private String outId;


}
