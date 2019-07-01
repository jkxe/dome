package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.QResource;
import cn.fintecher.pangolin.entity.Resource;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * @Author: LvGuoRong
 * @Description:资源信息
 * @Date:2017/7/18
 */
public interface ResourceRepository extends QueryDslPredicateExecutor<Resource>, JpaRepository<Resource, Long>, QuerydslBinderCustomizer<QResource> {
    @Override
    default void customize(final QuerydslBindings bindings, final QResource root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(StringUtils.trim(value)).concat("%")));
    }
    /**
     * @Description 查询指定用户的资源
     */
    @Query(value = "select * from resource " +
            "where id in " +
            "("+
             "select reso_id from role_resource " +
            "where role_id = :roleId"
             +")",nativeQuery = true)
    Set<Resource> queryResourceByRoleId(@Param("roleId") String roleId);
}
