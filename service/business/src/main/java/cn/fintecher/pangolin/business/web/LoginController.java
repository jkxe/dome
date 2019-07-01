package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.UserService;
import cn.fintecher.pangolin.business.utils.GetClientIp;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.MD5;
import cn.fintecher.pangolin.entity.util.Status;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 10:39 2017/7/17
 */
@RestController
@RequestMapping("/api/login")
@Api(value = "登录相关", description = "登陆相关")
public class LoginController extends BaseController {
    private static final String ENTITY_NAME = "login";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 无MD5加密用户登录 开发使用
     */
    @GetMapping("/getUserByToken")
    @ApiOperation(value = "通过token获取用户信息", notes = "通过token获取用户信息")
    public ResponseEntity<UserLoginResponse> getUserToken(@RequestHeader(value = "X-UserToken") String token) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户未登录,请重新登录")).body(null);
        }
        try {
            Set<Role> roles1 = user.getRoles();
            UserSimpleModel userSimpleModel = new UserSimpleModel();
            modelMapper.map(user, userSimpleModel);
            Set<Resource> resourcesTemp = new HashSet<>();
            for (Role role : roles1) {
                resourcesTemp.addAll(role.getResources());
                role.getResources().forEach(e -> {
                    if (Objects.equals(Resource.Type.BUTTON.getValue(), e.getType())) {
                        userSimpleModel.getResource().add(e.getId());
                    } else {
                        ResourceModel resourceModel = new ResourceModel();
                        modelMapper.map(e, resourceModel);
                        resourceModel.setParentId(Objects.isNull(e.getParent()) ? null : e.getParent().getId());
                        userSimpleModel.getMenu().add(resourceModel);
                    }
                });
            }
            if (resourcesTemp.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "权限异常,请联系管理员")).body(null);
            }

            Long resourceId = getMaxId() + 1;
            //加入我的主页 一级菜单
            userSimpleModel.getMenu().add(getMyPage(resourceId));
            //加入我的主页 二级菜单
            userSimpleModel.getMenu().add(getMainPage(resourceId, user));

            userLoginResponse.setUser(userSimpleModel);
            userLoginResponse.setToken(token);
            if (Objects.equals("administrator", user.getUserName())) {
                userLoginResponse.setRegDay("success");
            }
            Company company = companyRepository.findOne(QCompany.company.code.eq(user.getCompanyCode()));
            if (Objects.isNull(company.getRegisterDay())) {
                userLoginResponse.setRegDay("noReg");
            } else {
                userLoginResponse.setRegDay("success");
            }
            return ResponseEntity.ok().body(userLoginResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "登录异常，请重新登录")).body(null);
        }
    }

    /**
     * 无MD5加密用户登录 开发使用
     */
    @PostMapping("/noUseMD5Login")
    @ApiOperation(value = "用户登陆测试", notes = "用户登陆测试")
    public ResponseEntity noUseMd5Login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        loginRequest.setPassword(MD5.MD5Encode(loginRequest.getPassword()));
        return login(loginRequest, request);
    }

    /**
     * 根据ip获取MAC地址
     */
    public String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //if (str.indexOf("MAC Address") > 1) {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(
                                str.indexOf("=") + 2, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        System.out.println(macAddress);
        return macAddress;
    }
   /* private String getLocalMac(InetAddress ia) throws SocketException {
                //获取网卡，获取地址
                byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

                System.out.println("mac数组长度："+mac.length);
                StringBuffer sb = new StringBuffer("");
            for(int i=0; i<mac.length; i++) {
                    if(i!=0) {
                        sb.append("-");
                    }
                //字节转换为整数
                int temp = mac[i]&0xff;
                String str = Integer.toHexString(temp);
                System.out.println("每8位:"+str);
                if(str.length()==1) {
                        sb.append("0"+str);
                    }else {
                        sb.append(str);
                    }
                }
                return sb.toString().toUpperCase();
            }*/
    /**
     * @Description : 用户登录返回部门和角色
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    public ResponseEntity login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        try {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user = userRepository.findByUserName(loginRequest.getUsername());
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "该用户不存在")).body(null);
            }
            //用户登录状态
            if (Objects.equals(Status.Disable.getValue(), user.getStatus())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "该用户已停用，请联系管理员")).body(null);
            }
            //电催用户不能登录pc端(目前可以登录  无usdeType传入)
            if (Objects.equals(loginRequest.getUsdeType(), 1) && Objects.equals(user.getType(), User.Type.TEL.getValue())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "电催用户不能登录pc端")).body(null);
            }
            //用户登录验证
            if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute(Constants.SESSION_USER, user);
                UserLoginResponse response = new UserLoginResponse();
                UserSimpleModel userSimpleModel = new UserSimpleModel();
                modelMapper.map(user, userSimpleModel);
                userSimpleModel.setDeptId(user.getDepartment().getId());
                userSimpleModel.setRole(user.getRoles());
                Set<Resource> resourcesTemp = new HashSet<>();
                for (Role role : user.getRoles()) {
                    Set<Resource> resources = role.getResources();
                    resourcesTemp.addAll(resources);
                    resources.forEach(e -> {
                        if (Objects.equals(e.getType(), Resource.Type.BUTTON.getValue())) {
                            userSimpleModel.getResource().add(e.getId());
                        } else {
                            ResourceModel resourceModel = new ResourceModel();
                            modelMapper.map(e, resourceModel);
                            resourceModel.setParentId(Objects.nonNull(e.getParent()) ? e.getParent().getId() : null);
                            userSimpleModel.getMenu().add(resourceModel);
                        }
                    });
                }
                if (resourcesTemp.isEmpty()) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "权限异常,请联系管理员")).body(null);
                }
                Page<Resource> resourceRepositoryAll = resourceRepository.findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id")));
                if (Objects.isNull(resourceRepositoryAll) || !resourceRepositoryAll.hasNext()) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "权限异常,请联系管理员")).body(null);
                }

                //我的主页与主页在数据库中已经添加，不需要重复添加。
                Long resourceId = getMaxId() + 1;
                //加入我的主页 一级菜单
                userSimpleModel.getMenu().add(getMyPage(resourceId));
                //加入我的主页 二级菜单
                userSimpleModel.getMenu().add(getMainPage(resourceId, user));

                response.setUser(userSimpleModel);
                response.setToken(session.getId());
                //验证设备启用状态
                Set<UserDevice> userDevices = user.getUserDevices();
                for (UserDevice userDevice : userDevices) {
                    if (Objects.equals(userDevice.getType(), loginRequest.getUsdeType())) {
                        if (Objects.equals(userDevice.getStatus(), Status.Disable.getValue())) {
                            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户设备未启用")).body(null);
                        } else if (Objects.equals(userDevice.getValidate(), Status.Enable.getValue())) {
                            //验证登录为pc
                            if (Objects.equals(userDevice.getType(), Status.Enable.getValue())) {
                                String ip = GetClientIp.getIp(request);
                                // address=InetAddress.getByName(ip);
                                if (StringUtils.isEmpty(userDevice.getCode())) {
                                    String mac = getMACAddress(ip);
                                    loginRequest.setUsdeCode(ip);
                                    userDevice.setMac(mac);
                                    userDevice.setCode(ip);
                                } else {
                                    //String mac1 = getMACAddress(ip);
//                                    if (!Objects.equals(userDevice.getMac(), mac1)) {
//                                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "本次登录和上次登录地址不一致!")).body(null);
//                                    }
                                }
                            } else {
                                //登录为移动
                                if (StringUtils.isEmpty(userDevice.getCode())) {
                                    userDevice.setCode(loginRequest.getUsdeCode());
                                } else {
//                                    if (Objects.equals(userDevice.getCode(), loginRequest.getUsdeCode())) {
//                                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "本次登录和上次登录地址不一致!")).body(null);
//                                    }
                                }
                            }
                            userDeviceRepository.save(userDevice);
                        }
                        break;
                    }
                }
                //密码无定时修改
                if (Objects.isNull(user.getPasswordInvalidTime())) {
                    user.setPasswordInvalidTime(ZWDateUtil.getNowDateTime());
                    userRepository.save(user);
                    response.setReset(false);
                } else {
                    //系统参数设置密码修改时间限制
                    QSysParam qSysParam = QSysParam.sysParam;
                    SysParam sysParam;
                    try {
                        if (Objects.isNull(user.getCompanyCode())) {
                            sysParam = sysParamRepository.findOne(qSysParam.companyCode.isNull().and(qSysParam.code.eq(Constants.USER_OVERDAY_CODE)));
                        } else {
                            sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(user.getCompanyCode()).and(qSysParam.code.eq(Constants.USER_OVERDAY_CODE)));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "系统参数异常")).body(null);
                    }
                    if (Objects.nonNull(sysParam)) {
                        if (Objects.equals(sysParam.getStatus(), Status.Enable.getValue())) {
                            int overDay = Integer.parseInt(sysParam.getValue());
                            Date nowDate = ZWDateUtil.getNowDate();
                            Calendar calendar1 = Calendar.getInstance();
                            Calendar calendar2 = Calendar.getInstance();
                            calendar1.setTime(user.getPasswordInvalidTime()); //修改密码时间
                            calendar2.setTime(nowDate);
                            int daydiff = (calendar2.getTime().getYear() - calendar1.getTime().getYear()) * 12 + calendar2.getTime().getMonth() - calendar1.getTime().getMonth() + calendar2.getTime().getDate() - calendar1.getTime().getDate();
                            if (overDay >= daydiff) {
                                response.setReset(false);
                            } else {
                                response.setReset(true);
                            }
                        } else {
                            response.setReset(false);
                        }
                    } else {
                        response.setReset(false);
                    }
                }
                if (Objects.equals("administrator", user.getUserName())) {
                    response.setRegDay("success");
                } else {
                    QCompany qCompany = QCompany.company;
                    Company company = companyRepository.findOne(qCompany.code.eq(user.getCompanyCode()));
                    if (Objects.isNull(company.getRegisterDay())) {
                        response.setRegDay("noReg");
                    } else {
                        response.setRegDay("success");
                    }
                }
                //SessionStore.getInstance().addUser(session.getId(), session);
                QSysParam qSysParamt = QSysParam.sysParam;
                SysParam sysParamt = sysParamRepository.findOne(qSysParamt.code.eq(Constants.TEMP).and(qSysParamt.companyCode.eq("0001")));
//                if(Objects.isNull(sysParamt)){
//                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "服务已到期,请联系客服")).body(null);
//                }
//                if(ZWDateUtil.getBetween(ZWDateUtil.getNowDate(),ZWDateUtil.getFormatDate("2018-09-30"),ChronoUnit.DAYS)<0
//                        || Integer.valueOf(sysParamt.getValue())>35){
//                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "服务已到期,请联系客服")).body(null);
//                }
                redisTemplate.opsForValue().set(session.getId(),user, UserService.REDIS_EXPIRETIME, TimeUnit.MINUTES);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "密码错误")).body(null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "登录失败,请联系管理员")).body(null);
        }
    }

    /**
     * @Description : 软件注册返回码
     */
    @RequestMapping(value = "/returnCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "软件注册返回码", notes = "软件注册返回码")
    public ResponseEntity<String> returnCode(@RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParams = sysParamRepository.findOne(qSysParam.code.eq(Constants.REGISTER_SOFTWARE_CODE).and(qSysParam.type.eq(Constants.REGISTER_SOFTWARE_TYPE)).and(qSysParam.companyCode.eq(user.getCompanyCode())));
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("注册成功", ENTITY_NAME)).body(sysParams.getValue());
    }

    /**
     * @Description : 软件注册
     */
    @RequestMapping(value = "/registerSoftware", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "软件注册", notes = "软件注册")
    public ResponseEntity<String> registerSoftware(@Validated @RequestBody @ApiParam("软件注册") RegisterSoftware request,
                                                   @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParams = sysParamRepository.findOne(qSysParam.code.eq(Constants.REGISTER_SOFTWARE_CODE).and(qSysParam.type.eq(Constants.REGISTER_SOFTWARE_TYPE)).and(qSysParam.companyCode.eq(user.getCompanyCode())));
        String orgCode = MD5.MD5Encode(sysParams.getValue() + "zwjk");
        String code = MD5.MD5Encode(request.getCode() + "zwjk");
        if (Objects.equals(orgCode, code)) {
            QCompany qCompany = QCompany.company;
            Company company = companyRepository.findOne(qCompany.code.eq(user.getCompanyCode()));
            company.setRegisterDay(99999);
            companyRepository.save(company);
        } else {
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("注册失败", ENTITY_NAME)).body("注册失败");
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("注册成功", ENTITY_NAME)).body("注册成功");
    }

    /**
     * @Description : 修改密码
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResponseEntity<User> updatePassword(@Validated @RequestBody @ApiParam("修改的用户密码") UpdatePassword request,
                                               @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (Objects.isNull(request.getOldPassword())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Please enter the original password", "请输入原始密码")).body(null);
        }
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Enter the original password mistake", "输入的原始密码错误")).body(null);
        }
        if (Objects.equals(request.getNewPassword(), request.getOldPassword())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Enter a new password is the same as the original password, please enter again", "输入的新密码和原始密码相同，请重新输入")).body(null);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordInvalidTime(ZWDateUtil.getNowDateTime());
        User user1 = userService.save(user);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改密码成功", "")).body(user1);

    }

    /**
     * @Description : 重置密码
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public ResponseEntity<User> resetPassword(@Validated @RequestBody @ApiParam("重置密码") UpdatePassword request,
                                              @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User userOld = userRepository.findOne(request.getUserId());
        //重置密码的设置
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParamsPassword = null;
        try {
            sysParamsPassword = sysParamRepository.findOne(qSysParam.code.eq(Constants.USER_RESET_PD_CODE).and(qSysParam.type.eq(Constants.USER_RESET_PD_TYPE)).and(qSysParam.companyCode.eq(userOld.getCompanyCode())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "To reset the password parameter abnormalities", "重置密码参数异常")).body(null);
        }
        if (Objects.nonNull(sysParamsPassword) && Objects.equals(Status.Enable.getValue(), sysParamsPassword.getStatus())) {
            userOld.setPassword(passwordEncoder.encode(MD5.MD5Encode(sysParamsPassword.getValue())));
        } else {
            userOld.setPassword(passwordEncoder.encode(Constants.LOGIN_RET_PD));
        }
        userOld.setPasswordInvalidTime(ZWDateUtil.getNowDateTime());
        User userReturn = userService.save(userOld);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("重置密码成功", "")).body(userReturn);
    }

    /**
     * @Description :启用/禁用设备
     */
    @RequestMapping(value = "/disableDevice", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "启用/禁用设备", notes = "启用/禁用设备")
    public ResponseEntity<User> disableDevice(@Validated @RequestBody @ApiParam("启用/禁用设备") UserDeviceReset request,
                                              @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "用户未登录")).body(null);
        }
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "请登录后再修改")).body(null);
        }
        userService.resetDeviceStatus(request);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("禁用设备操作成功", "operator successfully")).body(user);
    }

    /**
     * @Description : 启用/停用设备锁
     */
    @RequestMapping(value = "/enableDeviceKey", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "启用/停用设备锁", notes = "启用/停用设备锁")
    public ResponseEntity<User> enableDeviceKey(@Validated @RequestBody @ApiParam("启用/停用设备锁") UserDeviceReset request,
                                                @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "用户未登录")).body(null);
        }
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "请登录后再修改")).body(null);
        }
        userService.resetDeviceValidate(request);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("启用设备锁操作成功", "operator successfully")).body(user);
    }

    /**
     * @Description : 重置设备
     */
    @RequestMapping(value = "/resetDevice", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "重置设备", notes = "重置设备")
    public ResponseEntity<User> resetDevice(@Validated @RequestBody @ApiParam("重置设备") UserDeviceReset request,
                                            @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "用户未登录")).body(null);
        }
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User not login", "请登录后再修改")).body(null);
        }
        userService.resetDeviceCode(request);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("重置设备操作成功", "operator successfully")).body(user);
    }

    /**
     * 获取resource 中最大的ID
     *
     * @return
     */
    private Long getMaxId() throws Exception {
        Long resourceId = new Long(0);
        Page<Resource> resourceRepositoryAll = resourceRepository.findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id")));
        if (Objects.isNull(resourceRepositoryAll) || !resourceRepositoryAll.hasNext()) {
            throw new Exception("角色权限异常,请联系管理员");
        }
        resourceId = resourceRepositoryAll.iterator().next().getId();
        return resourceId;
    }

    /**
     * 动态加载我的主页一级菜单
     *
     * @param resourceId
     * @return
     */
    private ResourceModel getMyPage(Long resourceId) {
        ResourceModel resourceModelMy = new ResourceModel();
        resourceModelMy.setId(resourceId);
        resourceModelMy.setName("我的主页");
        resourceModelMy.setSort(1000);
        resourceModelMy.setType(17);
        resourceModelMy.setShow(0);
        resourceModelMy.setUrl("/dashboard");
        resourceModelMy.setIcon("home");
        resourceModelMy.setParentId(null);
        return resourceModelMy;
    }

    /**
     * 获取 主页 二级菜单
     *
     * @param resourceId
     * @param user
     * @return
     */
    private ResourceModel getMainPage(Long resourceId, User user) {
        Long mainResourceId = resourceId + 1;
        ResourceModel resourceModelMain = new ResourceModel();
        resourceModelMain.setId(mainResourceId);
        resourceModelMain.setName("主页");
        resourceModelMain.setSort(Integer.valueOf(mainResourceId.toString()));
        resourceModelMain.setType(18);
        resourceModelMain.setShow(0);
        resourceModelMain.setParentId(resourceId);
        if (Objects.equals(user.getManager(), 1))
            resourceModelMain.setUrl("/dashboard/admin");
        else
            resourceModelMain.setUrl("/dashboard/user");
        return resourceModelMain;
    }
}