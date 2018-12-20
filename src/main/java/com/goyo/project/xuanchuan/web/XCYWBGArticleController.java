package com.goyo.project.xuanchuan.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.common.CommonDataForArticle;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
import com.goyo.project.model.Sysmessage;
import com.goyo.project.utils.CommonSysmessageUtils;

/**
 * @author: gdd
 * @ClassName: FWMSArticleController
 * @Description: 宣传业务办公
 */
// @JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/xcywbgArticle")
public class XCYWBGArticleController extends CommonDataForArticle {

	/**
	 * 指定类别文章查询(显示固定条数)
	 * 
	 * @return Result<Page<Article>>
	 */
	@PostMapping("/articlePage")
	public Result<Page<Article>> Article(Article article, String pageNum, String pageSize) {
		return getArticlePage(article, pageNum, pageSize);
	}

	/**
	 * 指定类别文章查询(更多 带分页)
	 * 
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
	 * 
	 * @return Result<Article>
	 */

	@PostMapping("/articleSingle")
	public Result<Article> ArticleSingle(Article article) {
		return getArticleSingle(article);
	}

	/**
	 * 更新;( 新增)
	 * 
	 * @return Result<String>
	 */
	@PostMapping("/articleAdd")
	public Result<String> ArticleAdd(Article article) {
		if (article.getTargetOrganization() != null && !article.getTargetOrganization().equals("")) {
			getArticleAdd(article);
			Sysmessage sysmessage = new Sysmessage();
			sysmessage.setRelUserno(article.getSourceOrganization());
			;
			sysmessage.setRelDeptno(article.getSourceOrganization());
			sysmessage.setRelTime(article.getCreateDate());
			sysmessage.setRecUserno(article.getTargetOrganization());
			sysmessage.setRecDeptno(article.getTargetOrganization());
			sysmessage.setRecState("未查看");
			sysmessage.setArticeId(article.getId());
			sysmessage.setSubjection(article.getSubjection());

			return CommonSysmessageUtils.getSysmessageAdd(sysmessage);
		}
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

	/***
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @param filePath
	 * @return
	 */
	@PostMapping("/fileUpload")
	public Object fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request) {
		// 文件位置
		String filePath = "xcywbg";

		return fileUpload(file, request, filePath);
	}

	/***
	 * 文件下载
	 */
	@PostMapping("/download")
	public Object downloads(String filename,String filePath)throws Exception{
	
		
		return download(filename,filePath);
    }
}
