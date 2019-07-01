package cn.fintecher.pangolin.common.web;

import cn.fintecher.pangolin.common.oss.AliyunCloudStorageService;
import cn.fintecher.pangolin.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Api(value = "云存储", description = "云储存")
@RequestMapping("/api/ossController")
@RestController
public class OSSFileController {
    @Value("${pangolin.fdd.url}")
    private String fddUrl;
    @Autowired
    AliyunCloudStorageService aliyunCloudStorageService;
    @Autowired
    RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(OSSFileController.class);

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载文件", notes = "下载文件")
    public ResponseEntity<HashMap> download(@RequestParam String objectName) {
        logger.debug("下载文件：{}", objectName);
        String url = aliyunCloudStorageService.get(objectName);
        HashMap map=new HashMap<String,String>();
        map.put("url", url);
        return ResponseEntity.ok(map);
    }

    @RequestMapping(value = "/fdd", method = RequestMethod.GET)
    @ApiOperation(value = "获取法大大文件地址", notes = "获取法大大文件地址")
    public ResponseEntity<HashMap> fdd(@RequestParam String fddId) {
        logger.debug("下载文件：{}", fddId);
        HashMap map = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", fddId);
        try {
            String result = HttpUtil.doGet(fddUrl, params);
            JSONObject obj = JSONObject.parseObject(result);
//        JSONObject obj = restTemplate.getForObject(fddUrl, JSONObject.class, params);
//        String result=restTemplate.getForObject(fddUrl+fddId,String.class);
//        JSONObject obj = JSONObject.parseObject(result);
            map.put("url", obj.getString("msg"));
            map.put("code", obj.get("code"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }


}
