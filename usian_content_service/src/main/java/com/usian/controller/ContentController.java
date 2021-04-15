package com.usian.controller;

import com.usian.pojo.TbContent;
import com.usian.service.ContentService;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("selectTbContentAllByCategoryId")
    public PageResult selectTbContentAllByCategoryId(@RequestParam Long categoryId){
        return  contentService.selectTbContentAllByCategoryId(categoryId);
    }

    @RequestMapping("insertTbContent")
    public Integer insertTbContent(@RequestBody TbContent tbContent){
        return contentService.insertTbContent(tbContent);
    }

    @RequestMapping("deleteContentByIds")
    public Integer deleteContentByIds(@RequestParam Long ids){
        return contentService.deleteContentByIds(ids);
    }
}
