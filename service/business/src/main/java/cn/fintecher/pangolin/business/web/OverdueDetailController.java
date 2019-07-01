package cn.fintecher.pangolin.business.web;


import cn.fintecher.pangolin.business.repository.OverdueDetailRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import com.hsjry.lang.common.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.util.Date;

@RestController
@RequestMapping("/api/overdueDetailController")
@Api(value = "逾期明细", description = "逾期明细")
public class OverdueDetailController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(OverdueDetailController.class);
    private static final String ENTITY_NAME = "overdueDetail";

    @Inject
    private OverdueDetailRepository overdueDetailRepository;

    @Inject
    RestTemplate restTemplate;

    @GetMapping("/getAll")
    @ApiOperation(value = "逾期明细列表", notes = "逾期明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })

    public ResponseEntity<Page<OverdueDetail>> getAll(@QuerydslPredicate(root = OverdueDetail.class) Predicate predicate,
                                                      @ApiIgnore Pageable pageable,
                                                      @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                      @RequestParam(required = false) @ApiParam(value = "统计时间") String statisticsTime,
                                                      @RequestParam(required = false) @ApiParam(value = "客户姓名") String name,
                                                      @RequestParam(required = false) @ApiParam(value = "手机号") String mobileNo,
                                                      @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                      @RequestParam(required = false) @ApiParam(value = "最小逾期天数") String minOverdueDays,
                                                      @RequestParam(required = false) @ApiParam(value = "最大逾期天数") String maxOverdueDays,
                                                      @RequestHeader(value = "X-UserToken") String token) {
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            //统计时间查询
            if(StringUtils.isNotEmpty(statisticsTime)){
                Date date = DateUtil.getDate(statisticsTime,"yyyy-MM-dd");
                builder.and(QOverdueDetail.overdueDetail.statTime.after(DateUtil.getStartTimeOfDay(date)));
                builder.and(QOverdueDetail.overdueDetail.statTime.before(DateUtil.getEndTimeOfDay(date)));
            }
            //客户姓名查询
            if(StringUtils.isNotEmpty(name)){
                builder.and(QOverdueDetail.overdueDetail.clientName.like("%"+name+"%"));
            }
            //手机号查询
            if(StringUtils.isNotEmpty(mobileNo)){
                builder.and(QOverdueDetail.overdueDetail.personalInfo.mobileNo.like("%"+mobileNo+"%"));
            }
            //身份证查询
            if(StringUtils.isNotEmpty(idCard)){
                builder.and(QOverdueDetail.overdueDetail.personalInfo.idCard.eq(idCard));
            }
            //逾期天数查询
            if (StringUtils.isNotEmpty(minOverdueDays) && StringUtils.isNotEmpty(maxOverdueDays)) {
                builder.and(QOverdueDetail.overdueDetail.overdueDays.between(Integer.parseInt(minOverdueDays), Integer.parseInt(maxOverdueDays)));
            }

            Page<OverdueDetail> page = overdueDetailRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/overdueDetailController/getAll");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", ENTITY_NAME, "查询失败")).body(null);
        }
    }

    /**
     * @Description 导出逾期明细列表
     */
    @GetMapping("/exportOverdueDetailList")
    @ApiOperation(value = "导出逾期明细列表", notes = "导出逾期明细列表")
    public ResponseEntity<String> exportChargeOffList(@QuerydslPredicate(root = OverdueDetail.class) Predicate predicate,
                                                      @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                      @RequestParam(required = false) @ApiParam(value = "统计时间") String statisticsTime,
                                                      @RequestParam(required = false) @ApiParam(value = "客户姓名") String name,
                                                      @RequestParam(required = false) @ApiParam(value = "手机号") String mobileNo,
                                                      @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                      @RequestParam(required = false) @ApiParam(value = "最小逾期天数") String minOverdueDays,
                                                      @RequestParam(required = false) @ApiParam(value = "最大逾期天数") String maxOverdueDays,
                                                      @RequestHeader(value = "X-UserToken") String token
    ) {

        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            //统计时间查询
            if(StringUtils.isNotEmpty(statisticsTime)){
                Date date = DateUtil.getDate(statisticsTime,"yyyy-MM-dd");
                builder.and(QOverdueDetail.overdueDetail.statTime.after(DateUtil.getStartTimeOfDay(date)));
                builder.and(QOverdueDetail.overdueDetail.statTime.before(DateUtil.getEndTimeOfDay(date)));
            }
            //客户姓名查询
            if(StringUtils.isNotEmpty(name)){
                builder.and(QOverdueDetail.overdueDetail.clientName.eq(name));
            }
            //手机号查询
            if(StringUtils.isNotEmpty(mobileNo)){
                builder.and(QOverdueDetail.overdueDetail.personalInfo.mobileNo.eq(mobileNo));
            }
            //身份证查询
            if(StringUtils.isNotEmpty(idCard)){
                builder.and(QOverdueDetail.overdueDetail.personalInfo.idCard.eq(idCard));
            }
            //逾期天数查询
            if (StringUtils.isNotEmpty(minOverdueDays) && StringUtils.isNotEmpty(maxOverdueDays)) {
                builder.and(QOverdueDetail.overdueDetail.overdueDays.between(Integer.parseInt(minOverdueDays), Integer.parseInt(maxOverdueDays)));
            }


            Iterable<OverdueDetail> list = IterableUtils.toList(overdueDetailRepository.findAll(builder));

            ResponseEntity<String> str = restTemplate.postForEntity("http://report-service/api/ReportController/exportOverdueDetailList", list, String.class);
            log.debug(str.getBody());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("导出成功", "")).body(str.getBody());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", ENTITY_NAME, "导出逾期明细列表失败")).body(null);
        }

    }

}
