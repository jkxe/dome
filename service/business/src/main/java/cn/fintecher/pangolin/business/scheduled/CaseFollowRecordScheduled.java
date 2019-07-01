package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.CaseFollowupRecordRepository;
import cn.fintecher.pangolin.entity.CaseFollowupRecord;
import cn.fintecher.pangolin.entity.QCaseFollowupRecord;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by sun on 2017/9/27.
 */
@Configuration
@EnableScheduling
public class CaseFollowRecordScheduled {

    private static final Logger logger = LoggerFactory.getLogger(CaseFollowRecordScheduled.class);

    @Autowired
    private CaseFollowupRecordRepository caseFollowupRecordRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${pangolin.tempFileDir}")
    private String tempFileDir;
    @Value("${pangolin.UDesk.secret}")
    private String secret;
    @Value("${pangolin.UDesk.callUrl}")
    private String callUrl;

    @Scheduled(cron = "0 0/5 * * * ?")
    private void caseAutoRecoverTask() {
        logger.debug("跟进记录录音更新任务调度开始...");
        List<CaseFollowupRecord> caseFollowupRecords = new ArrayList<>();
        QCaseFollowupRecord qCaseFollowupRecord = QCaseFollowupRecord.caseFollowupRecord;
        Iterable<CaseFollowupRecord> all = caseFollowupRecordRepository.findAll(qCaseFollowupRecord.opUrl.isNull()
                .and(qCaseFollowupRecord.operatorTime.between(ZWDateUtil.getNightTime(-1), ZWDateUtil.getNowDateTime()))
                .and(qCaseFollowupRecord.taskId.isNotNull())
                .and(qCaseFollowupRecord.taskId.isNotEmpty()));
        Iterator<CaseFollowupRecord> iterator = all.iterator();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        while (iterator.hasNext()) {
            try {
                CaseFollowupRecord caseFollowupRecord = iterator.next();

                if (ZWStringUtils.isNotEmpty(caseFollowupRecord.getTaskId())) {
                    String timeStamp = ZWDateUtil.fomratterDate(ZWDateUtil.getNowDateTime(), "yyyyMMddHHmmss");
                    String queryString = "call_id=".concat(caseFollowupRecord.getTaskId()).concat("&timestamp=").concat(timeStamp);
                    String sign = DigestUtils.md5Hex(queryString + "&" + secret);
                    JSONObject jsonObject = new RestTemplate().getForObject(callUrl + "open_api/callcenter/call_log?" + queryString + "&sign=" + sign, JSONObject.class);
                    if (Objects.nonNull(jsonObject.getJSONObject("call_log")) && Objects.equals(jsonObject.get("code"), 1000)) {
                        logger.debug("Result:" + jsonObject.toJSONString());
                        String url = jsonObject.getJSONObject("call_log").getString("record_url");
                        if(StringUtils.isEmpty(url)){
                            continue;
                        }
                        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                        RestTemplate restTemplate1 = new RestTemplate();
//                        ResponseEntity<byte[]> response = restTemplate1.exchange(url, HttpMethod.GET, entity, byte[].class);
//                        String filePath = tempFileDir.concat("/record"+System.currentTimeMillis()+".mp3");
//                        int downloadCount=0;
//                        File tempFile=null;
//                        int retCode=-1;
//                        while(downloadCount<3){
//                            retCode = dowloadFile(filePath, url);
//                            tempFile=new File(filePath);
//                            if(retCode==0&&tempFile.exists()&&tempFile.length()!=0){
//                                break;
//                            }
//                            downloadCount++;
//                        }
//                        if(retCode!=0||!tempFile.exists()||tempFile.length()==0){
//                            continue;
//                        }
//
//                        FileSystemResource resource = new FileSystemResource(tempFile);
//
//                        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//                        param.add("file", resource);
//                        String fileUrl = restTemplate.postForObject("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
                        logger.debug("upload file path:{}", url);

                        caseFollowupRecord.setOpUrl(url);
                        caseFollowupRecord.setStartTime(jsonObject.getJSONObject("call_log").getDate("start_time"));
                        caseFollowupRecord.setConnSecs(jsonObject.getJSONObject("call_log").getInteger("duration"));
                        caseFollowupRecord.setEndTime(jsonObject.getJSONObject("call_log").getDate("end_time"));
                        caseFollowupRecords.add(caseFollowupRecord);
                    }
                }
            } catch (Exception e) {
                logger.error("跟进记录录音更新任务调度错误");
                logger.error(e.getMessage(), e);
            }
        }
        caseFollowupRecordRepository.save(caseFollowupRecords);
        logger.debug("跟进记录录音更新任务调度结束");
    }


    private static int dowloadFile(String filePath, String url) {

        long start = System.currentTimeMillis();
        String token = "12345678901234567890";
        System.out.println("执行命令===curl -o "+ filePath +" \"" + url +"\""
                + " -H \"X-Auth-Token:"+token+"\" -X GET");
        Process process =null;
        int retCode=-1;
        try {
            process = Runtime.getRuntime().exec( "curl -o "+ filePath +" \"" + url +"\""
                    + " -H \"X-Auth-Token:"+token+"\" -X GET");
            retCode = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }
        if(retCode==0) {
            logger.info("下载成功耗时(ms)：" + (System.currentTimeMillis() - start));
        }else{
            logger.info("下载失败耗时(ms)：" + (System.currentTimeMillis() - start));
        }
        return retCode;
    }
}
