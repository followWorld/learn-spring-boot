package com.learn.globelTrans.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.globelTrans.entity.ProductFunDO;
import com.learn.globelTrans.entity.ProductFunParamDO;
import com.learn.globelTrans.mapper.ProductFunParamMapper;
import com.learn.globelTrans.service.ProductFunParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用户产品功能参数
 * @Author: jeecg-boot
 * @Date: 2020-11-12
 * @Version: V1.0
 */
@Service
public class ProductFunParamServiceImpl extends ServiceImpl<ProductFunParamMapper, ProductFunParamDO> implements ProductFunParamService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByFunIds(List<Integer> functionIds) {
        LambdaQueryWrapper<ProductFunParamDO> qw = new LambdaQueryWrapper<>();
        qw.in(ProductFunParamDO::getFunctionId, functionIds);
        return this.remove(qw);
    }

    @Override
    public List<ProductFunParamDO> getAllEnableFunParams() {

        LambdaQueryWrapper<ProductFunParamDO> qw = new LambdaQueryWrapper<>();
        qw.eq(ProductFunParamDO::getIsUse, 2);
        return this.list(qw);
    }

    @Override
    public List<ProductFunParamDO> queryList(ProductFunParamDO productFunParamDo) {
        QueryWrapper<ProductFunParamDO> qw = new QueryWrapper<>(productFunParamDo);
        return this.list(qw);
    }

    @Override
    public IPage<ProductFunParamDO> queryParamPageList(IPage<ProductFunParamDO> page,
                                                       ProductFunParamDO productFunParamDo) {
        QueryWrapper<ProductFunParamDO> qw = new QueryWrapper<>(productFunParamDo);
        return this.page(page, qw);
    }

    @Override
    public List<ProductFunParamDO> querySubByParamId(Integer id) {
        // 查询层级参数返回值
        ProductFunParamDO productFunParamDOResult = this.getById(id);
        this.getSubParam(productFunParamDOResult);
        // 封装返回值
        List<ProductFunParamDO> list = new ArrayList<>();
        list.add(productFunParamDOResult);
        return list;
    }

    @Override
    public ProductFunParamDO saveParam(ProductFunParamDO productFunParamDo) {
        // 保存当前参数
        productFunParamDo.setIsUse(2);
        productFunParamDo.setIsDelete(0);
        this.save(productFunParamDo);
        // 保存子参数
        saveOrUpdateSubBatch(productFunParamDo);
        return productFunParamDo;
    }

    @Override
    public List<ProductFunParamDO> saveOrUpdateBatchParam(List<ProductFunParamDO> productFunParamDOS) {
        // 保存当前这一批参数
        productFunParamDOS.forEach(p -> {
            p.setIsUse(2);
            p.setIsDelete(0);
        });
        this.saveOrUpdateBatch(productFunParamDOS);
        // 遍历这一批参数，保存各自的子参数
        productFunParamDOS.forEach(this::saveOrUpdateSubBatch);
        return productFunParamDOS;
    }

    private void saveOrUpdateSubBatch(ProductFunParamDO productFunParamDo) {
        List<ProductFunParamDO> children = productFunParamDo.getChildren();
        if (children != null && children.size() > 0) {
            children.forEach(p -> {
                p.setParentId(productFunParamDo.getId());
                p.setIsUse(2);
                p.setIsDelete(0);
            });
            this.saveOrUpdateBatch(children);
            children.forEach(this::saveOrUpdateSubBatch);
        }
    }

    @Override
    public void saveSubParamByFunBatch(ProductFunDO productFunDO, ProductFunParamDO param) {
        List<ProductFunParamDO> children = param.getChildren();
        if (children != null && children.size() > 0) {
            children.forEach(p -> {
                p.setId(null);
                p.setParentId(param.getId());
                p.setFunctionId(productFunDO.getId());
                p.setIsUse(2);
                p.setCreateBy(productFunDO.getCreateBy());
                p.setIsDelete(0);
            });
            this.saveBatch(children);
            children.forEach(p -> this.saveSubParamByFunBatch(productFunDO, p));
        }
    }

    /**
     * 递归查询所有子参数
     *
     * @param parentParam
     */
    private void getSubParam(ProductFunParamDO parentParam) {
        // 查询子参数
        Integer id = parentParam.getId();
        LambdaQueryWrapper<ProductFunParamDO> qw = new LambdaQueryWrapper<>();
        qw.eq(ProductFunParamDO::getParentId, id);
        List<ProductFunParamDO> children = this.list(qw);

        if (children != null && children.size() > 0) {
            // 有子参数则赋值children，
            parentParam.setChildren(children);
            // 遍历该子参数集合，继续添加children
            children.forEach(this::getSubParam);
        }
    }
}
