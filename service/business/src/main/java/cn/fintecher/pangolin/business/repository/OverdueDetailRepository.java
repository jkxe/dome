package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.OverdueDetail;
import cn.fintecher.pangolin.entity.QOverdueDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface OverdueDetailRepository extends QueryDslPredicateExecutor<OverdueDetail>,JpaRepository<OverdueDetail,String>,QuerydslBinderCustomizer<QOverdueDetail>{
    @Override
    default void customize(QuerydslBindings bindings, QOverdueDetail root){

    }

}
