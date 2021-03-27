package com.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2020-10-21
 * @Version: V1.0
 */
@Component
@TableName("bus_device")
public class Device {

    /**
     * 设备ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 所属 产品
     */
    private String productId;
    /**
     * 设备标识
     */
    private String deviceKey;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 所属设备key
     */
    private String parentKey;
    /**
     * 通信地址
     */
    private String deviceAddr;
    /**
     * 1=未启用 2=启用
     */
    private Integer state;
    /**
     * 设备描述
     */
    private String des;
    /**
     * 运营状态 1=调试状态 2=运营状态
     */
    private Integer operState;
    /**
     * 0=未删除 1=删除
     */
    private Integer isDelete;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Integer getOperState() {
        return operState;
    }

    public void setOperState(Integer operState) {
        this.operState = operState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

