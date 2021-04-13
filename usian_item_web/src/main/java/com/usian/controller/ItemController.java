package com.usian.controller;

import com.usian.feign.ItemServiceFeign;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("backend/item")
public class ItemController {

    @Autowired
    private ItemServiceFeign itemServiceFeign;

    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(Long itemId){
        TbItem tbItem = itemServiceFeign.selectItemInfo(itemId);
        if(tbItem != null){
            return Result.ok(tbItem);
        }
        return Result.error("兄弟，你请求有问题，检查一下肾。。。");
    }

    /**
     * 查询商品并分页处理
     *
     * @return
     */
    @RequestMapping("/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer rows) {

        PageResult pageResult = itemServiceFeign.selectTbItemAllByPage(page, rows);
        if (pageResult.getResult() != null && pageResult.getResult().size() > 0) {
            return Result.ok(pageResult);
        }
        return Result.error("查无结果");
    }

    @RequestMapping("insertTbItem")
    public Result insertTbItem(TbItem tbItem,String desc,String itemParams){
        Integer count = itemServiceFeign.insertTbItem(tbItem,desc,itemParams);
        if(count == 3){
            return Result.ok();
        }
        return Result.error("数据插入失败");
    }

    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(Long itemId){
        Map<String,Object> map = itemServiceFeign.preUpdateItem(itemId);
        if(map.size() > 0){
            return Result.ok(map);
        }
        return Result.error("查无结果");
    }

    @RequestMapping("deleteItemById")
    public Result deleteItemById(Long itemId){
        Integer result = itemServiceFeign.deleteItemById(itemId);
        if(result == 1){
            return  Result.ok();
        }
        return Result.error("删除失败");
    }


    @RequestMapping("updateTbItem")
    public Result updateTbItem(TbItem tbItem,String desc,String itemParams){
        Integer count = itemServiceFeign.updateTbItem(tbItem,desc,itemParams);
        if(count == 3){
            return Result.ok();
        }
        return Result.error("修改失败");
    }

}
