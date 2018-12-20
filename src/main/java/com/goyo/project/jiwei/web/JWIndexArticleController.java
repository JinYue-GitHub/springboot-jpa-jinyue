package com.goyo.project.jiwei.web;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.ZZBBackCommonDataForArticle;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
/**
 * @author: JinYue
 * @ClassName: IndexArticleController 
 * @Description: 纪委首页管理
 */
@RestController
@RequestMapping("/jwIndexArticle")
public class JWIndexArticleController extends ZZBBackCommonDataForArticle {

	//*********************************************组织部首页管理************************************************
	
	 /**
     * 文章查询(single)
     * @return Result<Article>
     */
//    @RequiresPermissions("article:view")//权限管理;
	@PostMapping("/articleSingle")
    public Result<Article> zzIndexArticleSingle(Article article){
		return getArticleSingle(article);
    }
	
    /**
     * 文章查询(all)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePage")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<Article>> zzIndexArticle(Article article,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getArticlePage(article, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 文章查询(all:更多)
     * @return Result<Page<Article>>
     */
	@PostMapping("/articlePageMore")
    public Result<Page<Article>> zzIndexArticleMore(Article article,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
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
//  @RequiresPermissions("userInfo:del")//权限管理;
    public Result<String> zzIndexArticleDel(Article article){
		return getArticleDel(article);
    }
}
