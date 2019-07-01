package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.entity.HistoryOutSourceDistribution;
import cn.fintecher.pangolin.entity.OutSourceWhip;
import cn.fintecher.pangolin.entity.Outsource;
import cn.fintecher.pangolin.report.entity.OutSourcePoolReport;
import cn.fintecher.pangolin.report.model.AreaCaseNumberModel;
import cn.fintecher.pangolin.report.model.OutSourceNumberModel;
import cn.fintecher.pangolin.report.model.PredistributionOutModel;
import cn.fintecher.pangolin.report.model.distribution.DistributionCaseParam;
import cn.fintecher.pangolin.report.model.distribution.OutSourceDistributionModel;
import cn.fintecher.pangolin.report.model.distribution.QueryDistributionCaseInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueryDistributionMapper {

    /**
     * 查询委外所有的待分配的案件
     * @return
     */
    List<OutSourceDistributionModel> findOutSourceDistritbution(@Param("caseIdlist") List<String> list);

    /**
     * 根据客户id查询对应的正在委外方
     * @param idCard
     * @return
     */
    List<OutSourceDistributionModel> findOutSourceDisByPersonalId(@Param("idCard") String idCard);

    /**
     * 将分配的结果插入临时表
     * @param outSourceWhip
     * @return
     */
    int insertIntoOutSourceWhip(OutSourceWhip outSourceWhip);

    /**
     * 删除临时表数据
     * @return
     */
    int deleteOutSourceWhip();

    /**
     * 根据案件id删除临时表
     * @param caseId
     * @return
     */
    int deleteOutSourceWhipByCaseId(@Param("caseId") String caseId);

    /**
     * 根据案件id查询对应的临时表信息
     * @param caseId
     * @return
     */
    List<OutSourceWhip> selectOutSourceWhipByCaseId(@Param("caseId") String caseId);

    /**
     * 根据用户查询分配历史表按时间倒叙排列
     * @param idCard
     * @return
     */
    List<HistoryOutSourceDistribution> findHistoryOutSourceByidCard(@Param("idCard") String idCard);

    /**
     * 插入历史表
     * @param historyOutSourceDistribution
     * @return
     */
    int insertIntoHistoryOutSourceDistribution(HistoryOutSourceDistribution historyOutSourceDistribution);

    /**
     * 根据城市id查询符合条件的委外方按级别升序排列
     * @param areaId
     * @return
     */
    List<Outsource> findOutSourceByAreaId(@Param("areaId") Integer areaId);

    /**
     * 更新委外池内容
     * @param outSourcePoolReport
     * @return
     */
    int updateOutSourcePool(OutSourcePoolReport outSourcePoolReport);

    /**
     * 根据案件id查询出对应委外案件池中待分配信息
     * @param caseId
     * @return
     */
    List<OutSourcePoolReport> selectOutsourcePoolByCaseId(@Param("caseId") String caseId);

    /**
     * 展示委外待分配池的预分配结果
     * @return
     */
    List<OutSourceNumberModel> selectOutSourceWhipRate(@Param("startTime") String startTime,
                                                       @Param("endTime") String endTime);


    /**
     * 展示委外待分配池的预分配结果详细信息
     * @return
     */
    List<AreaCaseNumberModel> getOutSourceCaseInfoDetail(@Param("outId") String outId);

    /**
     * 展示分配好的案件信息
     * @param distributionCaseParam
     * @return
     */
    List<QueryDistributionCaseInfoModel> queryDistributionCaseInfo(DistributionCaseParam distributionCaseParam);
}
