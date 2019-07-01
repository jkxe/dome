package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.PayPlan;
import cn.fintecher.pangolin.entity.QPayPlan;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PayPlanRepository extends QueryDslPredicateExecutor<PayPlan>,JpaRepository<PayPlan,String>,QuerydslBinderCustomizer<QPayPlan> {

    @Override
    default void customize(final QuerydslBindings bindings, final QPayPlan root){
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM pay_plan where case_number in (:caseNumberList)",nativeQuery =true)
    int deleteByCaseNumber(@Param("caseNumberList") List<String> caseNumberList);

}