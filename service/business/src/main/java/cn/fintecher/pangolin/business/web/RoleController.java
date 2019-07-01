package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.Messages;
import cn.fintecher.pangolin.business.service.ResourceService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.EntityUtil;
import cn.fintecher.pangolin.util.BeanUtils;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description:角色信息管理
 * @Date 14:51 2017/7/14
 */
@RestController
@RequestMapping("/api/roleController")
@Api(value = "角色资源管理", description = "角色资源管理")
public class RoleController extends BaseController {
    private static final String ENTITY_NAME = "Role";
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    ResourceService resourceService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private Messages messages;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * @Description : 带条件的分页查询
     */
    @GetMapping(value = "/getAllRolePage")
    @ResponseBody
    @ApiOperation(value = "带条件的分页查询", notes = "带条件的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<RoleModelAll>> getAllRolePage(@RequestParam(required = false) String companyCode,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String departmentCode,
                                                             @RequestParam(required = false) Integer status,
                                                             @RequestParam(required = false) String operator,
                                                             @ApiIgnore Pageable pageable) {
        logger.debug("REST request to get all Role");
        QRole qRole = QRole.role;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(companyCode)) {
            builder.and(qRole.companyCode.eq(companyCode));
        } else {
            if (Objects.nonNull(departmentCode)) {
                Department department = departmentRepository.findOne(QDepartment.department.code.eq(departmentCode));
                if (Objects.nonNull(department.getCompanyCode())) {
                    builder.and(qRole.companyCode.eq(department.getCompanyCode()));
                }
            }
        }
        if (Objects.nonNull(name)) {
            builder.and(qRole.name.like("%".concat(name).concat("%")));
        }
        if (Objects.nonNull(status)) {
            builder.and(qRole.status.eq(status));
        }
        if (Objects.nonNull(operator)) {
            builder.and(qRole.operator.like(operator.concat("%")));
        }
        Type listType = new TypeToken<List<RoleModelAll>>() {
        }.getType();

        Page<Role> page= roleRepository.findAll(builder,pageable);

