package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.OutsourcePool;
import cn.fintecher.pangolin.entity.QOutsourcePool;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.querydsl.core.types.dsl.DateTimePath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by  baizhangyu.
 * Description:
 * Date: 2017-07-26-11:18
 */
public interface OutsourcePoolRepository extends QueryDslPredicateExecutor<OutsourcePool>, JpaRepository<OutsourcePool, String>, QuerydslBinderCustomizer<QOutsourcePool> {
    @Override
    default void customize(final QuerydslBindings bindings, final QOutsourcePool root) {
        /*
        * Added by huyanmin at 2017/09/20
        * */
        //客户姓名模糊查询
        bindings.bind(root.caseInfo.personalInfo.name).first((path, value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        //委外方模糊查询
        bindings.bind(root.outsource.outsName).first((path, value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        //客户手机号
        bindings.bind(root.caseInfo.personalInfo.mobileNo).first((path, value) -> path.eq(StringUtils.trim(value)).or(root.caseInfo.personalInfo.personalContacts.any().phone.eq(StringUtils.trim(value))));
        //客户身份证号
        bindings.bind(root.caseInfo.personalInfo.idCard).first((path, value) -> path.contains(StringUtils.trim(value)));
        //批次号
        bindings.bind(root.caseInfo.batchNumber).first((path, value) -> path.eq(StringUtils.trim(value)));
        //案件金额
        bindings.bind(root.contractAmt).all((path, value) -> {
            Iterator<? extends BigDecimal> it = value.iterator();
            BigDecimal firstOverdueAmount = it.next();
            if (it.hasNext()) {
                BigDecimal secondOverDueAmont = it.next();
                return path.between(firstOverdueAmount, secondOverDueAmont);
            } else {
                //大于等于
                return path.goe(firstOverdueAmount);
            }
        });
        //还款状态
        List<String> list = new ArrayList<>();
        list.add("M0");
        list.add("M1");
        list.add("M2");
        list.add("M3");
        list.add("M4");
        list.add("M5");
        bindings.bind(root.overduePeriods).first((path, value) -> {
            if (Objects.equals(StringUtils.trim(value), CaseInfo.PayStatus.M6_PLUS.getRemark())) {
                return path.notIn(list);
            } else {
                return path.eq(value);
            }
        });
        //委案日期
        bindings.bind(root.outTime).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
            Iterator<? extends Date> it = value.iterator();
            Date operatorMinTime = it.next();
            if (it.hasNext()) {
                String date = ZWDateUtil.fomratterDate(it.next(), "yyyy-MM-dd");
                date = date + " 23:59:59";
                Date operatorMaxTime = null;
                try {
                    operatorMaxTime = ZWDateUtil.getUtilDate(date, "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return path.between(operatorMinTime, operatorMaxTime);
            } else {
                return path.goe(operatorMinTime);
            }
        });
        //到期日期
        bindings.bind(root.overOutsourceTime).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
            Iterator<? extends Date> it = value.iterator();
            Date operatorMinTime = it.next();
            if (it.hasNext()) {
                String date = ZWDateUtil.fomratterDate(it.next(), "yyyy-MM-dd");
                date = date + " 23:59:59";
                Date operatorMaxTime = null;
                try {
                    operatorMaxTime = ZWDateUtil.getUtilDate(date, "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return path.between(operatorMinTime, operatorMaxTime);
            } else {
                return path.goe(operatorMinTime);
            }
        });

        //结案日期
        bindings.bind(root.endOutsourceTime).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
            Iterator<? extends Date> it = value.iterator();
            Date operatorMinTime = it.next();
            if (it.hasNext()) {
                String date = ZWDateUtil.fomratterDate(it.next(), "yyyy-MM-dd");
                date = date + " 23:59:59";
                Date operatorMaxTime = null;
                try {
                    operatorMaxTime = ZWDateUtil.getUtilDate(date, "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return path.between(operatorMinTime, operatorMaxTime);
            } else {
                return path.goe(operatorMinTime);
            }
        });
        //案件金额
        bindings.bind(root.caseInfo.overdueAmount).all((path, value) -> {
            Iterator<? extends BigDecimal> it = value.iterator();
            BigDecimal firstOverdueAmount = it.next();
            if (it.hasNext()) {
                BigDecimal secondOverDueAmont = it.next();
                return path.between(firstOverdueAmount, secondOverDueAmont);
            } else {
                //大于等于
                return path.goe(firstOverdueAmount);
            }
        });
        //逾期天数
        bindings.bind(root.caseInfo.overdueDays).all((path, value) -> {
            Iterator<? extends Integer> it = value.iterator();
            Integer firstOverdueDays = it.next();
            if (it.hasNext()) {
                Integer secondOverdueDays = it.next();
                return path.between(firstOverdueDays, secondOverdueDays);
            } else {
                return path.goe(firstOverdueDays);
            }
        });
        //案件类型
        bindings.bind(root.caseInfo.caseType).first((path, value) -> path.eq(value));
        //委外方
        bindings.bind(root.outsource.outsName).first((path, value) -> path.eq(StringUtils.trim(value)));

        //佣金比例%
        bindings.bind(root.caseInfo.commissionRate).all((path, value) -> {
            Iterator<? extends BigDecimal> it = value.iterator();
            BigDecimal firstCommissionRate = it.next();
            if (it.hasNext()) {
                BigDecimal secondCommissionRate = it.next();
                return path.between(firstCommissionRate, secondCommissionRate);
            } else {
                //大于等于
                return path.goe(firstCommissionRate);
            }
        });
    }

    /**
     * 获取特定委外方下的特定案件的个数（共债案件）
     *
     * @param personalName,idCard,outIds
     * @return
     */
    @Query(value = "select a.out_id,COUNT(*) from outsource_pool a " +
            "LEFT JOIN case_info b on a.case_id=b.id " +
            "LEFT JOIN personal c on b.personal_id=c.id " +
            "where c.`name`=:personalName " +
            "and c.id_card=:idCard " +
            "and a.company_code=:companyCode " +
            "and a.out_status=168 " +
            "GROUP BY a.out_id", nativeQuery = true)
    Object getGzNum(@Param("personalName") String personalName, @Param("idCard") String idCard, @Param("companyCode") String companyCode);


    /**
     * 获取分配时的委外信息
     *
     * @param
     * @return
     */
    @Query(value = "select b.outs_code,b.outs_name,b.id,IFNULL(c.cc,0),IFNULL(c.end_amt,0.00),IFNULL(c.end_count,0),IFNULL(c.success_rate,0.00) from outsource b left JOIN" +
            "(select out_id, case_number, count(case_number) AS cc,end_amt,end_count,success_rate from (SELECT op.out_id,a.case_number,count( CASE WHEN a.collection_status <> 24 THEN a.case_number END ) AS case_count,count( CASE WHEN a.collection_status = 24 THEN a.case_number END) AS end_count,( count( CASE WHEN a.collection_status = 24 THEN a.case_number END ) / (count( CASE WHEN a.collection_status <> 24 THEN a.case_number END )+ count( CASE WHEN a.collection_status = 24 THEN a.case_number END))) AS success_rate,sum( CASE WHEN a.collection_status = 24 THEN op.contract_amt END ) AS end_amt FROM case_info a LEFT JOIN outsource_pool op on op.case_id = a.id WHERE op.out_id is not NULL GROUP BY op.out_id ) tempA GROUP BY tempA.out_id HAVING  !ISNULL(tempA.out_id) ) c on b.id =c.out_id where b.flag=0 and DATE_FORMAT(now(),'%Y-%m-%d') >= DATE_FORMAT(b.contract_start_time,'%Y-%m-%d') and DATE_FORMAT(now(),'%Y-%m-%d') <= DATE_FORMAT(b.contract_end_time,'%Y-%m-%d') and b.company_code=:companyCode", nativeQuery = true)
    Object[] getAllOutSourceByCase(@Param("companyCode") String companyCode);

    @Query(value = "SELECT count(*) FROM `outsource_pool` where out_status=168 and out_id= :outsourceId and company_code= :companyCode", nativeQuery = true)
    Integer getOutsourceCaseCount(@Param("outsourceId") String outsouceId, @Param("companyCode") String companyCode);

    @Query(value = "SELECT ifnull(sum(contract_amt),0) FROM `outsource_pool` where out_status=168 and out_id= :outsourceId and company_code= :companyCode", nativeQuery = true)
    BigDecimal getOutsourceAmtCount(@Param("outsourceId") String outsouceId, @Param("companyCode") String companyCode);

    @Query(value = "SELECT d.id as caseId,d.area_id as areaId,overdue_amount as overdueAmount,overdue_days as overdueDays,p.`name` as principalName,batch_number as batchNumber, product.series_name as seriesName, d.overdue_periods FROM `case_info` d \n" +
            "LEFT JOIN principal p ON d.principal_id = p.id\n" +
            "LEFT JOIN (SELECT pr.id, series.series_name FROM product pr LEFT JOIN product_series series on pr.series_id = series.id ) as product ON product.id = d.product_id\n" +
            "LEFT JOIN outsource_pool c on d.id=c.case_id\n" +
            "where recover_remark = 0 and case_pool_type = 226 and out_status = 167 and d.company_code = :companyCode", nativeQuery = true)
    List<Object[]> findAllOutsourcePoolCase(@Param("companyCode") String companyCode);

    @Query(value = "SELECT d.id as caseId,d.area_id as areaId,overdue_amount as overdueAmount,overdue_days as overdueDays,p.`name` as principalName,batch_number as batchNumber, product.series_name as seriesName, d.overdue_periods, c.id FROM `case_info` d \n" +
            "LEFT JOIN principal p ON d.principal_id = p.id\n" +
            "LEFT JOIN (SELECT pr.id, series.series_name FROM product pr LEFT JOIN product_series series on pr.series_id = series.id ) as product ON product.id = d.product_id\n" +
            "LEFT JOIN outsource_pool c on d.id=c.case_id\n" +
            "where recover_remark = 0 and case_pool_type = 226 and out_status = 167 and c.id in (:caseIdList)", nativeQuery = true)
    List<Object[]> findAllChoosedOutsourcePoolCase(@Param("caseIdList") List<String> caseIdList);

    @Query(value = "SELECT * FROM outsource_pool where case_id = :outCaseId", nativeQuery = true)
    OutsourcePool findOneOutsourcePoolCase(@Param("outCaseId") String outCaseId);


    @Query(value = "select sum(contract_amt) as contractAmt,sum(out_back_amt) as outBackAmt,out_id as outIds from outsource_pool " +
            "where out_id=:outId  AND DATE_FORMAT(over_outsource_time,'%Y-%m-%d') >= DATE_FORMAT(:startTime,'%Y-%m-%d')\n" +
            "  AND DATE_FORMAT(over_outsource_time,'%Y-%m-%d') <= DATE_FORMAT(:endTime,'%Y-%m-%d') GROUP BY out_id",nativeQuery = true)
    Object[] findOutSourceRate(@Param("outId") String outId,
                               @Param("startTime") String startTime,
                               @Param("endTime")String endTime);

}