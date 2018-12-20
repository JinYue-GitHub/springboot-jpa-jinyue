package com.goyo.project.jiwei.web;

import java.net.URLEncoder;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.goyo.project.common.CommonDataForArticle;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;

/**
 * @author: JinYue
 * @ClassName: LZJSArticleController 
 * @Description: 廉政建设
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/lzjsArticle")
public class LZJSArticleController extends CommonDataForArticle {
	
	
	//*********************************************工作动态 作风监督 廉政教育 政策法规************************************************
	 /**
     * 文章查询(single)
     * @return Result<Article>
     */
//    @RequiresPermissions("article:view")//权限管理;
	@PostMapping("/articleSingle")
    public Result<Article> articleSingle(Article article){
		return getArticleSingle(article);
    }
	
    /**
     * 文章查询(all)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePage")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<Article>> article(Article article,String pageNum,String pageSize
			){
		return getArticlePage(article, pageNum, pageSize);
    }
	 /**
     * 文章查询(all:更多)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePageMore")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<Article>> articleMore(Article article,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
    /**
     * 文章添加;(文章 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/articleAdd")
//    @RequiresPermissions("userInfo:add")//权限管理; 
    public Result<String> articleAdd(Article article){
		return getArticleAdd(article);
       
    }
	
	 /**
     * 文章更新;(文章 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/articleUpdate")
//    @RequiresPermissions("userInfo:add")//权限管理; 
    public Result<String> articleUpdate(Article article){
		return getArticleUpdate(article);
       
    }

    /**
     * 文章删除;
     * @return Result<String>
     */
	@PostMapping("/articleDel")
//    @RequiresPermissions("userInfo:del")//权限管理;
    public Result<String> articleDel(Article article){
		return getArticleDel(article);
    }
	
}
