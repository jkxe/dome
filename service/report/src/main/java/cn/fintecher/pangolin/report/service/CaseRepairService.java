package cn.fintecher.pangolin.report.service;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.mapper.CaseInfoMapper;
import cn.fintecher.pangolin.report.model.CaseInfoModel;
import cn.fintecher.pangolin.report.model.CaseRepairParams;
import cn.fintecher.pangolin.report.util.ExcelExportHelper;
import com.hsjry.lang.common.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 18:07 2017/8/1
 */
@Service("caseRepairService")
public class CaseRepairService {
    final Logger log = LoggerFactory.getLogger(CaseRepairService.class);

    @Inject
    RestTemplate restTemplate;

    @Autowired
     CaseInfoMapper  caseInfoMapper;



    /**
     * 获取导出案件跟踪数据
     *
     * @param
     * @return
     */
    public String queryExportReapair(CaseRepairParams caseRepairParams,User  user) {
        if(caseRepairParams.getOperatorTime() == null){
            caseRepairParams.setOperatorTime(null);
        }else{
        Date date = DateUtil.getDate(caseRepairParams.getOperatorTime(), "yyyy-MM-dd");
        String date1 = DateUtil.getDate(date, "yyyy-MM-dd");
        caseRepairParams.setOperatorTime(date1);
        }
        List<CaseInfoModel> list = caseInfoMapper.getAllRepairingCase(

                StringUtils.trim(caseRepairParams.getPersonalName()),// 客户姓名
                StringUtils.trim(caseRepairParams.getMobileNo()),// 客户手机号
                StringUtils.trim(caseRepairParams.getCaseNumber()),// 案件编号
                StringUtils.trim(caseRepairParams.getLoanInvoiceNumber()),// 借据号
                StringUtils.trim(caseRepairParams.getIdCard()),//  身份证
                caseRepairParams.getOverduePeriods(),
                caseRepairParams.getOperatorTime(),
                //   caseInfoConditionParams.getOverdueMaxAmt(),
                //   caseInfoConditionParams.getOverdueMinAmt(),
                caseRepairParams.getOverMaxDay(),//  预期最大天数
                caseRepairParams.getOverMinDay(),// 预期最小天数
                //    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                caseRepairParams.getRepairStatus(),
                caseRepairParams.getOverdueCount(),
                user.getDepartment().getCode(),
                user.getManager(),
                user.getId(),
                user.getCompanyCode());
        if (list == null || list.size() == 0) {
            throw new RuntimeException("没有数据");
        }
        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        Map<String, String> headMap = new LinkedHashMap<>(); //excel头
        List<Map<String, Object>> dataList = new ArrayList<>();
        headMap.put("caseNumber", "案件编号");
        headMap.put("loanInvoiceNumber", "借据号");
        headMap.put("personalName", "客户姓名");
        headMap.put("mobileNo", "手机号");
        headMap.put("idCard", "身份证号");
        headMap.put("seriesName", "产品类型");
        headMap.put("productName", "产品名称");
        headMap.put("overduePeriods","逾期期数");
        headMap.put("overdueDays", "逾期天数");
        headMap.put("accountBalance", "账户余额");
        headMap.put("overdueAmount", "逾期金额");
        headMap.put("overdueCount", "逾期次数");
        headMap.put("repairStatus", "修复状态");

        try {
            Map<String, Object> map;
            DecimalFormat format = new DecimalFormat("0.00");
            for (CaseInfoModel caseInfoModel : list) {
                map = new LinkedHashMap<>();
                map.put("caseNumber", (Objects.isNull(caseInfoModel.getCaseNumber()) ? "" : caseInfoModel.getCaseNumber()));
                map.put("loanInvoiceNumber", (Objects.isNull(caseInfoModel.getLoanInvoiceNumber()) ? "" : caseInfoModel.getLoanInvoiceNumber()));
                map.put("personalName", (Objects.isNull(caseInfoModel.getPersonalName()) ? "" : caseInfoModel.getPersonalName()));
                map.put("mobileNo", (Objects.isNull(caseInfoModel.getMobileNo()) ? "" : caseInfoModel.getMobileNo()));
                map.put("idCard", (Objects.isNull(caseInfoModel.getIdCard()) ? "" : caseInfoModel.getIdCard()));
                map.put("seriesName", (Objects.isNull(caseInfoModel.getSeriesName()) ? "" : caseInfoModel.getSeriesName()));
                map.put("productName", (Objects.isNull(caseInfoModel.getProductName()) ? "" : caseInfoModel.getProductName()));
                map.put("overduePeriods",(Objects.isNull(caseInfoModel.getPayStatus()) ? "" : caseInfoModel.getPayStatus()));
                map.put("overdueDays", (Objects.isNull(caseInfoModel.getOverdueDays()) ? "" : caseInfoModel.getOverdueDays()));
                map.put("overdueAmount", (Objects.isNull(caseInfoModel.getOverdueAmount()) ? "" :format.format(new BigDecimal(caseInfoModel.getOverdueAmount()))));
                map.put("accountBalance", (Objects.isNull(caseInfoModel.getAccountBalance()) ? "" : format.format(caseInfoModel.getAccountBalance())));
                map.put("overdueCount", (Objects.isNull(caseInfoModel.getOverdueCount()) ? "" : caseInfoModel.getOverdueCount()));
                if(Objects.isNull(caseInfoModel.getRepairStatus())){
                    map.put("repairStatus",null);
                }else{
                    if(caseInfoModel.getRepairStatus()==187){
                        map.put("repairStatus", "待修复");
                    }
                    if(caseInfoModel.getRepairStatus()==188){
                        map.put("repairStatus", "已修复");
                    }
                    if(caseInfoModel.getRepairStatus()==189){
                        map.put("repairStatus", "已分配");
                    }
                }
                dataList.add(map);
            }

            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("信修案件");
            ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "信修案件.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            return restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class).getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

}
