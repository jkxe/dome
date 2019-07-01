package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 车辆信息
 * @author liaosq23298
 * @version $Id: UserVehicleInfoandSourcePojo.java, v 0.1 Nov 22, 2017 3:17:35 PM liaosq23298 Exp $
 */
public class UserVehicleInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = 1487429891261542246L;
    //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //车牌号
    private String plateNumber;
    //车辆识别代码
    private String vehicleIdentifyCode;
    //车辆品牌
    private String vehicleBrand;
    //车辆型号
    private String vehicleTypeSpecification;
    //购买时间 
    private String purchaseTime;
    //购买价格
    private String purchasePrice;
    //车辆所有人
    private String vehicleOwner;
    //发动机号码
    private String engineNumber;
    //是否为过户车辆
    private String transferFlag;
    //资源来源
    private EnumResourceSource resourceSource;
    //车架号
    private String frameNumber;
    //车主
    private String carOwner;
    //初次登记日期
    private String initRegistDate;
    //车辆颜色
    private String vehicleColor;
    //当前评估价
    private String evaluationPrice;
    //里程数
    private String mileage;
    //是否营运
    private boolean isOperation;
    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }
    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Getter method for property <tt>plateNumber</tt>.
     * 
     * @return property value of plateNumber
     */
    public String getPlateNumber() {
        return plateNumber;
    }
    /**
     * Setter method for property <tt>plateNumber</tt>.
     * 
     * @param plateNumber value to be assigned to property plateNumber
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    /**
     * Getter method for property <tt>vehicleIdentifyCode</tt>.
     * 
     * @return property value of vehicleIdentifyCode
     */
    public String getVehicleIdentifyCode() {
        return vehicleIdentifyCode;
    }
    /**
     * Setter method for property <tt>vehicleIdentifyCode</tt>.
     * 
     * @param vehicleIdentifyCode value to be assigned to property vehicleIdentifyCode
     */
    public void setVehicleIdentifyCode(String vehicleIdentifyCode) {
        this.vehicleIdentifyCode = vehicleIdentifyCode;
    }
    /**
     * Getter method for property <tt>vehicleBrand</tt>.
     * 
     * @return property value of vehicleBrand
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }
    /**
     * Setter method for property <tt>vehicleBrand</tt>.
     * 
     * @param vehicleBrand value to be assigned to property vehicleBrand
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }
    /**
     * Getter method for property <tt>vehicleTypeSpecification</tt>.
     * 
     * @return property value of vehicleTypeSpecification
     */
    public String getVehicleTypeSpecification() {
        return vehicleTypeSpecification;
    }
    /**
     * Setter method for property <tt>vehicleTypeSpecification</tt>.
     * 
     * @param vehicleTypeSpecification value to be assigned to property vehicleTypeSpecification
     */
    public void setVehicleTypeSpecification(String vehicleTypeSpecification) {
        this.vehicleTypeSpecification = vehicleTypeSpecification;
    }
    /**
     * Getter method for property <tt>purchaseTime</tt>.
     * 
     * @return property value of purchaseTime
     */
    public String getPurchaseTime() {
        return purchaseTime;
    }
    /**
     * Setter method for property <tt>purchaseTime</tt>.
     * 
     * @param purchaseTime value to be assigned to property purchaseTime
     */
    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    /**
     * Getter method for property <tt>purchasePrice</tt>.
     * 
     * @return property value of purchasePrice
     */
    public String getPurchasePrice() {
        return purchasePrice;
    }
    /**
     * Setter method for property <tt>purchasePrice</tt>.
     * 
     * @param purchasePrice value to be assigned to property purchasePrice
     */
    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    /**
     * Getter method for property <tt>vehicleOwner</tt>.
     * 
     * @return property value of vehicleOwner
     */
    public String getVehicleOwner() {
        return vehicleOwner;
    }
    /**
     * Setter method for property <tt>vehicleOwner</tt>.
     * 
     * @param vehicleOwner value to be assigned to property vehicleOwner
     */
    public void setVehicleOwner(String vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }
    /**
     * Getter method for property <tt>engineNumber</tt>.
     * 
     * @return property value of engineNumber
     */
    public String getEngineNumber() {
        return engineNumber;
    }
    /**
     * Setter method for property <tt>engineNumber</tt>.
     * 
     * @param engineNumber value to be assigned to property engineNumber
     */
    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }
    /**
     * Getter method for property <tt>transferFlag</tt>.
     * 
     * @return property value of transferFlag
     */
    public String getTransferFlag() {
        return transferFlag;
    }
    /**
     * Setter method for property <tt>transferFlag</tt>.
     * 
     * @param transferFlag value to be assigned to property transferFlag
     */
    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }
    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }
    /**
     * Getter method for property <tt>frameNumber</tt>.
     * 
     * @return property value of frameNumber
     */
    public String getFrameNumber() {
        return frameNumber;
    }
    /**
     * Setter method for property <tt>frameNumber</tt>.
     * 
     * @param frameNumber value to be assigned to property frameNumber
     */
    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }
    /**
     * Getter method for property <tt>carOwner</tt>.
     * 
     * @return property value of carOwner
     */
    public String getCarOwner() {
        return carOwner;
    }
    /**
     * Setter method for property <tt>carOwner</tt>.
     * 
     * @param carOwner value to be assigned to property carOwner
     */
    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }
    /**
     * Getter method for property <tt>initRegistDate</tt>.
     * 
     * @return property value of initRegistDate
     */
    public String getInitRegistDate() {
        return initRegistDate;
    }
    /**
     * Setter method for property <tt>initRegistDate</tt>.
     * 
     * @param initRegistDate value to be assigned to property initRegistDate
     */
    public void setInitRegistDate(String initRegistDate) {
        this.initRegistDate = initRegistDate;
    }
    /**
     * Getter method for property <tt>vehicleColor</tt>.
     * 
     * @return property value of vehicleColor
     */
    public String getVehicleColor() {
        return vehicleColor;
    }
    /**
     * Setter method for property <tt>vehicleColor</tt>.
     * 
     * @param vehicleColor value to be assigned to property vehicleColor
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }
    /**
     * Getter method for property <tt>evaluationPrice</tt>.
     * 
     * @return property value of evaluationPrice
     */
    public String getEvaluationPrice() {
        return evaluationPrice;
    }
    /**
     * Setter method for property <tt>evaluationPrice</tt>.
     * 
     * @param evaluationPrice value to be assigned to property evaluationPrice
     */
    public void setEvaluationPrice(String evaluationPrice) {
        this.evaluationPrice = evaluationPrice;
    }
    /**
     * Getter method for property <tt>mileage</tt>.
     * 
     * @return property value of mileage
     */
    public String getMileage() {
        return mileage;
    }
    /**
     * Setter method for property <tt>mileage</tt>.
     * 
     * @param mileage value to be assigned to property mileage
     */
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    /**
     * Getter method for property <tt>isOperation</tt>.
     * 
     * @return property value of isOperation
     */
    public boolean isOperation() {
        return isOperation;
    }
    /**
     * Setter method for property <tt>isOperation</tt>.
     * 
     * @param isOperation value to be assigned to property isOperation
     */
    public void setOperation(boolean isOperation) {
        this.isOperation = isOperation;
    }
}
