package cn.fintecher.pangolin.dataimp.mapper;

import cn.fintecher.pangolin.dataimp.entity.CaseDistributedResult;
import cn.fintecher.pangolin.dataimp.entity.CaseInfoDistributedImp;
import cn.fintecher.pangolin.dataimp.model.CaseInfo;
import cn.fintecher.pangolin.dataimp.model.CaseInfoDistributedModelImp;
import cn.fintecher.pangolin.dataimp.model.OutSourcePoolModel;
import cn.fintecher.pangolin.dataimp.model.ProductSeriesImpModel;
import cn.fintecher.pangolin.dataimp.util.MyMapper;
import cn.fintecher.pangolin.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.mapper
 * @ClassName: cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedMapper
 * @date 2018年06月27日 14:01
 */
public interface CaseInfoDistributedMapper extends MyMapper<CaseInfoDistributedImp> {


    /**
     * 查询CaseDistributedResult中的所有属性，将查询出的属性设置到case_info对应的属性上。
     * @param queryStr
     * @return
     */
    List<CaseInfoDistributedModelImp> getCaseDistributedResultList(@Param("queryStr") String queryStr);


    List<CaseInfoDistributedModelImp> getCaseInfoResultList(@Param("queryStr") String queryStr);


    /**
     * 根据案件编号，查询案件类型与案件名称，然后将这3个值返回，执行后面的修改语句。
     * @return
     */
    List<CaseDistributedResult> getCaseProperties(@Param("companyCode") String companyCode);


    /**
     * 查询系列的id与名称，返回给前台作为下拉列表。
     * @return
     */
    List<ProductSeriesImpModel> selectProductSeries();


    /**
     * 根据案件类型删除数据。
     * @param caseNumberList
     * @return
     */
    int deleteByCaseNumbers(@Param("caseNumberList") List<String> caseNumberList);


    /**
     * 根据案件id删除 outsource_pool 表中的数据。
     * @param caseIdList
     * @return
     */
    int deleteOutsourcePoolByCaseIds(@Param("caseIdList") List<String> caseIdList);


    /**
     * 查询委外案件
     * @param casePoolType
     * @return
     */
    List<CaseInfo> getCaseInfoByCasePoolType(@Param("casePoolType") Integer casePoolType);

    //根据案件id查询委外案件池
    List<OutSourcePoolModel> getOutSourcePoolByCaseId(@Param("caseId") String caseId);

    /**
     * 委外案件池的插入
     * @param outsourcePool
     * @return
     */
    int insertOutSourcePool(OutSourcePoolModel outsourcePool);

    List<Company> selectCompanyByName(@Param("companyName") String companyName);


    /**
     * 根据案件编号查询案件id。
     * @param caseNumber
     * @return
     */
    public String getByCaseNumber(@Param("caseNumber") String caseNumber);


    /**
     * 更新案件信息。
     */
    public void updateCaseInfo(@Param("caseNumber") String caseNumber,
                               @Param("collectionType") Integer collectionType,
                               @Param("overduePeriods") Integer overduePeriods,
                               @Param("overdueDays") Integer overdueDays,
                               @Param("caseType") Integer caseType,
                               @Param("collectionStatus") Integer collectionStatus,
                               @Param("departId") String departId,
                               @Param("currentCollector") String currentCollector,
                               @Param("casePoolType") Integer casePoolType,
                               @Param("recoverRemark") Integer recoverRemark);


    /**
     * 根据案件id查询
     * @param caseId
     * @return
     */
    public Integer getOutStatusByCaseId(@Param("caseId") String caseId);

}
