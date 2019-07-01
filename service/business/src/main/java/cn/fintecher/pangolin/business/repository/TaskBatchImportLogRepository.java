package cn.fintecher.pangolin.business.repository;

import cn.fintecher.pangolin.entity.QTaskBatchImportLog;
import cn.fintecher.pangolin.entity.TaskBatchImportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface TaskBatchImportLogRepository extends QueryDslPredicateExecutor<TaskBatchImportLog>,JpaRepository<TaskBatchImportLog,String>,QuerydslBinderCustomizer<QTaskBatchImportLog>{
    @Override
    default void customize(QuerydslBindings bindings, QTaskBatchImportLog root){

    }
    public TaskBatchImportLog findFirstByTaskStateOrderByStartTimeDesc(Integer taskState);

}