        List<RoleModelAll> list =modelMapper.map(page.getContent(),listType);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert("", "")).body(new PageImpl<>(list, pageable, page.getTotalElements()));
    }

    /**
     * @Description : 增加角色
     */
    @PostMapping("/createRole")
    @ApiOperation(value = "增加角色", notes = "增加角色")
    public ResponseEntity<Role> createRole(@Validated @ApiParam("角色对象") @RequestBody Role role,
                                           @RequestHeader(value = "X-UserToken") String token) {
        role = (Role) EntityUtil.emptyValueToNull(role);
        logger.debug("REST request to save caseInfo : {}", role);
        if (role.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "idexists", "新增不应该含有ID")).body(null);
        }
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        //增加角色的code需要传入
        QRole qRole = QRole.role;
        boolean exist = false;
        if (Objects.isNull(role.getCompanyCode())) {
            exist = roleRepository.exists(qRole.name.eq(role.getName()));
        } else {
            exist = roleRepository.exists(qRole.name.eq(role.getName()).and(qRole.companyCode.eq(role.getCompanyCode())));
        }
        if (exist) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The role name has been occupied", "该角色名已被占用")).body(null);
        } else {
            role.setOperator(user.getRealName());
            role.setOperateTime(ZWDateUtil.getNowDateTime());
            Role role1 = roleRepository.save(role);
            return ResponseEntity.ok().body(role1);
        }
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/getAllRole")
    public ResponseEntity<List<RoleModel>> getAllRoles() {
        logger.debug("REST request to get all of Role");
        Type listType = new TypeToken<List<RoleModel>>() {
        }.getType();
        List<RoleModel> list = modelMapper.map(roleRepository.findAll(), listType);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @Description : 更新角色
     */
    @PostMapping("/updateRole")
    @ApiOperation(value = "更新角色", notes = "更新角色")
    public ResponseEntity<RoleModel> updateRole(@Validated @ApiParam("需更新的角色对象") @RequestBody RoleRequest request,
                                                @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to save Role : {}", request);
        if (Objects.isNull(request.getId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("编辑ID不能为空", "A new Role cannot already have an ID")).body(null);
        }
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }

        Role role = roleRepository.findOne(request.getId());
        BeanUtils.copyPropertiesIgnoreNull(request, role);
        if (Objects.nonNull(request.getResources())) {
            List<Resource> resources = resourceRepository.findAll(request.getResources());
            Set<Resource> allRes = new HashSet<>();
            for (Resource resource : resources) {
                addParentResource(allRes, resource);
            }
            role.setResources(allRes);
        }
        role.setOperator(user.getRealName());
        role.setOperateTime(ZWDateUtil.getNowDateTime());
        Role result = roleRepository.save(role);
        RoleModel operatorModel = modelMapper.map(result, RoleModel.class);
        return ResponseEntity.ok().body(operatorModel);
    }

    private void addParentResource(Set<Resource> allRes, Resource resource) {
        if (Objects.isNull(resource.getParent())) {
            allRes.add(resource);
            return;
        }
        allRes.add(resource);
        addParentResource(allRes, resource.getParent());
    }

    /**
     * @Description : 查找角色通过id
     */
    @GetMapping("/getRole")
    @ApiOperation(value = "查找角色通过id", notes = "查找角色通过id")
    public ResponseEntity<RoleModel> getRole(@ApiParam(value = "角色id", required = true) @RequestParam(value = "id") String id) {
        logger.debug("REST request to get Role : {}", id);
        Role role = roleRepository.findOne(id);
        Set<Resource> resources = role.getResources();
        RoleModel operatorModel = modelMapper.map(role, RoleModel.class);
        Set<ResourceModel> resourceModels = operatorModel.getResources();
        for (Resource resource : resources) {
            for (ResourceModel resourceModel : resourceModels) {
                if (Objects.nonNull(resource.getParent()) && Objects.equals(resource.getId(), resourceModel.getId())) {
                    Long pId = resource.getParent().getId();
                    resourceModel.setParentId(pId);
                    continue;
                }
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(operatorModel);
    }

    /**
     * @Description : 查找角色通过id
     */
    @GetMapping("/getRoleById")
    @ApiOperation(value = "查找角色通过id", notes = "查找角色通过id")
    public ResponseEntity<RoleModelID> getRoleById(@ApiParam(value = "角色id", required = true) @RequestParam(value = "id") String id) {
        logger.debug("REST request to get Role : {}", id);
        Role role = roleRepository.findOne(id);
        Set<Resource> resources = role.getResources();
        //RoleModel operatorModel = modelMapper.map(role, RoleModel.class);
        // Set<ResourceModel> resourceModels = operatorModel.getResources();
        RoleModelID roleModelID = new RoleModelID();
        roleModelID.setId(role.getId());
        roleModelID.setName(role.getName());
        roleModelID.setStatus(role.getStatus());
        Set<Long> resourcesSet = new HashSet<>();
        Set<Long> resourceModelButton = new HashSet<>();
        roleModelID.setResourceModelButton(resourceModelButton);
        roleModelID.setResourcesSet(resourcesSet);
        for (Resource resource : resources) {
            if (resource.getType() == Resource.Type.TWO_MENU.getValue()) {
                resourcesSet.add(resource.getId());
            } else {
                resourceModelButton.add(resource.getId());
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(roleModelID);
    }

    /*    *//**
     * @Description : 角色查找资源
     *//*
    @GetMapping("/getRoleRes")
    @ApiOperation(value = "角色查找资源", notes = "角色查找资源")
    public ResponseEntity<List<Resource>> getRoleRes(@RequestParam(required = false) String id) {
        try {
            QResource qResource = QResource.resource;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.nonNull(id)) {
                builder.and(qResource.roles.any().id.eq(id));
            }
            List<Resource> resourceList = IterableUtils.toList(resourceRepository.findAll(builder));
            return ResponseEntity.ok().body(resourceList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取失败", ENTITY_NAME, e.getMessage())).body(null);
        }
    }*/

    /**
     * @Description : 角色查找用户分页
     */
    @GetMapping("/roleFindUsers")
    @ApiOperation(value = "角色查找用户分页", notes = "角色查找用户分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> roleFindUsers(@RequestParam String id,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestParam(required = false) String companyCode,
                                                    @ApiIgnore Pageable pageable) {

        Role role = roleRepository.findOne(id);
        if (Objects.nonNull(role)) {
            QUser qUser = QUser.user;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.nonNull(id)) {
                builder.and(qUser.roles.any().id.eq(id));
            }
            if (Objects.nonNull(name)) {
                builder.and(qUser.roles.any().name.like(name.concat("%")));
            }
            if (Objects.nonNull(status)) {
                builder.and(qUser.roles.any().status.eq(status));
            }
            if (Objects.nonNull(companyCode)) {
                builder.and(qUser.roles.any().companyCode.eq(companyCode));
            }
            Page<User> page = userRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                "The role does not exist", "该角色不存在")).body(null);
    }

    /**
     * @Description : 删除角色
     */
    @DeleteMapping("/deleteRole")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public ResponseEntity<Role> deleteRole(@ApiParam(value = "角色id", required = true) @RequestParam String id,
                                           @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to save Role : {}", id);
        User userToken;
        try {
            userToken = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        if (Objects.equals(Constants.ADMINISTRATOR_ID, id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "The superadministrator is not allowed to delete", "超级管理员角色不允许删除")).body(null);
        }
        Role role = roleRepository.findOne(id);
        QUser qUser = QUser.user;
        boolean exist = userRepository.exists(qUser.roles.any().id.eq(id).and(qUser.companyCode.eq(role.getCompanyCode())));
        if (exist) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "The role has an associated user, please delete the relationship with the user first", "该角色下有关联用户，请先删除与用户的关系")).body(null);
        }
        //解除角色与资源的关系
        resourceService.deleteResoByRoleId(role.getId());
        Role roleNew = roleRepository.save(role);
        roleRepository.delete(id);
        return ResponseEntity.ok().body(null);
    }

    /**
     * @Description : 查询所有角色分页
     */
    @GetMapping("/findAllRole")
    @ApiOperation(value = "查询所有角色分页", notes = "查询所有角色分页")
    public ResponseEntity<Page<Role>> getAllRole(@ApiIgnore Pageable pageable) {
        logger.debug("REST request to get all of Role");
        try {
            Page<Role> page = roleRepository.findAll(pageable);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createEntityCreationAlert("查找失败", ENTITY_NAME)).body(null);
        }
    }

    @GetMapping("/queryAllRole")
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    public ResponseEntity<List<RoleSelectModel>> queryAllRole(@RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to get all of Role");
        try {
            User user = getUserByToken(token);
            List<RoleSelectModel> list = new ArrayList<>();
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if(Objects.nonNull(user.getCompanyCode())) {
                booleanBuilder.and(QRole.role.companyCode.eq(user.getCompanyCode()));
            }
            booleanBuilder.and(QRole.role.status.eq(0));
            Iterator<Role> iterator = roleRepository.findAll(booleanBuilder).iterator();
            while (iterator.hasNext()) {
                Role role = iterator.next();
                RoleSelectModel roleSelectModel = new RoleSelectModel();
                roleSelectModel.setId(role.getId());
                roleSelectModel.setName(role.getName());
                roleSelectModel.setDescription(role.getRemark());//角色描述
                list.add(roleSelectModel);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createEntityCreationAlert("查找失败", ENTITY_NAME)).body(null);
        }
    }
}
