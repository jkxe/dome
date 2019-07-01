package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.model.CaseInfoParams;
import cn.fintecher.pangolin.report.service.CaseInfoService;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: wangzhao
 * @Description:
 * @Date 17:59 2017/8/1
 */
@RestController
@RequestMapping("/api/ExportCaseInfoController")
@Api(description = "案件查找导出案件信息")
public class ExportCaseInfoController extends BaseController {

    private static final String ENTITY_NAME = "ExportCaseInfoController";
    private final Logger log = LoggerFactory.getLogger(ExportFollowupController.class);
    @Autowired
    private CaseInfoService caseInfoService;

    @PostMapping(value = "/exportCaseInfo")
    @ApiOperation(value = "案件查找导出案件信息", notes = "案件查找导出案件信息")
    public ResponseEntity<Map<String, String>> exportCaseInfo(@RequestBody CaseInfoParams caseInfo,
                                                              @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.nonNull(user.getCompanyCode())) {
                caseInfo.setCompanyCode(user.getCompanyCode());
            }
            String Upurl = caseInfoService.queryExportCaseInfo(caseInfo);
            Map<String, String> map = new HashMap<>();
            map.put("body", Upurl);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("导出成功", ENTITY_NAME)).body(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "ExportCaseInfoController", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/downloadLetter")
    @ApiOperation(value = "下载信函催收word", notes = "下载信函催收word")
    public String downloadLetter(@RequestBody Map map) {
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;
        File file = null;
        try {
            String html = (String) map.get("html");
            byte[] b = html.getBytes("utf-8");
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(b);//将字节数组包装到流中
//        POIFSFileSystem poifs = new POIFSFileSystem();
//        DirectoryEntry directory = poifs.getRoot();
//        DocumentEntry documentEntry = directory.createDocument("WordDocument", inputStream);
//        //输出文件
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("application/msword");//导出word格式
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//        String str = sdf.format(new Date());
//        response.addHeader("Content-Disposition", "attachment;filename="+str+".doc");
//
//
//        FileOutputStream ostream = new FileOutputStream("F:\\111.doc");
            String resultFilePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "letter.doc");
            file = new File(resultFilePath);
            output = new FileOutputStream(file);
            bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(b);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("下载信函催收word异常:{}", e);
        } finally {
            try {
                if (bufferedOutput != null)
                    bufferedOutput.close();
                if (output != null)
                    output.close();
            } catch (IOException e) {
                log.error("下载信函催收word异常:{}", e);
            }
        }
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
        return responseEntity.getBody();
    }

}
