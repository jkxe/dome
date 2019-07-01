//package cn.fintecher.pangolin.business.web;
//
//import cn.fintecher.pangolin.business.model.CaseTurnModel;
//import cn.fintecher.pangolin.business.repository.CaseTurnRecordRepository;
//import cn.fintecher.pangolin.business.service.SaveCaseTurnRecordService;
//import cn.fintecher.pangolin.entity.User;
//import cn.fintecher.pangolin.web.HeaderUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Created by 2018/6/26
// */
//@RestController
//@RequestMapping("/api/CaseTurnController")
//@Api(value = "CaseTurnController", description = "回收流转操作")
////public class CaseTurnController extends BaseController {
////    private static final String ENTITY_NAME = "CaseTurnController";
////    private final Logger logger = LoggerFactory.getLogger(CaseTurnController.class);
////
////    @Autowired
////    SaveCaseTurnRecordService saveCaseTurnRecordService;
////
//    @Autowired
//    CaseTurnRecordRepository caseTurnRecordRepository;
//
//    @PostMapping("/addCaseTurnRecord")
//    @ApiOperation(notes = "添加流转记录", value = "添加流转记录")
//    public ResponseEntity recoverCase(@RequestHeader(value = "X-UserToken") String token,
//                                      @RequestBody CaseTurnModel caseTurnModel) {
//        try {
//            User user = getUserByToken(token);
//            saveCaseTurnRecordService.saveCaseTurningRecord(caseTurnModel);
//            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功!", "")).body(null);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
//        }
//    }
//}
