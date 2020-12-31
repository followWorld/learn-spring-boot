package com.learn.globelTrans.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dfzt.analysis.api.ProductFunDubboService;
import com.dfzt.analysis.entity.ProductFun;
import com.dfzt.analysis.entity.ProductFunParam;
import com.dfzt.iot.common.page.IPage;
import com.learn.globelTrans.entity.ProductFunDO;
import com.learn.globelTrans.entity.ProductFunParamDO;
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
    public IPage<ProductFun> queryStandPageList(IPage<ProductFun> page, ProductFun productFun) {
        return null;
    }

    @Override
    public IPage<ProductFun> querySelfPageList(IPage<ProductFun> page, ProductFun productFun) {
        return null;
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

    @Override
    public List<ProductFunParam> queryList(ProductFunParam productFunParam) {
        ProductFunParamDO productFunParamDO = JSONObject.parseObject(JSONObject.toJSONString(productFunParam),
                ProductFunParamDO.class);
        List<ProductFunParamDO> productFunParamDOs = productFunParamService.queryList(productFunParamDO);
        return JSONArray.parseArray(JSON.toJSONString(productFunParamDOs), ProductFunParam.class);
    }

    @Override
    public IPage<ProductFunParam> queryParamPageList(IPage<ProductFunParam> page, ProductFunParam productFunParam) {
        return null;
    }

    @Override
    public List<ProductFunParam> querySubByParamId(Integer id) {
        List<ProductFunParamDO> productFunParamDOs = productFunParamService.querySubByParamId(id);
        return JSONArray.parseArray(JSON.toJSONString(productFunParamDOs), ProductFunParam.class);
    }

    @Override
    public ProductFunParam getParamById(Integer id) {
        ProductFunParamDO byId = productFunParamService.getById(id);
        return JSONObject.parseObject(JSON.toJSONString(byId), ProductFunParam.class);
    }

    @Override
    public ProductFunParam saveParam(ProductFunParam productFunParam) {
        ProductFunParamDO productFunParamDO = JSONObject.parseObject(JSONObject.toJSONString(productFunParam),
                ProductFunParamDO.class);
        ProductFunParamDO productFunParamDO1 = productFunParamService.saveParam(productFunParamDO);
        return JSONObject.parseObject(JSON.toJSONString(productFunParamDO1), ProductFunParam.class);
    }

    @Override
    public boolean updateParamById(ProductFunParam productFunParam) {
        ProductFunParamDO productFunParamDO = JSONObject.parseObject(JSONObject.toJSONString(productFunParam),
                ProductFunParamDO.class);
        return productFunParamService.updateById(productFunParamDO);
    }

    @Override
    public List<ProductFunParam> saveOrUpdateBatchParam(List<ProductFunParam> productFunParams) {
        List<ProductFunParamDO> productFunParamDO = JSONArray.parseArray(JSONObject.toJSONString(productFunParams),
                ProductFunParamDO.class);
        List<ProductFunParamDO> productFunParamDOs = productFunParamService.saveOrUpdateBatchParam(productFunParamDO);
        return JSONArray.parseArray(JSON.toJSONString(productFunParamDOs), ProductFunParam.class);
    }

    @Override
    public void saveSubParamByFunBatch(ProductFun productFun, ProductFunParam param) {
        ProductFunDO productFunDO = JSONObject.parseObject(JSONObject.toJSONString(productFun), ProductFunDO.class);
        ProductFunParamDO productFunParamDO = JSONObject.parseObject(JSONObject.toJSONString(param),
                ProductFunParamDO.class);
        productFunParamService.saveSubParamByFunBatch(productFunDO, productFunParamDO);
    }

    @Override
    public boolean removeParamById(Integer id) {
        return productFunParamService.removeById(id);
    }
}
