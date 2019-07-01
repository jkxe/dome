package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.repository.TaskBoxRepository;
import cn.fintecher.pangolin.entity.QTaskBox;
import cn.fintecher.pangolin.entity.TaskBox;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 存放下载任务的盒子
 */
@RestController
@RequestMapping("/api/taskBoxController")
@Api(value = "TaskBoxController", description = "消息盒子")
public class TaskBoxController extends BaseController {
    private static final String ENTITY_NAME = "TaskBox";
    private final Logger logger = LoggerFactory.getLogger(TaskBoxController.class);

    @Autowired
    private TaskBoxRepository taskBoxRepository;

    @GetMapping("/query")
    @ApiOperation(value = "按条件分页查询", notes = "按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<TaskBox>> query(@QuerydslPredicate(root = TaskBox.class) Predicate predicate,
                                               @RequestHeader(value = "X-UserToken") String token, @ApiIgnore Pageable pageable) {
        User tokenUser;
        try {
            tokenUser = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            Pageable able = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "operatorTime"));
            builder.and(QTaskBox.taskBox.operator.eq(tokenUser.getId()));
            Page<TaskBox> page = taskBoxRepository.findAll(builder, able);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taskBoxController/query");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", "")).body(null);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查找", notes = "通过ID查找")
    public ResponseEntity<TaskBox> getOne(@PathVariable(value = "id") String id,
                                          @RequestHeader(value = "X-UserToken") String token) {
        User tokenUser;
        try {
            tokenUser = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
        try {
            TaskBox taskBox = taskBoxRepository.findOne(id);
            return ResponseEntity.ok(taskBox);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", "")).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过ID查找", notes = "通过ID查找")
    public ResponseEntity<TaskBox> deleteById(@PathVariable(value = "id") String id,
                                              @RequestHeader(value = "X-UserToken") String token) {
        User tokenUser;
        try {
            tokenUser = getUserByToken(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
        try {
            taskBoxRepository.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "taskBox", "")).body(null);
        }
    }
}
