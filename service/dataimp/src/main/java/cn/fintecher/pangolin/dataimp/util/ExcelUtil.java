//package cn.fintecher.pangolin.dataimp.util;
//
//import cn.fintecher.pangolin.dataimp.annotation.ExcelAnno;
//import cn.fintecher.pangolin.dataimp.entity.CellError;
//import cn.fintecher.pangolin.dataimp.entity.ExcelSheetObj;
//import cn.fintecher.pangolin.dataimp.entity.RowError;
//import cn.fintecher.pangolin.dataimp.entity.TemplateExcelInfo;
//import cn.fintecher.pangolin.dataimp.model.ColumnError;
//import cn.fintecher.pangolin.entity.file.UploadFile;
//import cn.fintecher.pangolin.entity.util.Constants;
//import cn.fintecher.pangolin.entity.util.IdcardUtils;
//import cn.fintecher.pangolin.entity.util.SymbolReplace;
//import cn.fintecher.pangolin.util.ZWDateUtil;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StopWatch;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.util.*;
//
///**
// * @Author: PeiShouWen
// * @Description: Excel读写操作
// * @Date 11:26 2017/3/3
// */
//public class ExcelUtil {
//    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
//    /**
//     * 解析单sheet页的Excel
//     *
//     * @param file
//     * @param dataClass
//     * @param startRow
//     * @param startCol
//     * @return
//     */
//    public static ExcelSheetObj parseExcelSingle(UploadFile file, Class<?>[] dataClass, int[] startRow, int[] startCol,
//                                                 List<TemplateExcelInfo> templateExcelInfos) {
//        logger.info("线程 {} 开始解析Excel..............................................", Thread.currentThread());
//        StopWatch watch = new StopWatch();
//        watch.start();
//        Workbook workbook = null;
//        ExcelSheetObj excelSheetObj = null;
//        InputStream inputStream = null;
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<byte[]> response = restTemplate.exchange(file.getLocalUrl(), HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
//            byte[] result = response.getBody();
//            inputStream = new ByteArrayInputStream(result);
//            String fileType = file.getType();
//            if (Constants.EXCEL_TYPE_XLS.equals(fileType)) {
//                workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));//支持低版本的Excel文件
//            } else if (Constants.EXCEL_TYPE_XLSX.equals(fileType)) {
//                workbook = new XSSFWorkbook(inputStream);
//            }
//            LinkedHashMap<String, Sheet> sheetLinkedHashMap = getExcelSheets(workbook);
//            Iterator<Map.Entry<String, Sheet>> iterator = sheetLinkedHashMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry entry = iterator.next();
//                String sheetName = (String) entry.getKey();
//                Sheet sheet = (Sheet) entry.getValue();
//                try {
//                    logger.info("开始解析 {} 页数据", sheetName);
//                    excelSheetObj = parseSheet(sheet, startRow[0], startCol[0], dataClass[0], templateExcelInfos);
//                    logger.info("结束解析 {} 页数据", sheetName);
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            closeWorkBook(workbook);
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e1) {
//                    logger.error(e1.getMessage(), e1);
//                }
//            }
//        }
//        watch.stop();
//        logger.info("线程 {} 结束Excel解析,耗时：{} 毫秒", Thread.currentThread(), watch.getTotalTimeMillis());
//        return excelSheetObj;
//    }
//
//
//    /**
//     * 关闭文件流
//     *
//     * @param workbook
//     * @throws Exception
//     */
//    private static void closeWorkBook(Workbook workbook) {
//        if (workbook != null) {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
//
//    /**
//     * 获取Excel中的每个sheet页，
//     * 直接解析第一个sheet也页数据
//     *
//     * @param workbook
//     * @return 返回按Excel实际顺序的sheet对象
//     */
//    public static LinkedHashMap<String, Sheet> getExcelSheets(Workbook workbook) {
//        LinkedHashMap<String, Sheet> excelSheets = new LinkedHashMap<>();
//        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
//            String sheetName = workbook.getSheetName(sheetIndex);
//            Sheet sheet = workbook.getSheetAt(sheetIndex);
//            excelSheets.put(sheetName, sheet);
//            break;
//        }
//        if (excelSheets.isEmpty()) {
//            excelSheets = null;
//        }
//        return excelSheets;
//    }
//
//
//    /**
//     * 解析sheet页数据
//     *
//     * @param sheet
//     * @param startRow
//     * @param startCol
//     * @param dataClass 接收数据的实体对象
//     * @return 返回当前sheet的数据
//     */
//    public static ExcelSheetObj parseSheet(Sheet sheet, int startRow, int startCol, Class<?> dataClass,
//                                           List<TemplateExcelInfo> templateExcelInfos) throws Exception {
//        ExcelSheetObj excelSheetObj = new ExcelSheetObj();  //Excel sheet页对象信息
//        List dataList = new ArrayList();     //sheet页数据
//        List<RowError> sheetErrorList = new ArrayList<>();  //sheet页错误信息
//        String sheetName = sheet.getSheetName();  //sheet名称
//        //获取每个sheet页的头部信息,用于和实体属性匹配(默认模板使用)
//        Map<Integer, String> headerMap = new LinkedHashMap<>();
//        //数据开始列
//        int dataStartRow = 0;
//        //默认数据模板导入
//        if (Objects.isNull(templateExcelInfos)) {
//            dataStartRow = startRow + 1;
//            //读取该sheet页的标题信息
//            Row titleRow = sheet.getRow(startRow);
//            headerMap = parseExcelHeader(startCol, titleRow);
//        } else {
//            //走配置模板
//            dataStartRow = startRow;
//        }
//        //循环解析sheet每行的数据
//        int threadParseNum = 5;
//        int lastRowNum = sheet.getLastRowNum() - dataStartRow; //数据总行数
//        int threadNum = getThreadNum(lastRowNum, threadParseNum); //要启用的线程数
//
//        for (int i = 0; i < threadNum; i++) {
//            //线程开始解析的行
//            int istartNum = dataStartRow;
//            if (i == 0) {
//                istartNum = 1;
//            } else {
//                istartNum = (i * threadParseNum) + 1;
//            }
//            ExcelUtilAsyncTask excelUtilAsyncTask = new ExcelUtilAsyncTask();
//            excelUtilAsyncTask.parseRowDataExcel(istartNum,threadParseNum,startCol,sheet,dataClass,headerMap,dataList,sheetErrorList,templateExcelInfos);
//        }
//        excelSheetObj.setSheetName(sheetName);
//        excelSheetObj.setDataList(dataList);
//        excelSheetObj.setSheetErrorList(sheetErrorList);
//        return excelSheetObj;
//    }
//
//    private static int getThreadNum(int lastRowNum, int rowNum) {
//        int a = lastRowNum / rowNum;
//        int b = lastRowNum % rowNum;
//        if (b == 0) {
//            return a == 0 ? 1 : a;
//        } else {
//            return a == 0 ? 1 : a + 1;
//        }
//    }
//
//    /**
//     * @param dataClass 数据实体
//     * @param dataRow   数据行
//     * @param startCol  开始列
//     * @param headerMap 头部信息
//     * @param sheetName
//     * @param rowIndex  行数
//     * @return 返回实体对象
//     */
//    public static Map<Object, RowError> parseRow(Class<?> dataClass, Row dataRow, int startCol, Map<Integer, String> headerMap, RowError rowError,
//                                                 String sheetName, int rowIndex, List<TemplateExcelInfo> templateExcelInfos) {
//        Map<Object, RowError> map = new LinkedHashMap<>(1);
//        //反射创建实体对象
//        Object obj = null;
//        rowError.setSheetName(sheetName);
//        rowError.setRowIndex(rowIndex);
//        try {
//            obj = dataClass.newInstance();
//            //默认数据模板
//            if (Objects.isNull(templateExcelInfos)) {
//                //循环解析每行中的每个单元格
//                List<ColumnError> columnErrorList = new ArrayList<>();
//                for (int colIndex = startCol; colIndex < dataRow.getLastCellNum(); colIndex++) {
//                    //获取该列对应的头部信息中文
//                    String titleName = headerMap.get(colIndex);
//                    Cell cell = dataRow.getCell(colIndex);
//                    matchFields(dataClass, dataRow, columnErrorList, sheetName, rowIndex, obj, colIndex, titleName, cell);
//                }
//                rowError.setColumnErrorList(columnErrorList);
//            } else {
//                //配置模板
//                for (TemplateExcelInfo templateExcelInfo : templateExcelInfos) {
//                    if (StringUtils.isNotBlank(templateExcelInfo.getRelateName())) {
//                        Cell cell = dataRow.getCell(templateExcelInfo.getCellNum());
//                        if (cell != null && !cell.toString().trim().equals("")) {
//                            //获取类中所有的字段
//                            Field[] fields = dataClass.getDeclaredFields();
//                            for (Field field : fields) {
//                                //实体中的属性名称
//                                String proName = field.getName();
//                                //匹配到实体中相应的字段
//                                if (proName.equals(templateExcelInfo.getRelateName())) {
//                                    //打开实体中私有变量的权限
//                                    field.setAccessible(true);
//                                    //实体中变量赋值
//                                    try {
//                                        field.set(obj, getObj(field.getType(), cell, field));
//                                    } catch (Exception e) {
//                                        String errorMsg = "第[" + (dataRow.getRowNum() + 1) + "]行，字段:[" + templateExcelInfo.getCellName() + "]的数据类型不正确";
//                                        CellError errorObj = new CellError(sheetName, rowIndex, templateExcelInfo.getCellNum(), proName, null, errorMsg, e);
////                                        cellErrors.add(errorObj);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        map.put(obj, rowError);
//        return map;
//    }
//
//    /**
//     * 匹配相应的字段
//     */
//    private static void matchFields(Class<?> dataClass, Row dataRow, List<ColumnError> columnErrorList, String sheetName,
//                                    int rowIndex, Object obj, int colIndex, String titleName, Cell cell) throws Exception {
//        //获取类中所有的字段
//        Field[] fields = dataClass.getDeclaredFields();
//        int fieldCount = 0;
//        for (Field field : fields) {
//            fieldCount++;
//            //获取标记了ExcelAnno的注解字段
//            if (field.isAnnotationPresent(ExcelAnno.class)) {
//                ExcelAnno f = field.getAnnotation(ExcelAnno.class);
//                //实体中注解的属性名称
//                String cellName = f.cellName();
//                if (cellName != null && !cellName.isEmpty()) {
//                    //匹配到实体中相应的字段
//                    if (chineseCompare(cellName, titleName, "UTF-8")) {
//                        ColumnError columnError = new ColumnError();
//                        columnError.setColumnIndex(colIndex + 1);
//                        columnError.setTitleMsg(cellName);
//                        //打开实体中私有变量的权限
//                        field.setAccessible(true);
//                        //实体中变量赋值
//                        try {
//                            // 获取数据或者错误信息
//                            Map<Object, ColumnError> map = validityDataGetFieldValue(field, cell, columnError);
//                            Map.Entry<Object, ColumnError> next = map.entrySet().iterator().next();
//                            field.set(obj, next.getKey());
//                            if (StringUtils.isNotBlank(next.getValue().getErrorMsg())) {
//                                ColumnError value = next.getValue();
//                                columnErrorList.add(value);
//                            }
//                            break;
//                        } catch (Exception e) {
//                            logger.debug(e.getMessage());
//                            throw new RuntimeException(e.getMessage());
//                        }
//                    }
//                } else {
//                    logger.info(Thread.currentThread() + "实体：" + obj.getClass().getSimpleName() + "中的：" + field.getName() + " 未配置cellName属性");
//                    continue;
//                }
//                if (fieldCount == fields.length) {
//                    //标明没有找到匹配的属性字段
//                    logger.info(Thread.currentThread() + "模板中的：" + sheetName + "[" + titleName + "]未与实体：" + obj.getClass().getSimpleName() + " 对应");
//                }
//            }
//        }
//    }
//
//    private static Map<Object, ColumnError> validityDataGetFieldValue(Field field, Cell cell, ColumnError columnError) throws ParseException {
//        Map<Object, ColumnError> map = new HashedMap(1);
//        String cellValue = getCellValue(cell);
//        ExcelAnno.FieldCheck fieldCheck = field.getAnnotation(ExcelAnno.class).fieldCheck();
//        switch (fieldCheck) {
//            case PERSONAL_NAME:
//                if (StringUtils.equalsIgnoreCase(cellValue, "")) {
//                    columnError.setErrorMsg("客户姓名为空");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                }
//                map.put(cellValue, columnError);
//                break;
//            case IDCARD:
//                if (StringUtils.equalsIgnoreCase(cellValue, "")) {
//                    columnError.setErrorMsg("身份证号为空");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                }
//                if (!IdcardUtils.validateCard(cellValue)) {
//                    columnError.setErrorMsg("身份证号无效");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                }
//                map.put(cellValue, columnError);
//                break;
//            case PRODUCT_NAME:
//                if (StringUtils.equalsIgnoreCase(cellValue, "")) {
//                    columnError.setErrorMsg("产品名称为空");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                }
//                map.put(cellValue, columnError);
//                break;
//            case CASE_AMOUNT:
//                if (StringUtils.equalsIgnoreCase(cellValue, "")) {
//                    columnError.setErrorMsg("案件金额为空");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(0.00, columnError);
//                    break;
//                }
//                Double dou = 0D;
//                try {
//                    dou = Double.parseDouble(cellValue);
//                } catch (NumberFormatException e) {
//                    columnError.setErrorMsg("数据类型错误");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(dou, columnError);
//                    break;
//                }
//                map.put(dou, columnError);
//                break;
//            case PHONE_NUMBER:
//                String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
//                if (cellValue.length() >= 16) {
//                    columnError.setErrorMsg("电话号码长度过长");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                } else if (cellValue !="" && !cellValue.matches(regExp)) {
//                    columnError.setErrorMsg("电话号码不合规");
//                    columnError.setErrorLevel(ColumnError.ErrorLevel.PROMPT.getValue());
//                    map.put(cellValue, columnError);
//                    break;
//                } else {
//                    map.put(cellValue, columnError);
//                    break;
//                }
//            case NONE:
//                ExcelAnno.FieldType fieldType = field.getAnnotation(ExcelAnno.class).fieldType();
//                switch (fieldType) {
//                    case STRING:
//                        map.put(cellValue, columnError);
//                        break;
//                    case INTEGER:
//                        Integer inte = 0;
//                        try {
//                            inte = Integer.parseInt(cellValue);
//                        } catch (NumberFormatException e) {
//                            columnError.setErrorMsg("数据类型错误");
//                            columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                            map.put(inte, columnError);
//                            break;
//                        }
//                        map.put(inte, columnError);
//                        break;
//                    case DOUBLE:
//                        Double dou1 = 0D;
//                        try {
//                            dou = Double.parseDouble(cellValue);
//                        } catch (NumberFormatException e) {
//                            columnError.setErrorMsg("数据类型错误");
//                            columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                            map.put(dou1, columnError);
//                            break;
//                        }
//                        map.put(dou, columnError);
//                        break;
//                    case DATE:
//                        if (cellValue.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
//                            map.put(ZWDateUtil.getUtilDate(cellValue, "yyyy/MM/dd"), columnError);
//                            break;
//                        } else if (cellValue.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                            map.put(ZWDateUtil.getUtilDate(cellValue, "yyyy-MM-dd"), columnError);
//                            break;
//                        } else if (cellValue.matches("^\\d{4}\\d{2}\\d{2}")) {
//                            map.put(ZWDateUtil.getUtilDate(cellValue, "yyyyMMdd"), columnError);
//                            break;
//                        } else if (cellValue.matches("\\d{4}.\\d{1,2}.\\d{1,2}")) {
//                            map.put(ZWDateUtil.getUtilDate(cellValue, "yyyy.MM.dd"), columnError);
//                            break;
//                        } else {
//                            columnError.setErrorMsg("日期格式错误");
//                            columnError.setErrorLevel(ColumnError.ErrorLevel.FORCE.getValue());
//                            map.put(ZWDateUtil.getUtilDate("1970-01-01", "yyyy-MM-dd"), columnError);
//                            break;
//                        }
//                }
//        }
//        return map;
//
//    }
//
//    /**
//     * Excel 中将数字转化为字符 如 1 转为 A
//     *
//     * @param columnIndex
//     * @return
//     */
//    private static String excelColIndexToStr(int columnIndex) {
//        if (columnIndex <= 0) {
//            return null;
//        }
//        String columnStr = "";
//        columnIndex--;
//        do {
//            if (columnStr.length() > 0) {
//                columnIndex--;
//            }
//            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
//            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
//        } while (columnIndex > 0);
//        return columnStr;
//    }
//
//    /**
//     * Excel 中将字母转化为数字 如A -> 1
//     *
//     * @param column
//     * @return
//     */
//    public static Integer excelColStrToNum(String column) {
//        int num = 0;
//        int result = 0;
//        int length = column.length();
//        for (int i = 0; i < length; i++) {
//            char ch = column.charAt(length - i - 1);
//            num = (int) (ch - 'A' + 1);
//            num *= Math.pow(26, i);
//            result += num;
//        }
//        return result;
//    }
//
//
//    /**
//     * 判断Excel中是否为空行
//     *
//     * @param dataRow
//     * @return
//     */
//    public static boolean isBlankRow(Row dataRow) {
//        if (Objects.nonNull(dataRow)) {
//            for (int i = dataRow.getFirstCellNum(); i < dataRow.getLastCellNum(); i++) {
//                Cell cell = dataRow.getCell(i);
//                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
//                    return false;
//            }
//            return true;
//        }
//        return true;
//    }
//
//    /**
//     * @param clazz 实体中属性字段的类型
//     * @param cell  Excel中的单元格
//     * @return 返回单元格对应的实体
//     * @throws Exception
//     */
//    public static Object getObj(Class clazz, Cell cell, Field field) throws Exception {
//        if (cell == null) {
//            return null;
//        }
//        String cellValue = getCellValue(cell);
//        // 数据类型匹配
//        if ("java.util.Date".equalsIgnoreCase(clazz.getName())) {
//            if (cellValue.matches("\\d{4}/\\d{1,2}/\\d{1,2}"))
//                return ZWDateUtil.getUtilDate(cellValue, "yyyy/MM/dd");
//            else if (cellValue.matches("\\d{4}-\\d{2}-\\d{2}"))
//                return ZWDateUtil.getUtilDate(cellValue, "yyyy-MM-dd");
//            else if (cellValue.matches("^\\d{4}\\d{2}\\d{2}"))
//                return ZWDateUtil.getUtilDate(cellValue, "yyyyMMdd");
//            else if (cellValue.matches("\\d{4}.\\d{1,2}.\\d{1,2}"))
//                return ZWDateUtil.getUtilDate(cellValue, "yyyy.MM.dd");
//            else
//                throw new RuntimeException("日期格式不合适");
//        } else if ("java.lang.Integer".equalsIgnoreCase(clazz.getName())) {
//            return Integer.parseInt(cellValue);
//        } else if ("java.lang.Short".equalsIgnoreCase(clazz.getName())) {
//            return Short.parseShort(cellValue);
//        } else if ("java.lang.Double".equalsIgnoreCase(clazz.getName())) {
//            BigDecimal big = new BigDecimal(cellValue);
//            return big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        } else if ("java.math.BigDecimal".equalsIgnoreCase(clazz.getName())) {
//            return new BigDecimal(cellValue).setScale(2, BigDecimal.ROUND_HALF_UP);
//        } else {
//            Constructor con = clazz.getConstructor(String.class);
//            if (field.isAnnotationPresent(SymbolReplace.class)) {
//                return con.newInstance(filterEmoji(cellValue, ""));
//            }
//            return con.newInstance(cellValue);
//        }
//    }
//
//    /**
//     * emoji表情替换
//     *
//     * @param source  原字符串
//     * @param slipStr emoji表情替换成的字符串
//     * @return 过滤后的字符串
//     */
//    private static String filterEmoji(String source, String slipStr) {
//        if (StringUtils.isNotBlank(source)) {
//            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
//        } else {
//            return source;
//        }
//    }
//
//
//    /**
//     * 解析每个Sheet页的头部信息
//     *
//     * @param startCol 开始列
//     * @param titleRow 头部信息所在的行
//     * @return
//     */
//    public static Map<Integer, String> parseExcelHeader(int startCol, Row titleRow) throws Exception {
//        try {
//            int lastCellNum = titleRow.getLastCellNum();
//            if (startCol > lastCellNum) {
//                throw new Exception("Excel数据模板开始列大于数据总列数");
//            }
//            Map<Integer, String> headerMap = new LinkedHashMap<>();
//            for (int columnIndex = startCol; columnIndex < titleRow.getLastCellNum(); columnIndex++) {
//                Cell cell = titleRow.getCell(columnIndex);
//                headerMap.put(columnIndex, cell.getStringCellValue());
//            }
//            return headerMap;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new Exception("解析Excel表头信息失败");
//        }
//    }
//
//    /**
//     * 中文字符串比较
//     *
//     * @param str1
//     * @param str2
//     * @param engCode 字符编码
//     * @return
//     * @throws Exception
//     */
//    private static Boolean chineseCompare(String str1, String str2, String engCode) throws Exception {
//        String tmpStr1 = new String(str1.getBytes(engCode));
//        String tmpStr2 = new String(str2.getBytes(engCode));
//        if (tmpStr1.equals(tmpStr2)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 获取单元格的值
//     *
//     * @param cell
//     * @return
//     */
//    public static String getCellValue(Cell cell) {
//        if (cell == null) {
//            return null;
//        }
//        String cellValue = null;
//        //根据CellTYpe动态获取Excel中的值
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_BLANK:
//                cellValue = "";
//                break;
//            case Cell.CELL_TYPE_ERROR:
//                cellValue = "";
//                break;
//            case Cell.CELL_TYPE_BOOLEAN:
//                cellValue = String.valueOf(cell.getBooleanCellValue());
//                break;
//            case Cell.CELL_TYPE_NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    cellValue = String.valueOf(ZWDateUtil.fomratterDate(cell.getDateCellValue(), Constants.DATE_FORMAT));
//                } else {
//                    DecimalFormat df = new DecimalFormat("#.#########");
//                    cellValue = df.format(cell.getNumericCellValue());
//                }
//                break;
//            case Cell.CELL_TYPE_FORMULA:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    cellValue = String.valueOf(ZWDateUtil.fomratterDate(cell.getDateCellValue(), Constants.DATE_FORMAT));
//                } else {
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                }
//                break;
//            case Cell.CELL_TYPE_STRING:
//                cellValue = StringUtils.trim(cell.getStringCellValue());
//                break;
//            default:
//                break;
//        }
//        return cellValue;
//    }
//
//    /**
//     * 生成Excel
//     *
//     * @param dataList  数据List
//     * @param titleList 模板头文件
//     * @param proNames  实体属性list
//     * @param startRow  开始行
//     * @param startCol  开始列
//     * @return
//     * @throws Exception
//     */
//    public static void createExcel(Workbook workbook, Sheet sheet, List dataList, String[] titleList, String[] proNames, int startRow, int startCol) throws Exception {
//        CellStyle headStyle = workbook.createCellStyle();
//        CellStyle BodyStyle = workbook.createCellStyle();
//        headStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
//        //设置头文件字体
//        Font fontTitle = workbook.createFont();
//        fontTitle.setFontName("黑体");
//        fontTitle.setFontHeightInPoints((short) 12);//设置字体大小
//        headStyle.setFont(fontTitle);
//        Font fontBody = workbook.createFont();
//        fontBody.setFontName("宋体");
//        fontBody.setFontHeightInPoints((short) 11);
//        BodyStyle.setFont(fontBody);
//
//        //写入头文件
//        if (Objects.nonNull(titleList)) {
//            Row row = sheet.createRow((short) startRow);
//            Cell cell = null;
//            for (int j = 0; j < titleList.length; j++) {
//                cell = row.createCell((short) (startCol + j));
//                cell.setCellValue(titleList[j]);
//                cell.setCellStyle(headStyle);
//            }
//        }
//        if (!dataList.isEmpty()) {
//            int irowNum = startRow + 1;
//            for (int i = 0; i < dataList.size(); i++) {
//                Object obj = dataList.get(i);
//                Row row = sheet.createRow(irowNum + i);
//                for (int k = 0; k < proNames.length; k++) {
//                    String exportItem = proNames[k];
//                    String valueStr = null;
//                    if ((getProValue(exportItem, obj)) instanceof Date) {
//                        valueStr = ZWDateUtil.fomratterDate((Date) getProValue(exportItem, obj), Constants.DATE_FORMAT);
//                    } else if ((getProValue(exportItem, obj)) instanceof Integer) {
//                        valueStr = ((Integer) getProValue(exportItem, obj)).toString();
//                    } else if ((getProValue(exportItem, obj)) instanceof Double) {
//                        valueStr = ((Double) getProValue(exportItem, obj)).toString();
//                    } else {
//                        valueStr = (String) getProValue(exportItem, obj);
//                    }
//                    Cell cell = row.createCell(startCol + k, CellType.STRING);
//                    cell.setCellValue(valueStr);
//                    cell.setCellStyle(BodyStyle);
//                }
//            }
//        }
//    }
//
//    public static Object getProValue(String exportItem, Object obj) {
//        if (obj instanceof HashMap) {
//            return ((HashMap) obj).get(exportItem);
//        } else {
//            String firstLetter = exportItem.substring(0, 1).toUpperCase();
//            String getMethodName = "get" + firstLetter + exportItem.substring(1);
//            Method getMethod = null;
//            try {
//                getMethod = obj.getClass().getMethod(getMethodName, new Class[]{});
//                return getMethod.invoke(obj, new Object[]{});
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//}
