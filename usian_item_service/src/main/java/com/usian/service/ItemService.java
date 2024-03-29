package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.*;
import com.usian.utils.IDUtils;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

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

    public Integer insertTbItem(TbItem tbItem,String desc,String itemParams){
        Date date = new Date();
        //补齐 Tbitem 数据
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte)1);

        //补齐商品描述对象
        int i1=tbItemMapper.insert(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);


        //补齐商品规格参数
        int i2 = tbItemDescMapper.insert(tbItemDesc);
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        int i3 = tbItemParamItemMapper.insert(tbItemParamItem);

        return i1 + i2 + i3;
    }

    /**
     * 根据商品 ID 查询商品，商品分类，商品描述，商品规格参数
     * @param itemId
     * @return
     */
    public Map<String,Object> preUpdateItem(Long itemId){
        Map<String,Object> map = new HashMap<>();
        //根据商品 ID 查询商品
        TbItem tbItem = this.tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item",tbItem);
        //根据商品 ID 查询商品描述
        TbItemDesc itemDesc = this.tbItemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc",itemDesc.getItemDesc());
        //根据商品 ID 查询商品类目
        TbItemCat tbItemCat = this.tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
        map.put("itemCat",tbItemCat.getName());
        //根据商品 ID 查询商品规格信息
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = this.tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size() >0){
            map.put("itemParamItem",list.get(0).getParamData());
        }
        return map;
    }

    public Integer deleteItemById(Long itemId) {
        TbItem tbItem = new TbItem();
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 3);
        tbItem.setUpdated(new Date());
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    public Integer updateTbItem(TbItem tbItem, String desc, String itemParams) {
       tbItem.setUpdated(new Date());
       int tbitemNum = tbItemMapper.updateByPrimaryKeySelective(tbItem);

       TbItemDesc tbItemDesc = new TbItemDesc();
       tbItemDesc.setItemId(tbItem.getId());
       tbItemDesc.setItemDesc(desc);
       tbItemDesc.setUpdated(new Date());
       int tbItemDescNum = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);


       TbItemParamItem tbItemParamItem = new TbItemParamItem();
       tbItemParamItem.setParamData(itemParams);
       tbItemParamItem.setUpdated(new Date());

       TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
       TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
       criteria.andItemIdEqualTo(tbItem.getId());
       int tbItemParamItemNum = tbItemParamItemMapper.updateByExampleSelective(tbItemParamItem,tbItemParamItemExample);

        return tbitemNum+tbItemDescNum+tbItemParamItemNum;

    }
}
