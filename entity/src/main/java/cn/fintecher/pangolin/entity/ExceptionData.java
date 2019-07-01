package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 * @author hanwannan
 * @date 2019/1/16 0016下午 5:27
 */
@Entity
@Table(name = "hy_exception_data")
@Data
public class ExceptionData implements Serializable {

    public static final String EXCEPTION_CODE_PARSE_TECH="00000";//技术解析异常
    public static final String EXCEPTION_CODE_PARSE="10000";//业务解析异常
    public static final String EXCEPTION_CODE_INTODB="20000";//入库异常
    public static final String EXCEPTION_CODE_INTODB_BATCH="20001";//批量入库异常

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @ApiModelProperty(notes = "id主键")
    private Long id;

    @ApiModelProperty(notes = "异常数据类型")
    private Integer dataType;

    @ApiModelProperty(notes = "异常数据id")
    private String dataId;

    @ApiModelProperty(notes = "数据内容")
    private String dataContent;

    @ApiModelProperty(notes = "异常code")
    private String exceptionCode;

    @ApiModelProperty(notes = "异常信息")
    private String exceptionMsg;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum DataType {
        OVERDUE_DETAIL(1121, "逾期明细信息"),
        CUSTOMER_DETAIL(1122, "客户明细信息"),
        CUSTOMER_ACCOUNT(1123, "客户开户信息"),
        CUSTOMER_FILE_ATTACH(1124, "客户文本文件"),
        CUSTOMER_IMG_ATTACH(1125, "客户影像文件"),
        CUSTOMER_RELATION(1126, "客户关联人"),
        CUSTOMER_SOCIAL_PLAT(1127, "客户社交平台"),
        CUSTOMER_AST_OPER_CRDT(1128, "客户资产经营征信信息");
        private Integer value;
        private String remark;

        DataType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }

    public static ExceptionData createInstance(Integer exceptionType, String exceptionId, String dataContent, String exceptionCode, Exception ex){
        ExceptionData exceptionData=new ExceptionData();
        exceptionData.setDataType(exceptionType);
        exceptionData.setDataId(exceptionId);
        exceptionData.setDataContent(dataContent);
        exceptionData.setExceptionCode(exceptionCode);
        exceptionData.setExceptionMsg(ex.getMessage()+":"+getStackTrace(ex));
        exceptionData.setCreateTime(new Date());
        return exceptionData;
    }

    /**
     * 获取详细的异常链信息--精准定位异常位置
     *
     * @param aThrowable
     * @return
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
