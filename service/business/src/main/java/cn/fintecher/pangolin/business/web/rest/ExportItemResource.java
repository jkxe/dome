package cn.fintecher.pangolin.business.web.rest;

import cn.fintecher.pangolin.business.model.ItemsModel;
import cn.fintecher.pangolin.business.service.ExportItemService;
import cn.fintecher.pangolin.business.service.UserService;
import cn.fintecher.pangolin.business.session.SessionStore;
import cn.fintecher.pangolin.entity.ExportItem;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/9/26.
 */
@RestController
@RequestMapping("/api/exportItemResource")
@Api(description = "内催配置项")
public class ExportItemResource {

    @Inject
    ExportItemService exportItemService;
    @Autowired
    UserService userService;

    @GetMapping("/getExportItems")
    @ApiOperation(value = "查询导出项", notes = "查询导出项")
    public ResponseEntity<ItemsModel> getExportItems(@ApiParam(value = "Token", required = true)
                                                         @RequestParam String token) {
        try {
              User user =userService.getUserByToken(token);
            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.INRUSH.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }
    @GetMapping("/getExportItemsClosed")
    @ApiOperation(value = "查询内催已结案导出项", notes = "查询内催已结案导出项")
    public ResponseEntity<ItemsModel> getExportItemsClosed(@ApiParam(value = "Token", required = true)
                                                     @RequestParam String token) {
        try {
            User user =userService.getUserByToken(token);
            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.INNERCLOSEDFOLLOWUP.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    @GetMapping("/getExportItemsEliminate")
    @ApiOperation(value = "查询内催归C导出项", notes = "查询内催归C导出项")
    public ResponseEntity<ItemsModel> getExportItemsEliminate(@ApiParam(value = "Token", required = true)
                                                           @RequestParam String token) {
        try {
            User user =userService.getUserByToken(token);
            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.INNERRETURNC.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }


    @GetMapping("/getOutsourceFollowUpExportItems")
    @ApiOperation(value = "查询委外跟进记录导出项", notes = "查询委外跟进记录导出项")
    public ResponseEntity<ItemsModel> getOutsourceFollowUpExportItems(@ApiParam(value = "Token", required = true)
                                                     @RequestParam String token) {
        try {
            User user =userService.getUserByToken(token);
            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.OUTSOURCE.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    @GetMapping("/getOutsourceClosedFollowUpExportItems")
    @ApiOperation(value = "查询委外已结案跟进记录导出项", notes = "查询委外已结案跟进记录导出项")
    public ResponseEntity<ItemsModel> getOutsourceClosedFollowUpExportItems(@ApiParam(value = "Token", required = true)
                                                                      @RequestParam String token) {
        try {
            User user =userService.getUserByToken(token);
            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.OUTSOURCECLOSEDFOLLOWUP.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    @GetMapping("/getOutsourceExportItems")
    @ApiOperation(value = "查询委外案件导出项", notes = "查询委外案件导出项")
    public ResponseEntity<ItemsModel> getOutsourceExportItems(@ApiParam(value = "Token", required = true)
                                                              @RequestParam String token) {
        try {
            User user =userService.getUserByToken(token);

            ItemsModel result = exportItemService.getExportItems(user, ExportItem.Category.OUTSOURCEFOLLOWUP.getValue());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }


}
