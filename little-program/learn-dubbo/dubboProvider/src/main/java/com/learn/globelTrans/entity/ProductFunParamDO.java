package com.learn.globelTrans.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户产品功能表参数
 * @Author: jeecg-boot
 * @Date:   2020-11-19
 * @Version: V1.0
 */
@TableName("busi_product_fun_param")
public class ProductFunParamDO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**id*/
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**功能ID*/
    private Integer functionId;
    /**参数类型（1上行，2下行）*/
    private Integer upOrDown;
    /**数据项排序号*/
    private String orderNo;
    /**父级参数id*/
    private Integer parentId;
    /**数据中文名字*/
    private String cnName;
    /**数据英文名字*/
    private String enKey;
    /**数据类型 基本类型 或 struct*/
    private String dataType;
    /**如果data_type为array类型，array里面的数据类型，基本类型或struct*/
    private String arrayRefType;
    /**默认值*/
    private String defultVal;
    /**最大值*/
    private String maxNum;
    /**最小值*/
    private String minNum;
    /**单位*/
    private String unit;
    /**步长*/
    private Integer step;
    /**描述*/
    private String des;
    /**参数类型(1,基础参数，2,键值混合参数，3,内容参数)*/
    private Integer paramType;
    /**使用状态（1-禁用,2-启用）*/
    private Integer isUse;
    /**删除状态（0：未删除，1，删除）*/
    @TableLogic(value = "0",delval = "1")
    private Integer isDelete;
    /**可编辑状态（1：标准不可更改    2：标准部分可更改 3：自定义）*/
    private Integer editableState;
    /**createBy*/
    private String createBy;
    /**updateBy*/
    private String updateBy;
    /**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**updateTime*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    private List<ProductFunParamDO> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Integer upOrDown) {
        this.upOrDown = upOrDown;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getArrayRefType() {
        return arrayRefType;
    }

    public void setArrayRefType(String arrayRefType) {
        this.arrayRefType = arrayRefType;
    }

    public String getDefultVal() {
        return defultVal;
    }

    public void setDefultVal(String defultVal) {
        this.defultVal = defultVal;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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

    public List<ProductFunParamDO> getChildren() {
        return children;
    }

    public void setChildren(List<ProductFunParamDO> children) {
        this.children = children;
    }
}
