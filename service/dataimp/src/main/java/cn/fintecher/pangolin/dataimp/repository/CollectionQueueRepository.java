package cn.fintecher.pangolin.dataimp.repository;

import cn.fintecher.pangolin.dataimp.entity.CollectionQueue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  催收队列 接口
 * @Package cn.fintecher.pangolin.dataimp.repository
 * @ClassName: cn.fintecher.pangolin.dataimp.repository.CollectionQueueRepository
 */
public interface CollectionQueueRepository extends MongoRepository<CollectionQueue, String>,
        QueryDslPredicateExecutor<CollectionQueue> {

}
