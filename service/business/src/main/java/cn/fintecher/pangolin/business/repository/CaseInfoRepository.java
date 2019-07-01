package cn.fintecher.pangolin.business.repository;


import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.QCaseInfo;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by ChenChang on 2017/7/11.
 */
public interface CaseInfoRepository extends QueryDslPredicateExecutor<CaseInfo>, JpaRepository<CaseInfo, String>, QuerydslBinderCustomizer<QCaseInfo> {
    @Override
    default void customize(final QuerydslBindings bindings, final QCaseInfo root) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.id).first((path, value) -> path.eq(StringUtils.trim(value)));
        bindings.bind(root.product.productName).first((path, value) -> path.eq(StringUtils.trim(value)));
        //机构码搜索
//        bindings.bind(root.department.code).first((path, value) -> path.startsWith(StringUtils.trim(value)));
        //案件金额
        bindings.bind(root.department.code).all((path, value) -> {
            Iterator<? extends String> it = value.iterator();
            String firstCode = it.next();
            if (it.hasNext()) {
                String secondCode = it.next();
                return path.eq(secondCode);
            } else {
                //大于等于
                return path.eq(firstCode);
            }
        });
        //公司码
        bindings.bind(root.companyCode).first((path, value) -> path.eq(StringUtils.trim(value)));
        //案件金额
        bindings.bind(root.overdueAmount).all((path, value) -> {
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
        //逾期开始时间
        bindings.bind(root.overDueDate).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
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
        //出催时间
        bindings.bind(root.settleDate).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
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
        //逾期天数
        bindings.bind(root.overdueDays).all((path, value) -> {
            Iterator<? extends Integer> it = value.iterator();
            Integer firstOverdueDays = it.next();
            if (it.hasNext()) {
                Integer secondOverdueDays = it.next();
                return path.between(firstOverdueDays, secondOverdueDays);
            } else {
                return path.goe(firstOverdueDays);
            }
        });
        //案件手数
        bindings.bind(root.handNumber).all((path, value) -> {
            Iterator<? extends Integer> it = value.iterator();
            Integer firstHandNumber = it.next();
            if (it.hasNext()) {
                Integer secondHandNumber = it.next();
                return path.between(firstHandNumber, secondHandNumber);
            } else {
                return path.goe(firstHandNumber);
            }
        });
        //佣金比例%
        bindings.bind(root.commissionRate).all((path, value) -> {
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
        //委案日期
        bindings.bind(root.delegationDate).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
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
        bindings.bind(root.closeDate).all((DateTimePath<Date> path, Collection<? extends Date> value) -> {
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
        //委托方
        bindings.bind(root.principalId.id).first((path, value) -> path.eq(StringUtils.trim(value)));
        //案件状态
        bindings.bind(root.collectionStatus).first((path, value) -> path.eq(value));
        //案件类型
        bindings.bind(root.caseType).first((path, value) -> path.eq(value));
        //催收类型
        bindings.bind(root.collectionType).first((path, value) -> path.eq(value));
        //产品系列
        bindings.bind(root.product.productSeries.id).first((path, value) -> path.eq(StringUtils.trim(value)));
        //客户姓名
        bindings.bind(root.personalInfo.name).first((path, value) -> path.contains(StringUtils.trim(value)));
        //客户手机号
        bindings.bind(root.personalInfo.mobileNo).first((path, value) -> path.eq(StringUtils.trim(value)).or(root.personalInfo.personalContacts.any().phone.eq(StringUtils.trim(value))));
        //批次号
        bindings.bind(root.batchNumber).first((path, value) -> path.eq(StringUtils.trim(value)));
        //申请省份
        bindings.bind(root.area.parent.id).first((path, value) -> path.eq(value));
        //申请城市
        bindings.bind(root.area.id).first((path, value) -> path.eq(value));
        //标记颜色
        bindings.bind(root.caseMark).first((path, value) -> path.eq(value));
        //还款状态
        List<String> list = new ArrayList<>();
        list.add("M0");
        list.add("M1");
        list.add("M2");
        list.add("M3");
        list.add("M4");
        list.add("M5");
        bindings.bind(root.payStatus).first((path, value) -> {
            if (Objects.equals(StringUtils.trim(value), CaseInfo.PayStatus.M6_PLUS.getRemark())) {
                return path.notIn(list);
            } else {
                return path.eq(value);
            }
        });
        //催收反馈
        bindings.bind(root.followupBack).first((path, value) -> path.eq(value));
        //协催
        bindings.bind(root.assistFlag).first((path, value) -> path.eq(value));
        //协催方式
        bindings.bind(root.assistWay).first((path, value) -> path.eq(value));
        //根据id数组获取查询结果list
        bindings.bind(root.id).all((path, value) -> {
            Set<String> idSets = new HashSet<>();
            if (value.iterator().hasNext()) {
                StringBuilder sb = new StringBuilder(value.iterator().next());
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.length() - 1);
                List<String> idArray = Arrays.asList(sb.toString().split(","));
                Iterator<? extends String> it = idArray.iterator();
                while (it.hasNext()) {
                    idSets.add(it.next().trim());
                }
            }
            return path.in(idSets);
        });
        bindings.bind(root.collectionType).first((path, value) -> path.eq(value));
//        bindings.bind(root.department.code).first((path, value) -> path.startsWith(value));
    }

    /**
     * @Description 获得指定用户所持有的未结案案件总数
     */
    @Query(value = "select count(*) from case_info where current_collector = :userId and collection_status in (20,21,22,23,25,171,172)", nativeQuery = true)
    Integer getCaseCount(@Param("userId") String userId);

    /**
     * @Description 获得指定用户所持有的未结案案件总金额
     * zmm 2018-07-24 将 overdue_amount 逾期总金额 修改为 account_balance 账户余额
     */
    @Query(value = "select ifnull(sum(account_balance),0) from case_info where current_collector = :userId and collection_status in (20,21,22,23,25,171,172)", nativeQuery = true)
    BigDecimal getUserCaseAmt(@Param("userId") String userId);

    /**
     * @Description 获得指定部门所持有的未结案案件总数
     */
    @Query(value = "select count(*) from case_info where depart_id = :deptId and collection_status in (20,21,22,23,25,171,172)", nativeQuery = true)
    Integer getDeptCaseCount(@Param("deptId") String deptId);

    /**
     * @Description 获得指定部门所持有的未结案案件总金额
     * zmm 2018-07-24 将 overdue_amount 逾期总金额 修改为 account_balance 账户余额
     */
    @Query(value = "select ifnull(sum(account_balance),0) from case_info where depart_id = :deptId and collection_status in (20,21,22,23,25,171,172)", nativeQuery = true)
    BigDecimal getDeptCaseAmt(@Param("deptId") String deptId);

    /**
     * @Description 获得指定用户的待催收金额
     */
    @Query(value = "select sum(overdue_amount+early_settle_amt-early_real_settle_amt-real_pay_amount) from case_info where current_collector = :id or assist_collector = :id and collection_status = :collectionStatus", nativeQuery = true)
    BigDecimal getCollectionAmt(@Param("id") String id, @Param("collectionStatus") Integer collectionStatus);

    /**
     * 获取所有批次号
     *
     * @param companyCode
     * @return
     */
    @Query(value = "select distinct(batch_number) from case_info where company_code = ?1 and batch_number is not null", nativeQuery = true)
    List<String> findDistinctByBatchNumber(@Param("companyCode") String companyCode);

    /**
     * 获取所有批次号
     *
     * @return
     */
    @Query(value = "select distinct(batch_number) from case_info where batch_number is not null", nativeQuery = true)
    List<String> findAllDistinctByBatchNumber();


    /**
     * 根据案件编号查询案件
     *
     * @param caseNumber
     * @return
     */
    List<CaseInfo> findByCaseNumber(String caseNumber);

    /**
     * 设置案件协催状态为审批失效
     *
     * @param cupoIds
     */
    @Modifying
    @Query("update CaseInfo acc set acc.assistStatus=212 where acc.collectionStatus not in(24,166) and acc.id in ?1")
    void updateCaseStatusToCollectioning(Set<String> cupoIds);

    /**
     * 获取部门级别的共债案件
     *
     * @return
     */
    @Query(value = "select depart_id,collection_type,count(*) num " +
            "from case_info c " +
            "left join personal p " +
            "on p.id = c.personal_id " +
            "where current_collector is null " +
            "and depart_id is not null " +
            "and collection_status in (20,21,22,23,171,172) " +
            "and c.company_code =:companyCode " +
            "and p.name =:personName " +
            "and p.id_card =:idCard " +
            "GROUP BY depart_id,collection_type " +
            "ORDER BY num desc,collection_type " +
            "LIMIT 1", nativeQuery = true)
    Object findCaseByDept(@Param("personName") String personName, @Param("idCard") String idCard, @Param("companyCode") String companyCode);

    /**
     * 获取催员级别的共债案件
     *
     * @return
     */

    @Query(value = "select current_collector,collection_type,count(*) num " +
            "from case_info c " +
            "left join personal p " +
            "on p.id = c.personal_id " +
            "where current_collector is not null " +
            "and collection_status in (20,21,22,23,171,172) " +
            "and c.company_code =:companyCode " +
            "and p.name =:personName " +
            "and p.id_card =:idCard " +
            "GROUP BY current_collector,collection_type " +
            "ORDER BY num desc,collection_type " +
            "LIMIT 1", nativeQuery = true)
    Object findCaseByCollector(@Param("personName") String personName, @Param("idCard") String idCard, @Param("companyCode") String companyCode);

    @Query(value = "SELECT d.id as caseId,d.area_id as areaId,overdue_amount as overdueAmount,overdue_days as overdueDays,p.`name` as principalName,batch_number as batchNumber, product.series_name as seriesName, overdue_periods FROM `case_info` d \n" +
            "LEFT JOIN principal p ON d.principal_id = p.id\n" +
            "LEFT JOIN (SELECT pr.id, series.series_name FROM product pr LEFT JOIN product_series series on pr.series_id = series.id ) as product ON product.id = d.product_id\n" +
            "where recover_remark = 0 and case_pool_type = 225 and collection_status=25 and d.company_code = :companyCode", nativeQuery = true)
    List<Object[]> findAllCase(@Param("companyCode") String companyCode);

    @Query(value = "SELECT d.id as caseId,d.area_id as areaId,overdue_amount as overdueAmount,overdue_days as overdueDays,p.`name` as principalName,batch_number as batchNumber, product.series_name as seriesName, overdue_periods FROM `case_info` d \n" +
            "LEFT JOIN principal p ON d.principal_id = p.id\n" +
            "LEFT JOIN (SELECT pr.id, series.series_name FROM product pr LEFT JOIN product_series series on pr.series_id = series.id ) as product ON product.id = d.product_id\n" +
            "where d.id in (:caseIdList)", nativeQuery = true)
    List<Object[]> findAllChoosedCase(@Param("caseIdList") List<String> caseIdList);

    /**
     * 获取已结案案件的相关信息
     *
     * @return
     */
    @Query(value = "SELECT a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,b.id,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name,count(j.id)\n" +
            "from case_info a LEFT JOIN user b on a.current_collector = b.id\n" +
            "LEFT JOIN product c on a.product_id = c.id\n" +
            "LEFT JOIN personal d on a.personal_id = d.id\n" +
            "LEFT JOIN personal_bank e on d.id = e.personal_id\n" +
            "LEFT JOIN principal f on a.principal_id = f.id\n" +
            "LEFT JOIN product_series g ON g.id = c.series_id\n" +
            "LEFT JOIN personal_job h on h.personal_id = d.id\n" +
            "LEFT JOIN area_code i on i.id = a.area_id\n" +
            "LEFT JOIN case_followup_record j on a.id = j.case_id\n" +
            "where a.collection_status = 24 and a.operator_time > str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s') and a.company_code =:companyCode \n" +
            "group by a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,b.id,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name", nativeQuery = true)
    Object[] findCaseInfoClosed(@Param("companyCode") String companyCode);

    /**
     * 获取正在处理中案件的相关信息
     *
     * @return
     */
    @Query(value = "SELECT a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,b.id,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name,count(j.id)\n" +
            "from case_info a LEFT JOIN user b on a.current_collector = b.id\n" +
            "LEFT JOIN product c on a.product_id = c.id\n" +
            "LEFT JOIN personal d on a.personal_id = d.id\n" +
            "LEFT JOIN personal_bank e on d.id = e.personal_id\n" +
            "LEFT JOIN principal f on a.principal_id = f.id\n" +
            "LEFT JOIN product_series g ON g.id = c.series_id\n" +
            "LEFT JOIN personal_job h on h.personal_id = d.id\n" +
            "LEFT JOIN area_code i on i.id = a.area_id\n" +
            "LEFT JOIN case_followup_record j on a.id = j.case_id\n" +
            "where a.collection_status in(21,23) and a.operator_time > str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s') and a.company_code =:companyCode\n" +
            "group by a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,b.id,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name", nativeQuery = true)
    Object[] findCaseInfoIn(@Param("companyCode") String companyCode);

    /**
     * 获取待分配案件的相关信息
     *
     * @return
     */
    @Query(value = "SELECT a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name\n" +
            "from case_info_distributed a\n" +
            "LEFT JOIN product c on a.product_id = c.id\n" +
            "LEFT JOIN personal d on a.personal_id = d.id\n" +
            "LEFT JOIN personal_bank e on d.id = e.personal_id\n" +
            "LEFT JOIN principal f on a.principal_id = f.id\n" +
            "LEFT JOIN product_series g ON g.id = c.series_id\n" +
            "LEFT JOIN personal_job h on h.personal_id = d.id\n" +
            "LEFT JOIN area_code i on i.id = a.area_id\n" +
            "where a.company_code =:companyCode and a.case_number in (:caseNumbers)\n" +
            "group by a.case_number,a.company_code,d.id_card,d.mobile_no,h.company_name,\n" +
            "c.product_name,g.series_name,a.loan_date,a.contract_amount,a.left_capital,\n" +
            "a.left_interest,a.overdue_amount,a.overdue_capital,a.overdue_interest,a.overdue_fine,\n" +
            "a.overdue_delay_fine,a.periods,a.per_due_date,a.per_pay_amount,a.other_amt,\n" +
            "a.overdue_periods,a.overdue_days,a.over_due_date,a.has_pay_amount,a.has_pay_periods,\n" +
            "a.lately_pay_date,a.lately_pay_amount,a.commission_rate,e.deposit_bank,a.delegation_date,\n" +
            "f.name,a.collection_type,a.derate_amt,a.real_pay_amount,a.operator_time,i.area_name", nativeQuery = true)
    Object[] caseInfoToDistribute(@Param("companyCode") String companyCode,
                                  @Param("caseNumbers") List<String> caseNumbers);

    /**
     *
     *
     */
    @Query(value = "SELECT a.case_number,a.loan_invoice_number,c.`name` AS personalName,c.id_card,c.mobile_no,a.periods,\n" +
            "a.overdue_periods,a.overdue_days,a.overdue_capital,a.overdue_amount,a.account_balance,a.overdue_count,a.loan_date,\n" +
            "a.pay_status,d.series_name,b.product_name,c.id_card_address,a.collection_status,a.case_pool_type,\n" +
            "e.name,f.real_name,a.clean_date,a.settle_date\n" +
            "from case_info a\n" +
            "LEFT JOIN product b on a.product_id = b.id\n" +
            "LEFT JOIN personal c on a.personal_id = c.id\n" +
            "LEFT JOIN product_series d ON d.id = b.series_id\n" +
            "LEFT JOIN department e ON e.id = a.depart_id\n" +
            "LEFT JOIN `user` f on f.id = a.current_collector\n"+
            "where a.company_code =:companyCode " +
            "AND a.case_pool_type in(:casePoolType)\n", nativeQuery = true)
    Object[] getCaseInfoModelByCasePoolType(@Param("companyCode") String companyCode,
                              @Param("casePoolType") List<Integer> casePoolType);

    @Query(value = "SELECT a.case_number,a.loan_invoice_number,c.`name` AS personalName,c.id_card,c.mobile_no,a.periods,\n" +
            "a.overdue_periods,a.overdue_days,a.overdue_capital,a.overdue_amount,a.account_balance,a.overdue_count,a.loan_date,\n" +
            "a.pay_status,d.series_name,b.product_name,c.id_card_address,a.collection_status,a.case_pool_type,\n" +
            "e.`name` AS depatName,f.real_name,a.clean_date,a.settle_date\n" +
            "from case_info a\n" +
            "LEFT JOIN product b on a.product_id = b.id\n" +
            "LEFT JOIN personal c on a.personal_id = c.id\n" +
            "LEFT JOIN product_series d ON d.id = b.series_id\n" +
            "LEFT JOIN department e ON e.id = a.depart_id\n" +
            "LEFT JOIN `user` f on f.id = a.current_collector\n"+
            "where a.company_code =:companyCode " +
            "AND b.series_id in(:seriesId) \n", nativeQuery = true)
    Object[] getCaseInfoModelBySeriesId(@Param("companyCode") String companyCode,
                              @Param("seriesId") List<String> seriesId);

    @Query(value = "SELECT a.case_number,a.loan_invoice_number,c.`name` AS personalName,c.id_card,c.mobile_no,a.periods,\n" +
            "a.overdue_periods,a.overdue_days,a.overdue_capital,a.overdue_amount,a.account_balance,a.overdue_count,a.loan_date,\n" +
            "a.pay_status,d.series_name,b.product_name,c.id_card_address,a.collection_status,a.case_pool_type,\n" +
            "e.`name` AS depatName,f.real_name,a.clean_date,a.settle_date\n" +
            "from case_info a\n" +
            "LEFT JOIN product b on a.product_id = b.id\n" +
            "LEFT JOIN personal c on a.personal_id = c.id\n" +
            "LEFT JOIN product_series d ON d.id = b.series_id\n" +
            "LEFT JOIN department e ON e.id = a.depart_id\n" +
            "LEFT JOIN `user` f on f.id = a.current_collector\n"+
            "where a.company_code =:companyCode " +
            "AND a.collection_status in(:collectionStatus)\n", nativeQuery = true)
    Object[] getCaseInfoModelByCollectionStatus(@Param("companyCode") String companyCode,
                              @Param("collectionStatus") List<Integer> collectionStatus);

    /**
     * @Description 获得指定用户所持有的未结案案件账户余额
     */
    @Query(value = "select ifnull(sum(account_balance),0) from case_info where current_collector = :userId and collection_status in (20,21,22,23,25,171,172)", nativeQuery = true)
    BigDecimal getUserAccountBalance(@Param("userId") String userId);

    /**
     * @Description 查询案件逾期信息
     */
    @Query(value = "select a.loan_period AS payPeriod, a.left_periods AS hasPayPeriods, a.overdue_periods AS overduePeriods, " +
            "a.overdue_days AS OverdueDays, a.account_balance AS ApprovedLoanAmt, a.overdue_amount AS overdueAmount,a.overdue_capital AS overdueCapital, " +
            "a.overdue_interest AS overdueInterest,a.unpaid_other_fee AS unpaidOtherFee,d.id_card_address AS cardAddress,a.unpaid_fine AS unpaidFine, " +
            "a.unpaid_interest AS unpaidInterest,d.operator_time AS operatorTime,a.verfication_status AS verficationStatus,a.overdue_capital_interest AS overdueCapitalInterest," +
            "a.overdue_delay_fine AS overdueDelayFine,a.overdue_fine AS overdueFine,a.loan_period AS loanPeriod,a.account_balance AS accountBalance,a.overdue_manage_fee AS overdueManageFee from case_info a " +
            "left join order_info c on c.case_number = a.case_number " +
            "left join personal d on d.id = a.personal_id " +
            "where a.id =:id",nativeQuery = true)
    Object getOverdueInfo(@Param("id") String id);

    @Query(value = "select id,loan_invoice_number,customer_id,case_number, update_time,collection_status,case_pool_type,collection_type,depart_id,lately_collector,current_collector from case_info where collection_status!=24",nativeQuery = true)
    List<Object[]> findAllKey();

    @Query(value = "select id,loan_invoice_number,customer_id,case_number, update_time,collection_status,case_pool_type,collection_type,depart_id,lately_collector,current_collector from case_info where collection_status=24",nativeQuery = true)
    List<Object[]> findAllKeyOver();

    @Query(value = "select a.loan_invoice_number from case_info a where a.collection_status != 24",
            nativeQuery = true)
    List<String> findAllLoanInvoiceNumber();

}


