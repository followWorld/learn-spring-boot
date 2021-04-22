package com.learn.globelTrans.service.impl;
import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.globelTrans.entity.ProductFun;
import com.learn.globelTrans.entity.ProductFunDO;
import com.learn.globelTrans.entity.ProductFunParamDO;
import com.learn.globelTrans.mapper.ProductFunMapper;
import com.learn.globelTrans.service.ProductFunParamService;
import com.learn.globelTrans.service.ProductFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 用户产品功能
 * @Author: jeecg-boot
 * @Date: 2020-11-12
 * @Version: V1.0
 */
@Service
public class ProductFunServiceImpl extends ServiceImpl<ProductFunMapper, ProductFunDO> implements ProductFunService {
    @Autowired
    private ProductFunParamService productFunParamService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFunction(ProductFunDO productFunDO) {
        boolean save = this.saveBatch(Collections.singleton(productFunDO));
        if (save) {
            List<ProductFunParamDO> upParams = productFunDO.getUpParams();
            List<ProductFunParamDO> downParams = productFunDO.getDownParams();
            // 保存上行参数
            saveParam(productFunDO, upParams);
            // 保存下行参数
            saveParam(productFunDO, downParams);
        }
        return save;
    }

    private void saveParam(ProductFunDO productFunDO, List<ProductFunParamDO> params) {
        if (params != null && params.size() > 0) {
            params.forEach(p -> {
                p.setId(null);
                p.setFunctionId(productFunDO.getId());
                p.setIsUse(2);
                p.setCreateBy(productFunDO.getCreateBy());
                p.setIsDelete(0);
            });
            // 保存当前层
            productFunParamService.saveBatch(params);
            // 循环保存所有子参数
            params.forEach(p -> productFunParamService.saveSubParamByFunBatch(productFunDO, p));
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean ok = this.removeById(id);
        if (ok) {
            productFunParamService.deleteByFunIds(Collections.singletonList(id));
        }
        return ok;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByProductIds(List<String> ids) {
        //删除产品功能以及功能参数
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.in(ProductFunDO::getProductId, ids);
        List<ProductFunDO> productFunDOList = this.list(productFunQuery);
        productFunDOList.forEach(p -> this.deleteById(p.getId()));
        return true;
    }

    @Override
    public List<ProductFunDO> getAllEnableProductFun() {

        List<ProductFunParamDO> funParamList = productFunParamService.getAllEnableFunParams();
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getIsUse, 2);
        List<ProductFunDO> funList = this.list(productFunQuery);
        for (ProductFunDO productFunDO : funList) {
            List<ProductFunParamDO> subFunParamList = funParamList.stream().filter(funParam -> funParam.getFunctionId().equals(productFunDO.getId())).collect(Collectors.toList());
            List<ProductFunParamDO> upSubFunParams = subFunParamList.stream().filter(funParam -> funParam.getUpOrDown().equals(1)).collect(Collectors.toList());
            List<ProductFunParamDO> downSubFunParams = subFunParamList.stream().filter(funParam -> funParam.getUpOrDown().equals(2)).collect(Collectors.toList());
            productFunDO.setUpParams(upSubFunParams);
            productFunDO.setDownParams(downSubFunParams);
        }

        return funList;
    }

    @Override
    public List<ProductFunDO> getAllFunByProductId(String productId, String userName) {
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getProductId, productId);
        productFunQuery.eq(ProductFunDO::getUserName, userName);
        return this.list(productFunQuery);
    }


