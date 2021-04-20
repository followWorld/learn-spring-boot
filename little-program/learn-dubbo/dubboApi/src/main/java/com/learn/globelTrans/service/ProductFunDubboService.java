package com.learn.globelTrans.service;


import com.learn.globelTrans.entity.ProductFun;

import java.util.List;

/**
 * ClassName: <br/>
 * Description: <br/>
 * date: 2020/12/15 11:17<br/>
 *
 * @author WLSH<br />
 */
public interface ProductFunDubboService {

    public boolean addFunction(ProductFun productFun);

    public boolean deleteById(Integer id);

    public boolean deleteByProductIds(List<String> ids);

    public List<ProductFun> getAllFunByProductId(String productId, String userName);

    public ProductFun getById(Integer id);

    public boolean updateById(ProductFun productFun);

    public ProductFun getOneByKey(String userName, String productId, String enKey, Integer functionType);

    public List<ProductFun> getProductFunByProductId(String productId, Integer functionType, String cnName);

    public boolean deleteParamByFunIds(List<Integer> functionIds);
}
