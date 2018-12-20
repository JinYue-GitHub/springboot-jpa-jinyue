package com.goyo.project.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
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
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.PetitionReport;
import com.goyo.project.petition.report.dao.PetitionReportDao;

public class CommonDataForPetitionReport {

	//*********************************************信访举报-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<PetitionReport,String> baserepository;
	
	@Resource
	private PetitionReportDao petitionReportDao;
	

	public CommonDataForPetitionReport() {
		super();
	}

	/**
	 * 获取PetitionReport单个对象
	 * @param petitionReport
	 * @return Result<PetitionReport>
	 */
	protected Result<PetitionReport> getPetitionReportSingle(PetitionReport petitionReport) {
		return ResultGenerator.genSuccessResult(baserepository.findById(petitionReport, petitionReport.getId()));
	}

	/**
	 * 获取信访举报分页对象
	 * @param petitionReport
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<PetitionReport>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<PetitionReport>> getPetitionReportPage(PetitionReport petitionReport, String pageNum, String pageSize) {
		Page<PetitionReport> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(petitionReport.getPageNum())&&StringUtils.isNoneBlank(petitionReport.getPageSize())){
			int pageNumInt = Integer.parseInt(petitionReport.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(petitionReport.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<PetitionReport>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<PetitionReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                String name = petitionReport.getName();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = petitionReport.getOrganization();
					if(null != petitionReport && !StringUtils.isEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(null != petitionReport && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
	                 if(null != namep) query.where(namep);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, PetitionReport.class);
		}else if(StringUtils.isBlank(petitionReport.getPageNum())&&StringUtils.isBlank(petitionReport.getPageSize())){
			page = baserepository.findAll(pageable,PetitionReport.class);
		}else{
			return new Result<Page<PetitionReport>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 获取信访举报分页对象
	 * @param petitionReport
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<PetitionReport>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<PetitionReport>> getPetitionReportPage(PetitionReport petitionReport, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<PetitionReport> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(petitionReport.getPageNum())&&StringUtils.isNoneBlank(petitionReport.getPageSize())){
			int pageNumInt = Integer.parseInt(petitionReport.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(petitionReport.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<PetitionReport>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<PetitionReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = petitionReport.getName();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = petitionReport.getOrganization();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable, PetitionReport.class);
		}else if((StringUtils.isBlank(petitionReport.getPageNum())||StringUtils.isBlank(petitionReport.getPageSize())) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<PetitionReport>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<PetitionReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = petitionReport.getName();
//	                String organization = petitionReport.getOrganization();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable,PetitionReport.class);
		}else if(StringUtils.isBlank(petitionReport.getPageNum()) && StringUtils.isBlank(petitionReport.getPageSize()) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,PetitionReport.class);
		}else{
			return new Result<Page<PetitionReport>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 信访举报添加
	 * @param petitionReport
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportAdd(PetitionReport petitionReport) {
		
		try {
			baserepository.save(petitionReport);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	/**
	 * 信访举报修改
	 * @param petitionReport
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportUpdate(PetitionReport petitionReport) {
		
		try {
			baserepository.update(petitionReport);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 信访举报删除
	 * @param petitionReport
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportDel(PetitionReport petitionReport) {
		try {
			baserepository.delete(petitionReport);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	

}