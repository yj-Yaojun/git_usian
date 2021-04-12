package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemExample;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    public TbItem getById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
    /**
     * 查询所有商品，并分页。
     * @param page
     * @param rows
     * @return
     */
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        //查询状态是1 并且  按修改时间逆序排列
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated DESC");
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusEqualTo((byte)1);
        List<TbItem> tbItemList = tbItemMapper.selectByExample(tbItemExample);
        for (int i = 0; i < tbItemList.size(); i++) {
            TbItem tbItem =  tbItemList.get(i);
            tbItem.setPrice(tbItem.getPrice()/100);

        }
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItemList);
        //返回PageResult
        PageResult pageResult = new PageResult();
        pageResult.setResult(tbItemPageInfo.getList());
        pageResult.setTotalPage(Long.valueOf(tbItemPageInfo.getPages()));
        pageResult.setPageIndex(tbItemPageInfo.getPageNum());
        return pageResult;
    }
}
