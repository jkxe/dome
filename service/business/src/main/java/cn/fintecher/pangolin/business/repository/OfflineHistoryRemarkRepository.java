package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.OfflineHistoryRemark;
import cn.fintecher.pangolin.entity.QOfflineHistoryRemark;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.business.repository
 * @ClassName: cn.fintecher.pangolin.business.repository.OfflineHistoryRemarkRepository
 * @date 2018年06月28日 16:23
 */
public interface OfflineHistoryRemarkRepository extends QueryDslPredicateExecutor<OfflineHistoryRemark>, JpaRepository<OfflineHistoryRemark, String>,QuerydslBinderCustomizer<QOfflineHistoryRemark> {

    @Override
    default void customize(final QuerydslBindings bindings, final QOfflineHistoryRemark root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.like("%".concat(value).concat("%")));

    }
}
