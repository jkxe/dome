package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.repository.OfflineHistoryRemarkRepository;
import cn.fintecher.pangolin.entity.Department;
import cn.fintecher.pangolin.entity.OfflineHistoryRemark;
import cn.fintecher.pangolin.entity.QOfflineHistoryRemark;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  线下历史催记controller
 * @Package cn.fintecher.pangolin.business.web
 * @ClassName: cn.fintecher.pangolin.business.web.OfflineHistoryRemarkController
 * @date 2018年06月28日 14:25
 */
@RestController
@RequestMapping("/api/offlineHistoryRemarkController")
@Api(value = "线下历史催记管理", description = "线下历史催记管理")
public class OfflineHistoryRemarkController extends BaseController{

    private static final Logger log=Logger.getLogger(OfflineHistoryRemarkController.class);


    @Autowired
    private OfflineHistoryRemarkRepository offlineHistoryRemarkRepository;

    @GetMapping("/queryOfflineHistoryRemark")
    @ApiOperation(value = "多条件查询线下历史催记", notes = "多条件查询线下历史催记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OfflineHistoryRemark>> queryOfflineHistoryRemark(
            @QuerydslPredicate(root = Department.class) Predicate predicate,
            @ApiIgnore Pageable pageable,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String collectionUser,
            @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to findOfflineHistoryRemark");
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OfflineHistoryRemark", "User is not login", "用户未登录")).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (StringUtils.isNotEmpty(clientName)) {
            builder.and(QOfflineHistoryRemark.offlineHistoryRemark.clientName.like("%".concat(clientName).concat("%")));
        }
        if (StringUtils.isNotEmpty(collectionUser)) {
            builder.and(QOfflineHistoryRemark.offlineHistoryRemark.collectionUser.like("%".concat(collectionUser).concat("%")));
        }
        Page<OfflineHistoryRemark> page = offlineHistoryRemarkRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(page);
    }
}
