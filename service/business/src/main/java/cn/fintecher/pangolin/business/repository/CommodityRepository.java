package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.Commodity;
import cn.fintecher.pangolin.entity.QCommodity;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CommodityRepository  extends QueryDslPredicateExecutor<Commodity>,JpaRepository<Commodity,String>,QuerydslBinderCustomizer<QCommodity> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCommodity root){
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }
}
