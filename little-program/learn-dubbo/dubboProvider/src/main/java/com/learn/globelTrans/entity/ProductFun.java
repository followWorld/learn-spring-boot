package com.learn.globelTrans.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户产品功能表
 * @Author: jeecg-boot
 * @Date:   2020-11-19
 * @Version: V1.0
 */
@TableName("busi_product_fun")
public class ProductFun implements Serializable {
    private static final long serialVersionUID = 1L;
    /**功能ID*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**产品标识id*/
    private String productId;
    /**用户名*/
    private String userName;
    /**协议类型（698,3761,json）*/
    private String protocolType;
    /**对应规约的动作类型 如：GET-GetRequestNormal */
    private String actionType;
    /**中文名*/
    private String cnName;
    /**英文名*/
    private String enKey;
    /**上行topic*/
    private String upTopic;
    /**下行topic*/
    private String downTopic;
    /**功能类型（方法 ，事件）*/
    private Integer functionType;
    /**事件类型（1信息，2告警，3故障）*/
    private Integer eventType;
    /**是否异步*/
    private Integer isAsyn;
    /**描述*/
    private String des;
    /**使用状态（1-禁用,2-启用）*/
    private Integer isUse;
    /**删除状态（0：未删除，1，删除）*/
    @TableLogic(value = "0",delval = "1")
    private Integer isDelete;
    /**可编辑状态（1：标准不可更改    2：标准部分可更改 3：自定义）*/
    private Integer editableState;

    /**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**updateTime*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    private List<ProductFunParamDO> upParams;
    @TableField(exist = false)
    private List<ProductFunParamDO> downParams;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnKey() {
        return enKey;
    }

    public void setEnKey(String enKey) {
        this.enKey = enKey;
    }

    public String getUpTopic() {
        return upTopic;
    }

    public void setUpTopic(String upTopic) {
        this.upTopic = upTopic;
    }

    public String getDownTopic() {
        return downTopic;
    }

    public void setDownTopic(String downTopic) {
        this.downTopic = downTopic;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getEditableState() {
        return editableState;
    }

    public void setEditableState(Integer editableState) {
        this.editableState = editableState;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ProductFunParamDO> getUpParams() {
        return upParams;
    }

    public void setUpParams(List<ProductFunParamDO> upParams) {
        this.upParams = upParams;
    }

    public List<ProductFunParamDO> getDownParams() {
        return downParams;
    }

    public void setDownParams(List<ProductFunParamDO> downParams) {
        this.downParams = downParams;
    }

    public Integer getIsAsyn() {
        return isAsyn;
    }

    public void setIsAsyn(Integer isAsyn) {
        this.isAsyn = isAsyn;
    }

}
