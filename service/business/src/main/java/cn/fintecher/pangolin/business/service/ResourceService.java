package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.repository.ResourceRepository;
import cn.fintecher.pangolin.business.repository.RoleRepository;
import cn.fintecher.pangolin.entity.QResource;
import cn.fintecher.pangolin.entity.Resource;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-08-10-13:34
 */
@Service("resourceService")
public class ResourceService {
    final Logger log = LoggerFactory.getLogger(ResourceService.class);
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Cacheable(value = "resourceCache", key = "'petstore:resource:all'")
    public List<Resource> findAll() {
        Sort sort = new Sort("sort");
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QResource.resource.show.eq(0));
        Iterable<Resource> all = resourceRepository.findAll(booleanBuilder, sort);
        return IterableUtils.toList(all);
    }

    @CacheEvict(value = "resourceCache", key = "'petstore:resource:all'")
    public void save(List<Resource> object) {
        resourceRepository.save(object);
    }

    @CacheEvict(value = "resourceCache", key = "'petstore:resource:all'")
    public void save(Resource object) {
        resourceRepository.save(object);
    }

    @CacheEvict(value = "resourceCache", key = "'petstore:resource:all'")
    public int deleteResoByRoleId(String id) {
        return roleRepository.deleteResoByRoleId(id);
    }
}
