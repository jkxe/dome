package cn.fintecher.pangolin.business.model;

import lombok.Data;

/**
 * Created by  gaobeibei.
 * Description: 地图模型
 * Date: 2017-08-18
 */
@Data
public class MapModel {
    private String address;   //地址
    private double longitude;  //经度
    private double latitude;   //纬度
}
