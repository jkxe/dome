package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.CaseInfoDivisionException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface CaseInfoDivisionExceptionRepository extends QueryDslPredicateExecutor<CaseInfoDivisionException> ,JpaRepository<CaseInfoDivisionException,String> {

}