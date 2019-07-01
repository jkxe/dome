package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CasePayApply;
import cn.fintecher.pangolin.entity.QCasePayApply;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author : xiaqun
 * @Description :
 * @Date : 9:13 2017/7/19
 */

public interface CasePayApplyRepository extends QueryDslPredicateExecutor<CasePayApply>, JpaRepository<CasePayApply, String>, QuerydslBinderCustomizer<QCasePayApply> {
    @Override
    default void customize(final QuerydslBindings bindings, final QCasePayApply root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
        bindings.bind(root.caseId).first((path, value) -> path.like(StringUtils.trim(value))); //手机号
        bindings.bind(root.personalPhone).first((path, value) -> path.like(StringUtils.trim(value))); //手机号
        bindings.bind(root.principalId).first((path, value) -> path.like(StringUtils.trim(value))); //委托方
        bindings.bind(root.payType).first(SimpleExpression::eq); //还款类型
        bindings.bind(root.payWay).first(SimpleExpression::eq); //还款方式
        bindings.bind(root.approveStatus).first(SimpleExpression::eq); //还款审批状态
        bindings.bind(root.applyDate).all((DateTimePath<Date> path, Collection<? extends Date> value) -> { //申请时间
            Iterator<? extends Date> it = value.iterator();
            Date applyMinDate = it.next();
            if (it.hasNext()) {
                String date = ZWDateUtil.fomratterDate(it.next(), "yyyy-MM-dd");
                date = date + " 23:59:59";
                Date applyMaxDate = null;
                try {
                    applyMaxDate = ZWDateUtil.getUtilDate(date, "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return path.between(applyMinDate, applyMaxDate);
            } else {
                return path.goe(applyMinDate);
            }
        });
        bindings.bind(root.approveResult).first(SimpleExpression::eq); //审核结果
        bindings.bind(root.personalName).first((path, value) -> path.contains(StringUtils.trim(value)));//客户姓名
        bindings.bind(root.batchNumber).first((path, value) -> path.like(StringUtils.trim(value)));//案件批次号
        bindings.bind(root.applyDerateAmt).all((path, value) -> { //减免金额
            Iterator<? extends BigDecimal> it = value.iterator();
            BigDecimal applyDerateMinAmt = it.next();
            if (it.hasNext()) {
                BigDecimal applyDerateMaxAmt = it.next();
                return path.between(applyDerateMinAmt, applyDerateMaxAmt);
            } else {
                return path.goe(applyDerateMinAmt);
            }
        });
        bindings.bind(root.applyUserName).first((path, value) -> path.like(StringUtils.trim(value)));//申请人
        bindings.bind(root.batchNumber).first((path, value) -> path.like(StringUtils.trim(value))); //批次号
    }

    /**
     @Description 获得指定用户的待审核回款金额
     */
    @Query(value = "select sum(applyPayAmt) from CasePayApply where applyUserName = :username and  approveStatus in(:approveStatu1,:approveStatu2)")
    BigDecimal queryApplyAmtByUserName(@Param("username") String username, @Param("approveStatu1") Integer approveStatu1,@Param("approveStatu2") Integer approveStatu2);

    /**
     @Description 获得周回款榜
     */
    @Query(value = "select sum(a.apply_pay_amt) as amt,u.real_name,u.id,u.photo from " +
            "( " +
            "select apply_pay_amt,apply_user_name from case_pay_apply " +
            "where operator_date >=:startDate " +
            "and operator_date <=:endDate " +
            "and approve_status=:approveStatu " +
            ") as a " +
            "inner join " +
            "( " +
            "select u.id,u.real_name,u.photo,u.user_name from user u,department d " +
            "where u.type = :type " +
            "and u.company_code =:companyCode " +
            "and u.dept_id = d.id " +
            "and d.code like concat(:deptCode,'%') " +
            ") as u " +
            "on u.user_name = a.apply_user_name " +
            "GROUP BY " +
            "id " +
            "order by " +
            "amt desc",nativeQuery = true)
    List<Object[]> queryPayList(@Param("approveStatu") Integer approveStatu, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("type") Integer type,@Param("companyCode") String companyCode,@Param("deptCode") String deptCode);

    /**
     * @Description 本月催收佣金
     */
    @Query(value = "select sum(a.apply_pay_amt*c.commission_rate/100) from " +
            "( " +
            "select commission_rate,id from case_info " +
            ") as c " +
            "inner join " +
            "( " +
            "select apply_pay_amt,case_id from case_pay_apply " +
            "where approve_status=:approveStatu " +
            "and apply_user_name =:username " +
            "and operator_date >=:startDate " +
            "and operator_date <=:endDate " +
            ") as a " +
            "on a.case_id = c.id",nativeQuery = true)
    BigDecimal queryCommission(@Param("username") String username,@Param("approveStatu") Integer approveStatu, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
