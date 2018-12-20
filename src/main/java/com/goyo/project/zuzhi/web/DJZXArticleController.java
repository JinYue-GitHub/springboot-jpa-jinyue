package com.goyo.project.zuzhi.web;

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
 * @ClassName: DJZXArticleController 
 * @Description: 党建资讯 	党建咨询的子模块未知,先按廉政建设的子模块写了，确定了子模块是啥再改
 */
@RestController
@RequestMapping("/djzxArticle")
public class DJZXArticleController extends CommonDataForArticle {
	 //按类别查询
	//*********************************************工作动态 作风监督 廉政教育 政策法规************************************************
	 /**
     * 文章查询(single)
     * @return Result<Article>
     */
	@PostMapping("/articleSingle")
    public Result<Article> zgdtArticleSingle(Article article){
		return getArticleSingle(article);
    }
	
    /**
     * 文章查询(all) 
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePage")
    public Result<Page<Article>> zgdtArticle(Article article,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 文章查询(all:更多)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePageMore")
    public Result<Page<Article>> zgdtArticleMore(Article article,String pageNum,String pageSize,
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
