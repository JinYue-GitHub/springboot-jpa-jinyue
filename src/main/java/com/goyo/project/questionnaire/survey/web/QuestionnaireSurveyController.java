package com.goyo.project.questionnaire.survey.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.QuestionnaireSurvey;
import com.goyo.project.organization.management.dao.OrganizationDao;
import com.goyo.project.problem.option.dao.ProblemOptionDao;
import com.goyo.project.questionnaire.survey.dao.QuestionnaireSurveyDao;


/**
 * @author: JinYue
 * @ClassName: QuestionnaireSurveyController 
 * @Description: 问卷调查
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/questionnaireSurvey")
public class QuestionnaireSurveyController {
	
	@Resource
	private BaseRepository<QuestionnaireSurvey, String> baseRepository;
	
	@Resource
	private QuestionnaireSurveyDao questionnaireSurveyDao; 
	@Resource
	private ProblemOptionDao problemOptionDao; 
	
	@Resource
	private OrganizationDao organizationDao; 
	
	//*********************************************问卷调查************************************************
	 /**
     * 问卷调查查询(single)
     * @return Result<QuestionnaireSurvey>
     */
//    @RequiresPermissions("questionnaireSurvey:view")//权限管理;
	@PostMapping("/questionnaireSurveySingle")
    public Result<QuestionnaireSurvey> questionnaireSurveySingle(QuestionnaireSurvey questionnaireSurvey){
		QuestionnaireSurvey questionnaireSurveyDB = questionnaireSurveyDao.findById(questionnaireSurvey.getId()).get();
		questionnaireSurveyDB.setProblemList(questionnaireSurveyDB.getProblemList().parallelStream().distinct().collect(Collectors.toList()));
		return ResultGenerator.genSuccessResult(questionnaireSurveyDB);
    }
	
    /**
     * 问卷调查查询(all)
     * @return Result<Page<QuestionnaireSurvey>>
     */
	@PostMapping("/questionnaireSurveyPage")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<QuestionnaireSurvey>> questionnaireSurvey(QuestionnaireSurvey questionnaireSurvey,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime,
			String flag){
		Page<QuestionnaireSurvey> page = null;
		if(StringUtils.isEmpty(flag)){
			Pageable pageable = null;
			if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
				int pageNumInt = Integer.parseInt(pageNum);
				pageNumInt-=1;
				int pageSizeInt = Integer.parseInt(pageSize);
				pageable = new PageRequest(pageNumInt,pageSizeInt);
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
//							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         }, pageable);
			}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
//							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         },pageable);
			}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
