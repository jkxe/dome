package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseRepairApply;
import cn.fintecher.pangolin.entity.CaseRepairApprovalRecord;
import cn.fintecher.pangolin.entity.QCaseRepairApply;
import cn.fintecher.pangolin.entity.QCaseRepairApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface CaseRepairApprovalRecordRepository extends QueryDslPredicateExecutor<CaseRepairApprovalRecord>, JpaRepository<CaseRepairApprovalRecord, String>, QuerydslBinderCustomizer<QCaseRepairApprovalRecord> {

        default void customize(final QuerydslBindings bindings, final QCaseRepairApprovalRecord root) {

    }




}
