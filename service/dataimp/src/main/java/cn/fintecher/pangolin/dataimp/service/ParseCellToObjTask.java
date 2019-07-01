package cn.fintecher.pangolin.dataimp.service;

import cn.fintecher.pangolin.dataimp.annotation.ExcelAnno;
import cn.fintecher.pangolin.dataimp.entity.DataImportRecord;
import cn.fintecher.pangolin.dataimp.entity.DataInfoExcel;
import cn.fintecher.pangolin.dataimp.repository.DataInfoExcelRepository;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.Snowflake;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Author:peishouwen
 * @Desc: 将Excel对象转化为javaBean
 * @Date:Create in 14:50 2018/6/19
 */
@Service
public class ParseCellToObjTask {
    Logger logger= LoggerFactory.getLogger(ParseCellToObjTask.class);

    @Autowired
    MongoSequenceService mongoSequenceService;
    @Autowired
    DataInfoExcelRepository dataInfoExcelRepository;

    @Async
    public CompletableFuture<List<String>> parseCell(List<Map<String,String>> dataListMap, Class<?> dataClass,Map<String,String> titleMap,int pageNo,int pageSize,
                          DataImportRecord dataImportRecord){
        logger.info("处理第 {} 页数据开始........",pageNo);
        Snowflake snowflake= new Snowflake((int) (System.currentTimeMillis() % 1024));
        List<DataInfoExcel> dataList=new ArrayList<>();
        List<String> errorList=new ArrayList<>();
        try {
            // 创建任务集合
            for(int i=0;i<dataListMap.size();i++){
                Map<String,String> cellMap=dataListMap.get(i);
                Object obj=parseCellMap(cellMap, dataClass,titleMap,errorList,pageNo*pageSize+1);
                dataList.add((DataInfoExcel) obj);
            }
            //保存数据到数据库
            if(errorList.isEmpty()) {
                for(int k=0;k<dataList.size();k++){
                    DataInfoExcel dataInfoExcel=  dataList.get(k);
                    dataInfoExcel.setBatchNumber(dataImportRecord.getBatchNumber());
                    dataInfoExcel.setDataSources(Constants.DataSource.IMPORT.getValue());
                    dataInfoExcel.setPrinCode(dataImportRecord.getPrincipalId());
                    dataInfoExcel.setPrinName(dataImportRecord.getPrincipalName());
                    dataInfoExcel.setOperator(dataImportRecord.getOperator());
                    dataInfoExcel.setOperatorName(dataImportRecord.getOperatorName());
                    dataInfoExcel.setOperatorTime(ZWDateUtil.getNowDateTime());
                    dataInfoExcel.setCompanyCode(dataImportRecord.getCompanyCode());
                    dataInfoExcel.setPaymentStatus("M".concat(String.valueOf(dataInfoExcel.getOverDuePeriods() == null ? "M0" : dataInfoExcel.getOverDuePeriods())));
                    dataInfoExcel.setDelegationDate(dataImportRecord.getDelegationDate());
                    dataInfoExcel.setCloseDate(dataImportRecord.getCloseDate());
                    dataInfoExcel.setCaseNumber(dataImportRecord.getCompanySequence().concat(String.valueOf(snowflake.next())));
                    dataInfoExcel.setRecoverWay(dataImportRecord.getRecoverWay());
                }
                dataInfoExcelRepository.save(dataList);
            }
        }catch (Exception e){
            logger.error("处理第 {} 页数据异常",pageNo,e);
            dataList.clear();
        }
        logger.info("处理第 {} 页数据完成........",pageNo);
        return CompletableFuture.completedFuture(errorList);

    }

    /**
     * 解析每行数据转为对应的实体对象
     * @param cellMap
     * @param dataClass
     */
    private Object parseCellMap( Map<String,String> cellMap, Class<?> dataClass,Map<String,String> titleMap,List<String> errorList,int rowIndex) throws Exception {
        Object   obj = dataClass.newInstance();
        //获取类中所有的字段
        Field[] fields = dataClass.getDeclaredFields();
        for (Field field : fields) {
            //获取标记了ExcelAnno的注解字段
            if (field.isAnnotationPresent(ExcelAnno.class)) {
                ExcelAnno f = field.getAnnotation(ExcelAnno.class);
                //实体中注解的属性名称
                String cellName = f.cellName();
                String index=titleMap.get(cellName);
                if(StringUtils.isNotBlank(index)){
                    String value=cellMap.get(index);
                    //打开实体中私有变量的权限
                    field.setAccessible(true);
                    field.set(obj, fomatValue(field,value,errorList,rowIndex,index));
                }else {
                    //logger.info("Excel数据项中无此列:{}",cellName);
                }
            }
        }
        return obj;
    }

    /**
     * 格式化数据
     * @param field
     * @param cellValue
     * @return
     */
    private Object fomatValue(Field field,String cellValue,List<String> errorList,int rowIndex,String colStr){
        if(StringUtils.isEmpty(cellValue)){
            return null;
        }
        Object obj=null;
        ExcelAnno.FieldType fieldType = field.getAnnotation(ExcelAnno.class).fieldType();
        switch (fieldType) {
            case STRING:
                obj=cellValue;
                break;
            case INTEGER:
                try {
                    obj=Integer.parseInt(cellValue);
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage(),e);
                    errorList.add(createErrorStr( cellValue, "数值", rowIndex, colStr));
                }
                break;
            case DOUBLE:
                try {
                    obj = Double.parseDouble(cellValue);
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage(),e);
                    errorList.add(createErrorStr( cellValue, "数值", rowIndex, colStr));
                }
                break;
            case DATE:
                try {
                    if (cellValue.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
                        obj= ZWDateUtil.getUtilDate(cellValue, "yyyy/MM/dd");
                    } else if (cellValue.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        obj=ZWDateUtil.getUtilDate(cellValue, "yyyy-MM-dd");
                    } else if (cellValue.matches("^\\d{4}\\d{2}\\d{2}")) {
                        obj=ZWDateUtil.getUtilDate(cellValue, "yyyyMMdd");
                    } else if (cellValue.matches("\\d{4}.\\d{1,2}.\\d{1,2}")) {
                        obj=ZWDateUtil.getUtilDate(cellValue, "yyyy.MM.dd");
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                    errorList.add(createErrorStr( cellValue, "日期", rowIndex, colStr));
                }
                break;
            default:
                obj=cellValue;
                break;
        }
        return obj;
    }

    /**
     * 拼装错误信息
     * @param cellValue
     * @param remark
     * @param rowIndex
     * @param colStr
     * @return
     */
    private String createErrorStr(String cellValue,String remark,int rowIndex,String colStr){
        return  "第[".concat(String.valueOf(rowIndex)).concat("]行,第[").concat(colStr).concat("]列的值[").
                concat(cellValue).concat("]转为[").concat(remark).concat("]");
    }
}