//							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         },Pageable.unpaged());
			}else{
				return new Result<Page<QuestionnaireSurvey>>()
		                .setCode(ResultCode.FAIL)
		                .setMessage("分页查询失败!");
			}
		}else if(StringUtils.equals("1", flag)){
			if(Optional.ofNullable(questionnaireSurvey).isPresent()&&Optional.ofNullable(questionnaireSurvey.getUid()).isPresent()){
				LinkedList<String> listIds = questionnaireSurveyDao.findIdByRoot((byte)0);
				Set<String> setParentIds = questionnaireSurveyDao.findParentIdByRootAndUid((byte)1,questionnaireSurvey.getUid());
				//当前账号未填写的问卷id
				listIds.removeAll(setParentIds);
				List<String> list = new ArrayList<>();
				list.addAll(listIds);
				Pageable pageable = null;
				if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
					int pageNumInt = Integer.parseInt(pageNum);
					pageNumInt-=1;
					int pageSizeInt = Integer.parseInt(pageSize);
					pageable = new PageRequest(pageNumInt,pageSizeInt);
					page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
						
						private static final long serialVersionUID = 3312137508342580834L;

						@Override
			            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			                
							Predicate idp = null;
							Predicate uidRootFillStatusp = null;
			                Predicate uidp = null;
			                Predicate rootp0 = null;
			                Predicate fillStatusp0 = null;
			                Predicate organizationIdp = null;
			                Predicate time = null;
			                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
			                boolean idPresent = false;
			                boolean uidPresent = false;
			                boolean rootPresent = false;
			                boolean fillStatusPresent = false;
			                boolean organizationPresent = false;
			                boolean organizationIdpresent = false;
			                if(qsPresent){
			                	idPresent = Optional.ofNullable(questionnaireSurvey.getId()).isPresent();
			                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
			                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
			                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
			                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
			                	if(organizationPresent){
			                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
			                	}
			                }
			                
			                if(qsPresent) {
								idp = root.<String>get("id").in(list);
			                }
							if(qsPresent&&uidPresent) {
								uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
			                }
							if(qsPresent&&rootPresent) {
								rootp0 = cb.equal(root.<Byte> get("root"), (byte) 0 );
			                }
							if(qsPresent&&fillStatusPresent) {
								fillStatusp0 = cb.equal(root.<Byte> get("fillStatus"), (byte) 0  );
			                }
							if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
								uidRootFillStatusp = cb.and(uidp,rootp0,fillStatusp0);
			                }
							if(qsPresent&&organizationPresent&&organizationIdpresent){
								organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
							}
							if(Optional.ofNullable(startTime).isPresent() && 
								Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
			                }else if(Optional.ofNullable(startTime).isPresent()){
			                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
			                }else if(Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
			                }
//			                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//			                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//			                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//			                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
							if(Optional.ofNullable(idp).isPresent()) query.where(idp);
			                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
			                if(Optional.ofNullable(time).isPresent()) query.where(time);
			                return null;
			             }
			         }, pageable);
				}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
					page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
						
						private static final long serialVersionUID = 3312137508342580834L;

						@Override
			            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			                
							Predicate idp = null;
							Predicate uidRootFillStatusp = null;
			                Predicate uidp = null;
			                Predicate rootp0 = null;
			                Predicate fillStatusp0 = null;
			                Predicate organizationIdp = null;
			                Predicate time = null;
			                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
			                boolean idPresent = false;
			                boolean uidPresent = false;
			                boolean rootPresent = false;
			                boolean fillStatusPresent = false;
			                boolean organizationPresent = false;
			                boolean organizationIdpresent = false;
			                if(qsPresent){
			                	idPresent = Optional.ofNullable(questionnaireSurvey.getId()).isPresent();
			                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
			                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
			                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
			                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
			                	if(organizationPresent){
			                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
			                	}
			                }
			                
			                if(qsPresent&&idPresent) {
								idp = root.<String>get("id").in(list);
			                }
							if(qsPresent&&uidPresent) {
								uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
			                }
							if(qsPresent&&rootPresent) {
								rootp0 = cb.equal(root.<Byte> get("root"), (byte) 0 );
			                }
							if(qsPresent&&fillStatusPresent) {
								fillStatusp0 = cb.equal(root.<Byte> get("fillStatus"), (byte) 0  );
			                }
							if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
								uidRootFillStatusp = cb.and(uidp,rootp0,fillStatusp0);
			                }
							if(qsPresent&&organizationPresent&&organizationIdpresent){
								organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
							}
							if(Optional.ofNullable(startTime).isPresent() && 
								Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
			                }else if(Optional.ofNullable(startTime).isPresent()){
			                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
			                }else if(Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
			                }
//			                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//			                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//			                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//			                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
							if(Optional.ofNullable(idp).isPresent()) query.where(idp);
			                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
			                if(Optional.ofNullable(time).isPresent()) query.where(time);
			                return null;
			             }
			         },pageable);
				}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
					page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
						
						private static final long serialVersionUID = 3312137508342580834L;

						@Override
			            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			                
							Predicate idp = null;
							Predicate uidRootFillStatusp = null;
			                Predicate uidp = null;
			                Predicate rootp0 = null;
			                Predicate fillStatusp0 = null;
			                Predicate organizationIdp = null;
			                Predicate time = null;
			                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
			                boolean idPresent = false;
			                boolean uidPresent = false;
			                boolean rootPresent = false;
			                boolean fillStatusPresent = false;
			                boolean organizationPresent = false;
			                boolean organizationIdpresent = false;
			                if(qsPresent){
			                	idPresent = Optional.ofNullable(questionnaireSurvey.getId()).isPresent();
			                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
			                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
			                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
			                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
			                	if(organizationPresent){
			                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
			                	}
			                }
			                
			                if(qsPresent&&idPresent) {
								idp = root.<String>get("id").in(list);
			                }
							if(qsPresent&&uidPresent) {
								uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
			                }
							if(qsPresent&&rootPresent) {
								rootp0 = cb.equal(root.<Byte> get("root"), (byte) 0 );
			                }
							if(qsPresent&&fillStatusPresent) {
								fillStatusp0 = cb.equal(root.<Byte> get("fillStatus"), (byte) 0  );
			                }
							if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
								uidRootFillStatusp = cb.and(uidp,rootp0,fillStatusp0);
			                }
							if(qsPresent&&organizationPresent&&organizationIdpresent){
								organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
							}
							if(Optional.ofNullable(startTime).isPresent() && 
								Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
			                }else if(Optional.ofNullable(startTime).isPresent()){
			                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
			                }else if(Optional.ofNullable(endTime).isPresent()) {
			                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
			                }
