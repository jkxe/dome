package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/9/24.
 */

@Service
public class ParseExcelService {

    private final Logger logger = LoggerFactory.getLogger(ParseExcelService.class);

    public Sheet getExcelSheets(UploadFile file) {
        try {
            Workbook workbook = getWorkbook(file);
            if (workbook == null) throw new RuntimeException("获取Excel对象错误");
            Sheet sheetAt = workbook.getSheetAt(0);
            return sheetAt;
        } catch (Exception e) {
            throw new RuntimeException("获取Excel对象错误");
        }
    }

    private Workbook getWorkbook(UploadFile file) {
        Workbook workbook = null;
        InputStream inputStream = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.exchange(file.getLocalUrl(), HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);
            String fileType = file.getType();
            if (Constants.EXCEL_TYPE_XLS.equals(fileType)) {
                workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));//支持低版本的Excel文件
            } else if (Constants.EXCEL_TYPE_XLSX.equals(fileType)) {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("获取Excel对象错误");
        } finally {
            closeWorkBook(workbook);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
        }
        return workbook;
    }

    private void closeWorkBook(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void checkExcelHeader(Sheet sheet, int startRow, int startCol, List<String> isNeedTitle) throws Exception {

        Row titleRow = sheet.getRow(startRow);
        int lastCellNum = titleRow.getLastCellNum();
        if (startCol > lastCellNum) {
            throw new RuntimeException("Excel数据模板开始列大于数据总列数");
        }

        List<String> title = new ArrayList<>();
        for (int columnIndex = startCol; columnIndex < titleRow.getLastCellNum(); columnIndex++) {
            Cell cell = titleRow.getCell(columnIndex);
            if (ZWStringUtils.isNotEmpty(cell.getStringCellValue())) {
                title.add(cell.getStringCellValue());
            }
        }

        for (int i = 0; i < isNeedTitle.size(); i++) {
            if (!title.contains(isNeedTitle.get(i))) {
                throw new Exception("标题" + isNeedTitle.get(i) + "不能为空");
            }
        }
    }
}
