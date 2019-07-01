package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.util.List;

/**
 * @author : xiaqun
 * @Description : 修复信息模型
 * @Date : 14:46 2017/7/26
 */

@Data
public class RepairInfoModel {
    private String personalId; //客户信息ID
    private Integer relation; //关系
    private String name; //姓名
    private Integer phoneStatus; //电话状态
    private String phone; //电话号码
    private Integer socialType; //社交帐号类型
    private String socialValue; //社交帐号内容
    private String address; //地址
    private Integer addressStatus; //地址状态
    private Integer type; //地址类型
    private String mail; //邮箱地址
    private String caseId; //案件Id
    private List<String> fileIds; //文件ID
    private String remark; //修改备注
    private String livingProvinceCode;// 省code
    private String livingProvinceName;// 省名称
    private String livingCityCode;// 市code
    private String livingCityName;// 市名
    private String livingAreaCode;// 住宅code
    private String livingAreaName;// 住宅名称
}