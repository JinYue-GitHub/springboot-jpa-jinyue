package com.goyo.project.tuanwei.web;

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
 * @ClassName: TWManageArticleController
 * @Description: 团委业务办公
 */
// @JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/twManageArticle")
public class TWManageArticleController extends CommonDataForArticle {

	@PostMapping("/articlePage")
	public Result<Page<Article>> Article(Article article, String pageNum, String pageSize) {
		return getArticlePage(article, pageNum, pageSize);
	}

	/**
	 * 指定类别文章查询(管理列表 带分页)
	 * 
	 * @return Result<Page<Article>>
	 */
	@PostMapping("/articlePageMore")
	public Result<Page<Article>> ArticleMore(Article article, String pageNum, String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 根据情况
																			// 再扩张更多查询条件
		return getArticlePage(article, pageNum, pageSize, startTime, endTime);
	}

	/**
	 * 文章详情(single)
	 * 
	 * @return Result<Article>
	 */
	@PostMapping("/articleSingle")
	public Result<Article> ArticleSingle(Article article) {
		return getArticleSingle(article);
	}

	/**
	 * 文章添加;
	 * 
	 * @return Result<String>
	 */
	@PostMapping("/articleAdd")
	public Result<String> ArticleAdd(Article article) {
		return getArticleAdd(article);

	}

	/**
	 * 文章更新;(文章 上传/发布)
	 * 
	 * @return Result<String>
	 */
	@PostMapping("/articleUpdate")
	public Result<String> articleUpdate(Article article) {
		return getArticleUpdate(article);

	}

	/**
	 * 文章删除;
	 * 
	 * @return Result<String>
	 */
	@PostMapping("/articleDel")
	public Result<String> ArticleDel(Article article) {
		return getArticleDel(article);
	}
}
