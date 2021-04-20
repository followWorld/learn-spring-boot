package com.learn.globelTrans.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learn.globelTrans.entity.ProductFun;
import com.learn.globelTrans.entity.ProductFunDO;
import com.learn.globelTrans.service.ProductFunDubboService;
import com.learn.globelTrans.service.ProductFunParamService;
import com.learn.globelTrans.service.ProductFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: pr <br/>
 * Description: <br/>
 * date: 2020/12/15 11:17<br/>
 *
 * @author WLSH<br />
 */
@Service
public class ProductFunDubboServiceImpl implements ProductFunDubboService {
    @Autowired
    private ProductFunService productFunService;
    @Autowired
    private ProductFunParamService productFunParamService;

    @Override
    public boolean addFunction(ProductFun productFun) {
        ProductFunDO productFunDO = JSONObject.parseObject(JSONObject.toJSONString(productFun), ProductFunDO.class);
        return productFunService.addFunction(productFunDO);
    }

    @Override
    public boolean deleteById(Integer id) {
        return productFunService.deleteById(id);
    }

    @Override
    public boolean deleteByProductIds(List<String> ids) {
        return productFunService.deleteByProductIds(ids);
    }

    @Override
    public List<ProductFun> getAllFunByProductId(String productId, String userName) {
        List<ProductFunDO> allFunByProductId = productFunService.getAllFunByProductId(productId, userName);
        return JSONArray.parseArray(JSON.toJSONString(allFunByProductId), ProductFun.class);
    }

    @Override
    public ProductFun getById(Integer id) {
        return JSONObject.parseObject(JSON.toJSONString(productFunService.getById(id)), ProductFun.class);
    }

    @Override
    public boolean updateById(ProductFun productFun) {
        ProductFunDO productFunDO = JSONObject.parseObject(JSONObject.toJSONString(productFun), ProductFunDO.class);
        return productFunService.updateById(productFunDO);
    }

    @Override
    public ProductFun getOneByKey(String userName, String productId, String enKey, Integer functionType) {
        ProductFunDO oneByKey = productFunService.getOneByKey(userName, productId, enKey, functionType);
        return JSONObject.parseObject(JSON.toJSONString(oneByKey), ProductFun.class);
    }

    @Override
    public List<ProductFun> getProductFunByProductId(String productId, Integer functionType, String cnName) {
        List<ProductFunDO> productFunByProductId = productFunService.getProductFunByProductId(productId, functionType, cnName);
        return JSONArray.parseArray(JSON.toJSONString(productFunByProductId), ProductFun.class);
    }

    @Override
    public boolean deleteParamByFunIds(List<Integer> functionIds) {
        return productFunParamService.deleteByFunIds(functionIds);
    }

}
