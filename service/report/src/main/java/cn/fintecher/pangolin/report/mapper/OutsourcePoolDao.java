package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.model.OutsourcePoolBatchModel;
import cn.fintecher.pangolin.report.entity.OutsourcePoolEntity;
import cn.fintecher.pangolin.report.model.QueryCaseInfoParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 委外案件池
 * 
 * @author suyuchao
 * @email null
 * @date 2019-03-02 18:56:40
 */
@Mapper
public interface OutsourcePoolDao extends BaseDao<OutsourcePoolEntity> {
	//按委托方或批次号查看委外案件
    List<OutsourcePoolBatchModel> getOutSourceCaseByBatchnum(QueryCaseInfoParams queryCaseInfoParams);
}