    @Override
    public IPage<ProductFunDO> queryStandPageList(IPage<ProductFunDO> page, ProductFunDO productFunDO) {

        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getProductId, productFunDO.getProductId());
        productFunQuery.eq(ProductFunDO::getFunctionType, productFunDO.getFunctionType());
        productFunQuery.eq(ProductFunDO::getUserName, productFunDO.getUserName());
        // 非自定义
        productFunQuery.ne(ProductFunDO::getEditableState, 3);
        return this.page(page, productFunQuery);
    }

    @Override
    public IPage<ProductFunDO> querySelfPageList(IPage<ProductFunDO> page, ProductFunDO productFunDO) {
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getProductId, productFunDO.getProductId());
        productFunQuery.eq(ProductFunDO::getFunctionType, productFunDO.getFunctionType());
        productFunQuery.eq(ProductFunDO::getUserName, productFunDO.getUserName());
        // 自定义
        productFunQuery.eq(ProductFunDO::getEditableState, 3);
        return this.page(page, productFunQuery);
    }

    @Override
    public ProductFunDO getOneByKey(String userName, String productId, String enKey, Integer functionType) {
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getUserName, userName);
        productFunQuery.eq(ProductFunDO::getProductId, productId);
        productFunQuery.eq(ProductFunDO::getEnKey, enKey);
        productFunQuery.eq(ProductFunDO::getFunctionType, functionType);
        productFunQuery.last("limit 1");
        return this.getOne(productFunQuery);
    }

    @Override
    public List<ProductFunDO> getProductFunByProductId(String productId, Integer functionType, String cnName) {
        LambdaQueryWrapper<ProductFunDO> productFunQuery = new LambdaQueryWrapper<>();
        productFunQuery.eq(ProductFunDO::getProductId, productId);
        productFunQuery.eq(ProductFunDO::getFunctionType, functionType);
        if (cnName != null) {
            productFunQuery.like(ProductFunDO::getCnName, cnName);
        }
        productFunQuery.eq(ProductFunDO::getIsUse, 2);
        List<ProductFunDO> funList = this.list(productFunQuery);
        // 查这些方法所有参数
        List<Integer> funIds = funList.stream().map(ProductFunDO::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ProductFunParamDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProductFunParamDO::getFunctionId, funIds);
        List<ProductFunParamDO> funParamList = productFunParamService.list(queryWrapper);
        for (ProductFunDO productFunDO : funList) {
            List<ProductFunParamDO> subFunParamList = funParamList.stream().filter(funParam -> funParam.getFunctionId().equals(productFunDO.getId())).collect(Collectors.toList());
            List<ProductFunParamDO> upSubFunParams = subFunParamList.stream().filter(funParam -> funParam.getUpOrDown().equals(1)).collect(Collectors.toList());
            List<ProductFunParamDO> downSubFunParams = subFunParamList.stream().filter(funParam -> funParam.getUpOrDown().equals(2)).collect(Collectors.toList());
            productFunDO.setUpParams(upSubFunParams);
            productFunDO.setDownParams(downSubFunParams);
        }
        return funList;
    }

    private List<ProductFunDO> convertList(List<ProductFun> list){
        List<ProductFunDO> productFunDOlist=Lists.newArrayList();
        for (ProductFun productFun :list) {
        	productFunDOlist.add(convertFromProductFun(productFun));
        }
        return productFunDOlist;

    }

    private ProductFunDO convertFromProductFun(ProductFun productFun) {
        ProductFunDO productFunDO = new ProductFunDO();
        productFunDO.setId(productFun.getId());
        productFunDO.setProductId(productFun.getProductId());
        productFunDO.setUserName(productFun.getUserName());
        productFunDO.setProtocolType(productFun.getProtocolType());
        productFunDO.setActionType(productFun.getActionType());
        productFunDO.setCnName(productFun.getCnName());
        productFunDO.setEnKey(productFun.getEnKey());
        productFunDO.setUpTopic(productFun.getUpTopic());
        productFunDO.setDownTopic(productFun.getDownTopic());
        productFunDO.setFunctionType(productFun.getFunctionType());
        productFunDO.setEventType(productFun.getEventType());
        productFunDO.setDes(productFun.getDes());
        productFunDO.setIsUse(productFun.getIsUse());
        productFunDO.setIsDelete(productFun.getIsDelete());
        productFunDO.setEditableState(productFun.getEditableState());
        productFunDO.setCreateTime(productFun.getCreateTime());
        productFunDO.setUpdateTime(productFun.getUpdateTime());
        productFunDO.setIsAsyn(productFun.getIsAsyn());
        return productFunDO;
    }


}
