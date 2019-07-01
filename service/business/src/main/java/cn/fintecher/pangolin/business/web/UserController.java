package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.CaseAssistService;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-07-03-20:34
 */
@RestController
@RequestMapping("/api/userController")
@Api(value = "用户信息管理", description = "用户信息管理")
public class UserController extends BaseController {

    private static final String ENTITY_NAME = "User";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    CaseAssistService caseAssistService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SysParamRepository sysParamRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    /**
     * @Description : 新增用户
     */
    @PostMapping("/createUser")
    @ApiOperation(value = "增加用户", notes = "增加用户")
    public ResponseEntity<User> createUser(@Validated @ApiParam("用户对象") @RequestBody User user,
                                           @RequestHeader(value = "X-UserToken") String token) {
        user = (User) EntityUtil.emptyValueToNull(user);
        //前端的限制 一级部门普通管理员不能添加用户
        User userToken;
        String companyCode = user.getCompanyCode();
        try {
            userToken = getUserByToken(token);
            if (ZWStringUtils.isEmpty(companyCode)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "该机构不能新增用户", "该机构不能新增用户")).body(null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        if (user.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "idexists", "新增不应该含有ID")).body(null);
        }
        if (Objects.equals(Constants.ADMIN_USER_NAME, user.getUserName())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "The username is not allowed to be used", "该用户名不允许被使用")).body(null);
        }
        if (user.getDepartment().getLevel() <= 9 && !Objects.equals(Constants.ADMIN_USER_NAME, userToken.getUserName())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "New user institutions must be greater than the first class", "新增用户，用户机构等级必须大于一级")).body(null);
        }

        QSysParam qSysParam1 = QSysParam.sysParam;
        SysParam sysParamsNumber = null;
        try {
            sysParamsNumber = sysParamRepository.findOne(qSysParam1.code.eq(Constants.APPLY_USER_NUMBER_CODE).and(qSysParam1.type.eq(Constants.APPLY_USER_NUMBER_TYPE)).and(qSysParam1.companyCode.eq(user.getCompanyCode())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User number parameters", "用户个数参数异常")).body(null);
        }
        if (Objects.nonNull(sysParamsNumber) && Objects.equals(Status.Enable.getValue(), sysParamsNumber.getStatus())) {
            QUser qUser1 = QUser.user;
            int userNum = (int) userRepository.count(qUser1.companyCode.eq(companyCode));
            int size = Integer.parseInt(sysParamsNumber.getValue());
            if (userNum > size) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                        "New user has reached the online", "新增用户已达到上限")).body(null);
            }
        }
        //用户名不能重复
        QUser qUser = QUser.user;
