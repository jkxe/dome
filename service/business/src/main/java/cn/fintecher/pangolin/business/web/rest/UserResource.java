package cn.fintecher.pangolin.business.web.rest;

import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.business.service.UserService;
import cn.fintecher.pangolin.business.session.SessionStore;
import cn.fintecher.pangolin.entity.QUser;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-07-05-9:17
 */
@RestController
@RequestMapping("/api/userResource")
@Api(value = "userResource", description = "用户信息")
public class UserResource {
    private static final String ENTITY_NAME = "User";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/getUserByToken")
    @ApiOperation(value = "通过token获取用户", notes = "通过token获取用户")
    public ResponseEntity<User> getUserByToken(@RequestParam @ApiParam("token") String token) {
        try {
            User user = userService.getUserByToken(token);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取用户成功", ENTITY_NAME)).body(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("用户未登录", ENTITY_NAME)).body(null);
        }

    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户", notes = "保存用户")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User user1 = userService.save(user);
        return ResponseEntity.ok().body(user1);
    }

    @GetMapping("/findUserById")
    @ApiOperation(value = "通过id查询用户", notes = "通过id查询用户")
    public ResponseEntity<User> findUserById(@RequestParam @ApiParam("id") String id) {
        User user = userRepository.findOne(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/getUsersOnCompany")
    @ApiOperation(value = "统计公司下用户数", notes = "统计公司下用户数")
    public ResponseEntity<Integer> getUsersOnCompany(@RequestParam @ApiParam("ids") List<String> ids,
                                                     @RequestParam @ApiParam("token") String token) {
        ResponseEntity<User> userBody = getUserByToken(token);
        if (Objects.isNull(userBody)) {
            return ResponseEntity.ok().body(0);
        }
        User user = userBody.getBody();
        Set<String> setIds = new HashSet<>();
        for (String idstr : ids) {
            setIds.add(idstr);
        }
        int num = 0;
        for (String userId : setIds) {
            if (Objects.equals(userRepository.findOne(userId).getCompanyCode(), user.getCompanyCode())) {
                num += 1;
            }
        }
        return ResponseEntity.ok().body(num);
    }

    @GetMapping("/getAllUsers")
    @ApiOperation(value = "统计公司部门下的所有用户", notes = "统计公司下用户数")
    public ResponseEntity<Iterable<User>> getUsersOnCompany(@RequestParam @ApiParam("companyCode") String companyCode) {
        try {
            Iterable<User> userIterable = userRepository.findAll(QUser.user.companyCode.eq(companyCode));
            return ResponseEntity.ok().body(userIterable);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "获取所有用户失败")).body(null);
        }
    }
}
