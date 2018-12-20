package com.goyo.project.index.web;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.CommonDataForArticle;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
/**
 * @author: JinYue
 * @ClassName: IndexArticleController 
 * @Description: 首页
 */
@RestController
@RequestMapping("/indexArticle")
public class IndexArticleController extends CommonDataForArticle {
	
	
	//*********************************************重点工作 文件通知 党建论坛 支部党建************************************************
	 /**
     * 文章查询(single)
     * @return Result<Article>
     */
	@PostMapping("/articleSingle")
    public Result<Article> articleSingle(Article article){
		return getArticleSingle(article);
    }
	
    /**
     * 文章查询(all)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePage")
    public Result<Page<Article>> article(Article article,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 文章查询(all:更多)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePageMore")
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
