package com.goyo.project.problem.option.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Problem;
import com.goyo.project.model.ProblemOption;
import com.goyo.project.problem.option.dao.ProblemOptionDao;
/**
 * @author: JinYue
 * @ClassName: ProblemOptionController 
 * @Description: 题选项Controller 
 */
@RestController
@RequestMapping("/problemOption")
public class ProblemOptionController {
	
	private static List<String> list = Lists.newArrayList
			(
					ProjectConstant.A,
					ProjectConstant.B,
					ProjectConstant.C,
					ProjectConstant.D,
					ProjectConstant.E,
					ProjectConstant.F,
					ProjectConstant.G,
					ProjectConstant.H,
					ProjectConstant.I,
					ProjectConstant.J,
					ProjectConstant.K,
					ProjectConstant.L,
					ProjectConstant.M,
					ProjectConstant.N,
					ProjectConstant.O,
					ProjectConstant.P,
					ProjectConstant.Q,
					ProjectConstant.R,
					ProjectConstant.S,
					ProjectConstant.T,
					ProjectConstant.U,
					ProjectConstant.V,
					ProjectConstant.W,
					ProjectConstant.X,
					ProjectConstant.Y,
					ProjectConstant.Z
			);
	
	
	
	@Resource
	private ProblemOptionDao problemOptionDao; 
	//*********************************************题选项************************************************
	
	 /**
     * 返回选项名称列表(all,不包含选项值)
     * @return Result<ProblemOption>
     */
	@PostMapping("/problemOptionList")
    public  Result<List<String>> problemOptionList(){
		return ResultGenerator.genSuccessResult(getList());
    }
	
	 /**
     * 返回下一个选项名(不包含选项值)
     * @return Result<ProblemOption>
     */
	@PostMapping("/nextProblemOption")
    public Result<Object> nextProblemOption(ProblemOption problemOption){//前台把当前的选项名(例如：D)返给我，我返回下一个选项名(例如：E)
		if(StringUtils.isNotEmpty(problemOption.getName())){
			try {
				return ResultGenerator.genSuccessResult(getList().get(getList().indexOf(problemOption.getName())+1));
			} catch (Exception e) {
				e.printStackTrace();
				return ResultGenerator.genFailResultToObject(ProjectConstant.THERE_IS_NO_EXT_OPTION);
			}
		}else{
			return ResultGenerator.genFailResultToObject(ProjectConstant.PLEASE_PASS_IN_THE_OPTION_NAME);
		}
		
		
    }
	
	
	
	 /**
     * 题选项查询(single)
     * @return Result<ProblemOption>
     */
	@PostMapping("/problemOptionSingle")
    public Result<ProblemOption> problemOptionSingle(ProblemOption problemOption){
		return ResultGenerator.genSuccessResult(problemOptionDao.findById(problemOption.getId()).get());
    }
	
    /**
     * 题选项查询(all)
     * @return Result<Page<ProblemOption>>
     */
	@PostMapping("/problemOptionPage")
    public Result<Page<ProblemOption>> problemOption(ProblemOption problemOption,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<ProblemOption> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = problemOptionDao.findAll(new Specification<ProblemOption>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<ProblemOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate time = null;
	                String name = problemOption.getName();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = problemOptionDao.findAll(new Specification<ProblemOption>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<ProblemOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate time = null;
	                String name = problemOption.getName();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("currentAccountName"), "%" + name + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = problemOptionDao.findAll(pageable);
		}else{
			return new Result<Page<ProblemOption>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage(ProjectConstant.PAGING_QUERY_FAILURE);
		}
		return ResultGenerator.genSuccessResult(page);
    }
	 /**
     * 题选项查询(all:更多)
     * @return Result<Page<ProblemOption>>
     */
	@PostMapping("/problemOptionPageMore")
    public Result<Page<ProblemOption>> problemOptionMore(ProblemOption problemOption,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<ProblemOption> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = problemOptionDao.findAll(new Specification<ProblemOption>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<ProblemOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate time = null;
	                String name = problemOption.getName();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("currentAccountName"), "%" + name + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = problemOptionDao.findAll(new Specification<ProblemOption>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<ProblemOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate phoneNumberp = null;
	                Predicate time = null;
	                String name = problemOption.getName();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(phoneNumberp).isPresent()) query.where(phoneNumberp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = problemOptionDao.findAll(pageable);
		}else{
			return new Result<Page<ProblemOption>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage(ProjectConstant.PAGING_QUERY_FAILURE);
		}
		return ResultGenerator.genSuccessResult(page);
    }
    /**
     * 题选项添加;
     * @return Result<String>
     */
	@Transactional
	@PostMapping("/problemOptionAdd")
    public Result<Object> problemOptionAdd(@RequestBody(required=false)ProblemOption problemOption){
		try {
			
			if(0!=problemOption.getRoot()){//非根节点执行根节点人数加一
				ProblemOption problemOptionDB = problemOptionDao.findById(problemOption.getParentId()).get();
				problemOptionDB.setPersonCount(problemOptionDB.getPersonCount()+1);
				problemOptionDao.save(problemOptionDB);
				problemOptionDao.save(problemOption);
			}else{
				problemOptionDao.save(problemOption);
			}
			return ResultGenerator.genSuccessResult(problemOption);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"题选项添加失败!");
		}
		
       
    }

    /**
     * 题选项删除;
     * @return Result<String>
     */
	@PostMapping("/problemOptionDel")
    public Result<String> problemOptionDel(ProblemOption problemOption){
		try {
			
			if(0!=problemOption.getRoot()){//非根节点执行根节点人数减一
				ProblemOption problemOptionDB = problemOptionDao.findById(problemOption.getParentId()).get();
				problemOptionDB.setPersonCount(problemOptionDB.getPersonCount()-1);
				problemOptionDao.save(problemOptionDB);
				problemOptionDao.delete(problemOption);
			}else{
				problemOptionDao.delete(problemOption);
			}
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genSuccessResult(e.getMessage()+"题选项删除失败!");
		}
    }

	public static List<String> getList() {
		return list;
	}

	public static void setList(List<String> list) {
		ProblemOptionController.list = list;
	}
}
