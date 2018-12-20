package com.goyo.project.problem.web;

import java.util.Date;
import java.util.Optional;
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
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Problem;
import com.goyo.project.model.QuestionnaireSurvey;
import com.goyo.project.problem.dao.ProblemDao;
/**
 * @author: JinYue
 * @ClassName: ProblemController 
 * @Description: 题Controller 
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {
	
	@Resource
	private ProblemDao problemDao; 
	//*********************************************问卷调查************************************************
	 /**
     * 问卷调查查询(single)
     * @return Result<Problem>
     */
//    @RequiresPermissions("problem:view")//权限管理;
	@PostMapping("/problemSingle")
    public Result<Problem> problemSingle(Problem problem){
		Problem problemDB = problemDao.findById(problem.getId()).get();
		problemDB.setOptionList(problemDB.getOptionList().parallelStream().distinct().collect(Collectors.toList()));
		return ResultGenerator.genSuccessResult(problemDB);
    }
	
    /**
     * 问卷调查查询(all)
     * @return Result<Page<Problem>>
     */
	@PostMapping("/problemPage")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<Problem>> problem(Problem problem,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<Problem> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = problemDao.findAll(new Specification<Problem>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate titlep = null;
	                Predicate time = null;
	                String title = problem.getTitle();
					if(StringUtils.isNotEmpty(title)) {
						titlep = cb.like(root.<String> get("title"), "%" + title + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(titlep).isPresent()) query.where(titlep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = problemDao.findAll(new Specification<Problem>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate titlep = null;
	                Predicate time = null;
	                String title = problem.getTitle();
					if(StringUtils.isNotEmpty(title)) {
						titlep = cb.like(root.<String> get("title"), "%" + title + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(titlep).isPresent()) query.where(titlep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = problemDao.findAll(pageable);
		}else{
			return new Result<Page<Problem>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
    }
	 /**
     * 问卷调查查询(all:更多)
     * @return Result<Page<Problem>>
     */
	@PostMapping("/problemPageMore")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<Page<Problem>> problemMore(Problem problem,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<Problem> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = problemDao.findAll(new Specification<Problem>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate titlep = null;
	                Predicate time = null;
	                String title = problem.getTitle();
					if(StringUtils.isNotEmpty(title)) {
						titlep = cb.like(root.<String> get("title"), "%" + title + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(titlep).isPresent()) query.where(titlep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = problemDao.findAll(new Specification<Problem>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate titlep = null;
	                Predicate phoneNumberp = null;
	                Predicate time = null;
	                String title = problem.getTitle();
					if(StringUtils.isNotEmpty(title)) {
						titlep = cb.like(root.<String> get("title"), "%" + title + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(titlep).isPresent()) query.where(titlep);
	                 if(Optional.ofNullable(phoneNumberp).isPresent()) query.where(phoneNumberp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = problemDao.findAll(pageable);
		}else{
			return new Result<Page<Problem>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
    }
    /**
     * 问卷调查添加;
     * @return Result<String>
     */
	@PostMapping("/problemAdd")
//    @RequiresPermissions("userInfo:add")//权限管理;
    public Result<Object> problemAdd(@RequestBody(required=false)Problem problem){
		try {
			
			if(0!=problem.getRoot()){//非根节点执行根节点人数加一
//				problemDao.updatePersonCountPlus(problem.getParentId());
				Problem problemDB = problemDao.findById(problem.getParentId()).get();
				problemDB.setPersonCount(problemDB.getPersonCount()+1);
				problemDao.save(problemDB);
				problemDao.save(problem);
			}else{
				problemDao.save(problem);
			}
			return ResultGenerator.genSuccessResult(problem);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"问卷调查添加失败!");
		}
		
       
    }

    /**
     * 问卷调查删除;
     * @return Result<String>
     */
	@PostMapping("/problemDel")
//    @RequiresPermissions("userInfo:del")//权限管理;
    public Result<String> problemDel(Problem problem){
		try {
			
			if(0!=problem.getRoot()){//非根节点执行根节点人数减一
				Problem problemDB = problemDao.findById(problem.getParentId()).get();
				problemDB.setPersonCount(problemDB.getPersonCount()-1);
				problemDao.save(problemDB);
				problemDao.delete(problem);
			}else{
				problemDao.delete(problem);
			}
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"调查问卷删除失败!");
		}
    }
}
