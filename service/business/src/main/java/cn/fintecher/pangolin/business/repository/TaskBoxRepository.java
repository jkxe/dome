package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.QTaskBox;
import cn.fintecher.pangolin.entity.TaskBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * 任务盒子
 */
public interface TaskBoxRepository extends QueryDslPredicateExecutor<TaskBox>, JpaRepository<TaskBox, String>, QuerydslBinderCustomizer<QTaskBox> {

    @Override
    default void customize(final QuerydslBindings bindings, final QTaskBox root) {
    }

}
