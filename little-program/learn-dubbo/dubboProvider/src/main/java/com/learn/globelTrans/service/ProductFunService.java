package com.learn.globelTrans.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.globelTrans.entity.ProductFunDO;

import java.util.List;

/**
 * @Description: 用户产品功能
 * @Author: jeecg-boot
 * @Date: 2020-11-12
 * @Version: V1.0
 */
public interface ProductFunService extends IService<ProductFunDO> {

    /**
     * 添加用户产品方法，
     *
     * @param productFunDO
     * @return
     */
    boolean addFunction(ProductFunDO productFunDO);

    /**
     * 通过方法主键删除方法及参数
     *
     * @param id
     */
    boolean deleteById(Integer id);

    /**
     * 通过方法主键删除方法及参数
     *
     * @param id
     */
    boolean deleteByProductIds(List<String> id);

    /**
     * 查询所有可用的方法  包括里面的上下行参数
     *
     * @return
     */
    List<ProductFunDO> getAllEnableProductFun();

    /**
     * 不分页查询特定产品的所有方法
     *
     * @param productId       分页模型
     * @param userName 查询条件
     * @return
     */
    List<ProductFunDO> getAllFunByProductId(String productId, String userName);

    /**
     * 分页查询用户标准方法，
     *
     * @param page       分页组件
     * @param productFunDO 查询条件
     * @return
     */
    IPage<ProductFunDO> queryStandPageList(IPage<ProductFunDO> page, ProductFunDO productFunDO);

    /**
     * 分页查询用户自定义方法，
     *
     * @param page       分页组件
     * @param productFunDO 查询条件
     * @return
     */
    IPage<ProductFunDO> querySelfPageList(IPage<ProductFunDO> page, ProductFunDO productFunDO);

    /**
     * 通过用户id，产品id，方法英文名查询一条方法
     *
     * @param userName  用户id
     * @param productId 产品id
     * @param enKey     方法英文名
     * @return
     */
    ProductFunDO getOneByKey(String userName, String productId, String enKey, Integer functionType);

    /**
     * 通过产品id，方法类型，查询产品下的所有方法或事件
     *
     * @param productId    产品id
     * @param cnName 方法中文名
     * @param functionType 功能类型
     * @return
     */
    List<ProductFunDO> getProductFunByProductId(String productId, Integer functionType, String cnName);

}
