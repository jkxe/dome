package cn.fintecher.pangolin.business.web.out;

import cn.fintecher.pangolin.business.service.out.InputDataApiService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/inputBaseDataController")
@Api(value = "InputBaseDataController", description = "从核心获取案件信息")
public class InputBaseDataController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(InputBaseDataController.class);

    @Autowired
    private InputDataApiService inputDataApiService;

    @PostMapping(value = "/getBaseData")
    @ApiOperation(value = "获取核心推送过来的案件信息", notes = "获取核心推送过来的案件信息")
    public ResponseEntity<Void> getBaseData(){

        inputDataApiService.getData();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("", "")).build();

    }
}
