package cn.fintecher.pangolin.dataimp.service;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author:peishouwen
 * @Desc: SAX EventModel方式解析Excel
 * @Date:Create in 11:29 2018/6/15
 */
@Service
public class SaxParseExcelService  {
     static Logger logger=LoggerFactory.getLogger(SaxParseExcelService.class);


    public void parseExcel(InputStream in,SaxSheetContentsHandler saxSheetContentsHandler){
        OPCPackage pkg = null;
        InputStream sheetInputStream = null;

        try {
            pkg = OPCPackage.open(in);
            XSSFReader xssfReader = new XSSFReader(pkg);

            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            sheetInputStream = xssfReader.getSheetsData().next();

            processSheet(styles, strings, sheetInputStream,saxSheetContentsHandler);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            if(sheetInputStream != null){
                try {
                    sheetInputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if(pkg != null){
                try {
                    pkg.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream,SaxSheetContentsHandler saxSheetContentsHandler)
            throws SAXException, ParserConfigurationException, IOException{
        XMLReader sheetParser = SAXHelper.newXMLReader();
        sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, saxSheetContentsHandler, false));
        sheetParser.parse(new InputSource(sheetInputStream));
    }

    public static void main(String[] args) throws FileNotFoundException {
/*        List<String> isNeedTitle = new ArrayList<>();
            isNeedTitle.add("客户姓名");
            isNeedTitle.add("身份证号");
            isNeedTitle.add("逾期总金额(元)");

        StopWatch watch = new StopWatch();
        watch.start();
        List<Map<String,String>> dataListMap=new ArrayList<>();
        File file=new File("D:\\【大数据】导入案件模板.xlsx");
        new SaxParseExcelService().parseExcel(new FileInputStream(file),new SaxSheetContentsHandler(dataListMap,0,0));
        System.out.println(dataListMap.get(0).containsValue(isNeedTitle));
        watch.stop();
        logger.debug("耗时 {},数据 {} ",watch.getTotalTimeMillis(),dataListMap.size());*/
        String index="AKKKKK11";
        System.out.println(10001%5000);

    }




}
