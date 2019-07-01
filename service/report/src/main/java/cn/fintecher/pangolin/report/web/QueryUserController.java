package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.mapper.QueryUserMapper;
import cn.fintecher.pangolin.report.model.HomePageResult;
import cn.fintecher.pangolin.report.model.UserModel;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by BBG on 2018/7/30.
 */

@RestController
@RequestMapping("/api/queryUserController")
@Api(value = "QueryUserController", description = "用户查找")
public class QueryUserController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(QueryUserController.class);

    @Inject
    QueryUserMapper queryUserMapper;

    @GetMapping("/queryUsers")
    ResponseEntity<List<UserModel>> queryUsers(@RequestHeader(value = "X-UserToken") String token,
                                               @ApiParam(value = "deptCode", required = true) @RequestParam String deptCode){
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("HomePageController", "getHomePageInformation", e.getMessage())).body(null);
        }
        try {
            List<UserModel> userModels = queryUserMapper.queryUsers(deptCode,null);
            return ResponseEntity.ok().body(userModels);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("HomePageController", "getHomePageInformation", "系统异常!")).body(null);
        }

    }
    @GetMapping("/queryUsersIsInStrategy")
    ResponseEntity<List<UserModel>> queryUsersIsInStrategy(@RequestHeader(value = "X-UserToken") String token,
                                               @ApiParam(value = "deptCode", required = true) @RequestParam String deptCode){
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("HomePageController", "getHomePageInformation", e.getMessage())).body(null);
        }
        try {
            List<UserModel> userModels = queryUserMapper.queryUsers(deptCode,User.Status.OPEN.getValue());
            return ResponseEntity.ok().body(userModels);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("HomePageController", "getHomePageInformation", "系统异常!")).body(null);
        }

    }

}
