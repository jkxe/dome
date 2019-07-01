package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.report.entity.BackMoneyReport;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : huyanmin
 * @Description : 委外催收
 * @Date : 2017/9/25
 */

public interface QueryOutsourcePoolMapper extends MyMapper<BackMoneyReport> {
    /**
     * @Description 委外催收中按批次号查询
     */
    List<QueryOutsourcePool> getAllOutSourcePoolByBatchNumber(QueryOutsourcePoolParams queryOutsourcePoolParams);

    /**
     * @Description 委外催收中按委外方查询
     */
    List<QueryOutsourcePool> getAllOutSourceByOutsName(QueryOutsourcePoolParams queryOutsourcePoolParams);

    /**
     * 委外的待委外（待分配），委外的已结案
     *
     * @param queryOutsourcePoolParams
     * @return
     */
    List<OutSourceDistModel> queryDistribute(@Param("queryOutsourcePoolParams") QueryOutsourcePoolParams queryOutsourcePoolParams);

    /**
     * 委外的回收
     *
     * @param queryOutsourcePoolParams
     * @return
     */
    List<OutSourceDistModel> queryReturn(QueryOutsourcePoolParams queryOutsourcePoolParams);

    /**
     * 委外归C案件的查询
     * @param queryOutsourcePoolParams
     * @return
     */
    List<OutSourceDistModel> queryCleanUpcaseInfo(QueryOutsourcePoolParams queryOutsourcePoolParams);

    /**
     * 查询案件结案数和金额
     * @param batchNumber
     * @return
     */
    BatchAndNameModel findCountOfEnd(BatchAndNameParams batchNumber);
}