//        boolean exist = userRepository.exists(qUser.userName.eq(user.getUserName()).and(qUser.companyCode.eq(companyCode)));
        //新加用户去掉公司code码判断，以后重新设计
        boolean exist = userRepository.exists(qUser.userName.eq(user.getUserName()));
        if (exist) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "User name cannot be repeated", "用户名不能重复")).body(null);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //新增用户的密码设置
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParamsPassword = null;
        try {
            sysParamsPassword = sysParamRepository.findOne(qSysParam.code.eq(Constants.APPLY_PD_CODE).and(qSysParam.type.eq(Constants.APPLY_PD_TYPE)).and(qSysParam.companyCode.eq(user.getCompanyCode())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The user password parameters abnormality", "用户密码参数异常")).body(null);
        }
        String hashedPassword;
        if (Objects.nonNull(sysParamsPassword) && Objects.equals(Status.Enable.getValue(), sysParamsNumber.getStatus())) {
            hashedPassword = passwordEncoder.encode(MD5.MD5Encode(sysParamsPassword.getValue()));
        } else {
            //默认密码888888
            hashedPassword = passwordEncoder.encode(Constants.RET_PD);
        }
        if (Objects.isNull(user.getSignature())) {
            user.setSignature("Congratulations on becoming a member of the company, I wish you a happy work");
        }
        //密码过期时间
        user.setPasswordInvalidTime(ZWDateUtil.getNowDateTime());
        user.setPassword(hashedPassword);
        user.setOperator(userToken.getUserName());
        user.setOperateTime(ZWDateUtil.getNowDateTime());

        User userReturn = userService.save(user);
        Set<UserDevice> userDevices = new HashSet<>();
        UserDevice userDevicePc = new UserDevice();
        userDevicePc.setUserId(user.getId());
        userDevicePc.setStatus(Status.Enable.getValue());
        userDevicePc.setValidate(Status.Enable.getValue());
        userDevicePc.setType(Status.Enable.getValue());
        userDevicePc.setOperateTime(ZWDateUtil.getNowDateTime());
        userDeviceRepository.saveAndFlush(userDevicePc);
        userDevices.add(userDevicePc);

        UserDevice userDeviceApp = new UserDevice();
        userDeviceApp.setUserId(user.getId());
        userDeviceApp.setStatus(Status.Enable.getValue());
        userDeviceApp.setValidate(Status.Enable.getValue());
        userDeviceApp.setType(Status.Disable.getValue());
        userDeviceApp.setOperateTime(ZWDateUtil.getNowDateTime());
        userDeviceRepository.saveAndFlush(userDeviceApp);
        userDevices.add(userDeviceApp);
        user.setUserDevices(userDevices);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(userReturn);
    }

    /**
     * @Description : 修改用户
     */
    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public ResponseEntity<User> updateUser(@Validated @ApiParam("用户对象") @RequestBody User user,
                                           @RequestHeader(value = "X-UserToken") String token) {
        user = (User) EntityUtil.emptyValueToNull(user);
        User userToken;
        try {
            userToken = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        if (Objects.equals("0o0oo0o0-0o0o-0000-0000-0ooo000o0o0o", user.getId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "This user is not allowed to modify", "该用户不允许修改")).body(null);
        }
        if (user.getDepartment().getLevel() <= 9 && !Objects.equals(Constants.ADMIN_USER_NAME, userToken.getUserName()) && userToken.getDepartment().getLevel() > 9) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                    "New user institutions must be greater than the first class", "修改用户，用户机构等级必须大于一级")).body(null);
        }
        if (User.Status.CLOSED.getValue().equals(user.getDivisionSwitch())){
            Map<String, Object> map = new HashMap<>();
            map.put("userId",user.getId());
            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity("http://dataimp-service/api/obtainTaticsStrategyController/isInStrategy",map, Boolean.class);
            Boolean isInStrategy = responseEntity.getBody();
            if (isInStrategy){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "not.allow.switch.division", "该用户已关联分案策略,不允许关闭分案开关;如需关闭分案开关请先取消该用户的分案策略关联!")).body(null);
            }
        }
        //修改用户状态或者修改部门的类型 首先处理案件
        User userOld = userRepository.findOne(user.getId());
        if (Objects.equals(Status.Disable.getValue(), user.getStatus()) || !(Objects.equals(user.getDepartment().getType(), userOld.getDepartment().getType()))) {
            //用户下的电催和外访的案件
            CollectionCaseModel collectionCaseModel = caseInfoService.haveCollectionCase(userOld);
            if (Objects.nonNull(collectionCaseModel)) {
                int number = collectionCaseModel.getNum();
                if (0 != number) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User associated case, please handle cases", "该用户下关联" + number + "个未处理的案件，不能操作，请先处理完该用户下的案件")).body(null);
                }
            }
            //用户下的协催案件
            AssistingStatisticsModel assistingStatisticsMode = caseAssistService.getCollectorAssist(userOld);
            if (Objects.nonNull(assistingStatisticsMode)) {
                int number1 = assistingStatisticsMode.getNum();
                if (0 != number1) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User associated case, please handle cases", "该用户下关联" + number1 + "个未处理的协催案件，不能操作，请先处理完该用户下的案件")).body(null);
                }
            }
        }
        //修改用户 判断部门
        Department department = departmentRepository.findOne(user.getDepartment().getId());
        if (Objects.isNull(department)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The department has been deleted", "该部门已被删除")).body(null);
        }
        if (Objects.equals(Status.Disable.getValue(), department.getStatus())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The department for the disabled state, can not add user", "该部门为停用状态，不能添加用户")).body(null);
        }
        User updateUser = userService.save(user);
        Set<UserDevice> devices = updateUser.getUserDevices();
        if (devices.size() == 0) {
            Set<UserDevice> userDevices = new HashSet<>();
            UserDevice userDevicePc = new UserDevice();
            userDevicePc.setUserId(user.getId());
            userDevicePc.setStatus(Status.Enable.getValue());
            userDevicePc.setValidate(Status.Enable.getValue());
            userDevicePc.setType(Status.Enable.getValue());
            userDevicePc.setOperateTime(ZWDateUtil.getNowDateTime());
            userDeviceRepository.saveAndFlush(userDevicePc);
            userDevices.add(userDevicePc);

            UserDevice userDeviceApp = new UserDevice();
            userDeviceApp.setUserId(user.getId());
            userDeviceApp.setStatus(Status.Enable.getValue());
            userDeviceApp.setValidate(Status.Enable.getValue());
            userDeviceApp.setType(Status.Disable.getValue());
            userDeviceApp.setOperateTime(ZWDateUtil.getNowDateTime());
            userDeviceRepository.saveAndFlush(userDeviceApp);
            userDevices.add(userDeviceApp);
            user.setUserDevices(userDevices);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(updateUser);
    }

    /**
     * @Description : 用户分配角色
     */
    @PostMapping("/userAddRole")
    @ApiOperation(value = "用户分配角色", notes = "用户分配角色")
    public ResponseEntity<User> userAddRole(@ApiParam("封装用户id和角色id集合") @RequestBody UserAddRoleRequest request) {
        User user = userRepository.findOne(request.getUserId());
        user.setRoles(new HashSet<>());
        List<Role> roles = roleRepository.findAll(request.getRoleIds());
        List<Role> roles1 = new ArrayList<>();
        for(int i=0;i<roles.size();i++) {
            if (roles.get(i).getStatus()==1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("","","角色["+roles.get(i).getName()+"]已停用")).body(null);
             }
            roles1.add(roles.get(i));
        }
        user.getRoles().addAll(roles1);
        User userReturn = userService.save(user);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(userReturn);
    }

    /**
     * @Description : 用户批量分配角色
     */
    @PostMapping("/manyUserAddRole")
    @ApiOperation(value = "用户批量分配角色", notes = "用户批量分配角色")
    public ResponseEntity<List<User>> manyUserAddRole(@ApiParam("封装用户id集合和角色id集合") @RequestBody ManyUserAddRoleRequest request) {
        List<User> users = userRepository.findAll(request.getUserIds());
        List<Role> roles = roleRepository.findAll(request.getRoleIds());
        for (User user : users) {
            user.setRoles(new HashSet<>());
            user.getRoles().addAll(roles);
            userService.save(user);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(null);
    }

    /**
     * @Description : 查询特定部门下的特定角色的用户
     */
    @GetMapping("/queryUserByDeptAndRole")
    @ApiOperation(value = "查询特定部门下特定角色的用户", notes = "查询特定部门下特定角色的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> queryUserByDeptAndRole(@RequestParam String deptCode,
                                                             @RequestParam(required = false) String userName,
                                                             @RequestParam(required = false) String realName,
                                                             @RequestParam(required = false) String roleId,
                                                             @RequestParam(required = false) String roleName,
                                                             @RequestParam(required = false) String isManager,
                                                             @RequestHeader(value = "X-UserToken") String token,
                                                             @ApiIgnore Pageable pageable) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(deptCode)) {
            builder.and(qUser.department.code.like(deptCode.concat("%")));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(userName)) {
            builder.and(qUser.userName.like("%".concat(userName).concat("%")));
        }
        if (Objects.nonNull(realName)) {
            builder.and(qUser.realName.like("%".concat(realName).concat("%")));
        }
        if (Objects.nonNull(roleId)) {
            builder.and(qUser.roles.any().id.eq(roleId));
        }
        if (Objects.nonNull(roleName)) {
            builder.and(qUser.roles.any().name.like("%".concat(roleName).concat("%")));
        }
        if (Objects.nonNull(user.getCompanyCode())) {
            builder.and(qUser.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        Page<User> page = userRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(page);

    }

    /**
     * @Description : 查询特定部门下的特定类型的用户
     */
    @GetMapping("/queryUserByDeptAndType")
    @ApiOperation(value = "查询特定部门下的特定类型的用户", notes = "查询特定部门下的特定类型的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> queryUserByDeptAndType(@RequestParam String deptCode,
                                                             @RequestParam(required = false) String userName,
                                                             @RequestParam(required = false) String realName,
                                                             @RequestParam(required = false) String roleId,
                                                             @RequestParam(required = false) String roleName,
                                                             @RequestParam(required = false) String isManager,
                                                             @RequestParam(required = false) List<Integer> modelType,
                                                             @RequestHeader(value = "X-UserToken") String token,
                                                             @ApiIgnore Pageable pageable) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        if (Objects.nonNull(deptCode)) {
            builder.and(qUser.department.code.like(deptCode.concat("%")));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(userName)) {
            builder.and(qUser.userName.like("%".concat(userName).concat("%")));
        }
        if (Objects.nonNull(realName)) {
            builder.and(qUser.realName.like("%".concat(realName).concat("%")));
        }
        if (Objects.nonNull(roleId)) {
            builder.and(qUser.roles.any().id.eq(roleId));
        }
        if (Objects.nonNull(roleName)) {
            builder.and(qUser.roles.any().name.like("%".concat(roleName).concat("%")));
        }
        if (Objects.nonNull(modelType)) {
            builder.and(qUser.type.in(modelType));
        }
        if (Objects.nonNull(user.getCompanyCode())) {
            builder.and(qUser.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        Page<User> page = userRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(page);

    }

    /**
     * @Description : 查询部门下的特定用户分页
     * zmm  2018-06-19 添加查询条件，催收员分案开关(开启:504,关闭:505)。
     * 针对催员请假或其他事情，将状态设置为[关闭]，因此在查询的时候，只查询状态为[开启]的催员。
     */
    @GetMapping("/query")
    @ApiOperation(value = "查询部门下的特定用户", notes = "查询部门下的特定用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> query(@RequestParam(required = false)String deptCode,
                                            @RequestParam(required = false) String deptId,
                                            @RequestParam(required = false) String userName,
                                            @RequestParam(required = false) String realName,
                                            @RequestParam(required = false) Integer state,
                                            @RequestParam(required = false) String isManager,
                                            @RequestHeader(value = "X-UserToken") String token,
                                            @ApiIgnore Pageable pageable) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        // 当状态为空的时候，查询状态为开启的催员。
        if (Objects.nonNull(state)) {
            builder.and(qUser.status.eq(state));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
            state=User.Status.OPEN.getValue();
        }else{
            state=null;
        }
        if (Objects.nonNull(deptCode)) {
            builder.and(qUser.department.code.like(deptCode.concat("%")));
        }
        if (Objects.nonNull(userName)) {
            builder.and(qUser.userName.like("%".concat(userName).concat("%")));
        }
        if (Objects.nonNull(realName)) {
            builder.and(qUser.realName.like("%".concat(realName).concat("%")));
        }

        if (StringUtils.isNotEmpty(deptId)) {
            builder.and(qUser.department.id.eq(deptId));
        }
        Page<User> page = userRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(page);
    }

    /**
     * @Description : 查询部门下的用户不分页
     */
    @RequestMapping(value = "/queryNoPage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询部门下的用户", notes = "查询部门下的用户")
    public ResponseEntity<List<User>> queryNoPage(@RequestParam String id,
                                                  @RequestParam(required = false)String deptCode,
                                                  @RequestParam(required = false) String deptId,
                                                  @RequestParam(required = false) String userName,
                                                  @RequestParam(required = false) String realName,
                                                  @RequestParam(required = false) Integer state,
                                                  @RequestParam(required = false) String isManager,
                                                  @RequestHeader(value = "X-UserToken") String token) {

        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        Department department = departmentRepository.findOne(id);
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(department.getCode())) {
            builder.and(qUser.department.code.like(department.getCode().concat("%")));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if(StringUtils.isNotEmpty(deptId)){
            builder.and(qUser.department.id.eq(deptId));
        }
        if(StringUtils.isNotEmpty(userName)){
            builder.and(qUser.userName.like("%".concat(userName).concat("%")));
        }
        if(StringUtils.isNotEmpty(realName)){
            builder.and(qUser.userName.like("%".concat(realName).concat("%")));
        }
        if (Objects.nonNull(state)) {
            builder.and(qUser.status.eq(state));
        }else{
            builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(user.getCompanyCode())) {
            builder.and(qUser.companyCode.eq(user.getCompanyCode()));
        }
        List<User> userList = new ArrayList<>();
        Iterator<User> users = userRepository.findAll(builder).iterator();
        while (users.hasNext()) {
            userList.add(users.next());
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(userList);
    }
    /**
     * @Description : 根据id查询
     */
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据id查询用户", notes = "查询部门下的用户")
    public ResponseEntity<User> getUserById(@RequestParam String id) {

        User user = userRepository.findOne(id);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(user);
    }

    /**
     * @Description : 查询角色下的用户
     */
    @RequestMapping(value = "/getUserByRolePage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询角色下的用户", notes = "查询角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> getUserByRolePage(@RequestParam String id,
                                                        @RequestParam(required = false) String userName,
                                                        @RequestParam(required = false) String realName,
                                                        @RequestParam(required = false) String isManager,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        Role role = roleRepository.findOne(id);
        if (Objects.isNull(role)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "There is no role", "角色不存在")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.roles.any().id.eq(id));
        if (Objects.nonNull(userName)) {
            builder.and(qUser.userName.like("%".concat(userName).concat("%")));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(realName)) {
            builder.and(qUser.realName.like("%".concat(realName).concat("%")));
        }
        if (Objects.nonNull(user.getCompanyCode())) {
            builder.and(qUser.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        Page<User> page = userRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(page);
    }

    /**
     * @Description : 查询外访机构下的用户
     */
    @RequestMapping(value = "/getUserByType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询外访机构下的用户", notes = "查询外访机构下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<User>> getUserByType(@QuerydslPredicate(root = User.class) Predicate predicate,
                                                    @RequestParam(required = false) String companyCode,
                                                    @RequestParam(required = false) String realName,
                                                    @RequestParam(required = true) Integer type,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestHeader(value = "X-UserToken") String token,
                                                    @RequestParam(required = false) String isManager,
                                                    @RequestParam(required = false) String deptCode,
                                                    @RequestParam(required = false) String divisionSwitch,
                                                    @ApiIgnore Pageable pageable) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (StringUtils.isNotBlank(companyCode)) {
                builder.and(QUser.user.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QUser.user.companyCode.eq(user.getCompanyCode()));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(deptCode)) {
            builder.and(QUser.user.department.code.like(deptCode.concat("%")));
        }
        if (Objects.nonNull(realName)) {
            builder.and(QUser.user.realName.like(realName.concat("%")));
        }
        if(Objects.isNull(status)){
            builder.and(QUser.user.status.eq(status));
        }else{
            builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        }

        if (Objects.nonNull(type)) {
            builder.and(qUser.type.eq(type));
        }
        Page<User> page = userRepository.findAll(builder, pageable);
        return ResponseEntity.ok().body(page);
    }

    /**
     * @Description : 查询外访机构下的用户
     */
    @RequestMapping(value = "/getUserByTypeApp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询外访机构下的用户", notes = "查询外访机构下的用户")
    public ResponseEntity<List<String>> getUserByTypeApp(@QuerydslPredicate(root = User.class) Predicate predicate,
                                                    @RequestParam(required = false) String companyCode,
                                                    @RequestParam(required = false) String realName,
                                                    @RequestParam(required = true) Integer type,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestHeader(value = "X-UserToken") String token,
                                                    @RequestParam(required = false) String isManager,
                                                    @RequestParam(required = false) String deptCode,
                                                    @RequestParam(required = false) String divisionSwitch,
                                                    @ApiIgnore Pageable pageable) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (StringUtils.isNotBlank(companyCode)) {
                builder.and(QUser.user.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QUser.user.companyCode.eq(user.getCompanyCode()));
        }
        /**
         * 在查询用户的时候，除了系统管理需要返回分案开关开启与关闭的人员，其他的只返回开启的用户。
         * isManager 当该值不为空的时候，说明是管理界面，查询所有用户，即不传该参数。
         */
        if(StringUtils.isEmpty(isManager)){
            builder.and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()));
        }
        if (Objects.nonNull(deptCode)) {
            builder.and(QUser.user.department.code.like(deptCode.concat("%")));
        }
        if (Objects.nonNull(realName)) {
            builder.and(QUser.user.realName.like(realName.concat("%")));
        }
        if(Objects.isNull(status)){
            builder.and(QUser.user.status.eq(status));
        }else{
            builder.and(qUser.status.eq(User.Status.OPEN.getValue()));
        }

        if (Objects.nonNull(type)) {
            builder.and(qUser.type.eq(type));
        }
        Iterable<User> all = userRepository.findAll(builder);
        List<String> list = new ArrayList<>();
        all.forEach(obj->{list.add(obj.getRealName());});
        return ResponseEntity.ok().body(list);
    }

    /**
     * @Description 导出用户列表
     */
    @PostMapping("/exportUserList")
    @ApiOperation(value = "导出用户列表", notes = "导出用户列表")
    public ResponseEntity<String> exportReport(@RequestBody UserListExport request) {
        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;
        List<UserModel> userModelList = new ArrayList<>();
        for (User user : request.getUserList()) {
            UserModel userModel = new UserModel();
            userModel.setUserName(user.getUserName());
            userModel.setRealName(user.getRealName());
            userModel.setDepartment(user.getDepartment().getName());
            String type = null;
            Integer typetemp=user.getType();
            if(null == typetemp){
                typetemp=0;
            }
            switch (typetemp) {
                case 1:
                    type = "电话催收";
                    break;
                case 2:
                    type = "外访催收";
                    break;
                case 3:
                    type = "司法催收";
                    break;
                case 4:
                    type = "委外催收";
                    break;
                case 5:
                    type = "智能催收";
                    break;
                case 6:
                    type = "提醒催收";
                    break;
                case 7:
                    type = "修复管理";
                    break;
                case 196:
                    type = "综合管理";
                    break;
                case 508:
                    type = "特殊催收";
                    break;
                case 506:
                    type = "停催催收";
                    break;
                default:
                    type = "未知类型";
                    break;
            }
            userModel.setType(type);
            Integer managerTemp=user.getManager();
            if(null == managerTemp){
                managerTemp=0;
            }
            String manager = null;
            switch (managerTemp) {
                case 1:
                    manager = "是";
                    break;
                case 0:
                    manager = "否";
                    break;
                default:
                    manager = "未知类型";
                    break;
            }
            userModel.setManager(manager);
            Integer statusTemp=user.getStatus();
            if(null == statusTemp){
                statusTemp=0;
            }
            String  status = null;
            switch (statusTemp) {
                case 504:
                    status = "启用";
                    break;
                case 505:
                    status = "停用";
                    break;
                default:
                    status = "未知类型";
                    break;
            }
            userModel.setStatus(status);
            userModel.setCallPhone(user.getCallPhone());
            userModel.setOperateTime(user.getOperateTime());

            String collectionGrade=null;
            Integer grade=user.getCollectionGrade();
            if(null == grade){
                grade=0;
            }
            switch (grade) {
                case 500:
                    collectionGrade = "A";
                    break;
                case 501:
                    collectionGrade = "B";
                    break;
                case 502:
                    collectionGrade = "C";
                    break;
                case 503:
                    collectionGrade = "D";
                    break;
                default:
                    collectionGrade = "";
                    break;
            }
            userModel.setCollectionGrade(collectionGrade);

            Integer loginDeviceTemp=user.getLoginDevice();
            if(null == loginDeviceTemp){
                loginDeviceTemp=0;
            }
            String  loginDevice = null;
            switch (loginDeviceTemp) {
                case 1:
                    loginDevice = "pc登录";
                    break;
                case 2:
                    loginDevice = "手机登录";
                    break;
                default:
                    loginDevice = "未知类型";
                    break;
            }
            userModel.setLoginDevice(loginDevice);
            Integer switchTemp=user.getDivisionSwitch();
            if(null == switchTemp){
                switchTemp=0;
            }
            String divisionSwitch=null;
            switch (switchTemp) {
                case 504:
                    divisionSwitch = "开启";
                    break;
                case 505:
                    divisionSwitch = "关闭";
                    break;
                default:
                    divisionSwitch = "";
                    break;
            }
            userModel.setDivisionSwitch(divisionSwitch);
            userModel.setMessagePushId(user.getMessagePushId());
            userModel.setPhone(user.getPhone());
            userModel.setEmail(user.getEmail());
            userModel.setRemark(user.getRemark());
            //创建人显示用户真实姓名,禅道Bug1488
            if(ZWStringUtils.isNotEmpty(user.getOperator())) {
                userModel.setOperator(userRepository.findOne(QUser.user.userName.eq(user.getOperator())).getRealName());
            }
            userModelList.add(userModel);
        }
        try {
            String[] titleList = {"用户名","姓名","所属机构","状态","催收类型","是否管理员","电话","主叫号码","邮箱","创建时间","催收员等级","登录设备","分案开关","消息推送标识","备注", "创建人"};
            String[] proNames = {"userName","realName","department","status","type","manager","phone","callPhone","email","operateTime","collectionGrade","loginDevice","divisionSwitch","messagePushId","remark","operator"};
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            ExcelUtil.createExcel(workbook, sheet, userModelList, titleList, proNames, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "用户列表.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The upload server failed", "上传服务器失败")).body(null);
            } else {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "")).body(url.getBody());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The upload server failed", "失败")).body(null);
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 删除文件
            if (file != null) {
                file.delete();
            }
        }
    }
}
