package cn.fintecher.pangolin.dataimp.mapper;

import cn.fintecher.pangolin.dataimp.entity.CaseInfoEntity;
import cn.fintecher.pangolin.dataimp.model.CaseInfoDistributedModelImp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 案件池
 * 
 * @author suyuchao
 * @email null
 * @date 2019-01-26 15:34:27
 */
@Mapper
public interface CaseInfoDao extends BaseDao<CaseInfoEntity> {

    int updateCaseInfoByCaseNum(CaseInfoEntity caseInfoEntity);
    //根据case_number 批量更新caseInfo得队列Id,队列名称
    int updateBatchByCaseNum(List<CaseInfoDistributedModelImp> list);

    //自动分案批量标记异常案件
    int signExceptionCase(List<String> list);
}
