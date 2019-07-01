package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.UserDeviceReset;
import cn.fintecher.pangolin.business.repository.DepartmentRepository;
import cn.fintecher.pangolin.business.repository.RoleRepository;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.business.session.SessionStore;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : Administrator
 * @Description :
 * @Date : 17:43 2017/7/20
 */
@Service("userService")
public class UserService {
    final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Description : 得到特定公司部门下的用户 id 部门的id  state 用户的状态
     */
    public List<User> getAllUser(String id, Integer state) {
        Department department = departmentRepository.findOne(id);
        QUser qUser = QUser.user;
        Iterator<User> userList;
        if (Objects.isNull(state)) {
            userList = userRepository.findAll(qUser.department.code.like(department.getCode().concat("%")).and(qUser.companyCode.eq(department.getCompanyCode()))
                    .and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()))).iterator();
        } else {
            userList = userRepository.findAll(qUser.department.code.like(department.getCode().concat("%")).and(qUser.department.status.eq(state))
                    .and(qUser.status.eq(User.Status.OPEN.getValue())).and(qUser.companyCode.eq(department.getCompanyCode())).and(qUser.divisionSwitch.eq(User.Status.OPEN.getValue()))).iterator();
        }
        List<User> userReturn = new ArrayList<User>();
        //转成list
        while (userList.hasNext()) {
            userReturn.add(userList.next());
        }
        return userReturn;
    }

    /**
     * @Description : 得到外访的主管 type 用户类型   state 用户的状态   companyCode 公司code   manager 是否管理者  0 是  1 否
     */
    public List<User> getAllUser(String companyCode, Integer type, Integer state, Integer manager) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(companyCode)) {
            builder.and(qUser.companyCode.eq(companyCode));
        }
        if (Objects.nonNull(type)) {
            builder.and(qUser.type.eq(type));
        }
        if (Objects.nonNull(state)) {
            builder.and(qUser.status.eq(state));
        }
        if (Objects.nonNull(manager)) {
            builder.and(qUser.manager.eq(manager));
        }
        Iterator<User> userList = userRepository.findAll(builder).iterator();
        List<User> userReturn = IteratorUtils.toList(userList);
        return userReturn;
    }

    /**
     * 获取用户所在部门主管
     *
     * @param userId
     * @return
     */
    public List<User> getManagerByUser(String userId) {
        BooleanBuilder builder = new BooleanBuilder();
        QUser qUser = QUser.user;
        User user = userRepository.findOne(userId);
        builder.and(qUser.department.id.eq(user.getDepartment().getId()).
                and(qUser.manager.eq(1)).
                and(qUser.companyCode.eq(user.getCompanyCode())));
        return IterableUtils.toList(userRepository.findAll(builder));
    }

    /**
     * 获取对应角色的所有用户
     *
     * @param roleName
     * @return
     */
    public List<User> getUserByRoleName(String roleName) {
        BooleanBuilder builderRole = new BooleanBuilder();
        Iterator<Role> roleIterator=roleRepository.findAll(builderRole.and(QRole.role.name.eq(roleName))).iterator();
        if (roleIterator.hasNext()){
            Role role=roleIterator.next();
            BooleanBuilder builderUser = new BooleanBuilder();
            return IterableUtils.toList(userRepository.findAll(builderUser.and(QUser.user.roles.contains(role))));
        }
        return null;
    }


    /**
     * 通过userId获取所有上级领导
     *
     * @param userId
     * @return
     */
    public List<User> getAllHigherLevelManagerByUser(String userId){
        Department department = userRepository.findOne(userId).getDepartment();
        Department departmentTemp = department;
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department);
        while (Objects.nonNull(departmentTemp.getParent())) {
            departmentTemp = departmentTemp.getParent();
            departmentList.add(departmentTemp);
        }
        BooleanBuilder builder = new BooleanBuilder();
        QUser qUser = QUser.user;
        builder.and(qUser.department.in(departmentList)).
                and(qUser.manager.eq(1)).
                and(qUser.companyCode.eq(department.getCompanyCode()));
        return IterableUtils.toList(userRepository.findAll(builder));
    }

    public static final long REDIS_EXPIRETIME=15;
    /**
     * @Description : 通过token查询用户
     */
    public User getUserByToken(String token) throws Exception {
        User user =(User) redisTemplate.opsForValue().get(token);
        if (Objects.isNull(user)) {
            throw new Exception(Constants.SYS_EXCEPTION_NOSESSION);
        }
        redisTemplate.expire(token, REDIS_EXPIRETIME, TimeUnit.MINUTES);
        return user;
    }

    /**
     * 启用/禁用设备
     *
     * @param request
     */
    public void resetDeviceStatus(UserDeviceReset request) {
        Set<User> users = new HashSet<>();
        for (String id : request.getUserIds()) {
            User user = userRepository.findOne(id);
            Set<UserDevice> userDevices = new HashSet<>();
            userDevices.addAll(user.getUserDevices());
            for (UserDevice ud : userDevices) {
                if (Objects.equals(ud.getType(), request.getUsdeType())) {
                    ud.setStatus(request.getUsdeStatus());
                }
            }
            user.getUserDevices().clear();
            user.getUserDevices().addAll(userDevices);
            users.add(user);
        }
        userRepository.save(users);
        userRepository.flush();
    }

    /**
     * 启动/停用设备锁
     *
     * @param request
     */
    public void resetDeviceValidate(UserDeviceReset request) {
        Set<User> users = new HashSet<>();
        for (String id : request.getUserIds()) {
            User user = userRepository.findOne(id);
            Set<UserDevice> userDevices = new HashSet<>();
            userDevices.addAll(user.getUserDevices());
            if (!userDevices.isEmpty()) {
                for (UserDevice ud : userDevices) {
                    if (Objects.equals(ud.getType(), request.getUsdeType())) {
                        ud.setValidate(request.getValidate());
                    }
                }
            }
            user.getUserDevices().clear();
            user.getUserDevices().addAll(userDevices);
            users.add(user);
        }
        userRepository.save(users);
        userRepository.flush();
    }

    /**
     * 重置设备
     *
     * @param request
     */
    public void resetDeviceCode(UserDeviceReset request) {
        Set<User> users = new HashSet<>();
        for (String id : request.getUserIds()) {
            User user = userRepository.findOne(id);
            Set<UserDevice> userDevices = new HashSet<>();
            userDevices.addAll(user.getUserDevices());
            if (!userDevices.isEmpty()) {
                Iterator<UserDevice> iterator = userDevices.iterator();
                while (iterator.hasNext()) {
                    UserDevice ud = iterator.next();
                    if (Objects.equals(ud.getType(), request.getUsdeType())) {
                        ud.setCode(null);
                        ud.setMac(null);
                    }
                }
            }
            user.getUserDevices().clear();
            user.getUserDevices().addAll(userDevices);
            users.add(user);
        }
        userRepository.save(users);
        userRepository.flush();
    }

    @Cacheable(value = "userCache", key = "'petstore:user:all'")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @CacheEvict(value = "userCache", key = "'petstore:user:'+#user.userName")
    public User save(User user) {
        return userRepository.save(user);
    }

}