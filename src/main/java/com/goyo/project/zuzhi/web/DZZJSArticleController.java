package com.goyo.project.zuzhi.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.article.dao.ArticleDao;
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Article;
import com.goyo.project.model.PartyOrganization;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.UserInfoDao;
import com.goyo.project.partyOrganization.management.dao.PartyOrganizationDao;
import com.goyo.project.utils.ArrayList;
/**
 * @author: JinYue
 * @ClassName: DZZJSArticleController 
 * @Description: 党组织建设
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/dzzjs")
public class DZZJSArticleController{
	
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<UserInfo,Integer> baserepository;
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private PartyOrganizationDao partyOrganizationDao;
	@Resource
	private ArticleDao articleDao;
	//R
	//*********************************************党组织建设************************************************
	 /**
     * 当前情况
     * @return Result<Article>
     */
//    @RequiresPermissions("article:view")//权限管理;
	@PostMapping("/findAll")
    public Result<Map<String,Object>> zgdtArticleSingle(PartyOrganization partyOrganization){
//		List<Object> list = new ArrayList<>();
		PartyOrganization partyOrganizationDB = partyOrganizationDao.findById(partyOrganization.getId()).get();
		//简介
		String production = partyOrganizationDB.getProduction();
		//党组成员
		List<UserInfo> partyOrganizationMember = userInfoDao.findByPoliticalAndPartyOrganizationId(ProjectConstant.PARTY_MEMBER, partyOrganization.getId());
		List<String> partyOrganizationMemberNames = partyOrganizationMember.parallelStream().map(u -> u.getName()).collect(Collectors.toList());
		//党组织
		List<PartyOrganization> partyOrganizations = partyOrganizationDao.findByPartyOrganizationIds(partyOrganizationDB.getIds());
		//特色活动
		List<Article> specialEvents = articleDao.findByTypeIdsAndSourceOrganization("组织部/党务管理/特色活动",partyOrganization.getId().toString());
		//党员数量
		int partyMemberSum = Optional.ofNullable(partyOrganizationMember).isPresent()?partyOrganizationMember.stream().mapToInt(u->1).sum():0;
		//党组织数量
		int partyOrganizationSum = Optional.ofNullable(partyOrganizations).isPresent()?partyOrganizations.stream().mapToInt(u->1).sum():0;
		//特色活动数量
		int specialEventSum = Optional.ofNullable(specialEvents).isPresent()?specialEvents.stream().mapToInt(u->1).sum():0;
//		list.add(production);
//		list.add(partyMemberSum);
//		list.add(partyOrganizationSum);
//		list.add(specialEventSum);
//		list.add(partyOrganizationMemberNames);
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("production", production);
		hashMap.put("partyMemberSum", partyMemberSum);
		hashMap.put("partyOrganizationSum", partyOrganizationSum);
		hashMap.put("specialEventSum", specialEventSum);
		hashMap.put("partyOrganizationMemberNames", partyOrganizationMemberNames);
		//会议暂时不知道如何统计，先置0
		hashMap.put("partyOrganizationMemberNames", partyOrganizationMemberNames);
		hashMap.put("meeting",0);
		return ResultGenerator.genSuccessResult(hashMap);
    }
}
