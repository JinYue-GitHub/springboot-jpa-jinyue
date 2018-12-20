package com.goyo.project.document.issued.web;
import java.util.ArrayList;
import java.util.List;
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
 * @ClassName: DocumentIssuedController 
 * @Description: 文件发布
 */
@RestController
@RequestMapping("/documentIssued")
public class DocumentIssuedController extends BackCommonDataForArticle {
	//*********************************************文件发布************************************************
	 /**
     * 三会一课类型查询 
     * 党员大会
     * 党支部委员会
     * 党小组会
     * 党课
     * @return Result<Article>
     */
//  @RequiresPermissions("article:view")//权限管理;
	@PostMapping("/articleConstant")
    public Result<List<String>> shykArticleConstant(){
		List<String> list = new ArrayList<>();
		list.add(ProjectConstant.PARTY_MEMBER_CONFERENCE);
		list.add(ProjectConstant.PARTY_BRANCH_COMMITTEE);
		list.add(ProjectConstant.PARTY_GROUP_CONFERENCE);
		list.add(ProjectConstant.PARTY_LECTURE);
		return ResultGenerator.genSuccessResult(list);
    }
	 /**
     * 文章添加;
     * @return Result<String>
     */
	@PostMapping("/articleAdd")
//    @RequiresPermissions("userInfo:add")//权限管理;
    public Result<String> shykArticleAdd(Article article){
		return getArticleAdd(article);
    }
		
}
