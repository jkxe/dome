package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.ResourceModel;
import cn.fintecher.pangolin.business.model.RoleModel;
import cn.fintecher.pangolin.business.repository.ResourceRepository;
import cn.fintecher.pangolin.business.repository.RoleRepository;
import cn.fintecher.pangolin.business.service.DataDictService;
import cn.fintecher.pangolin.business.service.ResourceService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-07-05-19:35
 */
@RestController
@RequestMapping("/api/resourceController")
@Api(value = "资源管理", description = "资源管理")
public class ResourceController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private static final String ENTITY_NAME = "Resource";
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    DataDictService dataDictService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * @Description : 资源的level属性
     */
    @GetMapping("/getResourceLevel")
    @ApiOperation(value = "资源的level属性", notes = "资源的level属性")
    public ResponseEntity<List<DataDict>> getResourceLevel() {
        List<DataDict> dataDictList = dataDictService.getDataDictByTypeCode("0029");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取成功", "")).body(dataDictList);

    }

    /**
     * @Description : 资源的type属性
     */
    @GetMapping("/getResourceType")
    @ApiOperation(value = "资源的type属性", notes = "资源的type属性")
    public ResponseEntity<List<DataDict>> getResourceType() {
        List<DataDict> dataDictList = dataDictService.getDataDictByTypeCode("0030");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取成功", "")).body(dataDictList);
    }

    /**
     * @Description : 资源的filetype属性
     */
    @GetMapping("/getResourceFileType")
    @ApiOperation(value = "资源的filetype属性", notes = "资源的filetype属性")
    public ResponseEntity<List<DataDict>> getResourceFileType() {
        List<DataDict> dataDictList = dataDictService.getDataDictByTypeCode("0031");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取成功", "")).body(dataDictList);
    }

    /**
     * @Description : 资源的flag属性
     */
    @GetMapping("/getResourceStatus")
    @ApiOperation(value = "资源的flag属性", notes = "资源的flag属性")
    public ResponseEntity<List<DataDict>> getResourceStatus() {
        List<DataDict> dataDictList = dataDictService.getDataDictByTypeCode("0032");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取成功", "")).body(dataDictList);
    }

    /**
     * @Description : 新增资源方法
     */
    @PostMapping("/createResource")
    @ApiOperation(value = "增加资源", notes = "增加资源")
    public ResponseEntity<Resource> createResource(@Validated @ApiParam("资源") @RequestBody Resource resource,
                                                   @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to save resource : {}", resource);
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        if (resource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "idexists", "新增不应该含有ID")).body(null);
        }
        if (!(Objects.equals(user.getId(), Constants.ADMINISTRATOR_ID))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "Can't add without permission", "没有权限不能添加")).body(null);
        }
        resourceService.save(resource);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(null);
    }

    /**
     * @Description : 修改资源
     */
    @PostMapping("/updateResource")
    @ApiOperation(value = "修改资源", notes = "修改资源")
    public ResponseEntity<Resource> updateCompany(@Validated @ApiParam("资源对象") @RequestBody Resource resource,
                                                  @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to update resource : {}", resource);
        if (resource.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "idexists", "修改应该含有ID")).body(null);
        }
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        if (!(Objects.equals(user.getId(), Constants.ADMINISTRATOR_ID))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "Can't add without permission", "没有权限不能添加")).body(null);
        }
        resourceService.save(resource);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(null);
    }

    /**
     * @Description : 资源唯一值
     */
    @GetMapping(value = "/resourceHashCode")
    @ApiOperation(value = "资源唯一值", notes = "资源唯一值")
    public ResponseEntity<Map<String, String>> getAllResourceHashCode() {
        List<Resource> list = resourceRepository.findAll();
        String code = String.valueOf(list.hashCode());
        Map<String, String> map = new HashMap<String, String>();
        map.put("resourceHashCode", code);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("登录成功", ENTITY_NAME)).body(map);
    }

    /**
     * @Description : 获取所有资源
     */
    @GetMapping("/getAllResource")
    @ApiOperation(value = "查询所有", notes = "查询所有")
    public ResponseEntity<List<ResourceModel>> getAllResources() {
        logger.debug("REST request to get all of Resource");
        List<Resource> resources =resourceService.findAll();
        List<ResourceModel> list = new ArrayList<>();
        resources.forEach(e ->{
            ResourceModel resourceModel=modelMapper.map(e,ResourceModel.class);
            resourceModel.setParentId(Objects.nonNull(e.getParent()) ? e.getParent().getId() : null);
            if (resourceModel.getType() == 18){
                if(resourceModel.getId() != 1112){
                    list.add(resourceModel);
                }
            }else if(resourceModel.getType() == 19){
                if(resourceModel.getParentId() != 1112){
                    list.add(resourceModel);
                }
            }else if(resourceModel.getType() == 17){
                list.add(resourceModel);
            }
        });
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功","")).body(list);
    }


    @ApiOperation(value = "按ID查询", notes = "按ID查询")
    @GetMapping("/getRole/{id}")
    public ResponseEntity<RoleModel> getRole(@PathVariable String id) {
        logger.debug("REST request to get Role : {}", id);
        Role role = roleRepository.findOne(id);
        RoleModel operatorModel = modelMapper.map(role, RoleModel.class);
        return Optional.ofNullable(operatorModel)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * @Description : 查询资源列表
     */
    @PostMapping("/queryResource")
    @ApiOperation(value = "查询资源列表", notes = "查询资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<Resource>> queryResource(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Integer type,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to query Resource : {}");
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QResource qResource = QResource.resource;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(name)) {
            builder.and(qResource.name.like(name.concat("%")));
        }
        if (Objects.nonNull(type)) {
            builder.and(qResource.type.eq(type));
        }
        Page<Resource> page = resourceRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "操作成功")).body(page);
    }
}
