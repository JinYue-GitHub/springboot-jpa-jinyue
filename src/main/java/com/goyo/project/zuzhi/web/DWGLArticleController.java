package com.goyo.project.zuzhi.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.BackCommonDataForArticle;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Article;
/**
 * @author: JinYue
 * @ClassName: DWGLArticleController 
 * @Description: 党务管理
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/dwglArticle")
public class DWGLArticleController extends BackCommonDataForArticle {

	// source  target
	//*********************************************三会一课************************************************
	 /**
     * 三会一课类型查询 
     * 党员大会
     * 党支部委员会
     * 党小组会
     * 党课
     * @return Result<Article>
     */
////  @RequiresPermissions("article:view")//权限管理;
//	@PostMapping("/shykArticleConstant")
//    public Result<List<String>> shykArticleConstant(){
//		List<String> list = new ArrayList<>();
//		list.add(ProjectConstant.PARTY_MEMBER_CONFERENCE);
//		list.add(ProjectConstant.PARTY_BRANCH_COMMITTEE);
//		list.add(ProjectConstant.PARTY_GROUP_CONFERENCE);
//		list.add(ProjectConstant.PARTY_LECTURE);
//		return ResultGenerator.genSuccessResult(list);
//    }
//	
	
	 /**
     * 文章查询(single)
     * @return Result<Article>
     */
	@PostMapping("/articleSingle")
    public Result<Article> shykArticleSingle(Article article){
		return getArticleSingle(article);
    }
	
    /**
     * 文章查询(all)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePage")
    public Result<Page<Article>> shykArticle(Article article,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 文章查询(all:更多)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePageMore")
    public Result<Page<Article>> shykArticleMore(Article article,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
	
	 /**
     * 文章添加;(文章 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/articleAdd")
    public Result<String> articleAdd(Article article){
		return getArticleAdd(article);
       
    }
	
	 /**
     * 文章更新;(文章 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/articleUpdate")
    public Result<String> articleUpdate(Article article){
		return getArticleUpdate(article);
       
    }

    /**
     * 文章删除;
     * @return Result<String>
     */
	@PostMapping("/articleDel")
    public Result<String> articleDel(Article article){
		return getArticleDel(article);
    }
}