//			                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//			                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//			                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
//			                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
							if(Optional.ofNullable(idp).isPresent()) query.where(idp);
			                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
			                if(Optional.ofNullable(time).isPresent()) query.where(time);
			                return null;
			             }
			         },Pageable.unpaged());
				}else{
					return new Result<Page<QuestionnaireSurvey>>()
			                .setCode(ResultCode.FAIL)
			                .setMessage("分页查询失败!");
				}
			}
		}
		return ResultGenerator.genSuccessResult(page);
    }
	
	 /**
     * 问卷调查查询(all)
     * @return Result<Page<QuestionnaireSurvey>>
     */
    private Result<Page<QuestionnaireSurvey>> questionnaireSurveyDB(QuestionnaireSurvey questionnaireSurvey,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
			Page<QuestionnaireSurvey> page = null;
			Pageable pageable = null;
			if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
				int pageNumInt = Integer.parseInt(pageNum);
				pageNumInt-=1;
				int pageSizeInt = Integer.parseInt(pageSize);
				pageable = new PageRequest(pageNumInt,pageSizeInt);
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						
						Predicate rootFillStatusp = null;
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&fillStatusPresent&&rootPresent) {
							rootFillStatusp = cb.and(rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(rootFillStatusp).isPresent()) query.where(rootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         }, pageable);
			}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                
						Predicate rootFillStatusp = null;
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
//							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&fillStatusPresent&&rootPresent) {
							rootFillStatusp = cb.and(rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(rootFillStatusp).isPresent()) query.where(rootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         },pageable);
			}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
				page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
					
					private static final long serialVersionUID = 3312137508342580834L;

					@Override
		            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                
						Predicate rootFillStatusp = null;
						Predicate uidRootFillStatusp = null;
		                Predicate uidp = null;
		                Predicate rootp = null;
		                Predicate fillStatusp = null;
		                Predicate organizationIdp = null;
		                Predicate time = null;
		                boolean qsPresent = Optional.ofNullable(questionnaireSurvey).isPresent();
		                boolean uidPresent = false;
		                boolean rootPresent = false;
		                boolean fillStatusPresent = false;
		                boolean organizationPresent = false;
		                boolean organizationIdpresent = false;
		                if(qsPresent){
		                	uidPresent = Optional.ofNullable(questionnaireSurvey.getUid()).isPresent();
		                	rootPresent = Optional.ofNullable(questionnaireSurvey.getRoot()).isPresent();
		                	fillStatusPresent = Optional.ofNullable(questionnaireSurvey.getFillStatus()).isPresent();
		                	organizationPresent = Optional.ofNullable(questionnaireSurvey.getOrganization()).isPresent();
		                	if(organizationPresent){
		                		organizationIdpresent = Optional.ofNullable(questionnaireSurvey.getOrganization().getId()).isPresent();
		                	}
		                }
		                
						if(qsPresent&&uidPresent) {
							uidp = cb.equal(root.<Integer> get("uid"), questionnaireSurvey.getUid() );
		                }
						if(qsPresent&&rootPresent) {
							rootp = cb.equal(root.<Byte> get("root"), questionnaireSurvey.getRoot() );
		                }
						if(qsPresent&&fillStatusPresent) {
							fillStatusp = cb.equal(root.<Byte> get("fillStatus"), questionnaireSurvey.getFillStatus() );
		                }
						if(qsPresent&&uidPresent&&fillStatusPresent&&rootPresent) {
							uidRootFillStatusp = cb.and(uidp,rootp,fillStatusp);
		                }
						if(qsPresent&&fillStatusPresent&&rootPresent) {
							rootFillStatusp = cb.and(rootp,fillStatusp);
		                }
						if(qsPresent&&organizationPresent&&organizationIdpresent){
							organizationIdp = cb.equal(root.<Organization> get("organization").<Integer> get("id"),questionnaireSurvey.getOrganization().getId());
						}
						if(Optional.ofNullable(startTime).isPresent() && 
							Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
		                }else if(Optional.ofNullable(startTime).isPresent()){
		                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
		                }else if(Optional.ofNullable(endTime).isPresent()) {
		                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
		                }
