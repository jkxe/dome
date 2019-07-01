package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.business.model.ProcessCaseRepairModel;
import cn.fintecher.pangolin.entity.*;

import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface CaseRepairApplyRepository extends QueryDslPredicateExecutor<CaseRepairApply>, JpaRepository<CaseRepairApply, String>, QuerydslBinderCustomizer<QCaseRepairApply> {

        default void customize(final QuerydslBindings bindings, final QCaseRepairApply root) {

    }


    List<CaseRepairApply> findAllByCaseId(String caseId);


}
