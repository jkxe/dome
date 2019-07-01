package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.Material;
import cn.fintecher.pangolin.entity.QMaterial;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MaterialRepository extends QueryDslPredicateExecutor<Material>,JpaRepository<Material,String>,QuerydslBinderCustomizer<QMaterial> {

    @Override
    default void customize(final QuerydslBindings bindings, final QMaterial root){
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));

    }
}
