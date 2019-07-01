package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.ExceptionData;
import cn.fintecher.pangolin.entity.QExceptionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ExceptionDataRepository extends QueryDslPredicateExecutor<ExceptionData>,JpaRepository<ExceptionData,String>,QuerydslBinderCustomizer<QExceptionData>{
    @Override
    default void customize(QuerydslBindings bindings, QExceptionData root){

    }

}
