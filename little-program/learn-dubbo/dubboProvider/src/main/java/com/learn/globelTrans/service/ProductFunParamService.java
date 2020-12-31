package com.learn.globelTrans.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.globelTrans.entity.ProductFunDO;
import com.learn.globelTrans.entity.ProductFunParamDO;

import java.util.List;

/**
 * @Description: 用户产品功能参数
 * @Author: jeecg-boot
 * @Date: 2020-11-12
 * @Version: V1.0
 */
public interface ProductFunParamService extends IService<ProductFunParamDO> {

    /**
     * 通过方法id删除方法上下行所有参数
     *
     * @param functionIds
     */
    boolean deleteByFunIds(List<Integer> functionIds);

    List<ProductFunParamDO> getAllEnableFunParams();

    List<ProductFunParamDO> queryList(ProductFunParamDO productFunParamDo);

    IPage<ProductFunParamDO> queryParamPageList(IPage<ProductFunParamDO> page, ProductFunParamDO productFunParamDo);

    /**
     * 通过父id查询所有子参数列表
     *
     * @param id
     * @return
     */
    List<ProductFunParamDO> querySubByParamId(Integer id);

    ProductFunParamDO saveParam(ProductFunParamDO productFunParamDo);

    List<ProductFunParamDO> saveOrUpdateBatchParam(List<ProductFunParamDO> productFunParamDOS);

    void saveSubParamByFunBatch(ProductFunDO productFunDO, ProductFunParamDO param);
}
