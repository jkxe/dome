package cn.fintecher.pangolin.dataimp.repository;

import cn.fintecher.pangolin.dataimp.model.CaseDistributedModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.repository
 * @ClassName: cn.fintecher.pangolin.dataimp.repository.CaseDistributedRepository
 * @date 2018年06月20日 15:26
 */
public interface CaseDistributedRepository extends MongoRepository<CaseDistributedModel, String> {



}
