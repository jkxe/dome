package cn.fintecher.pangolin.dataimp.service;

import org.apache.http.client.utils.CloneUtils;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author:peishouwen
 * @Desc:
 * @Date:Create in 14:05 2018/6/15
 */
public class SaxSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    Logger logger= LoggerFactory.getLogger(SaxSheetContentsHandler.class);
    private Map<String,String> cellMap=new HashMap<>();
    private List<Map<String,String>> dataListMap;
    private int startRow=0;
    private int startCol=0;
    private int currentCol=0;


    public SaxSheetContentsHandler(List<Map<String,String>> dataListMap,int startRow,int startCol){
        this.dataListMap=dataListMap;
        this.startCol=startCol;
        this.startRow=startRow;
    }

    @Override
    public void startRow(int rowNum) {
        if(rowNum<startRow){
            return;
        }
        cellMap.clear();
    }

    @Override
    public void endRow(int rowNum) {
        if(rowNum<startRow){
            return;
        }
        if(!cellMap.isEmpty()){
            try {
                Map<String,String>  rowMap = (Map<String, String>) CloneUtils.clone(cellMap);
                dataListMap.add(rowMap);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        currentCol=0;
    }

    /**
     *
     * @param cellReference 列名
     * @param formattedValue 单元格值
     * @param comment
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        currentCol=currentCol+1;
        if(currentCol>=startCol){
            cellMap.put( cellReference.replaceAll("[^A-Za-z]","").concat("1"),formattedValue);
        }
        return;

    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        logger.debug("text: {},isHeader: {},tagName : {}",text,isHeader,tagName);
    }
}
