package com.usian.feign;

import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemParam;
import com.usian.utils.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("usian-item-service")
public interface ItemServiceFeign {

    @RequestMapping("service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam Long itemId);

    @GetMapping("service/item/selectTbItemAllByPage")
    PageResult selectTbItemAllByPage(@RequestParam Integer page, @RequestParam Integer rows);

    @RequestMapping("service/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam Integer id);

    @RequestMapping("service/itemParam/selectItemParamByItemCatId/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable Long itemCatId);

    @RequestMapping("service/item/insertTbItem")
    Integer insertTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams);

    @RequestMapping("service/item/preUpdateItem")
    Map<String, Object> preUpdateItem(@RequestParam Long itemId);
}
