package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.Principal;
import cn.fintecher.pangolin.entity.QPrincipal;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 10:21 2017/7/14
 */
public interface PrincipalRepository extends QueryDslPredicateExecutor<Principal>, JpaRepository<Principal, String>, QuerydslBinderCustomizer<QPrincipal> {
    @Override
    default void customize(final QuerydslBindings bindings, final QPrincipal root) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(StringUtils.trim(value)));
//        bindings.bind(root.domain.id).first((path, value) -> path.like(value));
    }

    /**
     * 根据委托方编码获取委托方
     * @param code
     * @return
     */
    Principal findByCode(String code);
}
