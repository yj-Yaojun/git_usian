package com.usian.controller;

import com.usian.feign.ItemServiceFeign;
import com.usian.pojo.TbItemParam;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("backend/itemParam")
public class ItemParamController {

    @Autowired
    private ItemServiceFeign itemServiceFeign;

    @RequestMapping("selectItemParamByItemCatId/{itemCatId}")
    public Result selectItemParamByItemCatId(@PathVariable Long itemCatId){
        TbItemParam tbItemParam = itemServiceFeign.selectItemParamByItemCatId(itemCatId);
        if(tbItemParam != null){
            return Result.ok(tbItemParam);
        }
        return Result.error("啥也查不到");
    }

    @RequestMapping("selectItemParamAll")
    public Result selectItemParamAll(){
       PageResult page = itemServiceFeign.selectItemParamAll();
        if(page !=null ){
            return Result.ok(page);
        }
        return Result.error("查询失败");
    }

    @RequestMapping("insertItemParam")
    public Result insertItemParam(TbItemParam tbItemParam){
        Integer item = itemServiceFeign.insertItemParam(tbItemParam);
        if(item > 0){
            return Result.ok();
        }
        return Result.error("添加失败");
    }

    @RequestMapping("deleteItemParamById")
    public Result deleteItemParamById(Long id){
        Integer itemNum = itemServiceFeign.deleteItemParamById(id);
        if(itemNum == 1){
            return  Result.ok();
        }
        return Result.error("删除失败");
    }
}
