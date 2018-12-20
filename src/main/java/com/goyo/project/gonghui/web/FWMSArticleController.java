package com.goyo.project.gonghui.web;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.goyo.project.common.CommonDataForArticle;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;

/**
 * @author: gdd
 * @ClassName: FWMSArticleController 
 * @Description: 服务民生
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/fwmsArticle")
public class FWMSArticleController extends CommonDataForArticle {
	    /**
	     * 指定类别文章查询(显示固定条数)
	     * @return Result<Page<Article>>
	     */
		@PostMapping("/articlePage")
	    public Result<Page<Article>> Article(Article article,String pageNum,String pageSize){
			return getArticlePage(article, pageNum, pageSize);
	    }
		 /**
	     * 指定类别文章查询(更多  带分页)
	     * @return Result<Page<Article>>
	     */
		@PostMapping("/articlePageMore")
	    public Result<Page<Article>> ArticleMore(Article article,String pageNum,String pageSize,
	    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
	    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){ //根据情况 再扩张更多查询条件
			return getArticlePage(article, pageNum, pageSize,startTime,endTime);
	    }
		 /**
	     * 文章详情(single)
	     * @return Result<Article>
	     */
		
		@PostMapping("/articleSingle")
	    public Result<Article> ArticleSingle(Article article){
			return getArticleSingle(article);
	    }
}