//		                if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
//		                if(Optional.ofNullable(rootp).isPresent()) query.where(rootp);
//		                if(Optional.ofNullable(fillStatusp).isPresent()) query.where(fillStatusp);
		                if(Optional.ofNullable(uidRootFillStatusp).isPresent()) query.where(uidRootFillStatusp);
		                if(Optional.ofNullable(rootFillStatusp).isPresent()) query.where(rootFillStatusp);
		                if(Optional.ofNullable(organizationIdp).isPresent()) query.where(organizationIdp);
		                if(Optional.ofNullable(time).isPresent()) query.where(time);
		                return null;
		             }
		         },Pageable.unpaged());
			}else{
				return new Result<Page<QuestionnaireSurvey>>()
		                .setCode(ResultCode.FAIL)
		                .setMessage("分页查询失败!");
			}
		return ResultGenerator.genSuccessResult(page);
    }
	 /**
     * 问卷调查查询(all:更多)
     * @return Result<Page<QuestionnaireSurvey>>
     */
	@PostMapping("/questionnaireSurveyPageMore")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<QuestionnaireSurvey>> questionnaireSurveyMore(QuestionnaireSurvey questionnaireSurvey,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<QuestionnaireSurvey> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate uidp = null;
	                Predicate time = null;
	                Integer uid = questionnaireSurvey.getUid();
					if(0!=uid) {
						uidp = cb.equal(root.<Integer> get("uid"), "%" + uid + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = questionnaireSurveyDao.findAll(new Specification<QuestionnaireSurvey>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<QuestionnaireSurvey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate uidp = null;
	                Predicate time = null;
	                Integer uid = questionnaireSurvey.getUid();
					if(0!=uid) {
						uidp = cb.equal(root.<Integer> get("uid"), "%" + uid + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(uidp).isPresent()) query.where(uidp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = questionnaireSurveyDao.findAll(Pageable.unpaged());
		}else{
			return new Result<Page<QuestionnaireSurvey>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
    }
    /**
     * 问卷调查添加;
     * @return Result<String>
     */
	@PostMapping("/questionnaireSurveyAdd")
//    @RequiresPermissions("userInfo:add")//权限管理;
    public Result<Object> questionnaireSurveyAdd(@RequestBody(required=false) QuestionnaireSurvey questionnaireSurvey){
		try {
			
			if(0!=questionnaireSurvey.getRoot()){//非根节点执行根节点人数加一
				QuestionnaireSurvey questionnaireSurveyDB = questionnaireSurveyDao.findById(questionnaireSurvey.getParentId()).get();
				questionnaireSurveyDB.setPersonCount(questionnaireSurveyDB.getPersonCount()+1);
				questionnaireSurveyDao.save(questionnaireSurveyDB);
				questionnaireSurveyDao.save(questionnaireSurvey);
//				String sql  = " UPDATE QUESTIONNAIRE_SURVEY T SET T.PERSON_COUNT=(T.PERSON_COUNT+1) WHERE T.ID = '297e9a0164cf37120164cf38189d0001' ";
//				questionnaireSurveyDao.updatePersonCountPlus(questionnaireSurvey.getParentId());
//				baseRepository.excuteSql(sql);
			}else{
				questionnaireSurveyDao.save(questionnaireSurvey);
			}
			return ResultGenerator.genSuccessResult(questionnaireSurvey);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"问卷调查添加失败!");
		}
		
       
    }

    /**
     * 问卷调查删除;
     * @return Result<String>
     */
	@PostMapping("/questionnaireSurveyDel")
//    @RequiresPermissions("userInfo:del")//权限管理;
    public Result<String> questionnaireSurveyDel(QuestionnaireSurvey questionnaireSurvey){
		try {
			
			if(0!=questionnaireSurvey.getRoot()){//非根节点执行根节点人数减一
				QuestionnaireSurvey questionnaireSurveyDB = questionnaireSurveyDao.findById(questionnaireSurvey.getParentId()).get();
				questionnaireSurveyDB.setPersonCount(questionnaireSurveyDB.getPersonCount()-1);
				questionnaireSurveyDao.save(questionnaireSurveyDB);
				questionnaireSurveyDao.delete(questionnaireSurvey);
			}else{
				questionnaireSurveyDao.delete(questionnaireSurvey);
			}
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"调查问卷删除失败!");
		}
    }
}
