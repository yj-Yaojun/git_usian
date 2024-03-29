package com.usian.controller;

import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("service/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("selectItemInfo")
    public TbItem selectItemInfo(Long itemId){
        return itemService.getById(itemId);
    }

    /**
     * 商品列表查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(Integer page, Integer rows){
        return this.itemService.selectTbItemAllByPage(page,rows);
    }

    /**
     * 商品的添加
     */
    @RequestMapping("insertTbItem")
    public Integer insertTbItem(@RequestBody TbItem tbItem,String desc,String itemParams){
        return itemService.insertTbItem(tbItem, desc, itemParams);
    }

    /**
     * 根据itemId回显商品信息
     * @param itemId
     * @return
     */
    @RequestMapping("preUpdateItem")
    public Map<String,Object> preUpdateItem(Long itemId){
        return this.itemService.preUpdateItem(itemId);
    }

    @RequestMapping("deleteItemById")
    public Integer deleteItemById(Long itemId){
        return  itemService.deleteItemById(itemId);
    }

    @RequestMapping("updateTbItem")
    public Integer updateTbItem(@RequestBody TbItem tbItem,String desc,String itemParams){
        return itemService.updateTbItem(tbItem,desc,itemParams);
    }
}
