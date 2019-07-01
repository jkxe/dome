package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-07-09-9:41
 */
@Entity
@Table(name = "data_dict")
@Data
@ApiModel(value = "dataDict", description = "data_dict")
public class DataDict {
    private static final long serialVersionUID = -5643850075856127202L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String typeCode;
    private String code;
    private String name;
    private Integer sort;

    public enum TypeCode {
        EDUCATION("1001", "教育程度"),
        MARRAGE("1020", "婚姻状况"),
        PROFESSIONAL("1030", "职业"),
        INDUSTRY("1040", "行业"),
        POSITION("1050", "职务"),
        UNIT_PROPERTY("1060", "工作单位性质"),
        CERTIFICATE_TYPE("1070", "证件类型"),
        PERSONAL_RELATION("1080", "关联人关系");
        private String value;
        private String remark;

        TypeCode(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public String getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }
}
