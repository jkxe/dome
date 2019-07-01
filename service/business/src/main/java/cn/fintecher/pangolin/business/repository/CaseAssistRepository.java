package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseAssist;
import cn.fintecher.pangolin.entity.QCaseAssist;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;


/**
 * @author : xiaqun
 * @Description :
 * @Date : 10:11 2017/7/18
 */

public interface CaseAssistRepository extends QueryDslPredicateExecutor<CaseAssist>, JpaRepository<CaseAssist, String>, QuerydslBinderCustomizer<QCaseAssist> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCaseAssist root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(value).concat("%")));
        bindings.bind(root.caseId.collectionType).first((path, value) -> path.eq(value));
        //客户手机号
        bindings.bind(root.caseId.personalInfo.mobileNo).first((path, value) -> path.eq(StringUtils.trim(value)).or(root.caseId.personalInfo.personalContacts.any().phone.eq(StringUtils.trim(value))));
        //案件金额
        bindings.bind(root.caseId.overdueAmount).all((path, value) -> {
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
    }

    @Query(value = "SELECT a.numa+b.numb FROM ( " +
            "SELECT COUNT(*) AS numa FROM case_info " +
            "WHERE current_collector = :userId " +
            "AND leave_case_flag = 1 " +
            "AND collection_status IN (20, 21, 22, 23, 25,171, 172) " +
            ") AS a, " +
            "( " +
            "SELECT COUNT(*) AS numb FROM case_assist " +
            "WHERE assist_collector = :userId " +
            "AND leave_case_flag = 1 " +
            "AND assist_status IN (28,118) " +
            ") AS b ", nativeQuery = true)
    long leaveCaseAssistCount(@Param("userId") String userId);

    /**
     * 查询协催案件流转
     * @param holdDays
     * @param companyCode
     * @return
     */
    @Query(value = "SELECT a.* FROM case_assist a LEFT JOIN case_followup_record b ON a.case_id = b.case_id AND (b.type is null or b.type = 186)  " +
            "AND (b.operator_time is null or b.operator_time >= a.case_flowin_time) AND b.id IS NULL WHERE " +
            " a.assist_way = ?1 AND a.hold_days >= ?2 " +
            " AND a.company_code=?3 AND a.assist_collector is NOT NULL AND a.leave_case_flag=?4  AND a.assist_status in (28,117,118)", nativeQuery = true)
    List<CaseAssist> queryAssitForce(@Param("assistWay") Integer assistWay, @Param("holdDays")Integer holdDays, @Param("companyCode") String companyCode,
                                     @Param("leaveCaseFlag") Integer leaveCaseFlag);
}
