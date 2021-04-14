package com.usian.service;

import com.usian.mapper.TbContentCategoryMapper;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    public List<TbContentCategory> selectContentCategoryByParentId(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    public Integer insertContentCategory(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        tbContentCategory.setCreated(date);
        tbContentCategory.setStatus(1);

        return tbContentCategoryMapper.insert(tbContentCategory);

    }

    public Integer deleteContentCategoryById(Long categoryId) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);
        if(tbContentCategory.getParentId() == 0){
            return 0;
        }
        this.deleteContentCategoryByparentId(categoryId);
        this.deleteById(categoryId);
        return 1;
    }

    private void deleteById(Long categoryId) {
        TbContentCategory t = new TbContentCategory();
        t.setId(categoryId);
        t.setStatus(0);
        tbContentCategoryMapper.updateByPrimaryKeySelective(t);
    }

    private void deleteContentCategoryByparentId(Long parentId) {
        List<TbContentCategory> list = this.getContentCategoryByParentId(parentId);
        for (TbContentCategory tbContentCategory :list){
            List<TbContentCategory> contentCategoryList = this.getContentCategoryByParentId(tbContentCategory.getId());
            if(contentCategoryList != null && contentCategoryList.size() > 0){
                this.deleteContentCategoryByparentId(tbContentCategory.getId());
            }
            this.deleteById(tbContentCategory.getId());
        }
    }

    private List<TbContentCategory> getContentCategoryByParentId(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    public Integer updateContentCategory(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        tbContentCategory.setCreated(date);
        tbContentCategory.setStatus(1);
        return  tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }
}
